package de.cuioss.test.generator.domain;

import de.cuioss.test.generator.TypedGenerator;

/**
 * Creates arbitrary {@link Person} objects
 *
 * @author Oliver Wolff
 *
 */
public class PersonGenerator implements TypedGenerator<Person> {

    private final TypedGenerator<String> firstNames = NameGenerators.FIRSTNAMES_ANY_ENGLISH.generator();
    private final TypedGenerator<String> familyNames = NameGenerators.FAMILY_NAMES_ENGLISH.generator();
    private final TypedGenerator<String> organizations = OrganizationNameGenerator.READABLE.generator();
    private final TypedGenerator<String> titles = TitleGenerator.READABLE.generator();

    @Override
    public Person next() {
        final var firstname = firstNames.next();
        final var lastname = familyNames.next();
        return Person.builder().email(EmailGenerator.createEmail(firstname, lastname)).firstname(firstname)
                .lastname(lastname).organisation(organizations.next()).title(titles.next()).build();
    }

}
