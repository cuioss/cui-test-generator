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
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;

import static de.cuioss.test.generator.internal.net.java.quickcheck.QuickCheck.forAll;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.integers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

abstract class PrimitiveArrayGeneratorTestCase {

    @Test
    void generatePrimitiveArrayWithValueRangeAndSizeParameters() {
        var size = 10;
        var min = 2L;
        var max = 4L;
        var arrayValue = createGeneratorValue(min, max, size);
        assertArrayValues(arrayValue, size, min, max);
    }

    void assertArrayValues(int size, long min, long max) {
        var arrayValue = createGeneratorValue(size);
        assertArrayValues(arrayValue, size, min, max);
    }

    protected void assertArrayValuesMinMaxSet(int size, long min, long max) {
        var arrayValue = createGeneratorValue(min, max, size);
        assertArrayValues(arrayValue, size, min, max);
    }

    private void assertArrayValues(Object arrayValue, int size, long min, long max) {
        Class<?> componentType = arrayValue.getClass().getComponentType();
        assertTrue(componentType.isPrimitive());
        assertEquals(getType(), arrayValue.getClass().getComponentType());
        assertLength(arrayValue, size, size);
        for (var i = 0; i < size; i++) {
            var value = Array.get(arrayValue, i);
            var longValue = ((Number) value).longValue();
            assertRange(longValue, min, max);
        }
    }

    protected abstract Class<?> getType();

    private void assertRange(long longValue, long min, long max) {
        assertTrue(longValue <= max && longValue >= min);
    }

    void assertLength(Object arrayValue, int minSize, int maxSize) {
        assertEquals(Array.getLength(arrayValue), minSize, maxSize);
    }

    protected abstract Object createGeneratorValue(int size);

    protected abstract Object createGeneratorValue(long min, long max, int size);

    protected abstract Generator<Object> createGenerator();

    void testGenerateDefaultPrimitiveArray(final int minSize, final int maxSize) {
        forAll(createGenerator(), new AbstractCharacteristic<>() {

            @Override
            protected void doSpecify(Object array) {
                assertLength(array, minSize, maxSize);
            }
        });
    }

    void testGeneratePrimitiveArrayWithSizeParameter(final long min, final long max) {
        forAll(integers(0, 3), new AbstractCharacteristic<>() {

            @Override
            protected void doSpecify(Integer size) {
                assertArrayValues(size, min, max);
            }
        });
    }
}
