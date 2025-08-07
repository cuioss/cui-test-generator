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
import de.cuioss.test.generator.junit.EnableGeneratorController;
import de.cuioss.test.generator.junit.GeneratorSeed;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.JUnitException;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
    @DisplayName("Should determine seed from annotation")
    void shouldDetermineSeedFromAnnotation() {
        var provider = new TestProvider(TEST_SEED, 1);
        assertEquals(TEST_SEED, provider.determineSeed(null));
    }

    @ParameterizedTest(name = "With count={0}, should generate {0} arguments")
    @CsvSource({"1", "3", "5", "10"})
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
