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

@DisplayName("LongGenerator should")
class LongGeneratorTest {

    @Test
    @DisplayName("generate values across full long range")
    void shouldGenerateFullRange() {
        var generator = new LongGenerator();
        for (int i = 0; i < 100; i++) {
            assertNotNull(generator.next());
        }
    }

    @Test
    @DisplayName("generate values within bounded range")
    void shouldGenerateBoundedRange() {
        var generator = new LongGenerator(-23000, 23000);
        for (int i = 0; i < 200; i++) {
            long value = generator.next();
            assertTrue(value >= -23000 && value <= 23000, "Value out of range: " + value);
        }
    }

    @Test
    @DisplayName("return Long.class as type")
    void shouldReturnCorrectType() {
        assertEquals(Long.class, new LongGenerator().getType());
    }

    @Test
    @DisplayName("be reproducible with same seed")
    void shouldBeReproducible() {
        var generator = new LongGenerator(0, 1000);
        RandomContext.setSeed(42L);
        long val1 = generator.next();
        RandomContext.setSeed(42L);
        long val2 = generator.next();
        assertEquals(val1, val2);
    }
}
