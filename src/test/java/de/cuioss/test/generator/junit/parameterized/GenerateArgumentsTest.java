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
package de.cuioss.test.generator.junit.parameterized;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.internal.RandomContext;
import de.cuioss.test.generator.junit.EnableGeneratorController;
import de.cuioss.test.generator.junit.GeneratorSeed;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Additional tests for the generateArguments method in {@link AbstractTypedGeneratorArgumentsProvider}.
 *
 * <p>The count-based cardinality of {@code generateArguments} is covered in
 * {@link AbstractTypedGeneratorArgumentsProviderTest}; this class focuses on the edge cases not
 * exercised there: zero count, seed reproducibility and seed sensitivity.</p>
 */
@EnableGeneratorController
@GeneratorSeed(4419141497282950341L)
class GenerateArgumentsTest {

    private static final long TEST_SEED = 42L;

    @Test
    @DisplayName("Should handle zero count")
    void shouldHandleZeroCount() {
        var provider = new TestArgumentsProvider(TEST_SEED, 0);
        var generator = Generators.strings(5, 10);
        var arguments = provider.generateArgumentsPublic(generator);

        assertNotNull(arguments);
        assertEquals(0, arguments.size());
    }

    @Test
    @DisplayName("Should reproduce identical arguments when the seed is reset")
    void shouldGenerateArgumentsWithConsistentSeed() {
        var provider = new TestArgumentsProvider(TEST_SEED, 5);
        // A real, full-range generator: identical output only proves reproducibility if
        // the seed is actually applied — a fixed generator would pass even when broken.
        var generator = Generators.integers(Integer.MIN_VALUE, Integer.MAX_VALUE);

        RandomContext.setSeed(TEST_SEED);
        var first = extractValues(provider.generateArgumentsPublic(generator));

        RandomContext.setSeed(TEST_SEED);
        var second = extractValues(provider.generateArgumentsPublic(generator));

        assertEquals(first, second, "Re-seeding must reproduce the exact argument sequence");
        assertTrue(new HashSet<>(first).size() > 1,
                "A full-range generator must not collapse to a single value");
    }

    private static List<Object> extractValues(List<Arguments> arguments) {
        List<Object> values = new ArrayList<>();
        for (var argument : arguments) {
            values.add(argument.get()[0]);
        }
        return values;
    }

    @ParameterizedTest(name = "With seed difference={0}, should generate different arguments")
    @ValueSource(longs = {1, 100, 1000})
    @DisplayName("Should generate different arguments with different seeds")
    void shouldGenerateDifferentArgumentsWithDifferentSeeds(long seedDifference) {
        var provider1 = new TestArgumentsProvider(TEST_SEED, 3);
        var provider2 = new TestArgumentsProvider(TEST_SEED + seedDifference, 3);
        var generator = Generators.strings(5, 10);

        var arguments1 = provider1.generateArgumentsPublic(generator);
        var arguments2 = provider2.generateArgumentsPublic(generator);

        assertNotNull(arguments1);
        assertNotNull(arguments2);
        assertEquals(3, arguments1.size());
        assertEquals(3, arguments2.size());

        // Check if at least one argument is different
        boolean atLeastOneDifferent = false;
        for (int i = 0; i < arguments1.size(); i++) {
            if (!arguments1.get(i).get()[0].equals(arguments2.get(i).get()[0])) {
                atLeastOneDifferent = true;
                break;
            }
        }

        assertTrue(atLeastOneDifferent,
                "At least one argument should be different with different seeds");
    }
}
