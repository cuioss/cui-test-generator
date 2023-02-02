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

import org.junit.jupiter.api.Test;

import io.cui.test.generator.internal.net.java.quickcheck.Generator;
import io.cui.test.generator.internal.net.java.quickcheck.QuickCheck;
import io.cui.test.generator.internal.net.java.quickcheck.characteristic.AbstractCharacteristic;

abstract class AbstractFixedValueGeneratorTestCase<T> {

    @Test
    void testNull() {

        QuickCheck.forAll(generator(), new AbstractCharacteristic<>() {

            @Override
            public void doSpecify(Object any) {
                assertEquals(value(), any);
            }

        });
    }

    protected abstract Generator<Object> generator();

    protected abstract T value();
}
