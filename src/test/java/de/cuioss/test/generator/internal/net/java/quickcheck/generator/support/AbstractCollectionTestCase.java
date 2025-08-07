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
import de.cuioss.test.generator.internal.net.java.quickcheck.characteristic.AbstractCharacteristic;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.Generators;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static de.cuioss.test.generator.internal.net.java.quickcheck.QuickCheck.forAll;
import static de.cuioss.test.generator.internal.net.java.quickcheck.characteristic.ClassificationTestHelper.assertFrequencyGreater;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.ListGenerator.MAX_SIZE;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.ListGenerator.MIN_SIZE;
import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractCollectionTestCase {

    private static final String UPPER_HALF = "upperHalf";
    private static final String LOWER_HALF = "lowerHalf";

    @Test
    void generatorPositiveNormalLength() {
        var listsGenerator = normalDistributionGenerator();
        var expectedLowerHalfFrequency = 60.0;
        var expectedUpperHalfFrequency = 10.0;
        testCollectionGenerator(listsGenerator, expectedLowerHalfFrequency, expectedUpperHalfFrequency);
    }

    protected abstract Generator<Collection<Integer>> normalDistributionGenerator();

    @Test
    void generator() {
        var expectedLowerHalfFrequency = 40.0;
        var expectedUpperHalfFrequency = 40.0;
        var collectionGenerator = defaultGenerator();
        testCollectionGenerator(collectionGenerator, expectedLowerHalfFrequency, expectedUpperHalfFrequency);
    }

    protected abstract Generator<Collection<Integer>> defaultGenerator();

    void testCollectionGenerator(Generator<Collection<Integer>> generator, double expectedLowerHalfFrequency,
            double expectedUpperHalfFrequency) {
        testCollectionGenerator(generator, expectedLowerHalfFrequency, expectedUpperHalfFrequency, MIN_SIZE, MAX_SIZE);
    }

    void testCollectionGenerator(Generator<Collection<Integer>> listGenerator, double expectedLowerHalfFrequency,
            double expectedUpperHalfFrequency, final int lo, final int hi) {
        final var half = (hi - lo) / 2 + lo;
        AbstractCharacteristic<Collection<Integer>> characteristic = new AbstractCharacteristic<>() {

            @Override
            public void doSpecify(Collection<Integer> any) {
                var msg = "size was %s".formatted(any.size());
                assertTrue(any.size() <= hi, msg);
                assertTrue(any.size() >= lo, msg);
                for (Integer e : any) {
                    assertNotNull(e);
                }
                classify(any.size() <= half, LOWER_HALF);
                classify(any.size() >= half, UPPER_HALF);
            }
        };
        forAll(500, listGenerator, characteristic);
        var classification = characteristic.getClassification();
        assertFrequencyGreater(classification, expectedLowerHalfFrequency, LOWER_HALF);
        assertFrequencyGreater(classification, expectedUpperHalfFrequency, UPPER_HALF);
    }

    @Test
    void nonEmptyList() {
        forAll(nonEmpty(), new AbstractCharacteristic<>() {

            @Override
            protected void doSpecify(Collection<Integer> any) {
                assertFalse(any.isEmpty());
                assertTrue(any.size() <= ListGenerator.MAX_SIZE);
            }
        });
    }

    protected abstract Generator<Collection<Integer>> nonEmpty();

    <T extends Collection<Integer>> Generator<Collection<Integer>> cast(Generator<T> generator) {
        return Generators.cast(generator);
    }
}
