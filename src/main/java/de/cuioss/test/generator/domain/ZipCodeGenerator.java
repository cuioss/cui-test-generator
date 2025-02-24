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

import static de.cuioss.test.generator.Generators.integers;

import de.cuioss.test.generator.TypedGenerator;

/**
 * Generates German postal codes (Postleitzahlen) for test data generation.
 * 
 * <p>Characteristics:</p>
 * <ul>
 *   <li>Generates 5-digit postal codes</li>
 *   <li>Range: 10000 - 99999</li>
 *   <li>Format matches official German postal code format</li>
 * </ul>
 * 
 * <p><em>Example usage:</em></p>
 * <pre>
 * var generator = new ZipCodeGenerator();
 * Integer zipCode = generator.next(); // e.g. 12345
 * String formattedZip = String.format("%05d", zipCode); // Ensures 5 digits with leading zeros
 * </pre>
 * 
 * <p>Note: This generator returns {@link Integer} values. For display purposes,
 * you may want to format the number to ensure it always shows 5 digits.</p>
 *
 * @author Oliver Wolff
 */
public class ZipCodeGenerator implements TypedGenerator<Integer> {

    private final TypedGenerator<Integer> zibCodes = integers(10000, 99999);

    @Override
    public Integer next() {
        return zibCodes.next();
    }

    @Override
    public Class<Integer> getType() {
        return Integer.class;
    }
}
