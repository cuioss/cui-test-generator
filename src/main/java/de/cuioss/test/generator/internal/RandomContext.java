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
package de.cuioss.test.generator.internal;

import java.util.Random;

/**
 * Manages the shared {@link Random} instance and seed state for all generators.
 * <p>
 * Setting the seed allows running tests deterministically with the same generated
 * objects. The {@link #setSeed(long)} method can be used for that.
 * </p>
 * <p>
 * The {@link #initSeed()} method sets a new random seed and returns it. This allows
 * generating random test objects in one run and repeating the same test by setting the
 * seed with {@link #setSeed(long)}.
 * </p>
 * <p>
 * You can set the seed for the JVM with {@link #SEED_SYSTEM_PROPERTY}. This system
 * property will be evaluated at start up. Calling {@link #setSeed(long)} will overwrite
 * this setting.
 * </p>
 */
public final class RandomContext {

    /**
     * System property ({@code de.cuioss.test.generator.seed}) to set the {@link Long long}
     * seed value for the random number generator.
     */
    public static final String SEED_SYSTEM_PROPERTY = "de.cuioss.test.generator.seed";

    static final Random random = new Random(); // NOSONAR: not about cryptography
    static long lastSeed = 0L;

    private RandomContext() {
    }

    static {
        readSystemProperty();
    }

    /**
     * Initializes a new random seed and returns it.
     *
     * @return the new seed value
     */
    public static long initSeed() {
        long seed = random.nextLong();
        setSeed(seed);
        return seed;
    }

    /**
     * Sets the seed for the shared random number generator.
     *
     * @param seed the seed value
     */
    public static void setSeed(long seed) {
        lastSeed = seed;
        random.setSeed(seed);
    }

    /**
     * Returns the last seed that was set.
     *
     * @return the last seed value
     */
    public static long getLastSeed() {
        return lastSeed;
    }

    /**
     * Returns the shared {@link Random} instance used by all generators.
     *
     * @return the shared random instance
     */
    public static Random random() {
        return random;
    }

    static void readSystemProperty() {
        String seed = System.getProperty(SEED_SYSTEM_PROPERTY);
        if (seed == null)
            return;
        setSeed(Long.parseLong(seed));
    }
}
