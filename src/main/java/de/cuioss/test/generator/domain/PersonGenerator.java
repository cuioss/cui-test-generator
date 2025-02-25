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
import de.cuioss.tools.logging.CuiLogger;

/**
 * Generates realistic {@link Person} objects for testing purposes.
 * The generator creates persons with:
 * <ul>
 *   <li>English first names (male and female) from {@link NameGenerators#FIRSTNAMES_ANY_ENGLISH}</li>
 *   <li>English family names from {@link NameGenerators#FAMILY_NAMES_ENGLISH}</li>
 *   <li>Organization names from {@link OrganizationNameGenerator#READABLE}</li>
 *   <li>Professional titles from {@link TitleGenerator#READABLE}</li>
 *   <li>Email addresses generated from the person's name using {@link EmailGenerator}</li>
 * </ul>
 * 
 * <p><em>Example usage:</em></p>
 * <pre>
 * var generator = new PersonGenerator();
 * Person person = generator.next();
 * // person will have realistic first name, last name, title, organization and email
 * </pre>
 * 
 * @author Oliver Wolff
 */
public class PersonGenerator implements TypedGenerator<Person> {

    private static final CuiLogger LOGGER = new CuiLogger(PersonGenerator.class);
    private final TypedGenerator<String> firstNames = NameGenerators.FIRSTNAMES_ANY_ENGLISH.generator();
    private final TypedGenerator<String> familyNames = NameGenerators.FAMILY_NAMES_ENGLISH.generator();
    private final TypedGenerator<String> organizations = OrganizationNameGenerator.READABLE.generator();
    private final TypedGenerator<String> titles = TitleGenerator.READABLE.generator();

    @Override
    public Person next() {
        final var firstname = firstNames.next();
        final var lastname = familyNames.next();
        final var organization = organizations.next();
        final var title = titles.next();

        if (null == firstname || null == lastname) {
            LOGGER.warn("Generated null name components: firstname=%s, lastname=%s", firstname, lastname);
        }

        var person = Person.builder()
                .email(EmailGenerator.createEmail(firstname, lastname))
                .firstname(firstname)
                .lastname(lastname)
                .organisation(organization)
                .title(title)
                .build();

        LOGGER.debug("Generated person: %s %s", firstname, lastname);
        return person;
    }

}
