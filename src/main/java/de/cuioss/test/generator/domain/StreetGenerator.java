/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.test.generator.domain;

import de.cuioss.test.generator.TypedGenerator;

import static de.cuioss.test.generator.Generators.integers;

/**
 * Generates complete German street addresses by combining street names from {@link StreetNameGenerator}
 * with random house numbers.
 * 
 * <p>The generator creates addresses in the format: "streetname housenumber"</p>
 * <ul>
 *   <li>Street names are typical German street names (e.g., "Hauptstraße", "Bahnhofstraße")</li>
 *   <li>House numbers range from 1 to 111</li>
 * </ul>
 * 
 * <p><em>Example usage:</em></p>
 * <pre>
 * var generator = new StreetGenerator();
 * String address = generator.next(); // e.g. "Hauptstraße 42"
 * </pre>
 *
 * @author Oliver Wolff
 * @see StreetNameGenerator
 */
public class StreetGenerator implements TypedGenerator<String> {

    private final TypedGenerator<String> streets = new StreetNameGenerator();
    private final TypedGenerator<Integer> number = integers(1, 111);

    @Override
    public String next() {
        return streets.next() + " " + number.next();
    }

    @Override
    public Class<String> getType() {
        return String.class;
    }
}
