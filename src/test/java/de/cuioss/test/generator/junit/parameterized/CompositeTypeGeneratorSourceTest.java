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
import de.cuioss.test.generator.impl.NonBlankStringGenerator;
import de.cuioss.test.generator.junit.EnableGeneratorController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link CompositeTypeGeneratorSource} and {@link CompositeTypeGeneratorArgumentsProvider}.
 */
@EnableGeneratorController
class CompositeTypeGeneratorSourceTest {

    @ParameterizedTest
    @DisplayName("Should generate combinations from generator classes")
    @CompositeTypeGeneratorSource(
            generatorClasses = {
                    NonBlankStringGenerator.class,
                    TestBooleanGenerator.class
            },
            count = 2
    )
    void shouldGenerateCombinationsFromClasses(String text, Boolean flag) {
        assertNotNull(text);
        assertFalse(text.isBlank());
        assertNotNull(flag);
    }

    @ParameterizedTest
    @DisplayName("Should generate combinations from generator methods")
    @CompositeTypeGeneratorSource(
            generatorMethods = {
                    "stringGenerator",
                    "integerGenerator"
            },
            count = 2
    )
    void shouldGenerateCombinationsFromMethods(String text, Integer number) {
        assertNotNull(text);
        assertTrue(text.length() >= 3 && text.length() <= 10);
        assertNotNull(number);
        assertTrue(number >= 1 && number <= 100);
    }

    @ParameterizedTest
    @DisplayName("Should generate combinations from mixed sources")
    @CompositeTypeGeneratorSource(
            generatorClasses = {NonBlankStringGenerator.class},
            generatorMethods = {"integerGenerator"},
            count = 2
    )
    void shouldGenerateCombinationsFromMixedSources(String text, Integer number) {
        assertNotNull(text);
        assertFalse(text.isBlank());
        assertNotNull(number);
        assertTrue(number >= 1 && number <= 100);
    }

    @ParameterizedTest
    @DisplayName("Should generate one-to-one pairs when cartesianProduct is false")
    @CompositeTypeGeneratorSource(
            generatorMethods = {
                    "fixedStringGenerator",
                    "fixedIntegerGenerator"
            },
            cartesianProduct = false,
            count = 3
    )
    void shouldGenerateOneToOnePairs(String text, Integer number) {
        assertNotNull(text);
        assertNotNull(number);
    }

    @ParameterizedTest
    @DisplayName("Should generate values with specific seed")
    @CompositeTypeGeneratorSource(
            generatorClasses = {
                    NonBlankStringGenerator.class,
                    TestBooleanGenerator.class
            },
            seed = 42L,
            count = 2
    )
    void shouldGenerateValuesWithSpecificSeed(String text, Boolean flag) {
        assertNotNull(text);
        assertNotNull(flag);
    }

    /**
     * Creates a generator for strings with length between 3 and 10.
     * 
     * @return a generator for strings
     */
    static TypedGenerator<String> stringGenerator() {
        return Generators.strings(3, 10);
    }

    /**
     * Creates a generator for integers between 1 and 100.
     * 
     * @return a generator for integers
     */
    static TypedGenerator<Integer> integerGenerator() {
        return Generators.integers(1, 100);
    }

    /**
     * Creates a generator that produces a fixed sequence of strings.
     * 
     * @return a generator for fixed strings
     */
    static TypedGenerator<String> fixedStringGenerator() {
        return Generators.fixedValues("A", "B", "C");
    }

    /**
     * Creates a generator that produces a fixed sequence of integers.
     * 
     * @return a generator for fixed integers
     */
    static TypedGenerator<Integer> fixedIntegerGenerator() {
        return Generators.fixedValues(1, 2, 3);
    }

    /**
     * A simple boolean generator for testing.
     */
    public static class TestBooleanGenerator implements TypedGenerator<Boolean> {
        @Override
        public Boolean next() {
            return Generators.booleans().next();
        }
    }
}
