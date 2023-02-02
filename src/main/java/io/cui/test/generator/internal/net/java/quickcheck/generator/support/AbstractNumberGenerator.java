/*
 *  Licensed to the author under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.cui.test.generator.internal.net.java.quickcheck.generator.support;

import static java.lang.Math.floor;
import static java.math.BigDecimal.valueOf;

import java.math.BigDecimal;
import java.math.RoundingMode;

import io.cui.test.generator.internal.net.java.quickcheck.Generator;
import io.cui.test.generator.internal.net.java.quickcheck.generator.distribution.Distribution;
import io.cui.test.generator.internal.net.java.quickcheck.util.Assert;

abstract class AbstractNumberGenerator<T> implements Generator<T> {

    private final Distribution distribution;
    private final long min;
    private final long max;
    private final long range;

    AbstractNumberGenerator(long min, long max, Distribution dist) {
        Assert.lessOrEqual(max, min, "min <= max");
        this.min = min;
        this.max = max;
        this.range = max - min;
        this.distribution = dist;
    }

    // TODO precision of (-1.0,1.0) distribution.nextRandomNumber() ?
    long nextLong() {
        // use long implementation if there is no overflow for better
        // performance
        return isLongOverflow() ? bigDecimalImpl() : longImpl();
    }

    private boolean isLongOverflow() {
        return range < 0;
    }

    private long longImpl() {
        return min
                + (long) floor(distribution.nextRandomNumber() * (range + 1.0));
    }

    /**
     * Same implementation as {@link AbstractNumberGenerator#longImpl()} based
     * on {@link BigDecimal} to prevent long overflows.
     *
     * @return next long value
     */
    private long bigDecimalImpl() {
        BigDecimal range = valueOf(max).add(valueOf(1L)).subtract(valueOf(min));
        return valueOf(min).add(
                valueOf(distribution.nextRandomNumber()).multiply(range))
                .setScale(0, RoundingMode.FLOOR).longValue();
    }
}
