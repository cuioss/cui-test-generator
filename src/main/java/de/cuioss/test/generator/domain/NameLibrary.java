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

import de.cuioss.test.generator.internal.net.java.quickcheck.Generator;
import de.cuioss.tools.collect.CollectionBuilder;
import lombok.experimental.UtilityClass;

import java.util.List;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;

/**
 * Provides lists of names for creating name {@link Generator}.
 *
 * @author Oliver Wolff
 *
 */
@UtilityClass
@SuppressWarnings("squid:S2386") // owolff: False positive, list are immutable
public final class NameLibrary {

    // German
    /** Top 10 male name in Germany 2014 */
    public static final List<String> FIRSTNAMES_MALE_GERMAN = immutableList("Ben", "Elias", "Fynn", "Jonas", "Leon",
            "Louis", "Luca", "Lukas", "Noah", "Paul");

    /** Top 10 female name in Germany 2014 */
    public static final List<String> FIRSTNAMES_FEMALE_GERMAN = immutableList("Anna", "Emilia", "Emma", "Hannah", "Lea",
            "Lena", "Leonie", "Marie", "Mia", "Sophia");

    /**
     * The intersection of {@link #FIRSTNAMES_FEMALE_GERMAN} and
     * {@link #FIRSTNAMES_MALE_GERMAN}.
     */
    public static final List<String> FIRSTNAMES_ANY_GERMAN = new CollectionBuilder<String>()
            .add(FIRSTNAMES_FEMALE_GERMAN).add(FIRSTNAMES_MALE_GERMAN).toImmutableList();

    /** Top 10 names in Wikipedia */
    public static final List<String> LAST_NAMES_GERMAN = immutableList("Müller", "Schmidt", "Schneider", "Fischer",
            "Weber", "Meyer", "Wagner", "Becker", "Schulz", "Hoffmann");

    // English

    /** Top 10 male name in Germany 2014 */
    public static final List<String> FIRSTNAMES_MALE_ENGLISH = immutableList("Jackson", "Aiden", "Liam", "Lucas",
            "Noah", "Mason", "Ethan", "Caden", "Jacob", "Logan");

    /** Top 10 female name in Germany 2014 */
    public static final List<String> FIRSTNAMES_FEMALE_ENGLISH = immutableList("Sophia", "Emma", "Olivia", "Ava",
            "Isabella", "Mia", "Zoe", "Lily", "Emily", "Madelyn");

    /**
     * The intersection of {@link #FIRSTNAMES_MALE_ENGLISH} and
     * {@link #FIRSTNAMES_FEMALE_ENGLISH}.
     */
    public static final List<String> FIRSTNAMES_ANY_ENGLISH = new CollectionBuilder<String>()
            .add(FIRSTNAMES_FEMALE_ENGLISH).add(FIRSTNAMES_MALE_ENGLISH).toImmutableList();

    /** Top 10 names from U.S. Census Bureau */
    public static final List<String> LAST_NAMES_ENGLISH = immutableList("Smith", "Johnson", "Williams", "Brown",
            "Jones", "Miller", "Davis", "Garcia", "Rodriguez", "Wilson");
}
