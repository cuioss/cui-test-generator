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

import de.cuioss.test.generator.internal.net.java.quickcheck.QuickCheck;
import de.cuioss.test.generator.internal.net.java.quickcheck.characteristic.AbstractCharacteristic;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.distribution.Distribution;
import org.junit.jupiter.api.Test;

import static de.cuioss.test.generator.internal.net.java.quickcheck.characteristic.ClassificationTestHelper.assertFrequencyGreater;
import static org.junit.jupiter.api.Assertions.assertEquals;

abstract class WholeNumberGeneratorTestCase<T extends Number> extends NumberGeneratorTestCase<T> {

    @Test
    void distributionOfBoundsValues() {

        AbstractCharacteristic<T> characteristic = new AbstractCharacteristic<>() {

            @Override
            protected void doSpecify(T any) {
                classify(any.longValue());
            }
        };
        byte high = 1;
        byte low = -1;
        QuickCheck.forAll(1000, generator(low, high, Distribution.UNIFORM), characteristic);

        assertEquals(3, characteristic.getClassification().getCategories().size());
        assertFrequencyGreater(characteristic.getClassification(), 28.0, -1L, 0L, 1L);
    }

}
