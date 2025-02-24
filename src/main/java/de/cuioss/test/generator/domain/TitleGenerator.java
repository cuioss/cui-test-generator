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
import static de.cuioss.test.generator.Generators.strings;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import de.cuioss.test.generator.TypedGenerator;

/**
 * Provides generators for academic and professional titles.
 * 
 * <p>Available generators:</p>
 * <ul>
 *   <li>{@link #READABLE} - Common academic titles for realistic test data</li>
 *   <li>{@link #UNIT_TESTS} - Technical string generator for unit testing</li>
 * </ul>
 * 
 * <p><em>Example usage:</em></p>
 * <pre>
 * {@code TypedGenerator<String> generator = TitleGenerator.READABLE.generator();}
 * String title = generator.next(); // Returns titles like "Dr.", "M.A.", etc.
 * </pre>
 *
 * @author Oliver Wolff
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum TitleGenerator {

    /** 
     * Common academic and professional titles.
     * Includes: Dr., Dr. h.c., M.A., Dr. med.
     */
    READABLE(fixedValues("Dr.", "Dr. h.c.", "M.A.", "Dr. med.")),

    /** 
     * Technical string generator for unit testing.
     * Generates random strings between 1 and 10 characters.
     */
    UNIT_TESTS(strings(1, 10));

    private final TypedGenerator<String> generator;

    /**
     * Provides access to the underlying title generator.
     *
     * @return A {@link TypedGenerator} that generates titles according to the enum constant's specification
     */
    public TypedGenerator<String> generator() {
        return generator;
    }
}
