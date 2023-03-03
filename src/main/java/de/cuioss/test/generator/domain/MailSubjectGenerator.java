package de.cuioss.test.generator.domain;

import static de.cuioss.test.generator.Generators.fixedValues;

import java.util.ArrayList;
import java.util.List;

import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators;
import de.cuioss.tools.string.Joiner;

/**
 * Provides Subjects for messages
 *
 * @author Oliver Wolff
 *
 */
public class MailSubjectGenerator implements TypedGenerator<String> {

    private final TypedGenerator<String> prefixes = fixedValues("Re:", "Fw:", "Answ:", "Yep:");
    private final TypedGenerator<String> contents =
        fixedValues("Hello", "Patient", "Document", "Record", "Yes", "No", "unknown", "Disease",
                "Hospital", "Doctor", "Healthy", "Not seen", "Referral", "Message", "Injury", "See Also",
                "Payment", "Registration", "Physician");

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
