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

import de.cuioss.test.generator.TypedGenerator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * The generator {@link #READABLE} is for visual mocks, {@link #UNIT_TESTS} for
 * unit-tests.
 *
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum TitleGenerator {

    /** Some Titles */
    READABLE(fixedValues("Dr.", "Dr. h.c.", "M.A.", "Dr. med.")),

    /** Technical String for unit-testing. Min size is 1, max size 256 */
    UNIT_TESTS(strings(1, 10));

    private final TypedGenerator<String> generator;

    /**
     * @return the concrete generator.
     */
    public TypedGenerator<String> generator() {
        return generator;
    }
}
