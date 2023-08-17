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

import java.util.Locale;

import de.cuioss.test.generator.TypedGenerator;

/**
 * Generates name strings in the form of 'firstname lastname', depending on the
 * given {@link Locale}
 *
 * @author Oliver Wolff
 *
 */
public class FullNameGenerator implements TypedGenerator<String> {

    private final TypedGenerator<String> firstNames;
    private final TypedGenerator<String> familyNames;

    /**
     * @param locale to be used for determining the concrete name-set. In case it is
     *               {@link Locale#GERMAN} german names will be generated, in all
     *               other cases english-names.
     */
    public FullNameGenerator(final Locale locale) {
        if (Locale.GERMAN.equals(locale)) {
            firstNames = NameGenerators.FIRSTNAMES_ANY_GERMAN.generator();
            familyNames = NameGenerators.FAMILY_NAMES_GERMAN.generator();
        } else {
            firstNames = NameGenerators.FIRSTNAMES_ANY_ENGLISH.generator();
            familyNames = NameGenerators.FAMILY_NAMES_ENGLISH.generator();
        }
    }

    @Override
    public String next() {
        return firstNames.next() + ' ' + familyNames.next();
    }

}
