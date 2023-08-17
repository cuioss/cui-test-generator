/*
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Licensed to the author under one or more
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.test.generator.internal.net.java.quickcheck.generator.support;

import static de.cuioss.tools.base.Preconditions.checkArgument;

import java.util.Objects;

import de.cuioss.test.generator.internal.net.java.quickcheck.Generator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.distribution.Distribution;

public class DoubleGenerator implements Generator<Double> {

    private final double min;
    private final double max;
    private final Distribution distribution;

    public DoubleGenerator() {
        this(Double.MIN_VALUE, Double.MAX_VALUE);
    }

    public DoubleGenerator(double min, double max) {
        this(min, max, Distribution.UNIFORM);
    }

    public DoubleGenerator(double min, double max, Distribution dist) {
        checkArgument(max >= min, "min");
        Objects.requireNonNull(dist, "dist");

        this.min = min;
        this.max = max;
        this.distribution = dist;
    }

    @Override
    public Double next() {
        return nextDouble();
    }

    public double nextDouble() {
        return this.distribution.nextRandomNumber() * (this.max - this.min) + this.min;
    }

    @Override
    public String toString() {
        return "%s[min=%s, max=%s, distribution=%s".formatted(getClass().getSimpleName(), min, max, distribution);
    }
}
