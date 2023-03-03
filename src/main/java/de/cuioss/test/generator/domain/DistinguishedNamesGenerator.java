package de.cuioss.test.generator.domain;

import static de.cuioss.test.generator.Generators.fixedValues;
import static de.cuioss.test.generator.Generators.integers;

import java.util.ArrayList;
import java.util.List;

import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.tools.string.Joiner;

/**
 * Generates formally correct Distinguished Names
 *
 * @author Oliver Wolff
 *
 */
public class DistinguishedNamesGenerator implements TypedGenerator<String> {

    private final TypedGenerator<String> prefixes = fixedValues("ou", "o", "dc");
    private final TypedGenerator<String> values =
        fixedValues("proxies", "ID", "dc", "accounts", "groups", "roles", "services");

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
