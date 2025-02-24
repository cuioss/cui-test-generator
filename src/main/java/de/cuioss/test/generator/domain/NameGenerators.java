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

import static de.cuioss.test.generator.Generators.fixedValues;
import static de.cuioss.test.generator.Generators.strings;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import de.cuioss.test.generator.TypedGenerator;

/**
 * Combines different variants of Generators for firstnames. The generators
 * {@link #FIRSTNAMES_MALE_GERMAN}, {@link #FIRSTNAMES_FEMALE_GERMAN} and
 * {@link #FIRSTNAMES_ANY_GERMAN} are for visual mocks, {@link #UNIT_TESTS} for
 * unit-tests.
 *
 * @author Oliver Wolff
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum NameGenerators {

    // German
    /** Top 10 male name in Germany 2014 */
    FIRSTNAMES_MALE_GERMAN(fixedValues(NameLibrary.FIRSTNAMES_MALE_GERMAN)),

    /** Top 10 female name in Germany 2014 */
    FIRSTNAMES_FEMALE_GERMAN(fixedValues(NameLibrary.FIRSTNAMES_FEMALE_GERMAN)),

    /**
     * The intersection of {@link #FIRSTNAMES_MALE_GERMAN} and
     * {@link #FIRSTNAMES_FEMALE_GERMAN} names
     */
    FIRSTNAMES_ANY_GERMAN(fixedValues(NameLibrary.FIRSTNAMES_ANY_GERMAN)),

    /** Top 10 names in Wikipedia */
    FAMILY_NAMES_GERMAN(fixedValues(NameLibrary.LAST_NAMES_GERMAN)),

    // English
    /** Top 10 male name in US 2014 */
    FIRSTNAMES_MALE_ENGLISH(fixedValues(NameLibrary.FIRSTNAMES_MALE_ENGLISH)),

    /** Top 10 female name in US 2014 */
    FIRSTNAMES_FEMALE_ENGLISH(fixedValues(NameLibrary.FIRSTNAMES_FEMALE_ENGLISH)),

    /**
     * The intersection of {@link #FIRSTNAMES_MALE_ENGLISH} and
     * {@link #FIRSTNAMES_FEMALE_ENGLISH} names
     */
    FIRSTNAMES_ANY_ENGLISH(fixedValues(NameLibrary.FIRSTNAMES_ANY_ENGLISH)),

    /** Top 10 names from U.S. Census Bureau */
    FAMILY_NAMES_ENGLISH(fixedValues(NameLibrary.LAST_NAMES_ENGLISH)),

    /** Technical String for unit-testing. Min size is 1, max size 256 */
    UNIT_TESTS(strings(1, 256));

    private final TypedGenerator<String> generator;

    /**
     * @return the concrete generator.
     */
    public TypedGenerator<String> generator() {
        return generator;
    }

}
