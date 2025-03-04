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
import de.cuioss.test.generator.impl.LocalDateGenerator;
import de.cuioss.test.generator.junit.EnableGeneratorController;
import de.cuioss.test.generator.junit.GeneratorSeed;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test demonstrating the combined use of TypeGeneratorSource and TypeGeneratorMethodSource
 * with various generator configurations.
 */
@EnableGeneratorController
@DisplayName("TypeGenerator JUnit Extensions Integration Test")
class TypeGeneratorIntegrationTest {

    // Simple example using a class-based generator
    @ParameterizedTest
    @TypeGeneratorSource(EmailGenerator.class)
    @DisplayName("Should generate valid email addresses")
    void shouldGenerateValidEmails(String email) {
        assertNotNull(email);
        assertTrue(email.contains("@"));
        assertTrue(email.contains("."));
    }

    // Example with count parameter to generate multiple test cases
    @ParameterizedTest
    @TypeGeneratorSource(value = PositiveIntegerGenerator.class, count = 5)
    @DisplayName("Should generate multiple positive integers")
    void shouldGenerateMultiplePositiveIntegers(Integer value) {
        assertNotNull(value);
        assertTrue(value > 0);
    }

    // Example with fixed seed for reproducible tests
    @ParameterizedTest
    @TypeGeneratorSource(value = EnumGenerator.class, seed = 123L)
    @GeneratorSeed(123L)
    @DisplayName("Should generate reproducible enum values")
    void shouldGenerateReproducibleEnumValues(TimeUnit unit) {
        assertNotNull(unit);
        // With seed 123L, the first value will always be the same
    }

    // Example using method source with a method in the same class
    @ParameterizedTest
    @TypeGeneratorMethodSource("createDateGenerator")
    @DisplayName("Should generate dates using method source")
    void shouldGenerateDatesUsingMethodSource(LocalDate date) {
        assertNotNull(date);
        LocalDate now = LocalDate.now();
        assertTrue(date.isAfter(now.minusYears(1)));
        assertTrue(date.isBefore(now.plusYears(1)));
    }

    // Example using method source with count parameter
    @ParameterizedTest
    @TypeGeneratorMethodSource(value = "createBoundedStringGenerator", count = 3)
    @DisplayName("Should generate multiple bounded strings")
    void shouldGenerateMultipleBoundedStrings(String value) {
        assertNotNull(value);
        assertTrue(value.length() >= 5 && value.length() <= 10);
    }

    // Example using method source with external method reference
    @ParameterizedTest
    @TypeGeneratorMethodSource("de.cuioss.test.generator.junit.parameterized.TypeGeneratorIntegrationTest#createExternalGenerator")
    @DisplayName("Should use external method reference")
    void shouldUseExternalMethodReference(String value) {
        assertEquals("external-value", value);
    }

    // Generator factory methods
    
    static TypedGenerator<LocalDate> createDateGenerator() {
        return new LocalDateGenerator() {
            @Override
            public LocalDate next() {
                LocalDate now = LocalDate.now();
                LocalDate date = super.next();
                // Ensure date is within the last year and next year
                while (date.isBefore(now.minusYears(1)) || date.isAfter(now.plusYears(1))) {
                    date = super.next();
                }
                return date;
            }
        };
    }

    static TypedGenerator<String> createBoundedStringGenerator() {
        return Generators.strings(5, 10);
    }

    public static TypedGenerator<String> createExternalGenerator() {
        return () -> "external-value";
    }

    // Custom generator implementations
    
    public static class EmailGenerator implements TypedGenerator<String> {
        @Override
        public String next() {
            return "user" + System.nanoTime() + "@example.com";
        }

        @Override
        public Class<String> getType() {
            return String.class;
        }
    }

    public static class PositiveIntegerGenerator implements TypedGenerator<Integer> {
        @Override
        public Integer next() {
            return Generators.integers(1, 1000).next();
        }

        @Override
        public Class<Integer> getType() {
            return Integer.class;
        }
    }

    public static class EnumGenerator implements TypedGenerator<TimeUnit> {
        private final TypedGenerator<TimeUnit> delegate;

        public EnumGenerator() {
            delegate = Generators.enumValues(TimeUnit.class);
        }

        @Override
        public TimeUnit next() {
            return delegate.next();
        }

        @Override
        public Class<TimeUnit> getType() {
            return TimeUnit.class;
        }
    }
}
