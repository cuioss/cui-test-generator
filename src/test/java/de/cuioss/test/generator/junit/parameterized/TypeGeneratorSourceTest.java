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

import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.generator.junit.EnableGeneratorController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests for {@link TypeGeneratorSource} and {@link TypeGeneratorArgumentsProvider}.
 */
@EnableGeneratorController
class TypeGeneratorSourceTest {

    @ParameterizedTest
    @TypeGeneratorSource(NonBlankStringGenerator.class)
    @DisplayName("Should generate non-blank strings")
    void shouldGenerateNonBlankStrings(String value) {
        assertNotNull(value);
        assertFalse(value.isBlank());
    }
    
    @ParameterizedTest
    @TypeGeneratorSource(value = IntegerGenerator.class, count = 5)
    @DisplayName("Should generate multiple integers")
    void shouldGenerateMultipleIntegers(Integer value) {
        assertNotNull(value);
        assertEquals(42, value.intValue());
    }
    
    @ParameterizedTest
    @TypeGeneratorSource(value = SingleValueGenerator.class)
    @DisplayName("Should use generator with fixed value")
    void shouldUseGeneratorWithFixedValue(String value) {
        assertEquals("fixed-value", value);
    }
    
    /**
     * Non-blank string generator for testing.
     */
    public static class NonBlankStringGenerator implements TypedGenerator<String> {
        @Override
        public String next() {
            return "test-string";
        }
        
        @Override
        public Class<String> getType() {
            return String.class;
        }
    }
    
    /**
     * Integer generator for testing.
     */
    public static class IntegerGenerator implements TypedGenerator<Integer> {
        @Override
        public Integer next() {
            return 42;
        }
        
        @Override
        public Class<Integer> getType() {
            return Integer.class;
        }
    }
    
    /**
     * Generator that always returns a single fixed value.
     */
    public static class SingleValueGenerator implements TypedGenerator<String> {
        @Override
        public String next() {
            return "fixed-value";
        }
        
        @Override
        public Class<String> getType() {
            return String.class;
        }
    }
}
