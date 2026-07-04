/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.test.generator.junit.parameterized;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.generator.internal.RandomContext;
import de.cuioss.test.generator.junit.EnableGeneratorController;
import de.cuioss.test.generator.junit.GeneratorSeed;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.commons.JUnitException;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.stream.Stream;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link AbstractTypedGeneratorArgumentsProvider}.
 */
@EnableGeneratorController
class AbstractTypedGeneratorArgumentsProviderTest {

    private static final long TEST_SEED = 42L;

    @Test
    @DisplayName("Should create generator instance correctly")
    void shouldCreateGeneratorInstance() {
        var provider = new TestProvider(TEST_SEED, 1);
        var generator = provider.createGeneratorInstancePublic(TestGenerator.class);
        assertNotNull(generator);
    }

    @Test
    @DisplayName("Should throw exception when creating invalid generator")
    void shouldThrowExceptionForInvalidGenerator() {
        var provider = new TestProvider(TEST_SEED, 1);
        assertThrows(JUnitException.class, () -> provider.createGeneratorInstancePublic(InvalidGenerator.class));
    }

    static Stream<Arguments> methodTestCases() {
        return Stream.of(
                // methodName, expectedFound
                Arguments.of("createGenerator", true),
                Arguments.of("nonExistentMethod", false),
                Arguments.of("wrongReturnType", false)
        );
    }

    @ParameterizedTest(name = "Finding method {0} should return {1}")
    @MethodSource("methodTestCases")
    @DisplayName("Should correctly find or not find methods")
    void shouldFindMethodCorrectly(String methodName, boolean expectedFound) {
        var provider = new TestProvider(TEST_SEED, 1);
        var methodOpt = provider.findMethodPublic(TestClass.class, methodName);

        assertNotNull(methodOpt);
        assertEquals(expectedFound, methodOpt.isPresent());
    }

    @Test
    @DisplayName("Should prefer an explicit source-level seed over any annotation")
    void shouldPreferExplicitSourceSeed() {
        var provider = new TestProvider(TEST_SEED, 1);
        assertEquals(TEST_SEED, provider.determineSeed(null));
        assertEquals(OptionalLong.of(TEST_SEED), provider.resolveExplicitSeed(null));
    }

    @Test
    @DisplayName("Should resolve seed from @GeneratorSeed on the test method")
    void shouldResolveSeedFromMethodAnnotation() throws Exception {
        var provider = new TestProvider(-1L, 1);
        var method = TestClass.class.getMethod("methodWithSeed");
        ExtensionContext context = createMock(ExtensionContext.class);
        expect(context.getElement()).andReturn(Optional.of((AnnotatedElement) method)).anyTimes();
        replay(context);

        assertEquals(OptionalLong.of(123L), provider.resolveExplicitSeed(context));
        verify(context);
    }

    @Test
    @DisplayName("Should resolve seed from class-level @GeneratorSeed when method has none")
    void shouldResolveSeedFromClassAnnotation() throws Exception {
        var provider = new TestProvider(-1L, 1);
        var method = ClassWithSeed.class.getMethod("methodWithoutSeed");
        ExtensionContext context = createMock(ExtensionContext.class);
        expect(context.getElement()).andReturn(Optional.of((AnnotatedElement) method)).anyTimes();
        replay(context);

        assertEquals(OptionalLong.of(456L), provider.resolveExplicitSeed(context));
        verify(context);
    }

    @Test
    @DisplayName("Should report no explicit seed and fall back to the global seed when unannotated")
    void shouldFallBackToGlobalSeedWhenNoAnnotation() throws Exception {
        var provider = new TestProvider(-1L, 1);
        var method = TestClass.class.getMethod("wrongReturnType");
        ExtensionContext context = createMock(ExtensionContext.class);
        expect(context.getElement()).andReturn(Optional.of((AnnotatedElement) method)).anyTimes();
        replay(context);

        assertEquals(OptionalLong.empty(), provider.resolveExplicitSeed(context));
        RandomContext.setSeed(987654321L);
        assertEquals(987654321L, provider.determineSeed(context));
        verify(context);
    }

