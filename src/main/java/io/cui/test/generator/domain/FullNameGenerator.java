package io.cui.test.generator.domain;

import java.util.Locale;

import io.cui.test.generator.TypedGenerator;

/**
 * Generates name strings in the form of 'firstname lastname', depending on the
 * given {@link Locale}
 *
 * @author Oliver Wolff
 *
 */
public class FullNameGenerator implements TypedGenerator<String> {

    private final TypedGenerator<String> firstNames;
    private final TypedGenerator<String> familyNames;

    /**
     * @param locale
     *            to be used for determining the concrete name-set. In case it
     *            is {@link Locale#GERMAN} german names will be generated, in
     *            all other cases english-names.
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
