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
import de.cuioss.test.generator.internal.RandomContext;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates values by randomly selecting from a fixed list of values.
 *
 * @param <T> the type of values to generate
 * @author Oliver Wolff
 */
public class FixedValuesGenerator<T> implements TypedGenerator<T> {

    @Getter
    private final Class<T> type;
    private final List<T> values;

    /**
     * Creates a generator that randomly selects from the given values.
     *
     * @param type   the type of the generated values
     * @param values the values to choose from, must not be empty
     * @throws IllegalArgumentException if values is empty
     */
    public FixedValuesGenerator(Class<T> type, List<T> values) {
        if (values == null || values.isEmpty()) {
            throw new IllegalArgumentException("values must not be null or empty");
        }
        this.type = type;
        this.values = new ArrayList<>(values);
    }

    @Override
    public T next() {
        return values.get(RandomContext.random().nextInt(values.size()));
    }
}
