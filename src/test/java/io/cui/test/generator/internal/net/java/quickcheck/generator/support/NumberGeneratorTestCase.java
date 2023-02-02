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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.cui.test.generator.internal.net.java.quickcheck.Generator;
import io.cui.test.generator.internal.net.java.quickcheck.QuickCheck;
import io.cui.test.generator.internal.net.java.quickcheck.characteristic.AbstractCharacteristic;
import io.cui.test.generator.internal.net.java.quickcheck.characteristic.Classification;
import io.cui.test.generator.internal.net.java.quickcheck.generator.distribution.Distribution;

abstract class NumberGeneratorTestCase<T extends Number> {

    private static final String GT_0 = ">0";
    private static final String LT_0 = "<0";
    private static final byte LOW_VALUE = -10;
    private static final byte HIGH_VALUE = 10;

    @Test
    void testNotNull() {
        QuickCheck.forAll(generator(LOW_VALUE, HIGH_VALUE, Distribution.UNIFORM),
                new AbstractCharacteristic<>() {

                    @Override
                    protected void doSpecify(T any) {
                        assertNotNull(any);
                    }
                });
    }

    protected abstract Generator<T> generator(byte lo, byte hi,
            Distribution distribution);

    @Test
    void testBounds() {

        double expectedLt = 0.2;
        double expectedGt = 0.2;
        Generator<T> generator = generator(LOW_VALUE, HIGH_VALUE, Distribution.UNIFORM);
        assertValue(generator, expectedLt, expectedGt);
    }

    @Test
    void testDiffNull() {
        assertEquals(LOW_VALUE, generator(LOW_VALUE, LOW_VALUE, Distribution.UNIFORM).next()
                .doubleValue());
        assertEquals(LOW_VALUE, generator(LOW_VALUE, LOW_VALUE, Distribution.POSITIV_NORMAL)
                .next().doubleValue());
    }

    @Test
    void testBoundsGausian() {
        double expectedLt = 0.5;
        double expectedGt = 0.2;
        Generator<T> generator = generator(LOW_VALUE, HIGH_VALUE,
                Distribution.POSITIV_NORMAL);
        assertValue(generator, expectedLt, expectedGt);
    }

    private void assertValue(Generator<T> doubleGenerator, double expectedLt,
            double expectedGt) {
        AbstractCharacteristic<T> characteristic = new AbstractCharacteristic<>() {

            @Override
            protected void doSpecify(T any) {
                double anyDoubleValue = any.doubleValue();
                assertTrue(LOW_VALUE <= anyDoubleValue
                        && HIGH_VALUE >= anyDoubleValue);
                classify(anyDoubleValue < 0, LT_0);
                classify(anyDoubleValue > 0, GT_0);
            }
        };
        QuickCheck.forAll(doubleGenerator, characteristic);
        Classification classification = characteristic.getClassification();

        assertTrue(classification.getFrequency(LT_0) > expectedLt);
        assertTrue(classification.getFrequency(GT_0) > expectedGt);

    }
}
