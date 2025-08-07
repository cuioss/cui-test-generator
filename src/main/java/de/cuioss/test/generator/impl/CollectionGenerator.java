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

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

/**
 * Enhances a {@link TypedGenerator} with collection generation capabilities.
 * This wrapper adds methods for creating {@link List}s, {@link Set}s, and other collection types
 * from any existing generator.
 * 
 * <p>Features:</p>
 * <ul>
 *   <li>Generates Lists with configurable size</li>
 *   <li>Creates Sets (both regular and sorted)</li>
 *   <li>Supports any collection type that can be built from Lists or Sets</li>
 *   <li>Thread-safe if the wrapped generator is thread-safe</li>
 * </ul>
 * 
 * <p><em>Example usage:</em></p>
 * <pre>
 * {@code
 * // Create a generator for integers
 * TypedGenerator<Integer> intGen = Generators.integers(1, 100);
 * 
 * // Create a collection generator
 * var collectionGen = new CollectionGenerator<>(intGen);
 * 
 * // Generate collections
 * List<Integer> list = collectionGen.list(5);      // List of 5 integers
 * Set<Integer> set = collectionGen.set(3);         // Set of 3 integers
 * Collection<Integer> coll = collectionGen.next(); // Random size collection
 * }
 * </pre>
 *
 * @param <T> The type of elements to be generated
 * @author Oliver Wolff
 */
public class CollectionGenerator<T> implements TypedGenerator<T> {

    private static final Logger LOGGER = Logger.getLogger(CollectionGenerator.class.getName());
    private static final String JAVA_UTIL_SORTED_SET = "java.util.SortedSet";

    private static final String JAVA_UTIL_COLLECTION = "java.util.Collection";

    private static final String JAVA_UTIL_SET = "java.util.Set";

    private static final String JAVA_UTIL_LIST = "java.util.List";

    private final TypedGenerator<T> wrapped;

    private final TypedGenerator<Integer> sizeGenerator;

    /**
     * @param wrapped       must not be null
     * @param sizeGenerator must not be null
     */
    public CollectionGenerator(final TypedGenerator<T> wrapped, final TypedGenerator<Integer> sizeGenerator) {
        this.wrapped = requireNonNull(wrapped, "wrapped must not be null");
        this.sizeGenerator = requireNonNull(sizeGenerator, "sizeGenerator must not be null");
    }

    /**
     * Constructor.
     *
     * @param wrapped    generator, must not be null
     * @param lowerBound defines the lower bound of the integer generator that
     *                   determines the of {@link Collection} size
     * @param upperBound defines the upper bound of the integer generator that
     *                   determines the of {@link Collection} size
     */
    public CollectionGenerator(final TypedGenerator<T> wrapped, final int lowerBound, final int upperBound) {
        this(wrapped, Generators.integers(lowerBound, upperBound));
    }

    /**
     * Constructor. using 2 and 12 as bounds of the {@link Collection} size to be
     * created.
     *
     * @param wrapped generator, must not be null
     */
    public CollectionGenerator(final TypedGenerator<T> wrapped) {
        this(wrapped, 2, 12);
    }

    /**
     * @return the next object from the contained {@link TypedGenerator}
     */
    @Override
    public T next() {
        return wrapped.next();
    }

    /**
     * Returns a {@link List} of the elements provided by the generator
     *
     * @param count the number of elements within the list
     * @return a list with a given number of elements.
     */
    public List<T> list(final int count) {
        final List<T> result = new ArrayList<>();
        for (var i = 0; i < count; i++) {
            result.add(next());
        }
        return result;
    }

    /**
     * Returns a {@link Set} of the elements provided by the generator
     *
     * @param count the number of elements within the {@link Set}. It defines an
     *              upper bound of elements, but depending on the elements / the
     *              entropy of the generator there may be a lower number of
     *              elements.
     * @return a {@link Set} with a given number of elements as maximum.
     */
    public Set<T> set(final int count) {
        final Set<T> result = new HashSet<>();
        for (var i = 0; i < count; i++) {
            result.add(next());
        }
        return result;
    }

    /**
     * Returns a {@link SortedSet} of the elements provided by the generator
     *
     * @param count the number of elements within the {@link Set}. It defines an
     *              upper bound of elements, but depending on the elements / the
     *              entropy of the generator there may be a lower number of
     *              elements.
     * @return a {@link Set} with a given number of elements as maximum.
     */
    public SortedSet<T> sortedSet(final int count) {
        return new TreeSet<>(set(count));
    }

    /**
     * @return a {@link Set} with a random number of elements as maximum.
     */
    public Set<T> set() {
        return set(sizeGenerator.next());
    }

    /**
     * @return a {@link List} with a random number of elements as maximum.
     */
    public List<T> list() {
        return list(sizeGenerator.next());
    }

    /**
     * @return a {@link SortedSet} with a random number of elements as maximum.
     */
    public SortedSet<T> sortedSet() {
        return sortedSet(sizeGenerator.next());
    }

    /**
     * Generates a concrete {@link Iterable}. It is smart enough to determine
     * whether the elements are to be wrapped in a {@link List}, {@link Set},
     * {@link Collection} or {@link SortedSet}.
     *
     * @param expectedType type of the expected {@link Iterable}
     * @return depending on the given expectedType a corresponding {@link Iterable},
     * {@link Collection}, {@link List}, {@link SortedSet} or {@link Set}
     */
    public Iterable<T> nextCollection(final Class<? extends Iterable<?>> expectedType) {
        requireNonNull(expectedType, "expectedType must not be null");
        return switch (expectedType.getName()) {
            case JAVA_UTIL_LIST -> list();
            case JAVA_UTIL_SET -> set();
            case JAVA_UTIL_COLLECTION -> list();
            case JAVA_UTIL_SORTED_SET -> sortedSet();
            default -> {
                LOGGER.log(Level.INFO, "No specific case defined for {0}. Returning list-implementation.", expectedType.getName());
                yield list();
            }
        };
    }
}
