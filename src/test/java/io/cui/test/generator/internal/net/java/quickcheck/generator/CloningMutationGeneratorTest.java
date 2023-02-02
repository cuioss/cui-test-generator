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

import static io.cui.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.fixedValues;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CloningMutationGeneratorTest {

    @Test
    void testCloningMutationGeneratorTest() {
        String prototypeValue = PrimitiveGenerators.strings().next();
        Prototype prototype = new Prototype(prototypeValue);
        Integer mutationValue = PrimitiveGenerators.integers().next();
        CloningMutationGenerator<Prototype, Integer> cloningMutationGenerator = new CloningMutationGenerator<>(
                prototype, fixedValues(mutationValue)) {

            @Override
            protected Prototype mutate(Prototype prototype, Integer mutation) {
                prototype.setValue(prototype.getValue() + mutation);
                return prototype;
            }
        };
        assertEquals(prototypeValue
                + mutationValue, cloningMutationGenerator.next().getValue(), "a new mutated value was created");
        assertEquals(prototypeValue, prototype.getValue(), "prototype value was not changed");
    }

}
