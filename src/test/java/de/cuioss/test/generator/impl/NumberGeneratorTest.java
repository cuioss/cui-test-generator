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
import static org.junit.jupiter.api.Assertions.assertNotNull;

@EnableGeneratorController
@GeneratorSeed(42L)
@DisplayName("NumberGenerator should")
class NumberGeneratorTest {

    @Test
    @DisplayName("return Number.class as type")
    void shouldReturnCorrectType() {
        assertEquals(Number.class, new NumberGenerator().getType());
    }

    @Test
    @DisplayName("generate non-null Number instances")
    void shouldGenerateNonNull() {
        var generator = new NumberGenerator();
        for (int i = 0; i < 100; i++) {
            Number value = generator.next();
            assertNotNull(value);
        }
    }

    @Test
    @DisplayName("be reproducible with the same seed")
    void shouldBeReproducible() {
        var generator = new NumberGenerator();
        RandomContext.setSeed(42L);
        Number first = generator.next();
        RandomContext.setSeed(42L);
        Number second = generator.next();
        assertEquals(first, second, "Same seed must yield the same number");
    }
}
