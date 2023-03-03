package de.cuioss.test.generator.domain;

import static de.cuioss.test.generator.Generators.fixedValues;
import static de.cuioss.test.generator.Generators.strings;

import de.cuioss.test.generator.TypedGenerator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * The generator {@link #READABLE} is for visual mocks, {@link #UNIT_TESTS} for unit-tests.
 *
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum TitleGenerator {

    /** Some Titles */
    READABLE(fixedValues("Dr.", "Dr. h.c.", "M.A.", "Dr. med.")),

    /** Technical String for unit-testing. Min size is 1, max size 256 */
    UNIT_TESTS(strings(1, 10));

    private final TypedGenerator<String> generator;

    /**
     * @return the concrete generator.
     */
    public TypedGenerator<String> generator() {
        return generator;
    }
}
