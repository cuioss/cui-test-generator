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

import de.cuioss.test.generator.TypedGenerator;

/**
 * Generates typical German street names for test data generation.
 * 
 * <p>Available street names:</p>
 * <ul>
 *   <li>Hauptstraße</li>
 *   <li>Bahnhofstraße</li>
 *   <li>Brunnenweg</li>
 *   <li>Schlossallee</li>
 *   <li>Altrottstrasse</li>
 *   <li>Parkweg</li>
 *   <li>Paradeplatz</li>
 * </ul>
 * 
 * <p><em>Example usage:</em></p>
 * <pre>
 * var generator = new StreetNameGenerator();
 * String streetName = generator.next(); // Returns one of the predefined street names
 * </pre>
 * 
 * <p>This generator is primarily used by {@link StreetGenerator} to create complete addresses.</p>
 *
 * @author Oliver Wolff
 * @see StreetGenerator
 */
public class StreetNameGenerator implements TypedGenerator<String> {

    private final TypedGenerator<String> streets = fixedValues("Hauptstraße", "Bahnhofstraße", "Brunnenweg",
            "Schlossallee", "Altrottstrasse", "Parkweg", "Paradeplatz");

    @Override
    public String next() {
        return streets.next();
    }

    @Override
    public Class<String> getType() {
        return String.class;
    }
}
