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
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGeneratorsIterables.somePairs;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.integers;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.strings;
import static org.junit.jupiter.api.Assertions.*;

class PairTest {

    @Test
    void pair() {
        var anyString = strings().next();
        var anyInt = integers().next();
        var pair = new Pair<>(anyInt, anyString);
        assertSame(anyInt, pair.getFirst());
        assertSame(anyString, pair.getSecond());
    }

    @Test
    void equals() {
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
