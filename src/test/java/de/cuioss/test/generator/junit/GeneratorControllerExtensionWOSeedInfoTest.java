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
package de.cuioss.test.generator.junit;

import de.cuioss.test.generator.internal.RandomContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@EnableGeneratorController
@DisplayName("GeneratorController without seed info should")
class GeneratorControllerExtensionWOSeedInfoTest {

    private static final int REPETITIONS = 5;

    private static final Set<Long> OBSERVED_SEEDS = new HashSet<>();

    @RepeatedTest(REPETITIONS)
    @DisplayName("draw a distinct random seed for each repetition")
    void shouldDrawDistinctRandomSeeds() {
        // Without @GeneratorSeed the extension must draw a fresh random seed per invocation.
        long seed = RandomContext.getLastSeed();

        // Recording the seed must reveal a value not seen in any previous repetition: if the
        // extension reused the same seed, add() would return false on a later repetition.
        assertTrue(OBSERVED_SEEDS.add(seed),
                "Each repetition must draw a distinct random seed, but saw a repeat: " + seed);
    }
}
