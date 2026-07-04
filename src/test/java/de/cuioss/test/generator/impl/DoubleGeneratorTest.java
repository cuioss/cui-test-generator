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

import de.cuioss.test.generator.junit.EnableGeneratorController;
import de.cuioss.test.generator.junit.GeneratorSeed;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@EnableGeneratorController
@GeneratorSeed(42L)
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
    @DisplayName("produce only finite, in-range values across the full [-MAX, MAX] span")
    void shouldProduceFiniteValuesAcrossFullRange() {
        var generator = new DoubleGenerator(-Double.MAX_VALUE, Double.MAX_VALUE);
        for (int i = 0; i < 10_000; i++) {
            double value = generator.next();
            assertTrue(Double.isFinite(value), "Non-finite value produced: " + value);
            assertTrue(value >= -Double.MAX_VALUE && value <= Double.MAX_VALUE, "Out of range: " + value);
        }
    }

    @Test
    @DisplayName("reject non-finite bounds in the constructor")
    void shouldRejectNonFiniteBounds() {
        assertThrows(IllegalArgumentException.class, () -> new DoubleGenerator(Double.NaN, 1.0));
        assertThrows(IllegalArgumentException.class, () -> new DoubleGenerator(0.0, Double.POSITIVE_INFINITY));
        assertThrows(IllegalArgumentException.class, () -> new DoubleGenerator(Double.NEGATIVE_INFINITY, 0.0));
    }

    @Test
    @DisplayName("produce negative and positive values with the default full range")
    void shouldProduceBothSignsByDefault() {
        var generator = new DoubleGenerator();
        boolean sawNegative = false;
        boolean sawPositive = false;
        for (int i = 0; i < 1_000 && !(sawNegative && sawPositive); i++) {
            double value = generator.next();
            sawNegative |= value < 0;
            sawPositive |= value > 0;
        }
        assertTrue(sawNegative, "Default double generator must be able to produce negative values");
        assertTrue(sawPositive, "Default double generator must be able to produce positive values");
    }

    @Test
    @DisplayName("produce negative float values with the default full range")
    void shouldProduceNegativeFloatsByDefault() {
        var generator = new FloatObjectGenerator();
        boolean sawNegative = false;
        for (int i = 0; i < 1_000 && !sawNegative; i++) {
            sawNegative = generator.next() < 0;
        }
        assertTrue(sawNegative, "Default float generator must be able to produce negative values");
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
