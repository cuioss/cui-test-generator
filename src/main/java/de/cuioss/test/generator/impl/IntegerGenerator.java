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

/**
 * Generates random {@link Integer} values within a configurable range.
 *
 * @author Oliver Wolff
 */
@Getter
public class IntegerGenerator implements TypedGenerator<Integer> {

    private final int min;
    private final int max;

    /**
     * Creates a generator for the full integer range.
     */
    public IntegerGenerator() {
        this(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Creates a generator for integers in the range [min, max].
     *
     * @param min lower bound (inclusive)
     * @param max upper bound (inclusive)
     */
    public IntegerGenerator(int min, int max) {
        if (max < min) {
            throw new IllegalArgumentException("max must be >= min");
        }
        this.min = min;
        this.max = max;
    }

    @Override
    public Integer next() {
        long range = (long) max - (long) min + 1;
        return (int) (min + RandomContext.random().nextLong(range));
    }

    @Override
    public Class<Integer> getType() {
        return Integer.class;
    }
}
