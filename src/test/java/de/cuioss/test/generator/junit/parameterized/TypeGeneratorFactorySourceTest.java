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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link TypeGeneratorFactorySource} and {@link TypeGeneratorFactoryArgumentsProvider}.
 */
@EnableGeneratorController
class TypeGeneratorFactorySourceTest {

    @ParameterizedTest
    @DisplayName("Should generate a fixed value from factory without parameters")
    @TypeGeneratorFactorySource(
            factoryClass = TestGeneratorFactory.class,
            factoryMethod = "createFixedValueGenerator"
    )
    void shouldGenerateFixedValueFromFactory(String value) {
        assertEquals("fixed-value", value);
    }

    @ParameterizedTest
    @DisplayName("Should generate multiple values from factory without parameters")
    @TypeGeneratorFactorySource(
            factoryClass = TestGeneratorFactory.class,
            factoryMethod = "createStringGenerator",
            count = 5
    )
    void shouldGenerateMultipleValuesFromFactory(String value) {
        assertNotNull(value);
        assertTrue(value.length() >= 3 && value.length() <= 10);
    }

    @ParameterizedTest
    @DisplayName("Should generate values from factory with parameters")
    @TypeGeneratorFactorySource(
            factoryClass = TestGeneratorFactory.class,
            factoryMethod = "createRangeGenerator",
            methodParameters = {"1", "10"},
            count = 5
    )
    void shouldGenerateValuesFromFactoryWithParameters(Integer value) {
        assertNotNull(value);
        assertTrue(value >= 1 && value <= 10);
    }

    @ParameterizedTest
    @DisplayName("Should generate values with specific seed")
    @TypeGeneratorFactorySource(
            factoryClass = TestGeneratorFactory.class,
            factoryMethod = "createStringGenerator",
            seed = 42L,
            count = 3
    )
    void shouldGenerateValuesWithSpecificSeed(String value) {
        assertNotNull(value);
    }

    /**
     * Test factory class for creating TypedGenerators.
     */
    public static class TestGeneratorFactory {

        /**
         * Creates a generator that always returns the same fixed value.
         * 
         * @return a generator for a fixed string value
         */
        public static TypedGenerator<String> createFixedValueGenerator() {
            return Generators.fixedValues("fixed-value");
        }

        /**
         * Creates a generator for strings with length between 3 and 10.
         * 
         * @return a generator for strings
         */
        public static TypedGenerator<String> createStringGenerator() {
            return Generators.strings(3, 10);
        }

        /**
         * Creates a generator for integers within a specified range.
         * 
         * @param minStr the minimum value as a string
         * @param maxStr the maximum value as a string
         * @return a generator for integers in the specified range
         */
        public static TypedGenerator<Integer> createRangeGenerator(String minStr, String maxStr) {
            int min = Integer.parseInt(minStr);
            int max = Integer.parseInt(maxStr);
            return Generators.integers(min, max);
        }
    }
}
