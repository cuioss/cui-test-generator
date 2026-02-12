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

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.lang.Math.floor;
import static java.math.BigDecimal.valueOf;

/**
 * Generates random {@link Long} values within a configurable range.
 *
 * @author Oliver Wolff
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
        return range < 0 ? bigDecimalImpl() : longImpl();
    }

    private long longImpl() {
        return min + (long) floor(RandomContext.random().nextDouble() * (range + 1.0));
    }

    private long bigDecimalImpl() {
        BigDecimal localRange = valueOf(max).add(valueOf(1L)).subtract(valueOf(min));
        return valueOf(min).add(valueOf(RandomContext.random().nextDouble()).multiply(localRange))
                .setScale(0, RoundingMode.FLOOR).longValue();
    }

    @Override
    public Class<Long> getType() {
        return Long.class;
    }
}
