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
package de.cuioss.test.generator.impl;

import static de.cuioss.test.generator.Generators.nonEmptyStrings;

import de.cuioss.test.generator.TypedGenerator;

/**
 * Provide any {@link String} which is not empty and not blank.
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
            if (!candidate.trim().isEmpty()) {
                return candidate;
            }
        }
        throw new IllegalStateException("Could not generate non blank string after %d tries".formatted(tries));
    }

}
