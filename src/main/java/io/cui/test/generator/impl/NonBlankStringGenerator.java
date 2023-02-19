package io.cui.test.generator.impl;

import static io.cui.test.generator.Generators.nonEmptyStrings;
import static java.lang.String.format;

import io.cui.test.generator.TypedGenerator;

/**
 * Provide any {@link String} which is not empty and not blank.
 */
public class NonBlankStringGenerator implements TypedGenerator<String> {

    private static final TypedGenerator<String> SOME_NONEMPTY_STRING = nonEmptyStrings();

    @Override
    public Class<String> getType() {
        return String.class;
    }

    @Override
    public String next() {
        var tries = 0;
        while (tries < 100) {
            tries++;
            var candidate = SOME_NONEMPTY_STRING.next();
            if (!candidate.trim().isEmpty()) {
                return candidate;
            }
        }
        throw new IllegalStateException(
                format("Could not generate non blank string after %d tries", tries));
    }

}
