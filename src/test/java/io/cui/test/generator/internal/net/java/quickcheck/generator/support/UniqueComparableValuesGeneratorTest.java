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

import static io.cui.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators.lists;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.strings;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable;
import static java.lang.String.CASE_INSENSITIVE_ORDER;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.cui.test.generator.internal.net.java.quickcheck.Generator;
import io.cui.test.generator.internal.net.java.quickcheck.StatefulGenerator;
import io.cui.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators;
import io.cui.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators;

class UniqueComparableValuesGeneratorTest extends UniqueValuesGeneratorTest {

    private final Comparator<Boolean> naturalOrder = Comparator.naturalOrder();

    @Test
    void uniqueValues() {
        Generator<String> values = strings(1, 1);
        StatefulGenerator<String> uniqueValues = CombinedGenerators.uniqueValues(values, CASE_INSENSITIVE_ORDER);
        for (List<String> gs : toIterable(lists(uniqueValues))) {
            for (String g : gs) {
                for (String o : removed(gs, g)) {
                    assertNotEquals(CASE_INSENSITIVE_ORDER.compare(g, o), 0);
                }
            }
            uniqueValues.reset();
        }
    }

    @Test
    void uniqueValuesContravariantComparator() {
        Comparator<Number> comparator = Comparator.comparingInt(Number::intValue);
        assertNotNull(CombinedGenerators.uniqueValues(PrimitiveGenerators.integers(), comparator).next());
    }

    @Override
    StatefulGenerator<Boolean> uniqueValuesGenerator(int tries, Generator<Boolean> generator) {
        return CombinedGenerators.uniqueValues(generator, naturalOrder, tries);
    }

    @Override
    StatefulGenerator<Boolean> uniqueValuesGenerator(Generator<Boolean> generator) {
        return CombinedGenerators.uniqueValues(generator, naturalOrder);
    }

    private List<String> removed(List<String> gs, String g) {
        List<String> others = new ArrayList<>(gs);
        others.remove(g);
        return others;
    }
}
