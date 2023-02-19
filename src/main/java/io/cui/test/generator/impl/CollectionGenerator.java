package io.cui.test.generator.impl;

import static io.cui.tools.collect.CollectionLiterals.mutableSet;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import io.cui.test.generator.Generators;
import io.cui.test.generator.TypedGenerator;
import io.cui.tools.logging.CuiLogger;

/**
 * Wraps a given {@link TypedGenerator} and provides additional methods for creating {@link List}s
 * and {@link Set}s
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
     * @param wrapped must not be null
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
     * @param wrapped generator, must not be null
     * @param lowerBound defines the lower bound of the integer generator that determines the
     *            of {@link Collection} size
     * @param upperBound defines the upper bound of the integer generator that determines the
     *            of {@link Collection} size
     */
    public CollectionGenerator(final TypedGenerator<T> wrapped, final int lowerBound,
            final int upperBound) {
        this(wrapped,
                Generators.integers(lowerBound, upperBound));
    }

    /**
     * Constructor.
     * using 2 and 12 as bounds of the {@link Collection} size to be created.
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
        return this.wrapped.next();
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
     * @param count the number of elements within the {@link Set}. It defines an upper bound of
     *            elements, but depending on the elements / the entropy of the generator there may
     *            be a lower number of elements.
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
     * @param count the number of elements within the {@link Set}. It defines an upper bound of
     *            elements, but depending on the elements / the entropy of the generator there may
     *            be a lower number of elements.
     * @return a {@link Set} with a given number of elements as maximum.
     */
    public SortedSet<T> sortedSet(final int count) {
        return new TreeSet<>(set(count));
    }

    /**
     * @return a {@link Set} with a random number of elements as maximum.
     */
    public Set<T> set() {
        return set(this.sizeGenerator.next());
    }

    /**
     * @return a {@link List} with a random number of elements as maximum.
     */
    public List<T> list() {
        return list(this.sizeGenerator.next());
    }

    /**
     * @return a {@link SortedSet} with a random number of elements as maximum.
     */
    public SortedSet<T> sortedSet() {
        return sortedSet(this.sizeGenerator.next());
    }

    /**
     * Generates a concrete {@link Iterable}.
     * It is smart enough to determine whether the elements are to be wrapped in a {@link List},
     * {@link Set}, {@link Collection} or {@link SortedSet}.
     *
     * @param expectedType type of the expected {@link Iterable}
     * @return depending on the given expectedType a corresponding {@link Iterable},
     *         {@link Collection}, {@link List}, {@link SortedSet} or {@link Set}
     */
    public Iterable<T> nextCollection(final Class<? extends Iterable<?>> expectedType) {
        requireNonNull(expectedType, "expectedType must not be null");
        switch (expectedType.getName()) {
            case JAVA_UTIL_LIST:
                return list();
            case JAVA_UTIL_SET:
                return set();
            case JAVA_UTIL_COLLECTION:
                return list();
            case JAVA_UTIL_SORTED_SET:
                return sortedSet();
            default:
                log.info("No specific case defined for {}. Returning list-implementation.",
                        expectedType.getName());
                return list();
        }
    }
}
