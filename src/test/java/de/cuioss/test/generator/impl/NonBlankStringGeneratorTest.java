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
package de.cuioss.test.generator.impl;

import de.cuioss.test.generator.TypedGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("NonBlankStringGenerator should")
class NonBlankStringGeneratorTest {

    private final TypedGenerator<String> underTest = new NonBlankStringGenerator();

    @Test
    @DisplayName("return String.class as type")
    void getTypeReturnsString() {
        // Act & Assert
        assertEquals(String.class, underTest.getType(), 
            "Generator should return String.class as type");
    }

    @Nested
    @DisplayName("when generating strings")
    class StringGenerationTests {

        @Test
        @DisplayName("generate non-null strings")
        void shouldGenerateNonNullStrings() {
            // Act
            var result = underTest.next();

            // Assert
            assertNotNull(result, "Generated string should not be null");
        }

        @Test
        @DisplayName("generate non-blank strings")
        void shouldGenerateNonBlankStrings() {
            // Act
            var result = underTest.next();

            // Assert
            assertFalse(result.trim().isEmpty(), "Generated string should not be blank");
        }

        @RepeatedTest(5)
        @DisplayName("consistently generate valid strings")
        void shouldConsistentlyGenerateValidStrings() {
            // Act
            var result = underTest.next();

            // Assert
            assertNotNull(result, "Generated string should not be null");
            assertFalse(result.trim().isEmpty(), "Generated string should not be blank");
            assertFalse(result.matches("^\\s+$"), "Generated string should not be only whitespace");
        }

        @Test
        @DisplayName("preserve string content")
        void shouldPreserveContent() {
            // Act
            var result = underTest.next();
            var trimmed = result.trim();

            // Assert
            assertEquals(result, trimmed, 
                "Generated string should not have leading or trailing whitespace");
        }
    }
}
