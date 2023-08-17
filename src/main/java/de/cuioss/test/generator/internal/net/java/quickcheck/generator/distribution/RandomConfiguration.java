/*
 * Licensed to the author under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.test.generator.internal.net.java.quickcheck.generator.distribution;

import java.util.Random;

/**
 * The {@link RandomConfiguration} allows to get and set the seed of the random
 * number generator.
 * <p>
 * Setting the seed allows to run test deterministically with the same generated
 * objects. The {@link #setSeed(long)} method can be used for that.
 * </p>
 * <p>
 * An the other hand the {@link #initSeed()} method sets a new random seed and
 * return it. This allows to generate random test objects in one run and repeat
 * the same test by setting the seed with {@link #setSeed(long)}.
 * </p>
 * <p>
 * You can set the seed for the JVM with {@link #SEED_SYSTEM_PROPERTY}. This
 * system property will be evaluated at start up. Calling {@link #setSeed(long)}
 * will overwrite this setting.
 * </p>
 */
public final class RandomConfiguration {

    /**
     * System property ({@code SEED_SYSTEM_PROPERTY}) to set the {@link Long long}
     * seed value for the random number generator.
     * <p>
     * Note: The actual values generated by the RNG still depend on the execution
     * order. That may be not under the control of quickcheck.
     * </p>
     */
    public static final String SEED_SYSTEM_PROPERTY = "de.cuioss.test.generator.seed";
    static final Random random = new Random(); // NOSONAR: owolff: This ist not about cryptography,
                                               // therefore sufficient
    static long lastSeed = 0L;

    private RandomConfiguration() {
    }

    static {
        readSystemProperty();
    }

    public static long initSeed() {
        long seed = random.nextLong();
        setSeed(seed);
        return seed;
    }

    public static void setSeed(long seed) {
        lastSeed = seed;
        random.setSeed(seed);
    }

    public static long getLastSeed() {
        return lastSeed;
    }

    static void readSystemProperty() {
        String seed = System.getProperty(SEED_SYSTEM_PROPERTY);
        if (seed == null)
            return;
        setSeed(Long.parseLong(seed));
    }
}
