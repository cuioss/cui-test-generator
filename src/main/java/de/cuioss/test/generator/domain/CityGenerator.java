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
 * Generates names of major German cities for testing purposes.
 * Provides a fixed set of well-known cities, primarily from southern Germany.
 * 
 * <p>Available cities:</p>
 * <ul>
 *   <li>Heidelberg</li>
 *   <li>Walldorf</li>
 *   <li>Mannheim</li>
 *   <li>Stuttgart</li>
 *   <li>Karlsruhe</li>
 *   <li>Berlin</li>
 *   <li>Freiburg</li>
 *   <li>Hamburg</li>
 *   <li>München</li>
 * </ul>
 * 
 * <p><em>Example usage:</em></p>
 * <pre>
 * var generator = new CityGenerator();
 * String city = generator.next(); // Returns one of the predefined German cities
 * </pre>
 *
 * @author Oliver Wolff
 */
public class CityGenerator implements TypedGenerator<String> {

    private final TypedGenerator<String> cities = fixedValues("Heidelberg", "Walldorf", "Mannheim", "Stuttgart",
            "Karlsruhe", "Berlin", "Freiburg", "Hamburg", "München");

    @Override
    public String next() {
        return cities.next();
    }

    @Override
    public Class<String> getType() {
        return String.class;
    }
}
