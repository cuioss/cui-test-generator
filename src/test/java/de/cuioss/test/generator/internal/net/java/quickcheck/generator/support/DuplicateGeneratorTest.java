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

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.internal.net.java.quickcheck.Generator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators;

class DuplicateGeneratorTest {

    final SetGenerator<Integer> inputs = new SetGenerator<>(new IntegerGenerator(),
            new IntegerGenerator(1, SetGenerator.MAX_SIZE), SetGenerator.MAX_TRIES);

    @Test
    void duplicate() {
        var input = inputs.next();
        Generator<List<Integer>> ds = CombinedGenerators.duplicates(input);
        assertIsDuplicate(input, ds.next());
    }

    @Test
    void duplicateFromVargs() {
        var input = inputs.next();
        Generator<List<Integer>> ds = CombinedGenerators.duplicates(input.toArray(new Integer[0]));
        assertIsDuplicate(input, ds.next());
    }

    private void assertIsDuplicate(Set<Integer> input, List<Integer> d) {
        assertTrue(d.size() > input.size());
        assertTrue(input.containsAll(d));
    }
}
