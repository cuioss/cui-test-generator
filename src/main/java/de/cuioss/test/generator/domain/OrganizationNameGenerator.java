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
import static de.cuioss.test.generator.Generators.strings;
import static de.cuioss.tools.collect.CollectionLiterals.immutableList;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import de.cuioss.test.generator.TypedGenerator;

/**
 * The generator {@link #READABLE} is for visual mocks, {@link #UNIT_TESTS} for
 * unit-tests.
 *
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum OrganizationNameGenerator {

    /** The 25 largest fictional companies */
    READABLE(fixedValues(immutableList("CHOAM", "Acme Corp.", "Sirius Cybernetics Corp.", "MomCorp", "Rich Industries",
            "Soylent Corp.", "Very Big Corp. of America", "Frobozz Magic Co.", "Warbucks Industries", "Tyrell Corp.",
            "Wayne Enterprises", "Virtucon", "Globex", "Umbrella Corp.", "Wonka Industries", "Stark Industries",
            "Clampett Oil", "Oceanic Airlines", "Yoyodyne Propulsion Sys.", "Cyberdyne Systems Corp.",
            "d’Anconia Copper", "Gringotts", "Oscorp", "Nakatomi Trading Corp.", "Spacely Space Sprockets"))),

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
