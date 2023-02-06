package io.cui.test.generator.domain;

import static io.cui.test.generator.Generators.fixedValues;
import static io.cui.test.generator.Generators.strings;

import io.cui.test.generator.TypedGenerator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

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
