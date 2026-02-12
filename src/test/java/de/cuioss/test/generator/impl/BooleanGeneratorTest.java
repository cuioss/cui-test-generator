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

@DisplayName("BooleanGenerator should")
class BooleanGeneratorTest {

    @Test
    @DisplayName("generate both true and false")
    void shouldGenerateBothValues() {
        var generator = new BooleanGenerator();
        boolean seenTrue = false, seenFalse = false;
        for (int i = 0; i < 100; i++) {
            if (generator.next()) seenTrue = true;
            else seenFalse = true;
        }
        assertTrue(seenTrue, "Should generate true");
        assertTrue(seenFalse, "Should generate false");
    }

    @Test
    @DisplayName("return Boolean.class as type")
    void shouldReturnCorrectType() {
        assertEquals(Boolean.class, new BooleanGenerator().getType());
    }
}
