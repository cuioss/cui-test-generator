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
import static io.cui.test.generator.internal.net.java.quickcheck.generator.CombinedGeneratorsIterables.someTriples;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.dates;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.integers;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.strings;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Date;

import org.junit.jupiter.api.Test;

class TripleTest {

    @Test
    void testTriple() {
        var anyInt = integers().next();
        var anyString = strings().next();
        var anyDate = dates().next();
        var triple = new Triple<Integer, String, Date>(
                anyInt, anyString, anyDate);
        assertSame(anyInt, triple.getFirst());
        assertSame(anyString, triple.getSecond());
        assertSame(anyDate, triple.getThird());
    }

    @Test
    void testEquals() {
        for (Triple<Integer, Integer, Integer> t : someTriples(nullsAnd(integers()), nullsAnd(integers()),
                nullsAnd(integers()))) {
            assertEquals(new Triple<>(t.getFirst(), t.getSecond(), t.getThird()), t);
            assertNotEquals(new Triple<>(inc(t.getFirst()), t.getSecond(), t.getThird()), t);
            assertNotEquals(new Triple<>(t.getFirst(), inc(t.getSecond()), t.getThird()), t);
            assertNotEquals(new Triple<>(t.getFirst(), t.getSecond(), inc(t.getThird())), t);
        }
    }

    private Integer inc(Integer first) {
        return first == null ? 0 : first + 1;
    }
}
