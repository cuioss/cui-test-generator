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
@DisplayName("ShortObjectGenerator should")
class ShortObjectGeneratorTest {

    @Test
    @DisplayName("return Short.class as type")
    void shouldReturnCorrectType() {
        assertEquals(Short.class, new ShortObjectGenerator().getType());
    }

    @Test
    @DisplayName("cover both negative and non-negative values across a sample")
    void shouldCoverBothSigns() {
        var generator = new ShortObjectGenerator();
        boolean sawNegative = false;
        boolean sawNonNegative = false;
        for (int i = 0; i < 200; i++) {
            short value = generator.next();
            sawNegative |= value < 0;
            sawNonNegative |= value >= 0;
        }
        assertTrue(sawNegative, "Short generator must produce negative values");
        assertTrue(sawNonNegative, "Short generator must produce non-negative values");
    }

    @Test
    @DisplayName("be reproducible with the same seed")
    void shouldBeReproducible() {
        var generator = new ShortObjectGenerator();
        RandomContext.setSeed(42L);
        short first = generator.next();
        RandomContext.setSeed(42L);
        short second = generator.next();
        assertEquals(first, second, "Same seed must yield the same value");
    }
}
