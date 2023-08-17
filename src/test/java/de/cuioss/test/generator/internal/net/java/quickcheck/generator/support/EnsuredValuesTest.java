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

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.internal.net.java.quickcheck.Generator;
import de.cuioss.test.generator.internal.net.java.quickcheck.StatefulGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators;

class EnsuredValuesTest {

    private static final String[] STRING_VALUES = { "a", "b", "c", "d", "e", "f", "g", "h" };
    private static final String DISJUNCT_STRING = "z";
    private static final Set<String> VALUES = Set.of(STRING_VALUES);
    static {
        assert !VALUES.contains(DISJUNCT_STRING);
    }

    @Test
    void rejectConstructionForEmptyIterable() {
        assertThrows(IllegalArgumentException.class, CombinedGenerators::ensureValues);
    }

    @Test
    void generateEachValueOneTimeInASequence() {
        assertGenerateEachValueOnce(CombinedGenerators.ensureValues(VALUES));
    }

    @Test
    void generateEachValueOneTimeInASequenceArray() {
        assertGenerateEachValueOnce(CombinedGenerators.ensureValues(STRING_VALUES));
    }

    private void assertGenerateEachValueOnce(Generator<String> generator) {
        Set<String> rest = new HashSet<>(VALUES);
        for (var idx = 0; idx < VALUES.size(); idx++) {
            assertTrue(rest.remove(generator.next()));
        }
        assertTrue(rest.isEmpty());
    }

    @Test
    void generateRandomCollectionValuesAfterFirstSequence() {
        Generator<String> generator = CombinedGenerators.ensureValues(new HashSet<>(VALUES));
        for (var idx = 0; idx < VALUES.size(); idx++) {
            generator.next();
        }
        for (var idx = 0; idx < VALUES.size() * 4; idx++) {
            assertTrue(VALUES.contains(generator.next()));
        }
    }

    @Test
    void returnGeneratorValuesAfterFirstSequence() {
        var len = 2;
        Generator<String> strings = new StringGenerator(new FixedValuesGenerator<>(len), new CharacterGenerator());
        Generator<String> generator = CombinedGenerators.ensureValues(new HashSet<>(VALUES), strings);
        for (var idx = 0; idx < VALUES.size(); idx++) {
            assertNotEquals(generator.next().length(), len);
        }
        for (var idx = 0; idx < VALUES.size() * 4; idx++) {
            assertEquals(generator.next().length(), len);
        }
    }

    @Test
    void resetGenerator() {
        StatefulGenerator<Boolean> generator = CombinedGenerators.ensureValues(Collections.singleton(true),
                PrimitiveGenerators.fixedValues(false));
        assertTrue(generator.next());
        assertFalse(generator.next());
        generator.reset();
        assertTrue(generator.next());
        assertFalse(generator.next());
    }

    @Test
    void generateEnsuredValuesInWindow() {
        Generator<String> generator = CombinedGenerators.ensureValues(VALUES, VALUES.size(),
                new FixedValuesGenerator<>(DISJUNCT_STRING));
        var rest = new HashSet<>(VALUES);
        for (var i = 0; i < VALUES.size(); i++) {
            assertTrue(rest.remove(generator.next()));
        }
        assertTrue(rest.isEmpty());
        assertEquals(DISJUNCT_STRING, generator.next());
    }

    @Test
    void generateEnsuredValuesInBiggerWindow() {
        var window = VALUES.size() * 2;
        Generator<String> generator = CombinedGenerators.ensureValues(VALUES, window,
                new FixedValuesGenerator<>(DISJUNCT_STRING));
        var rest = new HashSet<>(VALUES);
        for (var i = 0; i < window; i++) {
            var n = generator.next();
            assertTrue(rest.remove(n) || n.equals(DISJUNCT_STRING));
        }
        assertTrue(rest.isEmpty());
        assertEquals(DISJUNCT_STRING, generator.next());
    }

    @Test
    void generateEnsuredValuesSpreadInWindow() {
        List<String> values = asList(STRING_VALUES);
        var window = values.size() * 20;
        Generator<String> generator = CombinedGenerators.ensureValues(values, window,
                new FixedValuesGenerator<>(DISJUNCT_STRING));
        var c = new String[STRING_VALUES.length];
        for (var i = 0; i < c.length; i++) {
            c[i] = generator.next();
        }
        // Although it is not part of contract we know that the values are taken
        // in order from the ensured values iterable. If the values are nicely spread
        // over the
        // window they should not be the first values to generate.
        // (Note: this is only were likely to work)
        assertFalse(Arrays.equals(c, STRING_VALUES));
        assert c.length == STRING_VALUES.length;

        generator = CombinedGenerators.ensureValues(values, c.length, new FixedValuesGenerator<>(DISJUNCT_STRING));
        for (var i = 0; i < c.length; i++) {
            c[i] = generator.next();
        }
        assertArrayEquals(STRING_VALUES, c, "the generation order expectation is not correct");
    }

    @Test
    @SuppressWarnings("java:S5778")
    void invalidWindowSize() {
        int invalidWindow = PrimitiveGenerators.integers(0, VALUES.size() - 1).next();
        assertThrows(IllegalArgumentException.class,
                () -> CombinedGenerators.ensureValues(VALUES, invalidWindow, PrimitiveGenerators.nulls()));
    }
}
