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

import java.util.HashSet;
import java.util.Set;

/**
 * Wraps a source generator and ensures all generated values are unique.
 *
 * @param <T> the type of values to generate
 * @author Oliver Wolff
 */
public class UniqueValuesGenerator<T> implements TypedGenerator<T> {

    private static final int DEFAULT_MAX_RETRIES = 100;

    private final TypedGenerator<T> source;
    private final int maxRetries;
    private final Set<T> seen = new HashSet<>();

    /**
     * Creates a unique values generator wrapping the given source.
     *
     * @param source the source generator
     */
    public UniqueValuesGenerator(TypedGenerator<T> source) {
        this(source, DEFAULT_MAX_RETRIES);
    }

    /**
     * Creates a unique values generator with a configurable retry limit.
     *
     * @param source     the source generator
     * @param maxRetries maximum attempts to find a unique value before throwing
     */
    public UniqueValuesGenerator(TypedGenerator<T> source, int maxRetries) {
        this.source = source;
        this.maxRetries = maxRetries;
    }

    @Override
    public T next() {
        for (int i = 0; i < maxRetries; i++) {
            T value = source.next();
            if (seen.add(value)) {
                return value;
            }
        }
        throw new IllegalStateException(
                "Unable to generate unique value after " + maxRetries + " attempts. "
                        + seen.size() + " unique values generated so far.");
    }

    @Override
    public Class<T> getType() {
        return source.getType();
    }
}
