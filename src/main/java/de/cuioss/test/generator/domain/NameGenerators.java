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
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import static de.cuioss.test.generator.Generators.fixedValues;
import static de.cuioss.test.generator.Generators.strings;

/**
 * Provides a collection of name generators for testing purposes.
 * Includes both German and English names, separated by gender and usage context.
 * 
 * <p>Available generators for German names:</p>
 * <ul>
 *   <li>{@link #FIRSTNAMES_MALE_GERMAN} - Top 10 male names in Germany (2014)</li>
 *   <li>{@link #FIRSTNAMES_FEMALE_GERMAN} - Top 10 female names in Germany (2014)</li>
 *   <li>{@link #FIRSTNAMES_ANY_GERMAN} - Combined German names</li>
 *   <li>{@link #FAMILY_NAMES_GERMAN} - Top 10 German family names</li>
 * </ul>
 * 
 * <p>Available generators for English names:</p>
 * <ul>
 *   <li>{@link #FIRSTNAMES_MALE_ENGLISH} - Top 10 male names in US (2014)</li>
 *   <li>{@link #FIRSTNAMES_FEMALE_ENGLISH} - Top 10 female names in US (2014)</li>
 *   <li>{@link #FIRSTNAMES_ANY_ENGLISH} - Combined English names</li>
 *   <li>{@link #FAMILY_NAMES_ENGLISH} - Top 10 US family names</li>
 * </ul>
 * 
 * <p>Special generators:</p>
 * <ul>
 *   <li>{@link #UNIT_TESTS} - Technical string generator for unit testing</li>
 * </ul>
 * 
 * <p><em>Example usage:</em></p>
 * <pre>
 * {@code TypedGenerator<String> generator = NameGenerators.FIRSTNAMES_MALE_ENGLISH.generator();}
 * String name = generator.next(); // Returns a common English male first name
 * </pre>
 *
 * @author Oliver Wolff
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum NameGenerators {

    // German
    /** Top 10 male names in Germany (2014) */
    FIRSTNAMES_MALE_GERMAN(fixedValues(NameLibrary.FIRSTNAMES_MALE_GERMAN)),

    /** Top 10 female names in Germany (2014) */
    FIRSTNAMES_FEMALE_GERMAN(fixedValues(NameLibrary.FIRSTNAMES_FEMALE_GERMAN)),

    /** Combined set of male and female German names */
    FIRSTNAMES_ANY_GERMAN(fixedValues(NameLibrary.FIRSTNAMES_ANY_GERMAN)),

    /** Top 10 German family names from Wikipedia */
    FAMILY_NAMES_GERMAN(fixedValues(NameLibrary.LAST_NAMES_GERMAN)),

    // English
    /** Top 10 male names in US (2014) */
    FIRSTNAMES_MALE_ENGLISH(fixedValues(NameLibrary.FIRSTNAMES_MALE_ENGLISH)),

    /** Top 10 female names in US (2014) */
    FIRSTNAMES_FEMALE_ENGLISH(fixedValues(NameLibrary.FIRSTNAMES_FEMALE_ENGLISH)),

    /** Combined set of male and female English names */
    FIRSTNAMES_ANY_ENGLISH(fixedValues(NameLibrary.FIRSTNAMES_ANY_ENGLISH)),

    /** Top 10 family names from U.S. Census Bureau */
    FAMILY_NAMES_ENGLISH(fixedValues(NameLibrary.LAST_NAMES_ENGLISH)),

    /** 
     * Technical string generator for unit testing.
     * Generates random strings between 1 and 256 characters.
     */
    UNIT_TESTS(strings(1, 256));

    private final TypedGenerator<String> generator;

    /**
     * Provides access to the underlying name generator.
     *
     * @return A {@link TypedGenerator} that generates names according to the enum constant's specification
     */
    public TypedGenerator<String> generator() {
        return generator;
    }

}
