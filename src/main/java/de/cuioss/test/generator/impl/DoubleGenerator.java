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
 * Note: The default range uses {@link Double#MIN_VALUE} (smallest positive value, ~4.9E-324)
 * to {@link Double#MAX_VALUE}. For negative doubles, use the two-argument constructor.
 * </p>
 *
 * @author Oliver Wolff
 */
@Getter
public class DoubleGenerator implements TypedGenerator<Double> {

    private final double min;
    private final double max;

    /**
     * Creates a generator for the full double range.
     */
    public DoubleGenerator() {
        this(Double.MIN_VALUE, Double.MAX_VALUE);
    }

    /**
     * Creates a generator for doubles in the range [min, max].
     *
     * @param min lower bound (inclusive)
     * @param max upper bound (inclusive)
     */
    public DoubleGenerator(double min, double max) {
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
        if (Double.isInfinite(bound)) {
            // max is Double.MAX_VALUE; fall back to scaled nextDouble
            return RandomContext.random().nextDouble() * (max - min) + min;
        }
        return RandomContext.random().nextDouble(min, bound);
    }

    @Override
    public Class<Double> getType() {
        return Double.class;
    }
}
