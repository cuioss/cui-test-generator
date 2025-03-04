/*
 * Licensed to the author under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.test.generator.internal.net.java.quickcheck.generator.support;

import de.cuioss.test.generator.internal.net.java.quickcheck.Generator;
import de.cuioss.test.generator.internal.net.java.quickcheck.collection.Pair;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators.pairs;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators.sortedLists;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.integers;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.ListGenerator.MAX_SIZE;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.ListGenerator.MIN_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SortedListTest {

    @Test
    void sortedList() {
        for (List<Integer> any : toIterable(sortedLists(PrimitiveGenerators.integers()))) {
            var sorted = new ArrayList<>(any);
            Collections.sort(sorted);
            assertEquals(any, sorted);
        }
    }

    @Test
    void sortedWithBounds() {
        for (Pair<Integer, Integer> sizes : toIterable(sizes())) {
            var bounds = bounds(sizes);
            var sortedList = CombinedGenerators.sortedLists(integers(), bounds.getFirst(), bounds.getSecond()).next();
            assertBounds(bounds, sortedList);
        }
    }

    @Test
    void sortedWithBoundsGenerator() {
        for (Pair<Integer, Integer> sizes : toIterable(sizes())) {
            var bounds = bounds(sizes);
            var sortedList = CombinedGenerators.sortedLists(integers(), integers(bounds.getFirst(), bounds.getSecond()))
                    .next();
            assertBounds(bounds, sortedList);
        }
    }

    private void assertBounds(Pair<Integer, Integer> bounds, List<Integer> sortedList) {
        assertTrue(sortedList.size() <= bounds.getSecond());
        assertTrue(sortedList.size() >= bounds.getFirst());
    }

    private Pair<Integer, Integer> bounds(Pair<Integer, Integer> sizes) {
        int lo = sizes.getFirst();
        var hi = sizes.getSecond() + lo + 1;
        return new Pair<>(lo, hi);
    }

    public Generator<Pair<Integer, Integer>> sizes() {
        var sizes = integers(MIN_SIZE, MAX_SIZE / 2);
        return pairs(sizes, sizes);
    }
}
