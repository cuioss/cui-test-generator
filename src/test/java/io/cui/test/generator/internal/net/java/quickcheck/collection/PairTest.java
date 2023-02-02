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
package io.cui.test.generator.internal.net.java.quickcheck.collection;

import static io.cui.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators.nullsAnd;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.CombinedGeneratorsIterables.somePairs;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.integers;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.strings;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PairTest {

    @Test
    void testPair() {
        String anyString = strings().next();
        Integer anyInt = integers().next();
        Pair<Integer, String> pair = new Pair<>(anyInt,
                anyString);
        assertSame(anyInt, pair.getFirst());
        assertSame(anyString, pair.getSecond());
    }

    @Test
    void testEquals() {
        for (Pair<Integer, Integer> t : somePairs(nullsAnd(integers()), nullsAnd(integers()))) {
            assertEquals(new Pair<>(t.getFirst(), t.getSecond()), t);
            assertNotEquals(new Pair<>(inc(t.getFirst()), t.getSecond()), t);
            assertNotEquals(new Pair<>(inc(t.getFirst()), inc(t.getSecond())), t);
        }
    }

    private Integer inc(Integer first) {
        return first == null ? 0 : first + 1;
    }
}
