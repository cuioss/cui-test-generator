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

import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.generator.internal.RandomContext;
import de.cuioss.test.generator.junit.EnableGeneratorController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies that every {@link GeneratorType} produces a reproducible sequence of values when the
 * shared {@link RandomContext} is seeded with the same value. This closes the gap where individual
 * generators lacked explicit reproducibility coverage.
 */
@EnableGeneratorController
@DisplayName("Every GeneratorType should be reproducible under a fixed seed")
class GeneratorTypeReproducibilityTest {

    private static final long SEED = 42L;

    private static final int SEQUENCE_LENGTH = 5;

    @ParameterizedTest
    @EnumSource(GeneratorType.class)
    @DisplayName("produce identical sequences when re-seeded with the same value")
    void shouldBeReproducible(GeneratorType type) {
        TypedGenerator<?> generator = GeneratorTypeFactory.createGenerator(type);

        RandomContext.setSeed(SEED);
        List<Object> first = generate(generator);

        RandomContext.setSeed(SEED);
        List<Object> second = generate(generator);

        assertEquals(first, second,
                "Generator for " + type + " must reproduce the same sequence for seed " + SEED);
    }

    private static List<Object> generate(TypedGenerator<?> generator) {
        List<Object> values = new ArrayList<>(SEQUENCE_LENGTH);
        for (int i = 0; i < SEQUENCE_LENGTH; i++) {
            values.add(generator.next());
        }
        return values;
    }
}
