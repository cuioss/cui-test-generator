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
 * Generates German postal codes (Postleitzahlen) for test data generation.
 *
 * <p>Characteristics:</p>
 * <ul>
 *   <li>Range: 1067 - 99999, covering the valid German codes from 01067 (Dresden) upward</li>
 *   <li>Returned as {@link Integer}; codes below 10000 represent leading-zero codes such as
 *       01067 and must be formatted with {@code %05d} for display</li>
 * </ul>
 *
 * <p><em>Example usage:</em></p>
 * <pre>
 * var generator = new ZipCodeGenerator();
 * Integer zipCode = generator.next(); // e.g. 1067 or 12345
 * String formattedZip = String.format("%05d", zipCode); // e.g. "01067", "12345"
 * </pre>
 *
 * @author Oliver Wolff
 */
public class ZipCodeGenerator implements TypedGenerator<Integer> {

    private final TypedGenerator<Integer> zipCodes = integers(1067, 99999);

    @Override
    public Integer next() {
        return zipCodes.next();
    }

    @Override
    public Class<Integer> getType() {
        return Integer.class;
    }
}
