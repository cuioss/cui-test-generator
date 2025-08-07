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

import de.cuioss.test.generator.internal.net.java.quickcheck.Generator;
import de.cuioss.test.generator.internal.net.java.quickcheck.collection.Pair;

public class CharacterGenerator implements Generator<Character> {

    public static final Pair<Character, Character> BASIC_LATIN = new Pair<>((char) 0x20, (char) 0x7F);

    public static final Pair<Character, Character> LATIN_1_SUPPLEMENT = new Pair<>((char) 0xa0, (char) 0xFF);

    private final IntegerGenerator generator;

    public CharacterGenerator() {
        this(BASIC_LATIN.getFirst(), BASIC_LATIN.getSecond());
    }

    public CharacterGenerator(char first, char last) {
        if (last < first) {
            throw new IllegalStateException("first <= last");
        }
        this.generator = new IntegerGenerator(first, last);
    }

    @Override
    public Character next() {
        return nextChar();
    }

    public char nextChar() {
        return (char) this.generator.nextInt();
    }
}
