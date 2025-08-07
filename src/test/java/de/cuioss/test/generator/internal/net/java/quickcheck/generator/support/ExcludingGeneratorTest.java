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
package de.cuioss.test.generator.internal.net.java.quickcheck.generator.support;

import de.cuioss.test.generator.internal.net.java.quickcheck.Generator;
import de.cuioss.test.generator.internal.net.java.quickcheck.GeneratorException;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators;
import org.junit.jupiter.api.Test;

import java.util.List;

import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators.lists;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.fixedValues;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.integers;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable;
import static org.junit.jupiter.api.Assertions.*;

class ExcludingGeneratorTest {

    @Test
    void excludeNothing() {
        var value = integers().next();
        Generator<Integer> generator = CombinedGenerators.excludeValues(PrimitiveGenerators.fixedValues(value));
        assertEquals(value, generator.next());
    }

    @Test
    void excludeEverything() {
        var value = integers().next();
        Generator<Integer> excludeValues = CombinedGenerators.excludeValues(fixedValues(value), value);
        assertThrows(GeneratorException.class, excludeValues::next);
    }

    @Test
    void excludeCollection() {
        for (List<Integer> excluded : toIterable(lists(integers()))) {
            var value = CombinedGenerators.excludeValues(integers(), excluded).next();
            assertFalse(excluded.contains(value));
        }
    }

    @Test
    void excludeFixedValueGenerator() {
        for (List<Integer> fixedValues : toIterable(lists(integers(), 2))) {
            var excluded = fixedValues(fixedValues).next();
            var value = CombinedGenerators.excludeValues(fixedValues, excluded).next();
            assertNotEquals(excluded, value);
        }
    }
}
