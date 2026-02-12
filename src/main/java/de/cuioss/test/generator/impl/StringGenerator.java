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
import de.cuioss.test.generator.internal.RandomContext;

/**
 * Generates random {@link String} values with configurable length and character set.
 * <p>
 * The default configuration generates strings of 0-30 characters from the Basic Latin
 * and Latin-1 Supplement unicode blocks.
 * </p>
 *
 * @author Oliver Wolff
 */
public class StringGenerator implements TypedGenerator<String> {

    private static final int DEFAULT_MAX_LENGTH = 30;

    private final IntegerGenerator lengthGenerator;
    private final String allowedChars;
    private final CharacterGenerator charGenerator;

    /**
     * Creates a generator for strings of 0-30 characters from Basic Latin + Latin-1 Supplement.
     */
    public StringGenerator() {
        this(0, DEFAULT_MAX_LENGTH);
    }

    /**
     * Creates a generator for strings with length in [minLen, maxLen] from Basic Latin + Latin-1 Supplement.
     *
     * @param minLen minimum string length (inclusive)
     * @param maxLen maximum string length (inclusive)
     */
    public StringGenerator(int minLen, int maxLen) {
        this.lengthGenerator = new IntegerGenerator(minLen, maxLen);
        this.allowedChars = null;
        this.charGenerator = new CharacterGenerator();
    }

    /**
     * Creates a generator for strings using only the specified allowed characters.
     *
     * @param allowedChars the characters to choose from
     * @param minLen       minimum string length (inclusive)
     * @param maxLen       maximum string length (inclusive)
     */
    public StringGenerator(String allowedChars, int minLen, int maxLen) {
        if (allowedChars == null || allowedChars.isEmpty()) {
            throw new IllegalArgumentException("allowedChars must not be null or empty");
        }
        this.lengthGenerator = new IntegerGenerator(minLen, maxLen);
        this.allowedChars = allowedChars;
        this.charGenerator = null;
    }

    /**
     * Creates a generator for letter-only strings (a-z, A-Z) with length in [minLen, maxLen].
     *
     * @param minLen minimum string length (inclusive)
     * @param maxLen maximum string length (inclusive)
     * @return a new StringGenerator producing letter-only strings
     */
    public static StringGenerator letterStrings(int minLen, int maxLen) {
        var letters = new StringBuilder();
        for (char c = 'a'; c <= 'z'; c++) letters.append(c);
        for (char c = 'A'; c <= 'Z'; c++) letters.append(c);
        return new StringGenerator(letters.toString(), minLen, maxLen);
    }

    @Override
    public String next() {
        int length = lengthGenerator.next();
        var sb = new StringBuilder(length);
        if (allowedChars != null) {
            for (int i = 0; i < length; i++) {
                sb.append(allowedChars.charAt(RandomContext.random().nextInt(allowedChars.length())));
            }
        } else {
            for (int i = 0; i < length; i++) {
                sb.append(charGenerator.next());
            }
        }
        return sb.toString();
    }

    @Override
    public Class<String> getType() {
        return String.class;
    }
}
