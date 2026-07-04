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

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@EnableGeneratorController
@GeneratorSeed(42L)
@DisplayName("LocalTimeGenerator should")
class LocalTimeGeneratorTest {

    @Test
    @DisplayName("return LocalTime.class as type")
    void shouldReturnCorrectType() {
        assertEquals(LocalTime.class, new LocalTimeGenerator().getType());
    }

    @Test
    @DisplayName("generate values within [00:00, 23:59:59.999999999]")
    void shouldGenerateWithinDay() {
        var generator = new LocalTimeGenerator();
        for (int i = 0; i < 200; i++) {
            LocalTime value = generator.next();
            assertTrue(!value.isBefore(LocalTime.MIN) && !value.isAfter(LocalTime.MAX),
                    "Value out of range: " + value);
            assertTrue(value.toSecondOfDay() >= 0 && value.toSecondOfDay() <= 86399,
                    "Second-of-day out of range: " + value.toSecondOfDay());
        }
    }

    @Test
    @DisplayName("be reproducible with the same seed")
    void shouldBeReproducible() {
        var generator = new LocalTimeGenerator();
        RandomContext.setSeed(42L);
        LocalTime first = generator.next();
        RandomContext.setSeed(42L);
        LocalTime second = generator.next();
        assertEquals(first, second, "Same seed must yield the same time");
    }
}
