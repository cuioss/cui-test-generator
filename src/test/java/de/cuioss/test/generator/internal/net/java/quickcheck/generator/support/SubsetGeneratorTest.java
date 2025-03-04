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

import de.cuioss.test.generator.internal.net.java.quickcheck.Generator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Set;

import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators.oneOf;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGeneratorSamples.anyInteger;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.fixedValues;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.integers;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable;
import static org.junit.jupiter.api.Assertions.*;

class SubsetGeneratorTest {

    @Test
    void subset() {
        Collection<Integer> superset = CombinedGenerators.sets(integers()).next();
        for (Set<Integer> set : toIterable(CombinedGenerators.sets(superset))) {
            assertTrue(superset.containsAll(set));
        }
    }

    @Test
    void subsetFromVarArgs() {
        Collection<Integer> superset = CombinedGenerators.sets(integers()).next();
        Generator<Set<Integer>> generator = CombinedGenerators.sets(superset.toArray(new Integer[0]));
        for (Set<Integer> set : toIterable(generator)) {
            assertTrue(superset.containsAll(set));
        }
    }

    @Test
    void subsetOfGivenSize() {
        Collection<Integer> superset = CombinedGenerators.sets(integers()).next();
        int size = anyInteger(0, superset.size());
        for (Set<Integer> set : toIterable(CombinedGenerators.sets(superset, fixedValues(size)))) {
            assertTrue(superset.containsAll(set));
            assertEquals(size, set.size());
        }
    }

    @Test
    void invalidSize() {
        Collection<Integer> superset = CombinedGenerators.sets(integers()).next();
        Generator<Integer> invalidSizes = oneOf(integers(Integer.MIN_VALUE, -1)).add(integers(superset.size() + 1));
        for (Integer size : toIterable(invalidSizes)) {
            assertThrows(IllegalArgumentException.class,
                    () -> CombinedGenerators.sets(superset, fixedValues(size)).next());
        }
    }
}
