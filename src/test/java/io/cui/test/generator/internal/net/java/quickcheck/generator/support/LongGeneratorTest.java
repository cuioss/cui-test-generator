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

import static io.cui.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.longs;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.positiveLongs;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable;
import static java.lang.Long.MAX_VALUE;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.cui.test.generator.internal.net.java.quickcheck.Generator;
import io.cui.test.generator.internal.net.java.quickcheck.characteristic.Classification;
import io.cui.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators;
import io.cui.test.generator.internal.net.java.quickcheck.generator.distribution.Distribution;

class LongGeneratorTest extends WholeNumberGeneratorTestCase<Long> {

    @Override
    protected Generator<Long> generator(byte lo, byte hi,
            Distribution distribution) {
        return PrimitiveGenerators.longs(lo, hi, distribution);
    }

    private static final int VALUES = 5;

    @Test
    void testBigLongValues() {
        var classifiction = new Classification();
        for (Long any : toIterable(longs(Long.MAX_VALUE - VALUES, Long.MAX_VALUE))) {
            classifiction.classifyCall(any);
        }
        for (var i = 0; i <= VALUES; i++) {
            Long val = MAX_VALUE - i;
            assertTrue(classifiction.getFrequency(val) < 100.0 / VALUES * 3);
            assertTrue(classifiction.getFrequency(val) > 100.0 / VALUES / 3);
        }
    }

    public Generator<Long> biggestLongs() {
        return longs(Long.MAX_VALUE - VALUES, Long.MAX_VALUE);
    }

    @Test
    void testLongRangeOverflow() {
        for (Long longs : toIterable(positiveLongs())) {
            var lowerBound = -1 * longs;
            long next = longs(lowerBound, Long.MAX_VALUE).next();
            assertTrue(lowerBound < 0, "lower bound valid " + lowerBound);
            assertTrue(lowerBound <= next);
        }
    }

    @Test
    void testLongRangeNegativeOverflow() {
        for (Long upperBound : toIterable(positiveLongs())) {
            long next = PrimitiveGenerators.longs(Long.MIN_VALUE, upperBound).next();
            assertTrue(upperBound > 0, "upper bound valid");
            assertTrue(next <= upperBound);
        }
    }

    @Test
    void testPositiveLongs() {
        for (Long any : toIterable(positiveLongs())) {
            assertTrue(any > 0);
        }
    }

    @Test
    void testPositiveLongWithUpperBound() {
        for (long bound : toIterable(positiveLongs())) {
            var next = PrimitiveGenerators.positiveLongs(bound).next();
            assertTrue(next > 0 && next <= bound);
        }
    }
}
