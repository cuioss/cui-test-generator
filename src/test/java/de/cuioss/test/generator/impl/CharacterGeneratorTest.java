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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("CharacterGenerator should")
class CharacterGeneratorTest {

    @Test
    @DisplayName("generate characters in default Basic Latin range")
    void shouldGenerateBasicLatin() {
        var generator = new CharacterGenerator();
        for (int i = 0; i < 100; i++) {
            char c = generator.next();
            assertTrue(c >= '\u0020' && c <= '\u007E', "Character out of range: " + (int) c);
        }
    }

    @Test
    @DisplayName("generate characters in custom range")
    void shouldGenerateCustomRange() {
        var generator = new CharacterGenerator('A', 'Z');
        for (int i = 0; i < 100; i++) {
            char c = generator.next();
            assertTrue(c >= 'A' && c <= 'Z', "Character out of range: " + c);
        }
    }

    @Test
    @DisplayName("return Character.class as type")
    void shouldReturnCorrectType() {
        assertEquals(Character.class, new CharacterGenerator().getType());
    }
}
