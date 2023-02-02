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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.cui.test.generator.internal.net.java.quickcheck.Generator;
import io.cui.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators;
import io.cui.test.generator.internal.net.java.quickcheck.generator.Prototype;
import lombok.val;

class CloningGeneratorTest {

    private Generator<Prototype> cloningGenerator;
    private Prototype prototype;

    @BeforeEach
    void setUp() {
        prototype = new Prototype("");
        cloningGenerator = PrimitiveGenerators.clonedValues(prototype);
    }

    @Test
    void testCloningGeneratorPrototypeNotSameObjectAsGeneratedValue() {

        Prototype next = cloningGenerator.next();
        assertNotNull(next);
        assertNotSame(prototype, next);
    }

    @Test
    void testCloningGeneratorGeneratedValuesAreNotTheSame() {
        Prototype last = null;
        for (int i = 0; i < 10; i++) {
            Prototype next = cloningGenerator.next();
            assertNotSame(next, last);
            last = next;
        }
    }

    @Test
    void testThrowsExceptionIfNotSerializable() {
        val generator = new CloningGenerator<>(new Object());
        assertThrows(IllegalArgumentException.class, generator::next);
    }

}
