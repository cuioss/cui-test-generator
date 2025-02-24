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

import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.tools.logging.CuiLogger;

/**
 * Generates syntactically valid email addresses for testing purposes.
 * The generator creates email addresses in the format: firstname.lastname@domain.tld
 * 
 * <ul>
 *   <li>First and last names are taken from {@link NameGenerators#FIRSTNAMES_ANY_ENGLISH} 
 *       and {@link NameGenerators#FAMILY_NAMES_ENGLISH}</li>
 *   <li>Domains include: email, mail, cuioss, message, example, hospital</li>
 *   <li>TLDs include: de, org, com, net</li>
 * </ul>
 * 
 * <p><em>Example usage:</em></p>
 * <pre>
 * var generator = new EmailGenerator();
 * String email = generator.next(); // e.g. "john.doe@mail.com"
 * 
 * // Or create email directly from names
 * String email = EmailGenerator.createEmail("john", "doe"); // e.g. "john.doe@example.org"
 * </pre>
 *
 * @author Oliver Wolff
 *
 */
public class EmailGenerator implements TypedGenerator<String> {

    private static final CuiLogger LOGGER = new CuiLogger(EmailGenerator.class);

    private final TypedGenerator<String> firstNames = NameGenerators.FIRSTNAMES_ANY_ENGLISH.generator();
    private final TypedGenerator<String> familyNames = NameGenerators.FAMILY_NAMES_ENGLISH.generator();

    private static final TypedGenerator<String> TLDS = fixedValues("de", "org", "com", "net");
    private static final TypedGenerator<String> DOMAINS = fixedValues("email", "mail", "cuioss", "message", "example",
            "hospital");

    @Override
    public String next() {
        return createEmail(firstNames.next(), familyNames.next());
    }

    /**
     * Creates an email address from given first and last names.
     * All components are converted to lowercase.
     * 
     * @param firstname The person's first name, must not be null or empty
     * @param lastname The person's last name, must not be null or empty
     * @return An email address in the format firstname.lastname@domain.tld.
     *         Returns "invalid.email@example.com" if either name component is null or empty.
     */
    public static String createEmail(final String firstname, final String lastname) {
        if (null == firstname || null == lastname || firstname.isEmpty() || lastname.isEmpty()) {
            LOGGER.warn("Invalid name components for email generation: firstname='%s', lastname='%s'", firstname, lastname);
            return "invalid.email@example.com";
        }
        var email = (firstname + "." + lastname + "@" + DOMAINS.next()).toLowerCase();
        LOGGER.debug("Generated email: %s", email + '.' + TLDS.next());
        return email + '.' + TLDS.next();
    }

}
