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
 * Generates random {@link Double} values within a configurable range.
 * <p>
 * The default range spans the full finite double range,
 * {@code -Double.MAX_VALUE} to {@link Double#MAX_VALUE}, so negative values and zero are
 * produced. Use the two-argument constructor for a narrower range.
 * </p>
 *
 * @author Oliver Wolff
 */
@Getter
public class DoubleGenerator implements TypedGenerator<Double> {

    private final double min;
    private final double max;

    /**
     * Creates a generator for the full finite double range.
     */
    public DoubleGenerator() {
        this(-Double.MAX_VALUE, Double.MAX_VALUE);
    }

    /**
     * Creates a generator for doubles in the range [min, max].
     *
     * @param min lower bound (inclusive), must be finite
     * @param max upper bound (inclusive), must be finite
     * @throws IllegalArgumentException if a bound is not finite or {@code max < min}
     */
    public DoubleGenerator(double min, double max) {
        if (!Double.isFinite(min) || !Double.isFinite(max)) {
            throw new IllegalArgumentException("min and max must be finite, given: [" + min + ", " + max + "]");
        }
        if (max < min) {
            throw new IllegalArgumentException("max must be >= min");
        }
        this.min = min;
        this.max = max;
    }

    @Override
    public Double next() {
        if (min >= max) {
            return min;
        }
        double bound = Math.nextUp(max);
        if (!Double.isInfinite(bound)) {
            return RandomContext.random().nextDouble(min, bound);
        }
        // max == Double.MAX_VALUE, so nextDouble(min, bound) would reject the infinite bound.
        // Apply the JDK's overflow-safe halving (see Random.nextDouble(origin, bound)) so the
        // full [min, max] span never yields Infinity or NaN, mapping the top back to max.
        double r = RandomContext.random().nextDouble();
        r = 2.0 * (r * (0.5 * max - 0.5 * min) + 0.5 * min);
        return r >= max ? max : r;
    }

    @Override
    public Class<Double> getType() {
        return Double.class;
    }
}
