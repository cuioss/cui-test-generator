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

import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators.lists;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators.nonEmptyLists;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.integers;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.ListGenerator.MAX_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.internal.net.java.quickcheck.Generator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.distribution.Distribution;

class ListGeneratorTest extends AbstractCollectionTestCase {

    @Test
    void typeVariance() {
        Generator<List<Object>> objs = CombinedGenerators.nonEmptyLists(PrimitiveGenerators.integers());
        assertInstanceOf(Integer.class, objs.next().iterator().next());
    }

    @Override
    protected Generator<Collection<Integer>> defaultGenerator() {
        return cast(lists(integers()));
    }

    @Override
    protected Generator<Collection<Integer>> normalDistributionGenerator() {
        return cast(lists(integers(), integers(0, MAX_SIZE, Distribution.POSITIV_NORMAL)));
    }

    @Override
    public Generator<Collection<Integer>> nonEmpty() {
        return cast(nonEmptyLists(integers()));
    }

    @Test
    void testListGeneratorWithHiAndLowValue() {
        var hiLow = SizeGenerator.anyMinMax();
        int lo = hiLow.get(0);
        int hi = hiLow.get(1);
        testListGenerator(lo, hi, lists(integers(), lo, hi));
    }

    @Test
    void testListGeneratorWithLowValue() {
        for (int low : toIterable(new SizeGenerator())) {
            testListGenerator(low, MAX_SIZE, lists(integers(), low));
        }
    }

    @Test
    void testListGeneratorWithLowValueLargerThanDefaultMaxValue() {
        for (int low : toIterable(integers(MAX_SIZE, MAX_SIZE + MAX_SIZE))) {
            assertEquals(low, CombinedGenerators.lists(integers(), low).next().size());
        }
    }

    private void testListGenerator(int lo, int hi,
            Generator<List<Integer>> generator) {
        testCollectionGenerator(cast(generator), 40.0, 40.0, lo, hi);
    }
}