    @Test
    @DisplayName("Should re-apply an explicit seed even when it equals the current global seed")
    void shouldReapplyExplicitSeedEqualToLastSeed() throws Exception {
        long seed = 987654321L;
        var generator = Generators.integers(Integer.MIN_VALUE, Integer.MAX_VALUE);

        // Baseline: three values from the very start of the seed's stream
        RandomContext.setSeed(seed);
        var baseline = List.of(generator.next(), generator.next(), generator.next());

        // Advance the shared RNG mid-stream while lastSeed still equals the explicit seed
        RandomContext.setSeed(seed);
        RandomContext.random().nextInt();
        assertEquals(seed, RandomContext.getLastSeed());

        var provider = new GeneratingProvider(seed, 3, generator);
        var produced = provider.provideArguments(null, null).map(a -> a.get()[0]).toList();

        assertEquals(baseline, produced,
                "Explicit seed must be re-applied so arguments do not depend on prior consumption");
    }

    @ParameterizedTest(name = "With count={0}, should generate {0} arguments")
    @ValueSource(ints = {1, 3, 5, 10})
    @DisplayName("Should generate correct number of arguments")
    void shouldGenerateCorrectNumberOfArguments(int count) {
        var provider = new TestProvider(TEST_SEED, count);
        var generator = Generators.strings(5, 10);
        var arguments = provider.generateArgumentsPublic(generator);

        assertNotNull(arguments);
        assertEquals(count, arguments.size());

        for (var argument : arguments) {
            assertNotNull(argument);
            Object[] args = argument.get();
            assertEquals(1, args.length);
            assertNotNull(args[0]);
        }
    }

    /**
     * Test implementation of AbstractTypedGeneratorArgumentsProvider.
     */
    private static class TestProvider extends AbstractTypedGeneratorArgumentsProvider {
        private final long seed;
        private final int count;

        TestProvider(long seed, int count) {
            this.seed = seed;
            this.count = count;
        }

        @Override
        protected Stream<? extends Arguments> provideArgumentsForGenerators(ExtensionContext context) {
            return Stream.empty();
        }

        @Override
        protected long getSeed() {
            return seed;
        }

        @Override
        protected int getCount() {
            return count;
        }

        // Public wrappers for protected methods to enable testing

        public TypedGenerator<?> createGeneratorInstancePublic(Class<? extends TypedGenerator<?>> generatorClass) {
            return createGeneratorInstance(generatorClass);
        }

        public Optional<Method> findMethodPublic(Class<?> clazz, String methodName) {
            return findMethod(clazz, methodName);
        }

        public List<Arguments> generateArgumentsPublic(TypedGenerator<?> generator) {
            return generateArguments(generator);
        }
    }

    /**
     * Provider that actually generates arguments from a supplied generator, used to
     * observe seed application end-to-end.
     */
    private static class GeneratingProvider extends AbstractTypedGeneratorArgumentsProvider {
        private final long seed;
        private final int count;
        private final TypedGenerator<?> generator;

        GeneratingProvider(long seed, int count, TypedGenerator<?> generator) {
            this.seed = seed;
            this.count = count;
            this.generator = generator;
        }

        @Override
        protected Stream<? extends Arguments> provideArgumentsForGenerators(ExtensionContext context) {
            return generateArguments(generator).stream();
        }

        @Override
        protected long getSeed() {
            return seed;
        }

        @Override
        protected int getCount() {
            return count;
        }
    }

    /**
     * Valid test generator.
     */
    public static class TestGenerator implements TypedGenerator<String> {
        @Override
        public String next() {
            return "test";
        }

        @Override
        public Class<String> getType() {
            return String.class;
        }
    }

    /**
     * Invalid generator without a no-args constructor.
     */
    public static class InvalidGenerator implements TypedGenerator<String> {
        private final String value;

        public InvalidGenerator(String value) {
            this.value = value;
        }

        @Override
        public String next() {
            return value;
        }

        @Override
        public Class<String> getType() {
            return String.class;
        }
    }

    /**
     * Test class with methods for testing method finding.
     */
    public static class TestClass {
        public static TypedGenerator<String> createGenerator() {
            return Generators.strings(5, 10);
        }

        public static String wrongReturnType() {
            return "not a generator";
        }

        @GeneratorSeed(123L)
        public static void methodWithSeed() {
            // Method with seed annotation for testing
        }
    }

    /**
     * Test class with class-level seed annotation.
     */
    @GeneratorSeed(456L)
    public static class ClassWithSeed {
        public static void methodWithoutSeed() {
            // Method in a class with seed annotation
        }
    }
}
