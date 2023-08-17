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
 * Generator for some German Streets with Housenumber.
 *
 * @author Oliver Wolff
 *
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
