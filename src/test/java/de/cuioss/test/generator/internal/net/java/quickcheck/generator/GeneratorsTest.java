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
package de.cuioss.test.generator.internal.net.java.quickcheck.generator;

import de.cuioss.test.generator.internal.net.java.quickcheck.Generator;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators.lists;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.integers;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GeneratorsTest {

    @Test
    void integerGeneratorIsObjectGenerator() {
        Generator<Object> objects = Generators.cast(integers());
        assertInstanceOf(Integer.class, objects.next());
    }

    @Test
    void integerGeneratorIsIntegerGenerator() {
        Generator<Integer> objects = Generators.cast(integers());
        assertNotNull(objects.next());
    }

    @Test
    void listGeneratorIsCollectionGenerator() {
        Generator<List<Integer>> base = lists(integers());
        Generator<Collection<Integer>> objects = Generators.cast(base);
        assertInstanceOf(List.class, objects.next());
    }

    @Test
    void shouldCreateStringGenerator() {
        // Act
        var generator = Generators.strings(Generators.characters());

        // Assert
        assertNotNull(generator, "Generator should not be null");
        assertNotNull(generator.next(), "Generated string should not be null");
    }

    @Test
    void shouldCreateExcludeGenerator() {
        // Act
        var generator = Generators.excludeValues(Generators.characters(), 'a');

        // Assert
        assertNotNull(generator, "Generator should not be null");
        assertNotNull(generator.next(), "Generated string should not be null");
    }

}
