/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.test.generator.domain;

import de.cuioss.test.generator.TypedGenerator;

import java.util.Locale;

/**
 * Generates full names in the format 'firstname lastname' based on the specified locale.
 * Supports both German and English name sets.
 * 
 * <p>Name sources:</p>
 * <ul>
 *   <li>German names from {@link NameGenerators#FIRSTNAMES_ANY_GERMAN} and {@link NameGenerators#FAMILY_NAMES_GERMAN}</li>
 *   <li>English names from {@link NameGenerators#FIRSTNAMES_ANY_ENGLISH} and {@link NameGenerators#FAMILY_NAMES_ENGLISH}</li>
 * </ul>
 * 
 * <p><em>Example usage:</em></p>
 * <pre>
 * // For German names
 * var generator = new FullNameGenerator(Locale.GERMAN);
 * String name = generator.next(); // e.g. "Hans Schmidt"
 * 
 * // For English names
 * generator = new FullNameGenerator(Locale.ENGLISH);
 * name = generator.next(); // e.g. "John Smith"
 * </pre>
 *
 * @author Oliver Wolff
 *
 */
public class FullNameGenerator implements TypedGenerator<String> {

    private final TypedGenerator<String> firstNames;
    private final TypedGenerator<String> familyNames;

    /**
     * Creates a new FullNameGenerator for the specified locale.
     * 
     * @param locale Determines the name set to use. If {@link Locale#GERMAN}, German names will be used;
     *              for all other locales, English names will be used.
     */
    public FullNameGenerator(final Locale locale) {
        if (Locale.GERMAN.equals(locale)) {
            firstNames = NameGenerators.FIRSTNAMES_ANY_GERMAN.generator();
            familyNames = NameGenerators.FAMILY_NAMES_GERMAN.generator();
        } else {
            firstNames = NameGenerators.FIRSTNAMES_ANY_ENGLISH.generator();
            familyNames = NameGenerators.FAMILY_NAMES_ENGLISH.generator();
        }
    }

    @Override
    public String next() {
        return firstNames.next() + ' ' + familyNames.next();
    }

}
