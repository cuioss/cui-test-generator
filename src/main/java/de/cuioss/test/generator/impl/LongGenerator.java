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
import lombok.AccessLevel;
import lombok.Getter;

/**
 * Generates random {@link Long} values within a configurable range.
 *
 * @author Oliver Wolff
 * @since 1.0
 */
@Getter
public class LongGenerator implements TypedGenerator<Long> {

    private final long min;
    private final long max;

    @Getter(AccessLevel.NONE)
    private final long range;

    /**
     * Creates a generator for the full long range.
     */
    public LongGenerator() {
        this(Long.MIN_VALUE, Long.MAX_VALUE);
    }

    /**
     * Creates a generator for longs in the range [min, max].
     *
     * @param min lower bound (inclusive)
     * @param max upper bound (inclusive)
     */
    public LongGenerator(long min, long max) {
        if (max < min) {
            throw new IllegalArgumentException("max must be >= min");
        }
        this.min = min;
        this.max = max;
        this.range = max - min;
    }

    @Override
    public Long next() {
        return range < 0 ? wideRangeImpl() : longImpl();
    }

    private long longImpl() {
        if (range == 0) {
            return min;
        }
        if (range == Long.MAX_VALUE) {
            return min + (RandomContext.random().nextLong() & Long.MAX_VALUE);
        }
        return min + RandomContext.random().nextLong(range + 1);
    }

    /**
     * Handles spans whose size {@code (max - min + 1)} exceeds the {@code long} range (i.e. the
     * computed {@link #range} overflowed to a negative value). A full 64-bit draw is sampled and
     * rejected until it falls within {@code [min, max]}. Because such a span always covers at least
     * half of the {@code long} domain, the acceptance probability is {@code >= 0.5} and the expected
     * number of draws is below two. Unlike a single {@code nextDouble()} scaling, this uses the full
     * 64 bits of entropy and can reach {@link Long#MAX_VALUE}.
     */
    private long wideRangeImpl() {
        long candidate;
        do {
            candidate = RandomContext.random().nextLong();
        } while (candidate < min || candidate > max);
        return candidate;
    }

    @Override
    public Class<Long> getType() {
        return Long.class;
    }
}
