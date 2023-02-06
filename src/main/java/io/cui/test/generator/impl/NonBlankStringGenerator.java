package io.cui.test.generator.impl;

import static io.cui.test.generator.Generators.nonEmptyStrings;
import static io.cui.test.generator.Generators.unwrap;
import static java.lang.String.format;

import io.cui.test.generator.TypedGenerator;
import io.cui.test.generator.internal.net.java.quickcheck.GeneratorException;

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
        int tries = 0;
        while (tries < 100) {
            tries++;
            String candidate = SOME_NONEMPTY_STRING.next();
            if (!candidate.trim().isEmpty()) {
                return candidate;
            }
        }
        throw new GeneratorException(
            format("Could not generate non blank string after %d tries", tries),
            unwrap(SOME_NONEMPTY_STRING));
    }

}
