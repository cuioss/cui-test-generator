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
package de.cuioss.test.generator.impl;

import de.cuioss.test.generator.TypedGenerator;

import static de.cuioss.test.generator.Generators.nonEmptyStrings;

/**
 * Generates non-empty and non-blank string values for testing purposes.
 * This generator ensures that the produced strings contain at least one
 * non-whitespace character.
 * 
 * <p>Generation rules:</p>
 * <ul>
 *   <li>Uses {@link de.cuioss.test.generator.Generators#nonEmptyStrings()} as base generator</li>
 *   <li>Validates that the string contains non-whitespace characters</li>
 *   <li>Retries up to 100 times to generate a valid string</li>
 *   <li>Throws {@link IllegalStateException} if no valid string can be generated</li>
 * </ul>
 * 
 * <p><em>Example usage:</em></p>
 * <pre>
 * var generator = new NonBlankStringGenerator();
 * String value = generator.next(); // Returns a string with at least one non-whitespace char
 * </pre>
 * 
 * <p>This generator is particularly useful for testing input validation
 * where whitespace-only strings should be treated differently from
 * strings with actual content.</p>
 *
 * @author Oliver Wolff
 * @see de.cuioss.test.generator.Generators#nonEmptyStrings()
 */
public class NonBlankStringGenerator implements TypedGenerator<String> {

    private static final TypedGenerator<String> SOME_NONEMPTY_STRING = nonEmptyStrings();

    @Override
    public Class<String> getType() {
        return String.class;
    }

    @Override
    public String next() {
        var tries = 0;
        while (tries < 100) {
            tries++;
            var candidate = SOME_NONEMPTY_STRING.next();
            if (!candidate.isBlank()) {
                return candidate;
            }
        }
        throw new IllegalStateException("Could not generate non blank string after %d tries".formatted(tries));
    }

}
