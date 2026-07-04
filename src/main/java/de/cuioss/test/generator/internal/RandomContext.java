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

import lombok.experimental.UtilityClass;

import java.util.Optional;
import java.util.Random;
import java.util.logging.Logger;

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
@UtilityClass
public class RandomContext {

    /**
     * System property ({@code de.cuioss.test.generator.seed}) to set the {@link Long long}
     * seed value for the random number generator.
     */
    public static final String SEED_SYSTEM_PROPERTY = "de.cuioss.test.generator.seed";

    private static final Logger LOGGER = Logger.getLogger(RandomContext.class.getName());

    static final Random random = new Random(); // NOSONAR: not about cryptography

    /**
     * The last seed applied to {@link #random}. Volatile so that concurrent readers
     * always observe the value actually applied to the shared RNG.
     */
    static volatile long lastSeed;

    /**
     * The seed configured via {@link #SEED_SYSTEM_PROPERTY}, or {@code null} if the
     * property is absent or malformed. Evaluated once at class initialization.
     */
    private static final Long CONFIGURED_SEED = readSystemProperty();

    static {
        if (CONFIGURED_SEED != null) {
            setSeed(CONFIGURED_SEED);
        } else {
            // Capture a concrete seed up-front so getLastSeed() always reflects the
            // actual state of the shared RNG (never the misleading default 0L).
            setSeed(random.nextLong());
        }
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

    /**
     * Returns the seed configured via {@link #SEED_SYSTEM_PROPERTY}, if any.
     * <p>
     * When present, this seed is meant to be applied directly as the per-test seed so
     * that a failing test can be reproduced by re-running the whole suite with the
     * property set.
     * </p>
     *
     * @return the configured seed, or an empty {@link Optional} if the property is
     *         absent or could not be parsed
     */
    public static Optional<Long> getConfiguredSeed() {
        return Optional.ofNullable(CONFIGURED_SEED);
    }

    static Long readSystemProperty() {
        var seed = System.getProperty(SEED_SYSTEM_PROPERTY);
        if (seed == null) {
            return null;
        }
        try {
            return Long.parseLong(seed.trim());
        } catch (NumberFormatException e) {
            LOGGER.warning(() -> "Ignoring malformed value '" + seed + "' for system property '"
                    + SEED_SYSTEM_PROPERTY + "'; falling back to a random seed.");
            return null;
        }
    }
}
