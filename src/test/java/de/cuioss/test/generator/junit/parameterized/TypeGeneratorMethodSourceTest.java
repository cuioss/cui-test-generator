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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link TypeGeneratorMethodSource} and {@link TypeGeneratorMethodArgumentsProvider}.
 */
@EnableGeneratorController
class TypeGeneratorMethodSourceTest {

    @ParameterizedTest
    @TypeGeneratorMethodSource("createStringGenerator")
    @DisplayName("Should use method to get generator")
    void shouldUseMethodToGetGenerator(String value) {
        assertNotNull(value);
        assertTrue(value.length() >= 5 && value.length() <= 10);
    }
    
    @ParameterizedTest
    @TypeGeneratorMethodSource(value = "createIntegerGenerator", count = 5)
    @DisplayName("Should generate multiple values")
    void shouldGenerateMultipleValues(Integer value) {
        assertNotNull(value);
        assertTrue(value >= 1 && value <= 100);
    }
    
    @ParameterizedTest
    @TypeGeneratorMethodSource("de.cuioss.test.generator.junit.parameterized.TypeGeneratorMethodSourceTest#createExternalGenerator")
    @DisplayName("Should use external static method")
    void shouldUseExternalStaticMethod(String value) {
        assertEquals("external", value);
    }
    
    /**
     * Creates a string generator for testing.
     * 
     * @return a string generator that produces strings between 5 and 10 characters
     */
    static TypedGenerator<String> createStringGenerator() {
        return Generators.strings(5, 10);
    }
    
    /**
     * Creates an integer generator for testing.
     * 
     * @return an integer generator that produces values between 1 and 100
     */
    static TypedGenerator<Integer> createIntegerGenerator() {
        return Generators.integers(1, 100);
    }
    
    /**
     * Creates an external generator for testing.
     * 
     * @return a generator that always returns "external"
     */
    public static TypedGenerator<String> createExternalGenerator() {
        return Generators.fixedValues(String.class, "external");
    }
}
