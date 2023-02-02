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

import static io.cui.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators.arrays;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators.nonEmptyArrays;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.integers;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.support.ListGenerator.MAX_SIZE;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.Test;

import io.cui.test.generator.internal.net.java.quickcheck.Generator;
import io.cui.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators;
import io.cui.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators;
import io.cui.test.generator.internal.net.java.quickcheck.generator.distribution.Distribution;

class ArrayGeneratorTest extends AbstractCollectionTestCase {

    @Test
    void typeVariance() {
        Generator<Object[]> objs = nonEmptyArrays(PrimitiveGenerators.integers(), Object.class);
        assertTrue(objs.next()[0] instanceof Integer);
    }

    @Override
    protected Generator<Collection<Integer>> defaultGenerator() {
        return toCollectionGenerator(arrays(PrimitiveGenerators.integers(),
                Integer.class));
    }

    @Override
    protected Generator<Collection<Integer>> normalDistributionGenerator() {
        return toCollectionGenerator(CombinedGenerators.arrays(integers(),
                integers(0, MAX_SIZE, Distribution.POSITIV_NORMAL), Integer.class));
    }

    private Generator<Collection<Integer>> toCollectionGenerator(
            Generator<Integer[]> listsGenerator) {
        return new AbstractTransformerGenerator<>(
                listsGenerator) {

            @Override
            protected Collection<Integer> transform(
                    Generator<Integer[]> inputGenerator) {

                Integer[] next = inputGenerator.next();
                return Arrays.asList(next);
            }
        };
    }

    @Override
    protected Generator<Collection<Integer>> nonEmpty() {
        return toCollectionGenerator(CombinedGenerators.nonEmptyArrays(integers(), Integer.class));
    }
}
