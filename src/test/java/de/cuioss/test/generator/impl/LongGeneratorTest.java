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

import static org.junit.jupiter.api.Assertions.*;

@EnableGeneratorController
@GeneratorSeed(42L)
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
    @DisplayName("cover both signs and high-magnitude values across the full long range")
    void shouldCoverFullLongRange() {
        var generator = new LongGenerator();
        boolean sawNegative = false;
        boolean sawPositive = false;
        boolean sawHighMagnitude = false;
        for (int i = 0; i < 10_000; i++) {
            long value = generator.next();
            sawNegative |= value < 0;
            sawPositive |= value > 0;
            // A single nextDouble() scaling could only reach ~every 2048th value; a full-entropy
            // draw regularly lands in the top of the range.
            sawHighMagnitude |= Math.abs(value) > (Long.MAX_VALUE / 2);
        }
        assertTrue(sawNegative, "Full-range long generator must produce negative values");
        assertTrue(sawPositive, "Full-range long generator must produce positive values");
        assertTrue(sawHighMagnitude, "Full-range long generator must reach high-magnitude values");
    }

    @Test
    @DisplayName("distribute uniformly and reach both ends of a wide (> Long.MAX_VALUE) span")
    void shouldReachEndsOfWideSpan() {
        // min..max spans more than Long.MAX_VALUE, exercising the wide-range rejection path.
        long min = Long.MIN_VALUE / 2;
        long max = Long.MAX_VALUE;
        var generator = new LongGenerator(min, max);
        long observedMin = Long.MAX_VALUE;
        long observedMax = Long.MIN_VALUE;
        for (int i = 0; i < 20_000; i++) {
            long value = generator.next();
            assertTrue(value >= min && value <= max, "Out of range: " + value);
            observedMin = Math.min(observedMin, value);
            observedMax = Math.max(observedMax, value);
        }
        // Should get within the top and bottom deciles of the span.
        assertTrue(observedMax > max - (max / 8), "Never approached the upper bound: " + observedMax);
        assertTrue(observedMin < min / 2, "Never approached the lower bound: " + observedMin);
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
