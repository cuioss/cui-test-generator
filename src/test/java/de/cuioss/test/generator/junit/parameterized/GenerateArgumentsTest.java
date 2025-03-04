/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
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
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Additional tests for the generateArguments method in {@link AbstractTypedGeneratorArgumentsProvider}.
 */
@EnableGeneratorController
@GeneratorSeed(4419141497282950341L)
class GenerateArgumentsTest {

    private static final long TEST_SEED = 42L;

    @ParameterizedTest(name = "With count={0}, should generate {0} arguments")
    @ValueSource(ints = {1, 5, 10})
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

    @Test
    @DisplayName("Should handle zero count")
    void shouldHandleZeroCount() {
        var provider = new TestProvider(TEST_SEED, 0);
        var generator = Generators.strings(5, 10);
        var arguments = provider.generateArgumentsPublic(generator);

        assertNotNull(arguments);
        assertEquals(0, arguments.size());
    }

    @Test
    @DisplayName("Should generate arguments with consistent seed")
    void shouldGenerateArgumentsWithConsistentSeed() {
        // Use a fixed generator that always returns the same value
        var provider = new TestProvider(TEST_SEED, 3);
        var generator = new FixedStringGenerator("test-value");

        var arguments1 = provider.generateArgumentsPublic(generator);
        var arguments2 = provider.generateArgumentsPublic(generator);

        assertNotNull(arguments1);
        assertNotNull(arguments2);
        assertEquals(3, arguments1.size());
        assertEquals(3, arguments2.size());

        // Compare each argument
        for (int i = 0; i < arguments1.size(); i++) {
            assertEquals(arguments1.get(i).get()[0], arguments2.get(i).get()[0],
                    "Arguments should be the same with fixed generator");
            assertEquals("test-value", arguments1.get(i).get()[0]);
        }
    }

    @ParameterizedTest(name = "With seed difference={0}, should generate different arguments")
    @ValueSource(longs = {1, 100, 1000})
    @DisplayName("Should generate different arguments with different seeds")
    void shouldGenerateDifferentArgumentsWithDifferentSeeds(long seedDifference) {
        var provider1 = new TestProvider(TEST_SEED, 3);
        var provider2 = new TestProvider(TEST_SEED + seedDifference, 3);
        var generator = Generators.strings(5, 10);

        var arguments1 = provider1.generateArgumentsPublic(generator);
        var arguments2 = provider2.generateArgumentsPublic(generator);

        assertNotNull(arguments1);
        assertNotNull(arguments2);
        assertEquals(3, arguments1.size());
        assertEquals(3, arguments2.size());

        // Check if at least one argument is different
        boolean atLeastOneDifferent = false;
        for (int i = 0; i < arguments1.size(); i++) {
            if (!arguments1.get(i).get()[0].equals(arguments2.get(i).get()[0])) {
                atLeastOneDifferent = true;
                break;
            }
        }

        assertTrue(atLeastOneDifferent,
                "At least one argument should be different with different seeds");
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

        // Public wrapper for protected method to enable testing
        public List<Arguments> generateArgumentsPublic(TypedGenerator<?> generator) {
            return generateArguments(generator);
        }
    }

    /**
     * A generator that always returns the same fixed string.
     */
    private static class FixedStringGenerator implements TypedGenerator<String> {
        private final String value;

        FixedStringGenerator(String value) {
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
}
