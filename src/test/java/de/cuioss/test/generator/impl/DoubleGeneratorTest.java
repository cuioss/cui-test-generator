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

@DisplayName("DoubleGenerator should")
class DoubleGeneratorTest {

    @Test
    @DisplayName("generate values across full double range")
    void shouldGenerateFullRange() {
        var generator = new DoubleGenerator();
        for (int i = 0; i < 100; i++) {
            assertNotNull(generator.next());
        }
    }

    @Test
    @DisplayName("generate values within bounded range")
    void shouldGenerateBoundedRange() {
        var generator = new DoubleGenerator(0.0, 100.0);
        for (int i = 0; i < 200; i++) {
            double value = generator.next();
            assertTrue(value >= 0.0 && value <= 100.0, "Value out of range: " + value);
        }
    }

    @Test
    @DisplayName("return Double.class as type")
    void shouldReturnCorrectType() {
        assertEquals(Double.class, new DoubleGenerator().getType());
    }
}
