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
package io.cui.test.generator.internal.net.java.quickcheck.generator.iterable;

import static io.cui.test.generator.internal.net.java.quickcheck.QuickCheck.MAX_NUMBER_OF_RUNS;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.CombinedGeneratorSamples.anyNonEmptySet;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators.ensureValues;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators.lists;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.PrimitiveGeneratorSamples.anyInteger;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.integers;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.strings;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.PrimitiveGeneratorsIterables.someIntegers;
import static java.util.Collections.emptySet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.jupiter.api.Test;

import io.cui.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators;

class IterablesTest {

    @Test
    void iterableDefaultNumberOfRuns() {
        var expectedNumberOfRuns = MAX_NUMBER_OF_RUNS;

        var potentialValues = potentialValues(expectedNumberOfRuns);

        Iterable<Integer> iterable = Iterables.toIterable(ensureValues(potentialValues));
        var actual = run(iterable);

        assertRun(potentialValues, expectedNumberOfRuns, actual);
    }

    @Test
    void iterableNumberOfRuns() {
        int expectedNumberOfRuns = anyInteger(0, MAX_NUMBER_OF_RUNS);

        var potentialValues = potentialValues(expectedNumberOfRuns);

        Iterable<Integer> iterable = Iterables.toIterable(ensureValues(potentialValues), expectedNumberOfRuns);
        var actual = run(iterable);

        assertRun(potentialValues, expectedNumberOfRuns, actual);
    }

    @Test
    @SuppressWarnings("java:S5778")
    void invalidNumberOfRuns() {
        for (int invalid : someIntegers(Integer.MIN_VALUE, -1)) {
            assertThrows(IllegalArgumentException.class, () -> Iterables.toIterable(strings(), invalid));
        }
    }

    private void assertRun(List<Integer> potentialValues, int expectedNumberOfRuns, List<Integer> actual) {
        var expected = potentialValues.subList(0, expectedNumberOfRuns);
        assertEquals(expected, actual);
    }

    private List<Integer> run(Iterable<Integer> iterable) {
        List<Integer> actual = new ArrayList<>();
        for (Integer t : iterable) {
            actual.add(t);
        }
        return actual;
    }

    private List<Integer> potentialValues(int expectedNumberOfRuns) {
        var enoughValues = expectedNumberOfRuns + 1;
        return lists(integers(), enoughValues, enoughValues).next();
    }

    @Test
    void consumedIteratorHasNext() {
        Iterator<?> iterator = consumedIterator();
        assertFalse(iterator.hasNext());
    }

    @Test
    void consumedIteratorNext() {
        Iterator<?> iterator = consumedIterator();
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void removeNotSupported() {
        Iterator<?> iterator = anyIterator();
        assertThrows(UnsupportedOperationException.class, iterator::remove);
    }

    @Test
    void generatorMayNotBeNull() {
        assertThrows(NullPointerException.class, () -> Iterables.toIterable(null));
    }

    @Test
    void sizeOfWhenIterableIsEmpty() {
        assertEquals(0, Iterables.sizeOf(emptySet()));
    }

    @Test
    void sizeOf() {
        Set<Object> set = anyNonEmptySet(PrimitiveGenerators.objects());
        assertEquals(set.size(), Iterables.sizeOf(set));
    }

    private Iterator<?> consumedIterator() {
        Iterator<?> iterator = anyIterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
        return iterator;
    }

    private Iterator<Integer> anyIterator() {
        return Iterables.toIterable(PrimitiveGenerators.integers()).iterator();
    }
}
