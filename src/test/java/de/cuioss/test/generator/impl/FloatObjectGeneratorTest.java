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
import de.cuioss.test.generator.junit.EnableGeneratorController;
import de.cuioss.test.generator.junit.GeneratorSeed;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@EnableGeneratorController
@GeneratorSeed(42L)
@DisplayName("FloatObjectGenerator should")
class FloatObjectGeneratorTest {

    @Test
    @DisplayName("return Float.class as type")
    void shouldReturnCorrectType() {
        assertEquals(Float.class, new FloatObjectGenerator().getType());
    }

    @Test
    @DisplayName("respect the bounded range")
    void shouldRespectBoundedRange() {
        var generator = new FloatObjectGenerator(0f, 100f);
        for (int i = 0; i < 200; i++) {
            float value = generator.next();
            assertTrue(value >= 0f && value <= 100f, "Value out of range: " + value);
        }
    }

    @Test
    @DisplayName("produce negative values with the default full range")
    void shouldProduceNegativesByDefault() {
        var generator = new FloatObjectGenerator();
        boolean sawNegative = false;
        for (int i = 0; i < 1_000 && !sawNegative; i++) {
            sawNegative = generator.next() < 0f;
        }
        assertTrue(sawNegative, "Default float generator must be able to produce negative values");
    }

    @Test
    @DisplayName("be reproducible with the same seed")
    void shouldBeReproducible() {
        var generator = new FloatObjectGenerator(0f, 100f);
        RandomContext.setSeed(42L);
        float first = generator.next();
        RandomContext.setSeed(42L);
        float second = generator.next();
        assertEquals(first, second, "Same seed must yield the same value");
    }
}
