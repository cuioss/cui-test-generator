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
package io.cui.test.generator.internal.net.java.quickcheck.generator;

import static io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.cui.test.generator.internal.net.java.quickcheck.Generator;
import io.cui.test.generator.internal.net.java.quickcheck.characteristic.Classification;
import io.cui.test.generator.internal.net.java.quickcheck.generator.distribution.Distribution;

class DistributionTest {

    private static final String LT_0_15 = "<0.15";
    private static final String GT_0_85 = ">0.85";
    private static final String GT_0 = ">0";
    private static final String LT_0 = "<0";
    private static final int RUNS = 1000;

    private static class TestGenerator implements Generator<Double> {

        private final Distribution distribution;

        public TestGenerator(Distribution distribution) {
            this.distribution = distribution;
        }

        @Override
        public Double next() {
            return distribution.nextRandomNumber();
        }
    }

    @Test
    void testPositiveGausian() {
        var classification = new Classification();
        for (Double any : toIterable(new TestGenerator(Distribution.POSITIV_NORMAL), RUNS)) {
            doClassification(classification, any);
        }

        assertEquals(0, classification.getFrequency(LT_0));
        assertTrue(classification.getFrequency(LT_0_15) > 25.0);
        assertTrue(classification.getFrequency(GT_0_85) < 2.0);
    }

    @Test
    void testNegativGausian() {
        var classification = new Classification();
        for (Double any : toIterable(new TestGenerator(Distribution.NEGATIV_NORMAL), RUNS)) {
            doClassification(classification, any);
        }
        assertEquals(0, classification.getFrequency(LT_0));
        assertTrue(classification.getFrequency(LT_0_15) < 2.0);
        assertTrue(classification.getFrequency(GT_0_85) > 25.0);

    }

    @Test
    void testInvertedGausian() {
        var classification = new Classification();
        for (Double any : toIterable(new TestGenerator(Distribution.INVERTED_NORMAL), RUNS)) {
            doClassification(classification, any);
        }
        assertEquals(0.0, classification.getFrequency(LT_0));
        assertTrue(classification.getFrequency(LT_0_15) > 27.0);
        assertTrue(classification.getFrequency(GT_0_85) > 27.0, classification::toString);
    }

    private void doClassification(Classification classification, Double any) {
        classification.doClassify(any < 0, LT_0);
        classification.doClassify(any > 0, GT_0);
        classification.doClassify(any < 0.15, LT_0_15);
        classification.doClassify(any > 0.85, GT_0_85);
        assertTrue(any >= 0);
        assertTrue(any <= 1.0);
        classification.call();
    }
}
