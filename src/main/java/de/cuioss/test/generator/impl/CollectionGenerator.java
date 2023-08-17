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
package de.cuioss.test.generator.impl;

import static de.cuioss.tools.collect.CollectionLiterals.mutableSet;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.tools.logging.CuiLogger;

/**
 * Wraps a given {@link TypedGenerator} and provides additional methods for
 * creating {@link List}s and {@link Set}s
 *
 * @param <T> identifying the type of the object being generated
 * @author Oliver Wolff
 */
public class CollectionGenerator<T> implements TypedGenerator<T> {

    private static final CuiLogger log = new CuiLogger(CollectionGenerator.class);
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
        super();
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
        final Set<T> result = mutableSet();
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
     *         {@link Collection}, {@link List}, {@link SortedSet} or {@link Set}
     */
    public Iterable<T> nextCollection(final Class<? extends Iterable<?>> expectedType) {
        requireNonNull(expectedType, "expectedType must not be null");
        return switch (expectedType.getName()) {
        case JAVA_UTIL_LIST -> list();
        case JAVA_UTIL_SET -> set();
        case JAVA_UTIL_COLLECTION -> list();
        case JAVA_UTIL_SORTED_SET -> sortedSet();
        default -> {
            log.info("No specific case defined for {}. Returning list-implementation.", expectedType.getName());
            yield list();
        }
        };
    }
}
