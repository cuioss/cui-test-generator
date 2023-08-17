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
package de.cuioss.test.generator.internal.net.java.quickcheck.characteristic;

import static java.util.Collections.EMPTY_LIST;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("ProhibitedExceptionDeclared")
class AbstractCharacteristicTest {

    private AbstractCharacteristic<Integer> characteristic;

    @BeforeEach
    void setUp() {
        characteristic = new AbstractCharacteristic<>() {

            @Override
            protected void doSpecify(Integer any) {
            }
        };
    }

    @Test
    void testClassifyEmpty() {
        assertEquals(EMPTY_LIST, characteristic.getClassification().getCategories());
    }

    @Test
    void testToClassifiedString() {
        assertEquals("Classifications :none", characteristic.getClassification().toString());
    }

    @Test
    void testClassify() throws Throwable {
        var classification = "string";
        characteristic.classify(classification);
        expectOneClassificationEntry(classification);
    }

    private void expectOneClassificationEntry(String classification) throws Throwable {
        characteristic.specify(0);
        assertEquals(100.0, characteristic.getClassification().getFrequency(classification), 0.01);
    }
}
