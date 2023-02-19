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
import static io.cui.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.fixedValues;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.integers;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import io.cui.test.generator.internal.net.java.quickcheck.Generator;
import io.cui.test.generator.internal.net.java.quickcheck.GeneratorException;
import io.cui.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators;
import io.cui.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators;

class ExcludingGeneratorTest {

    @Test
    void excludeNothing() {
        var value = integers().next();
        Generator<Integer> generator = CombinedGenerators.excludeValues(PrimitiveGenerators.fixedValues(value));
        assertEquals(value, generator.next());
    }

    @Test
    void excludeEverything() {
        var value = integers().next();
        Generator<Integer> excludeValues = CombinedGenerators.excludeValues(fixedValues(value), value);
        assertThrows(GeneratorException.class, excludeValues::next);
    }

    @Test
    void excludeEverythingVargs() {
        var value = integers().next();
        Generator<Integer> excludeValues = CombinedGenerators.excludeValues(fixedValues(value), value);
        assertThrows(GeneratorException.class, excludeValues::next);
    }

    @Test
    void excludeCollection() {
        for (List<Integer> excluded : toIterable(lists(integers()))) {
            var value = CombinedGenerators.excludeValues(integers(), excluded).next();
            assertFalse(excluded.contains(value));
        }
    }

    @Test
    void excludeFixedValueGenerator() {
        for (List<Integer> fixedValues : toIterable(lists(integers(), 2))) {
            var excluded = fixedValues(fixedValues).next();
            var value = CombinedGenerators.excludeValues(fixedValues, excluded).next();
            assertNotEquals(excluded, value);
        }
    }
}
