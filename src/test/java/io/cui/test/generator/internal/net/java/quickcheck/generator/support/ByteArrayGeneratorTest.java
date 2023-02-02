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

import static io.cui.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.fixedValues;

import org.junit.jupiter.api.Test;

import io.cui.test.generator.internal.net.java.quickcheck.Generator;
import io.cui.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators;
import io.cui.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators;

class ByteArrayGeneratorTest extends PrimitiveArrayGeneratorTestCase {

    @Test
    void testGeneratePrimitiveArrayWithSizeParameter() {
        final long min = Byte.MIN_VALUE;
        final long max = Byte.MAX_VALUE;
        testGeneratePrimitiveArrayWithSizeParameter(min, max);
    }

    @Override
    protected Object createGeneratorValue(int size) {
        return CombinedGenerators.byteArrays(fixedValues(size)).next();
    }

    @Test
    void testGenerateDefaultPrimitiveArray() {
        testGenerateDefaultPrimitiveArray(ByteArrayGenerator.MIN_SIZE,
                ByteArrayGenerator.MAX_SIZE);
    }

    @Override
    protected Generator<Object> createGenerator() {

        return new AbstractTransformerGenerator<>(
                CombinedGenerators.byteArrays()) {

            @Override
            protected Object transform(Generator<byte[]> inputGenerator) {
                return inputGenerator.next();
            }
        };
    }

    @Override
    protected Object createGeneratorValue(long min, long max, int size) {
        return CombinedGenerators.byteArrays(
                PrimitiveGenerators.bytes((byte) min, (byte) max),
                PrimitiveGenerators.fixedValues(size)).next();
    }

    @Override
    protected Class<?> getType() {
        return byte.class;
    }
}
