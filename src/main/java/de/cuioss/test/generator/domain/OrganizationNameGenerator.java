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
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import static de.cuioss.test.generator.Generators.fixedValues;
import static de.cuioss.test.generator.Generators.strings;
import static de.cuioss.tools.collect.CollectionLiterals.immutableList;

/**
 * Provides generators for organization names, including well-known fictional companies.
 * 
 * <p>Available generators:</p>
 * <ul>
 *   <li>{@link #READABLE} - Names of famous fictional companies from various media sources</li>
 *   <li>{@link #UNIT_TESTS} - Technical string generator for unit testing</li>
 * </ul>
 * 
 * <p><em>Example usage:</em></p>
 * <pre>
 * {@code TypedGenerator<String> generator = OrganizationNameGenerator.READABLE.generator();}
 * String company = generator.next(); // Returns names like "Acme Corp.", "Stark Industries", etc.
 * </pre>
 *
 * @author Oliver Wolff
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum OrganizationNameGenerator {

    /** 
     * The 25 largest fictional companies from popular culture.
     * Includes companies from literature, movies, TV shows, and comics.
     * Examples: Acme Corp., Stark Industries, Wayne Enterprises
     */
    READABLE(fixedValues(immutableList("CHOAM", "Acme Corp.", "Sirius Cybernetics Corp.", "MomCorp", "Rich Industries",
            "Soylent Corp.", "Very Big Corp. of America", "Frobozz Magic Co.", "Warbucks Industries", "Tyrell Corp.",
            "Wayne Enterprises", "Virtucon", "Globex", "Umbrella Corp.", "Wonka Industries", "Stark Industries",
            "Clampett Oil", "Oceanic Airlines", "Yoyodyne Propulsion Sys.", "Cyberdyne Systems Corp.",
            "d'Anconia Copper", "Gringotts", "Oscorp", "Nakatomi Trading Corp.", "Spacely Space Sprockets"))),

    /** 
     * Technical string generator for unit testing.
     * Generates random strings between 1 and 256 characters.
     */
    UNIT_TESTS(strings(1, 256));

    private final TypedGenerator<String> generator;

    /**
     * Provides access to the underlying organization name generator.
     *
     * @return A {@link TypedGenerator} that generates organization names according to the enum constant's specification
     */
    public TypedGenerator<String> generator() {
        return generator;
    }
}
