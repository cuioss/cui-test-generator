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
package de.cuioss.test.generator.internal;

import de.cuioss.test.generator.junit.EnableGeneratorController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@EnableGeneratorController
@DisplayName("RandomContext should")
class RandomContextTest {

    @Test
    @DisplayName("return the last seed set via setSeed")
    void shouldReturnLastSeed() {
        RandomContext.setSeed(42L);
        assertEquals(42L, RandomContext.getLastSeed());
    }

    @Test
    @DisplayName("initialize a new seed via initSeed")
    void shouldInitSeed() {
        long seed = RandomContext.initSeed();
        assertNotEquals(0L, seed);
        assertEquals(seed, RandomContext.getLastSeed());
    }

    @Test
    @DisplayName("produce reproducible sequences with the same seed")
    void shouldBeReproducible() {
        RandomContext.setSeed(12345L);
        var r1 = RandomContext.random();
        int val1 = r1.nextInt();

        RandomContext.setSeed(12345L);
        int val2 = r1.nextInt();

        assertEquals(val1, val2);
    }

    @Test
    @DisplayName("provide a non-null shared Random instance")
    void shouldProvideRandom() {
        Random random = RandomContext.random();
        assertNotNull(random);
    }

    @Test
    @DisplayName("parse a valid, whitespace-padded seed system property")
    void shouldParseValidSeedProperty() {
        withProperty(" 4711 ", () ->
                assertEquals(4711L, RandomContext.readSystemProperty()));
    }

    @Test
    @DisplayName("fall back to no seed on a malformed seed system property")
    void shouldFallBackOnMalformedSeedProperty() {
        withProperty("not-a-number", () ->
                assertNull(RandomContext.readSystemProperty(),
                        "A malformed property must not raise, but fall back to a random seed"));
    }

    @Test
    @DisplayName("report no seed when the system property is absent")
    void shouldReportNoSeedWhenPropertyAbsent() {
        var property = RandomContext.SEED_SYSTEM_PROPERTY;
        var previous = System.getProperty(property);
        System.clearProperty(property);
        try {
            assertNull(RandomContext.readSystemProperty());
        } finally {
            restore(property, previous);
        }
    }

    @Test
    @DisplayName("expose the configured seed as an Optional")
    void shouldExposeConfiguredSeed() {
        assertNotNull(RandomContext.getConfiguredSeed());
    }

    private static void withProperty(String value, Runnable assertion) {
        var property = RandomContext.SEED_SYSTEM_PROPERTY;
        var previous = System.getProperty(property);
        System.setProperty(property, value);
        try {
            assertion.run();
        } finally {
            restore(property, previous);
        }
    }

    private static void restore(String property, String previous) {
        if (previous == null) {
            System.clearProperty(property);
        } else {
            System.setProperty(property, previous);
        }
    }
}
