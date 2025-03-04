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
import de.cuioss.tools.string.Joiner;

import java.util.ArrayList;
import java.util.List;

import static de.cuioss.test.generator.Generators.fixedValues;
import static de.cuioss.test.generator.Generators.integers;

/**
 * Generates LDAP Distinguished Names (DN) for testing purposes.
 * The generated DNs follow the standard format of comma-separated name-value pairs.
 * 
 * <p>Components:</p>
 * <ul>
 *   <li>Prefixes: ou (Organizational Unit), o (Organization), dc (Domain Component)</li>
 *   <li>Values: Common LDAP terms like proxies, accounts, groups, roles, etc.</li>
 * </ul>
 * 
 * <p>Generation rules:</p>
 * <ul>
 *   <li>Each DN contains 2-12 components</li>
 *   <li>Components are in the format prefix=value</li>
 *   <li>Components are joined with commas</li>
 * </ul>
 * 
 * <p><em>Example outputs:</em></p>
 * <pre>
 * "ou=proxies,dc=accounts,o=services"
 * "dc=groups,ou=roles,dc=ID"
 * </pre>
 * 
 * <p><em>Example usage:</em></p>
 * <pre>
 * var generator = new DistinguishedNamesGenerator();
 * String dn = generator.next(); // Returns a valid LDAP DN string
 * </pre>
 *
 * @author Oliver Wolff
 */
public class DistinguishedNamesGenerator implements TypedGenerator<String> {

    private final TypedGenerator<String> prefixes = fixedValues("ou", "o", "dc");
    private final TypedGenerator<String> values = fixedValues("proxies", "ID", "dc", "accounts", "groups", "roles",
            "services");

    @Override
    public String next() {
        List<String> elements = new ArrayList<>();
        int count = integers(2, 12).next();
        for (var i = 0; i < count; i++) {
            elements.add(prefixes.next() + "=" + values.next());
        }
        return Joiner.on(',').join(elements);
    }

    @Override
    public Class<String> getType() {
        return String.class;
    }
}
