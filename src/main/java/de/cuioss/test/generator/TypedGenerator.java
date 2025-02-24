/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.test.generator;

/**
 * A generator creates instances of type T for testing purposes.
 * Implementations must ensure thread-safety and handle null values appropriately.
 *
 * <p>
 * The method {@link #getType()} provides a default implementation using {@link #next()} and reading the
 * concrete {@link Class} of the returned element.
 * This approach assumes that {@link #next()} returns a non-null value at least once.
 * If your generator may return null values, you should override
 * {@link #getType()} to provide the correct type information.
 * </p>
 *
 * <p><em>Usage example from tests:</em></p>
 * <pre>
 * {@code
 * // Create a generator
 * TypedGenerator<String> generator = Generators.nonEmptyStrings();
 * 
 * // Generate values
 * String value = generator.next();
 * 
 * // Get type information
 * Class<String> type = generator.getType();
 * }
 * </pre>
 *
 * @param <T> The type of objects to be generated. Can be any Java type including primitives,
 *            objects, collections, and custom types. The actual constraints on the type depend
 *            on the specific implementation.
 * @author Oliver Wolff
 * @since 1.0
 */
public interface TypedGenerator<T> {

    /**
     * Provides type information about what kind of objects this generator creates.
     * The default implementation uses the first non-null result from {@link #next()}
     * to determine the type.
     *
     * <p><strong>Note:</strong> If your generator may return null values or the generated
     * type differs from the actual instance type, you should override this method.</p>
     *
     * @return The class information indicating which type this generator is responsible for.
     * @throws IllegalStateException if the generator cannot determine the type, for example
     *                               if {@link #next()} consistently returns null
     */
    @SuppressWarnings("unchecked") // the implicit providing of the type is the actual idea
    default Class<T> getType() {
        return (Class<T>) next().getClass();
    }

    /**
     * Generates the next instance based on the generator's configuration.
     * Implementations must ensure thread-safety.
     *
     * @return A newly created instance. May be null if the generator explicitly supports
     * null value generation.
     */
    T next();
}
