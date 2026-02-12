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
package de.cuioss.test.generator.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("StringGenerator should")
class StringGeneratorTest {

    @Test
    @DisplayName("generate strings with default settings")
    void shouldGenerateDefault() {
        var generator = new StringGenerator();
        for (int i = 0; i < 100; i++) {
            String value = generator.next();
            assertNotNull(value);
            assertTrue(value.length() <= 30, "Length exceeded: " + value.length());
        }
    }

    @Test
    @DisplayName("generate non-empty strings with min length")
    void shouldGenerateNonEmpty() {
        var generator = new StringGenerator(1, 30);
        for (int i = 0; i < 100; i++) {
            String value = generator.next();
            assertFalse(value.isEmpty(), "Should not generate empty strings");
        }
    }

    @Test
    @DisplayName("generate strings from allowed chars only")
    void shouldUseAllowedChars() {
        var generator = new StringGenerator("ABC", 2, 5);
        for (int i = 0; i < 100; i++) {
            String value = generator.next();
            assertTrue(value.length() >= 2 && value.length() <= 5);
            assertTrue(value.matches("[ABC]+"), "Unexpected chars in: " + value);
        }
    }

    @Test
    @DisplayName("generate letter-only strings via factory method")
    void shouldGenerateLetterStrings() {
        var generator = StringGenerator.letterStrings(3, 10);
        for (int i = 0; i < 100; i++) {
            String value = generator.next();
            assertTrue(value.length() >= 3 && value.length() <= 10);
            assertTrue(value.matches("[a-zA-Z]+"), "Non-letter char in: " + value);
        }
    }

    @Test
    @DisplayName("return String.class as type")
    void shouldReturnCorrectType() {
        assertEquals(String.class, new StringGenerator().getType());
    }
}
