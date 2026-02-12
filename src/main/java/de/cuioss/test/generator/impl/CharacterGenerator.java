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
package de.cuioss.test.generator.impl;

import de.cuioss.test.generator.TypedGenerator;

/**
 * Generates random {@link Character} values within a configurable range.
 * <p>
 * The default range covers printable Basic Latin characters (0x20-0x7E).
 * </p>
 *
 * @author Oliver Wolff
 */
public class CharacterGenerator implements TypedGenerator<Character> {

    private static final char BASIC_LATIN_LO = '\u0020';
    private static final char BASIC_LATIN_HI = '\u007E';

    private final IntegerGenerator delegate;

    /**
     * Creates a generator for Basic Latin characters (0x20-0x7E).
     */
    public CharacterGenerator() {
        this(BASIC_LATIN_LO, BASIC_LATIN_HI);
    }

    /**
     * Creates a generator for characters in the range [lo, hi].
     *
     * @param lo lower bound (inclusive)
     * @param hi upper bound (inclusive)
     */
    public CharacterGenerator(char lo, char hi) {
        this.delegate = new IntegerGenerator(lo, hi);
    }

    @Override
    public Character next() {
        return (char) delegate.next().intValue();
    }

    @Override
    public Class<Character> getType() {
        return Character.class;
    }
}
