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
package io.cui.test.generator.internal.net.java.quickcheck.generator.support;

import static io.cui.test.generator.internal.net.java.quickcheck.QuickCheck.forAll;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators.sortedLists;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.fixedValues;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.integers;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.positiveIntegers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import io.cui.test.generator.internal.net.java.quickcheck.Generator;
import io.cui.test.generator.internal.net.java.quickcheck.characteristic.AbstractCharacteristic;
import io.cui.test.generator.internal.net.java.quickcheck.characteristic.Classification;
import io.cui.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators;
import io.cui.test.generator.internal.net.java.quickcheck.generator.distribution.Distribution;
import io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables;

class IntegerGeneratorTest extends WholeNumberGeneratorTestCase<Integer> {

    @Test
    void testGeneratesNotNull() {
        for (int i = 0; i < 100; i++) {
            assertNotNull(PrimitiveGenerators.integers().next());
        }
    }

    @Test
    void testGeneratorDistribution() {
        final String gt0 = ">0";
        final String lt0 = "<0";

        AbstractCharacteristic<Integer> characteristic = new AbstractCharacteristic<>() {

            @Override
            protected void doSpecify(Integer any) {
                classify(any < 0, lt0);
                classify(any > 0, gt0);
            }
        };
        forAll(integers(), characteristic);
        assertTrue(characteristic.getClassification().getFrequency(gt0) > 30);
        assertTrue(characteristic.getClassification().getFrequency(lt0) > 30);

    }

    @Test
    void testZero() {
        Generator<Integer> integerGenerator = PrimitiveGenerators.integers(0, 0);
        forAll(integerGenerator, new AbstractCharacteristic<>() {

            @Override
            protected void doSpecify(Integer any) {
                Integer i = 0;
                assertEquals(i, any);

            }
        });
    }

    @Test
    void testOneAndZero() {
        Generator<Integer> integerGenerator = PrimitiveGenerators
                .integers(0, 1);
        Classification classification = new Classification();
        for (Integer i : Iterables.toIterable(integerGenerator))
            classification.classifyCall(i);
        assertTrue(classification.getFrequency(0) > 0);
        assertTrue(classification.getFrequency(1) > 0);
    }

    @Override
    @Test
    void testBounds() {
        List<Integer> loHi = sortedLists(integers(), fixedValues(2)).next();
        int lo = loHi.get(0);
        int hi = loHi.get(1);
        Generator<Integer> integerGenerator = integers(lo, hi);
        testBounds(integerGenerator, lo, hi);
    }

    @Test
    void testLowerBound() {
        Integer low = integers().next();
        Generator<Integer> integerGenerator = PrimitiveGenerators.integers(low);
        testBounds(integerGenerator, low, Integer.MAX_VALUE);
    }

    @Override
    @Test
    void testBoundsGausian() {
        List<Integer> loHi = sortedLists(integers(), fixedValues(2)).next();
        int lo = loHi.get(0);
        int hi = loHi.get(1);
        Generator<Integer> integerGenerator = PrimitiveGenerators.integers(lo,
                hi, Distribution.POSITIV_NORMAL);
        testBounds(integerGenerator, lo, hi);
    }

    @Test
    void testPositiveIntegers() {
        forAll(positiveIntegers(), new AbstractCharacteristic<>() {

            @Override
            protected void doSpecify(Integer any) {
                assertTrue(any > 0);
            }
        });
    }

    @Test
    void testPositiveIntegersWithUpperBound() {
        final Integer upper = positiveIntegers().next();
        forAll(positiveIntegers(upper), new AbstractCharacteristic<>() {

            @Override
            protected void doSpecify(Integer any) {
                assertTrue(any > 0 && any < upper);
            }
        });
    }

    private void testBounds(Generator<Integer> integerGenerator, final int lo,
            final int hi) {
        forAll(1000, integerGenerator, new AbstractCharacteristic<>() {

            @Override
            protected void doSpecify(Integer any) {
                assertTrue(any <= hi);
                assertTrue(any >= lo);
            }
        });
    }

    @Override
    protected Generator<Integer> generator(byte lo, byte hi,
            Distribution distribution) {
        return integers(lo, hi, distribution);
    }
}
