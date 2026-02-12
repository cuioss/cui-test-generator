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

import de.cuioss.test.generator.internal.RandomContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("IntegerGenerator should")
class IntegerGeneratorTest {

    @Test
    @DisplayName("generate values across full int range")
    void shouldGenerateFullRange() {
        var generator = new IntegerGenerator();
        for (int i = 0; i < 100; i++) {
            assertNotNull(generator.next());
        }
    }

    @Test
    @DisplayName("generate values within bounded range")
    void shouldGenerateBoundedRange() {
        var generator = new IntegerGenerator(0, 10);
        for (int i = 0; i < 200; i++) {
            int value = generator.next();
            assertTrue(value >= 0 && value <= 10, "Value out of range: " + value);
        }
    }

    @Test
    @DisplayName("reach boundary values")
    void shouldReachBoundaries() {
        var generator = new IntegerGenerator(0, 2);
        boolean seenMin = false, seenMax = false;
        for (int i = 0; i < 200; i++) {
            int value = generator.next();
            if (value == 0) seenMin = true;
            if (value == 2) seenMax = true;
        }
        assertTrue(seenMin, "Should reach minimum value");
        assertTrue(seenMax, "Should reach maximum value");
    }

    @Test
    @DisplayName("return Integer.class as type")
    void shouldReturnCorrectType() {
        assertEquals(Integer.class, new IntegerGenerator().getType());
    }

    @Test
    @DisplayName("be reproducible with same seed")
    void shouldBeReproducible() {
        var generator = new IntegerGenerator(0, 1000);
        RandomContext.setSeed(42L);
        int val1 = generator.next();
        RandomContext.setSeed(42L);
        int val2 = generator.next();
        assertEquals(val1, val2);
    }
}
