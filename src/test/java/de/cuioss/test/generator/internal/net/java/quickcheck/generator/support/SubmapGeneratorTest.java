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
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators.maps;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators.oneOf;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.integers;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.longs;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable;
import static org.junit.jupiter.api.Assertions.*;

class SubmapGeneratorTest {

    @Test
    void submap() {
        for (Map<Long, Integer> supermap : toIterable(maps(longs(), integers()))) {
            var submap = CombinedGenerators.maps(supermap).next();
            assertTrue(supermap.entrySet().containsAll(submap.entrySet()));
        }
    }

    @Test
    void sizedSubmap() {
        var supermap = maps(longs(), integers()).next();
        for (int size : toIterable(PrimitiveGenerators.integers(0, supermap.size()))) {
            var submap = CombinedGenerators.maps(supermap, PrimitiveGenerators.fixedValues(size)).next();
            assertTrue(supermap.entrySet().containsAll(submap.entrySet()));
            assertEquals(size, submap.size());
        }
    }

    @Test
    void invalidSize() {
        var supermap = maps(longs(), integers()).next();
        Generator<Integer> invalidSizes = oneOf(integers(Integer.MIN_VALUE, -1)).add(integers(supermap.size() + 1));
        for (Integer size : toIterable(invalidSizes)) {
            final var generator = CombinedGenerators.maps(supermap, PrimitiveGenerators.fixedValues(size));
            assertThrows(IllegalArgumentException.class, generator::next);
        }
    }
}
