/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.test.generator.internal.net.java.quickcheck.collection;

import org.junit.jupiter.api.Test;

import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators.nullsAnd;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGeneratorsIterables.someTriples;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.*;
import static org.junit.jupiter.api.Assertions.*;

class TripleTest {

    @Test
    void triple() {
        var anyInt = integers().next();
        var anyString = strings().next();
        var anyDate = dates().next();
        var triple = new Triple<>(anyInt, anyString, anyDate);
        assertSame(anyInt, triple.getFirst());
        assertSame(anyString, triple.getSecond());
        assertSame(anyDate, triple.getThird());
    }

    @Test
    void equals() {
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
