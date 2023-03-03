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
package de.cuioss.test.generator.internal.net.java.quickcheck.generator;

import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators.frequency;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators.pairs;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators.vectors;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.fixedValues;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.integers;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.nulls;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.internal.net.java.quickcheck.Generator;
import de.cuioss.test.generator.internal.net.java.quickcheck.characteristic.Classification;
import de.cuioss.test.generator.internal.net.java.quickcheck.collection.Pair;
import de.cuioss.test.generator.internal.net.java.quickcheck.collection.Triple;

class CombinedGeneratorsTest {

    private static final String NULL = "null";

    @Test
    @SuppressWarnings("java:S5778")
    void frequencyNotValidWithFrequency0() {
        assertThrows(IllegalArgumentException.class, () -> frequency(nulls(), 0));
    }

    @Test
    void frequencyTrivial() {
        var classification = new Classification();
        for (Integer i : toIterable(frequency(PrimitiveGenerators.<Integer> nulls(), 1))) {
            classification.classifyCall(i == null, NULL);
        }
        assertEquals(100, classification.getFrequency(NULL), 0);
    }

    @Test
    void frequencyNullAndIntegerEqualDistribution() {
        Generator<Integer> nulls = nulls();
        var classification = new Classification();
        for (Integer i : toIterable(frequency(nulls, 1).add(integers(), 1))) {
            classification.classifyCall(i == null, NULL);
        }
        assertEqualDistribution(classification.getFrequency(NULL));
    }

    @Test
    void frequency3NullAnd1Integer() {
        Generator<Integer> nulls = nulls();
        var classification = new Classification();
        for (Integer i : toIterable(frequency(nulls, 3).add(integers(), 1))) {
            classification.classifyCall(i == null, NULL);
        }
        var nullFrequency = classification.getFrequency(NULL);
        assertTrue(nullFrequency > 50 && nullFrequency < 90, () -> "" + nullFrequency);
    }

    @Test
    void oneOf() {
        Generator<Integer> nulls = nulls();
        Generator<Integer> gen = CombinedGenerators.oneOf(nulls).add(integers());
        var classification = new Classification();
        for (Integer i : toIterable(gen)) {
            classification.classifyCall(i == null, NULL);
        }
        assertEqualDistribution(classification.getFrequency(NULL));
    }

    @Test
    void vectorEmpty() {
        Generator<List<Integer>> gen = vectors(PrimitiveGenerators.integers(), 0);
        var next = gen.next();
        assertTrue(next.isEmpty());
    }

    @Test
    void vector() {
        Generator<List<Integer>> gen = vectors(PrimitiveGenerators.integers(), 3);
        var actual = gen.next();
        assertEquals(3, actual.size());
        for (Integer i : actual) {
            assertNotNull(i);
        }
    }

    @Test
    void pair() {

        var returnByFirst = new Object();
        var returnBySecond = new Object();

        Generator<Pair<Object, Object>> pairs = pairs(fixedValues(returnByFirst), fixedValues(returnBySecond));

        for (Pair<Object, Object> p : toIterable(pairs)) {
            assertSame(returnByFirst, p.getFirst());
            assertSame(returnBySecond, p.getSecond());
        }
    }

    @Test
    void sortedPair() {
        Generator<Pair<Integer, Integer>> pairs = CombinedGenerators.sortedPairs(integers());
        for (Pair<Integer, Integer> pair : toIterable(pairs)) {
            assertTrue(pair.getFirst().compareTo(pair.getSecond()) <= 0);
        }
    }

    @Test
    void triple() {
        var returnByFirst = new Object();
        var returnBySecond = new Object();
        var returnByThird = new Object();

        Generator<Triple<Object, Object, Object>> triples = CombinedGenerators.triples(fixedValues(returnByFirst),
                fixedValues(returnBySecond), fixedValues(returnByThird));

        for (Triple<Object, Object, Object> triple : toIterable(triples)) {
            assertSame(returnByFirst, triple.getFirst());
            assertSame(returnBySecond, triple.getSecond());
            assertSame(returnByThird, triple.getThird());
        }
    }

    @Test
    void sortedTriple() {
        Generator<Triple<Integer, Integer, Integer>> pairs = CombinedGenerators.sortedTriple(integers());
        for (Triple<Integer, Integer, Integer> triple : toIterable(pairs)) {
            assertTrue(triple.getFirst().compareTo(triple.getSecond()) <= 0);
            assertTrue(triple.getSecond().compareTo(triple.getThird()) <= 0);
        }
    }

    @Test
    void nullsAnd() {
        var classification = new Classification();
        for (Integer i : toIterable(CombinedGenerators.nullsAnd(integers()))) {
            classification.classifyCall(i == null, NULL);
        }
        var nullFrequency = classification.getFrequency(NULL);
        assertTrue(nullFrequency > 5 && nullFrequency < 65);
    }

    @Test
    void testNullsAndWithVargsParameter() {
        List<Object> expected = new ArrayList<>(asList(new Object(), new Object(), null));
        Generator<Object> generator = CombinedGenerators.nullsAnd(expected.get(0), expected.get(1));
        for (var i = 0; i < 1 << 10 && expected.size() > 0; i++) {
            expected.remove(generator.next());
        }
        assertTrue(expected.isEmpty());
    }

    private void assertEqualDistribution(double frequency) {
        assertTrue(frequency > 30 && frequency < 80);
    }
}
