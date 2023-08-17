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
