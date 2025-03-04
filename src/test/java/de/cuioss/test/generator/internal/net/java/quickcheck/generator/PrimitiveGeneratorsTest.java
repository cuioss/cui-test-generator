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

import de.cuioss.test.generator.internal.net.java.quickcheck.Generator;
import de.cuioss.test.generator.internal.net.java.quickcheck.characteristic.Classification;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static de.cuioss.test.generator.internal.net.java.quickcheck.characteristic.ClassificationTestHelper.assertFrequencyGreater;
import static de.cuioss.test.generator.internal.net.java.quickcheck.characteristic.ClassificationTestHelper.assertFrequencySmaller;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.*;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PrimitiveGeneratorsTest {

    @Test
    void fixedValuesGenerator() {
        final var values = new Integer[]{integers().next(), integers().next(), integers().next()};
        var classification = new Classification();
        for (Integer i : toIterable(fixedValues(values))) {
            classification.classifyCall(i);
            assertTrue(Arrays.asList(values).contains(i));
        }
        assertFrequencyGreater(classification, 20.0, values);
    }

    enum TestEnum {
        A, B, C
    }

    @Test
    void enums() {
        Generator<TestEnum> generator = enumValues(TestEnum.class);
        var classification = new Classification();
        for (TestEnum e : toIterable(generator)) {
            classification.classifyCall(e);
        }

        assertFrequencyGreater(classification, expectedFrequency(TestEnum.values().length), TestEnum.values());
    }

    private double expectedFrequency(int elements) {
        var tolerance = 0.7;
        return 100 / elements * tolerance;
    }

    @Test
    void enumsExcept() {
        var excluded = enumValues(TestEnum.class).next();
        Generator<TestEnum> generator = enumValues(TestEnum.class, excluded);
        var classification = new Classification();
        for (TestEnum e : toIterable(generator)) {
            classification.classifyCall(e);
        }

        for (TestEnum e : TestEnum.values()) {
            if (e == excluded) {
                assertFrequencySmaller(classification, 0.0, e);
            } else {
                assertFrequencyGreater(classification, expectedFrequency(TestEnum.values().length - 1), e);
            }
        }
    }

    @Test
    @SuppressWarnings("java:S5778")
    void enumAllExcluded() {
        assertThrows(IllegalArgumentException.class,
                () -> PrimitiveGenerators.enumValues(TestEnum.class, TestEnum.values()));
    }

    @Test
    void booleansGeneration() {
        var classification = new Classification();
        for (Boolean b : toIterable(PrimitiveGenerators.booleans())) {
            classification.classifyCall(b);
        }
        assertTrue(classification.getFrequency(TRUE) > 35);
        assertTrue(classification.getFrequency(FALSE) > 35);
    }
}
