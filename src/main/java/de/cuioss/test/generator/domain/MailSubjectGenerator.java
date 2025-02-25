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

import java.util.ArrayList;
import java.util.List;

import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators;
import de.cuioss.tools.string.Joiner;

/**
 * Generates realistic email subject lines for testing purposes, particularly focused
 * on healthcare-related communications.
 * 
 * <p>Subject line components:</p>
 * <ul>
 *   <li>Prefixes (0-3): "Re:", "Fw:", "Answ:", "Yep:"</li>
 *   <li>Content words (0-7): Healthcare-related terms like "Patient", "Document", "Hospital", etc.</li>
 * </ul>
 * 
 * <p>The generator creates subject lines by combining:</p>
 * <ol>
 *   <li>Random number (0-3) of prefix elements</li>
 *   <li>Random number (0-7) of content words</li>
 *   <li>All elements are joined with spaces</li>
 * </ol>
 * 
 * <p><em>Example outputs:</em></p>
 * <pre>
 * "Re: Fw: Patient Document"
 * "Hospital Doctor Referral"
 * "Answ: Registration Physician"
 * </pre>
 * 
 * <p><em>Example usage:</em></p>
 * <pre>
 * var generator = new MailSubjectGenerator();
 * String subject = generator.next(); // Returns a randomly generated subject line
 * </pre>
 *
 * @author Oliver Wolff
 */
public class MailSubjectGenerator implements TypedGenerator<String> {

    private final TypedGenerator<String> prefixes = fixedValues("Re:", "Fw:", "Answ:", "Yep:");
    private final TypedGenerator<String> contents = fixedValues("Hello", "Patient", "Document", "Record", "Yes", "No",
            "unknown", "Disease", "Hospital", "Doctor", "Healthy", "Not seen", "Referral", "Message", "Injury",
            "See Also", "Payment", "Registration", "Physician");

    @Override
    public String next() {
        final List<String> elements = new ArrayList<>();
        for (var i = 0; i < PrimitiveGenerators.integers(0, 3).next(); i++) {
            elements.add(prefixes.next());
        }
        for (var i = 0; i < PrimitiveGenerators.integers(0, 7).next(); i++) {
            elements.add(contents.next());
        }
        return Joiner.on(' ').join(elements);
    }

}
