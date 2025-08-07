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
package de.cuioss.test.generator.internal.net.java.quickcheck.generator.support;

import de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubstringGeneratorTest {

    @Test
    void areSubstrings() {
        for (String superstring : Iterables.toIterable(new StringGenerator())) {
            var subs = PrimitiveGenerators.substrings(superstring);
            for (String sub : Iterables.toIterable(subs)) {
                assertTrue(superstring.contains(sub));
            }
        }
    }

    @Test
    void ofSize() {
        for (String superstring : Iterables.toIterable(new StringGenerator())) {
            int size = new IntegerGenerator(0, superstring.length()).next();
            var subs = PrimitiveGenerators.substrings(superstring, size);
            for (String sub : Iterables.toIterable(subs)) {
                assertTrue(superstring.contains(sub));
                assertEquals(size, sub.length());
            }
        }
    }

    @Test
    void full() {
        var base = new StringGenerator().next();
        assertEquals(base, PrimitiveGenerators.substrings(base, base.length()).next());
    }

    @Test
    void invalidMinSize() {
        var base = new StringGenerator().next();
        var invalidMin = new IntegerGenerator(Integer.MIN_VALUE, -1).nextInt();
        final var baseLength = base.length();
        assertThrows(IllegalArgumentException.class,
                () -> PrimitiveGenerators.substrings(base, invalidMin, baseLength));
    }

    @Test
    void invalidMaxSize() {
        var base = new StringGenerator().next();
        var invalidMax = new IntegerGenerator(base.length() + 1, Integer.MAX_VALUE).nextInt();
        assertThrows(IllegalArgumentException.class, () -> PrimitiveGenerators.substrings(base, 0, invalidMax));
    }

    @Test
    void sizeInRange() {
        var base = new StringGenerator().next();
        var min = new IntegerGenerator(0, base.length()).nextInt();
        var max = new IntegerGenerator(min, base.length()).nextInt();
        for (String s : Iterables.toIterable(PrimitiveGenerators.substrings(base, min, max))) {
            assertTrue(min <= s.length() && s.length() <= max);
            assertTrue(base.contains(s));
        }
    }
}
