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
 * Generates random German-style phone numbers for testing purposes.
 * 
 * <p>Phone number format: 0XXX/YYYY where:</p>
 * <ul>
 *   <li>XXX: Area code (100-999)</li>
 *   <li>YYYY: Local number (1000-9999)</li>
 * </ul>
 * 
 * <p>The generated numbers follow a simplified German phone number pattern:
 * <ul>
 *   <li>Always starts with '0' (domestic format)</li>
 *   <li>3-digit area code</li>
 *   <li>Forward slash separator</li>
 *   <li>4-digit local number</li>
 * </ul>
 * 
 * <p><em>Example outputs:</em></p>
 * <pre>
 * "0123/4567"
 * "0789/1234"
 * </pre>
 * 
 * <p><em>Example usage:</em></p>
 * <pre>
 * var generator = new PhoneNumberGenerator();
 * String phoneNumber = generator.next(); // Returns a phone number like "0123/4567"
 * </pre>
 *
 * @author Oliver Wolff
 */
public class PhoneNumberGenerator implements TypedGenerator<String> {

    private final TypedGenerator<Integer> prepend = integers(100, 999);
    private final TypedGenerator<Integer> number = integers(1000, 9999);

    @Override
    public String next() {
        return "0" + prepend.next() + "/" + number.next();
    }

    @Override
    public Class<String> getType() {
        return String.class;
    }
}
