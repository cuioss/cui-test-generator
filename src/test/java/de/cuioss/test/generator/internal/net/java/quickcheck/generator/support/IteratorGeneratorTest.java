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

import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.integers;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.IteratorGenerator.MAX_SIZE;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.internal.net.java.quickcheck.Generator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.distribution.Distribution;

class IteratorGeneratorTest extends AbstractCollectionTestCase {

    @Test
    void typeVariance() {
        Generator<Iterator<Object>> objs = CombinedGenerators.iterators(integers());
        var values = objs.next();
        while (values.hasNext()) {
            assertTrue(values.next() instanceof Integer);
        }
    }

    @Override
    protected Generator<Collection<Integer>> defaultGenerator() {
        return toCollectionGenerator(iterators());
    }

    private Generator<Iterator<Integer>> iterators() {
        return CombinedGenerators.iterators(PrimitiveGenerators.integers());
    }

    private Generator<Collection<Integer>> toCollectionGenerator(final Generator<Iterator<Integer>> iterators) {
        return () -> {
            var iterator = iterators.next();
            Collection<Integer> collection = new ArrayList<>();
            while (iterator.hasNext()) {
                collection.add(iterator.next());
            }
            return collection;
        };
    }

    @Override
    protected Generator<Collection<Integer>> nonEmpty() {
        return toCollectionGenerator(CombinedGenerators.nonEmptyIterators(PrimitiveGenerators.integers()));
    }

    @Override
    protected Generator<Collection<Integer>> normalDistributionGenerator() {
        return toCollectionGenerator(CombinedGenerators.iterators(integers(),
                integers(0, MAX_SIZE, Distribution.POSITIV_NORMAL)));
    }

    @Test
    void removeNotSupported() {
        var iterator = iterators().next();
        assertThrows(UnsupportedOperationException.class, iterator::remove);
    }

}
