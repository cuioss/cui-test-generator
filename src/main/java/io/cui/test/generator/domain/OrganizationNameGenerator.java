package io.cui.test.generator.domain;

import static io.cui.test.generator.Generators.fixedValues;
import static io.cui.test.generator.Generators.strings;
import static io.cui.tools.collect.CollectionLiterals.immutableList;

import io.cui.test.generator.TypedGenerator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * The generator {@link #READABLE} is for visual mocks, {@link #UNIT_TESTS} for unit-tests.
 *
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum OrganizationNameGenerator {

    /** The 25 largest fictional companies */
    READABLE(fixedValues(immutableList("CHOAM", "Acme Corp.", "Sirius Cybernetics Corp.", "MomCorp",
            "Rich Industries", "Soylent Corp.", "Very Big Corp. of America", "Frobozz Magic Co.", "Warbucks Industries",
            "Tyrell Corp.", "Wayne Enterprises", "Virtucon", "Globex", "Umbrella Corp.", "Wonka Industries",
            "Stark Industries", "Clampett Oil", "Oceanic Airlines", "Yoyodyne Propulsion Sys.",
            "Cyberdyne Systems Corp.", "d’Anconia Copper", "Gringotts", "Oscorp", "Nakatomi Trading Corp.",
            "Spacely Space Sprockets"))),

    /** Technical String for unit-testing. Min size is 1, max size 256 */
    UNIT_TESTS(strings(1, 256));

    private final TypedGenerator<String> generator;

    /**
     * @return the concrete generator.
     */
    public TypedGenerator<String> generator() {
        return generator;
    }
}
