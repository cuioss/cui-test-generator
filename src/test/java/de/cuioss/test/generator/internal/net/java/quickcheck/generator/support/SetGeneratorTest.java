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
package de.cuioss.test.generator.internal.net.java.quickcheck.generator.support;

import static de.cuioss.test.generator.internal.net.java.quickcheck.QuickCheck.forAll;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators.sets;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.integers;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.SetGenerator.MAX_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.util.Collection;
import java.util.Set;

import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.internal.net.java.quickcheck.Generator;
import de.cuioss.test.generator.internal.net.java.quickcheck.characteristic.AbstractCharacteristic;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.distribution.Distribution;

class SetGeneratorTest extends AbstractCollectionTestCase {

    @Test
    void typeVariance() {
        Generator<Set<Object>> objs = CombinedGenerators.nonEmptySets(integers());
        assertInstanceOf(Integer.class, objs.next().iterator().next());
    }

    @Override
    protected Generator<Collection<Integer>> defaultGenerator() {
        return cast(CombinedGenerators.sets(PrimitiveGenerators.integers()));
    }

    @Override
    protected Generator<Collection<Integer>> normalDistributionGenerator() {
        return cast(CombinedGenerators.sets(integers(), integers(0, MAX_SIZE, Distribution.POSITIV_NORMAL)));
    }

    @Test
    void testSizeIsValid() throws de.cuioss.test.generator.internal.net.java.quickcheck.GeneratorException,
            de.cuioss.test.generator.internal.net.java.quickcheck.CharacteristicException {
        forAll(sets(integers(0, MAX_SIZE * 10), MAX_SIZE, MAX_SIZE), new AbstractCharacteristic<>() {

            @Override
            protected void doSpecify(Set<Integer> any) {
                assertEquals(MAX_SIZE, any.size());
            }
        });
    }

    @Override
    protected Generator<Collection<Integer>> nonEmpty() {
        return cast(CombinedGenerators.nonEmptySets(integers()));
    }
}
