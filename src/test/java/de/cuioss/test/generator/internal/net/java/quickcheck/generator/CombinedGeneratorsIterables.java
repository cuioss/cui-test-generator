package de.cuioss.test.generator.internal.net.java.quickcheck.generator;

@SuppressWarnings("unused")
public class CombinedGeneratorsIterables {

    /**
     * See documentation of {@link CombinedGenerators#uniqueValues}.
     */
    public static <T> Iterable<T> someUniqueValues(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> generator, int tries) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.uniqueValues(generator, tries));
    }

    /**
     * See documentation of {@link CombinedGenerators#uniqueValues}.
     */
    public static <T> Iterable<T> someUniqueValues(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> generator,
            java.util.Comparator<? super T> comparator, int tries) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.uniqueValues(generator, comparator, tries));
    }

    /**
     * See documentation of {@link CombinedGenerators#uniqueValues}.
     */
    public static <T> Iterable<T> someUniqueValues(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> generator,
            java.util.Comparator<? super T> comparator) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.uniqueValues(generator, comparator));
    }

    /**
     * See documentation of {@link CombinedGenerators#uniqueValues}.
     */
    public static <T> Iterable<T> someUniqueValues(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> generator) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(CombinedGenerators.uniqueValues(generator));
    }

    /**
     * See documentation of {@link CombinedGenerators#triples}.
     */
    public static <A, B, C> Iterable<de.cuioss.test.generator.internal.net.java.quickcheck.collection.Triple<A, B, C>> someTriples(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<A> first,
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<B> second,
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<C> third) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.triples(first, second, third));
    }

    /**
     * See documentation of {@link CombinedGenerators#intArrays}.
     */
    public static Iterable<int[]> someIntArrays() {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(CombinedGenerators.intArrays());
    }

    /**
     * See documentation of {@link CombinedGenerators#intArrays}.
     */
    public static Iterable<int[]> someIntArrays(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<Integer> size) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(CombinedGenerators.intArrays(size));
    }

    /**
     * See documentation of {@link CombinedGenerators#intArrays}.
     */
    public static Iterable<int[]> someIntArrays(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<Integer> content,
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<Integer> size) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.intArrays(content, size));
    }

    /**
     * See documentation of {@link CombinedGenerators#ensureValues}.
     */
    public static <T> Iterable<T> someEnsureValues(Iterable<T> ensuredValues) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(CombinedGenerators.ensureValues(ensuredValues));
    }

    /**
     * See documentation of {@link CombinedGenerators#ensureValues}.
     */
    @SafeVarargs
    public static <T> Iterable<T> someEnsureValues(T... content) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(CombinedGenerators.ensureValues(content));
    }

    /**
     * See documentation of {@link CombinedGenerators#ensureValues}.
     */
    public static <T> Iterable<T> someEnsureValues(Iterable<T> ensuredValues,
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> otherValues) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.ensureValues(ensuredValues, otherValues));
    }

    /**
     * See documentation of {@link CombinedGenerators#ensureValues}.
     */
    public static <T> Iterable<T> someEnsureValues(Iterable<T> ensuredValues, int window,
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> otherValues) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.ensureValues(ensuredValues, window, otherValues));
    }

    /**
     * See documentation of {@link CombinedGenerators#nullsAnd}.
     */
    @SafeVarargs
    public static <T> Iterable<T> someNullsAnd(T... values) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(CombinedGenerators.nullsAnd(values));
    }

    /**
     * See documentation of {@link CombinedGenerators#nullsAnd}.
     */
    public static <T> Iterable<T> someNullsAnd(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> generator) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(CombinedGenerators.nullsAnd(generator));
    }

    /**
     * See documentation of {@link CombinedGenerators#nullsAnd}.
     */
    public static <T> Iterable<T> someNullsAnd(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> generator, int weight) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.nullsAnd(generator, weight));
    }

    /**
     * See documentation of {@link CombinedGenerators#pairs}.
     */
    public static <A, B> Iterable<de.cuioss.test.generator.internal.net.java.quickcheck.collection.Pair<A, B>> somePairs(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<A> first,
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<B> second) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.pairs(first, second));
    }

    /**
     * See documentation of {@link CombinedGenerators#frequency}.
     */
    public static <T> Iterable<T> someFrequency(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> generator, int weight) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.frequency(generator, weight));
    }

    /**
     * See documentation of {@link CombinedGenerators#oneOf}.
     */
    public static <T> Iterable<T> someOneOf(de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> generator) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(CombinedGenerators.oneOf(generator));
    }

    /**
     * See documentation of {@link CombinedGenerators#duplicates}.
     */
    @SafeVarargs
    public static <T> Iterable<java.util.List<T>> someDuplicates(T... input) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(CombinedGenerators.duplicates(input));
    }

    /**
     * See documentation of {@link CombinedGenerators#duplicates}.
     */
    public static <T> Iterable<java.util.List<T>> someDuplicates(Iterable<T> input) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(CombinedGenerators.duplicates(input));
    }

    /**
     * See documentation of {@link CombinedGenerators#maps}.
     */
    public static <K, V> Iterable<java.util.Map<K, V>> someMaps(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<K> keys,
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<V> values) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.maps(keys, values));
    }

    /**
     * See documentation of {@link CombinedGenerators#maps}.
     */
    public static <K, V> Iterable<java.util.Map<K, V>> someMaps(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<K> keys,
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<V> values,
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<Integer> size) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.maps(keys, values, size));
    }

    /**
     * See documentation of {@link CombinedGenerators#maps}.
     */
    public static <K, V> Iterable<java.util.Map<K, V>> someMaps(java.util.Map<K, V> supermap) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(CombinedGenerators.maps(supermap));
    }

    /**
     * See documentation of {@link CombinedGenerators#maps}.
     */
    public static <K, V> Iterable<java.util.Map<K, V>> someMaps(java.util.Map<K, V> supermap,
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<Integer> sizes) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.maps(supermap, sizes));
    }

    /**
     * See documentation of {@link CombinedGenerators#sortedLists}.
     */
    public static <T extends Comparable<T>> Iterable<java.util.List<T>> someSortedLists(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> content) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(CombinedGenerators.sortedLists(content));
    }

    /**
     * See documentation of {@link CombinedGenerators#sortedLists}.
     */
    public static <T extends Comparable<T>> Iterable<java.util.List<T>> someSortedLists(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> content, int low, int high) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.sortedLists(content, low, high));
    }

    /**
     * See documentation of {@link CombinedGenerators#sortedLists}.
     */
    public static <T extends Comparable<T>> Iterable<java.util.List<T>> someSortedLists(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> content,
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<Integer> size) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.sortedLists(content, size));
    }

    /**
     * See documentation of {@link CombinedGenerators#nonEmptySets}.
     */
    public static <T> Iterable<java.util.Set<T>> someNonEmptySets(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<? extends T> content) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(CombinedGenerators.nonEmptySets(content));
    }

    /**
     * See documentation of {@link CombinedGenerators#vectors}.
     */
    public static <T> Iterable<java.util.List<T>> someVectors(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> content, int size) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.vectors(content, size));
    }

    /**
     * See documentation of {@link CombinedGenerators#lists}.
     */
    public static <T> Iterable<java.util.List<T>> someLists(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<? extends T> content) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(CombinedGenerators.lists(content));
    }

    /**
     * See documentation of {@link CombinedGenerators#lists}.
     */
    public static <T> Iterable<java.util.List<T>> someLists(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<? extends T> content,
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<Integer> size) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.lists(content, size));
    }

    /**
     * See documentation of {@link CombinedGenerators#lists}.
     */
    public static <T> Iterable<java.util.List<T>> someLists(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<? extends T> content, int low, int high) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.lists(content, low, high));
    }

    /**
     * See documentation of {@link CombinedGenerators#lists}.
     */
    public static <T> Iterable<java.util.List<T>> someLists(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<? extends T> content, int low) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.lists(content, low));
    }

    /**
     * See documentation of {@link CombinedGenerators#sortedPairs}.
     */
    public static <T extends Comparable<T>> Iterable<de.cuioss.test.generator.internal.net.java.quickcheck.collection.Pair<T, T>> someSortedPairs(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> content) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(CombinedGenerators.sortedPairs(content));
    }

    /**
     * See documentation of {@link CombinedGenerators#sortedTriple}.
     */
    public static <T extends Comparable<T>> Iterable<de.cuioss.test.generator.internal.net.java.quickcheck.collection.Triple<T, T, T>> someSortedTriple(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> content) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(CombinedGenerators.sortedTriple(content));
    }

    /**
     * See documentation of {@link CombinedGenerators#nonEmptyIterators}.
     */
    public static <T> Iterable<java.util.Iterator<T>> someNonEmptyIterators(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> content) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(CombinedGenerators.nonEmptyIterators(content));
    }

    /**
     * See documentation of {@link CombinedGenerators#arrays}.
     */
    public static <T> Iterable<T[]> someArrays(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<? extends T> content, Class<T> type) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.arrays(content, type));
    }

    /**
     * See documentation of {@link CombinedGenerators#arrays}.
     */
    public static <T> Iterable<T[]> someArrays(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<? extends T> content,
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<Integer> size, Class<T> type) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.arrays(content, size, type));
    }

    /**
     * See documentation of {@link CombinedGenerators#iterators}.
     */
    public static <T> Iterable<java.util.Iterator<T>> someIterators(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<? extends T> content) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(CombinedGenerators.iterators(content));
    }

    /**
     * See documentation of {@link CombinedGenerators#iterators}.
     */
    public static <T> Iterable<java.util.Iterator<T>> someIterators(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<? extends T> content,
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<Integer> size) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.iterators(content, size));
    }

    /**
     * See documentation of {@link CombinedGenerators#nonEmptyLists}.
     */
    public static <T> Iterable<java.util.List<T>> someNonEmptyLists(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<? extends T> content) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(CombinedGenerators.nonEmptyLists(content));
    }

    /**
     * See documentation of {@link CombinedGenerators#nonEmptyArrays}.
     */
    public static <T> Iterable<T[]> someNonEmptyArrays(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<? extends T> content, Class<T> type) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.nonEmptyArrays(content, type));
    }

    /**
     * See documentation of {@link CombinedGenerators#byteArrays}.
     */
    public static Iterable<byte[]> someByteArrays() {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(CombinedGenerators.byteArrays());
    }

    /**
     * See documentation of {@link CombinedGenerators#byteArrays}.
     */
    public static Iterable<byte[]> someByteArrays(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<Integer> size) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(CombinedGenerators.byteArrays(size));
    }

    /**
     * See documentation of {@link CombinedGenerators#byteArrays}.
     */
    public static Iterable<byte[]> someByteArrays(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<Byte> content,
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<Integer> size) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.byteArrays(content, size));
    }

    /**
     * See documentation of {@link CombinedGenerators#excludeValues}.
     */
    @SafeVarargs
    public static <T> Iterable<T> someExcludeValues(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> generator, T... excluded) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.excludeValues(generator, excluded));
    }

    /**
     * See documentation of {@link CombinedGenerators#excludeValues}.
     */
    @SafeVarargs
    public static <T> Iterable<T> someExcludeValues(Iterable<T> values, T... excluded) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.excludeValues(values, excluded));
    }

    /**
     * See documentation of {@link CombinedGenerators#excludeValues}.
     */
    public static <T> Iterable<T> someExcludeValues(Iterable<T> values, Iterable<T> excluded) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.excludeValues(values, excluded));
    }

    /**
     * See documentation of {@link CombinedGenerators#excludeValues}.
     */
    public static <T> Iterable<T> someExcludeValues(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> generator, Iterable<T> excluded) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.excludeValues(generator, excluded));
    }

    /**
     * See documentation of {@link CombinedGenerators#sets}.
     */
    public static <T> Iterable<java.util.Set<T>> someSets(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<? extends T> content) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(CombinedGenerators.sets(content));
    }

    /**
     * See documentation of {@link CombinedGenerators#sets}.
     */
    public static <T> Iterable<java.util.Set<T>> someSets(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<? extends T> content,
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<Integer> size) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.sets(content, size));
    }

    /**
     * See documentation of {@link CombinedGenerators#sets}.
     */
    public static <T> Iterable<java.util.Set<T>> someSets(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<? extends T> content, int low, int high) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.sets(content, low, high));
    }

    /**
     * See documentation of {@link CombinedGenerators#sets}.
     */
    @SafeVarargs
    public static <T> Iterable<java.util.Set<T>> someSets(T... superset) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(CombinedGenerators.sets(superset));
    }

    /**
     * See documentation of {@link CombinedGenerators#sets}.
     */
    public static <T> Iterable<java.util.Set<T>> someSets(Iterable<T> superset) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(CombinedGenerators.sets(superset));
    }

    /**
     * See documentation of {@link CombinedGenerators#sets}.
     */
    public static <T> Iterable<java.util.Set<T>> someSets(Iterable<T> superset,
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<Integer> size) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.sets(superset, size));
    }

    /**
     * See documentation of {@link CombinedGenerators#strictlyOrdered}.
     */
    public static <T extends Comparable<T>> Iterable<java.util.List<T>> someStrictlyOrdered(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> input) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(CombinedGenerators.strictlyOrdered(input));
    }

    /**
     * See documentation of {@link CombinedGenerators#strictlyOrdered}.
     */
    public static <T extends Comparable<T>> Iterable<java.util.List<T>> someStrictlyOrdered(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> input, int low, int high) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.strictlyOrdered(input, low, high));
    }

    /**
     * See documentation of {@link CombinedGenerators#strictlyOrdered}.
     */
    public static <T> Iterable<java.util.List<T>> someStrictlyOrdered(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> input, java.util.Comparator<T> comparator) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.strictlyOrdered(input, comparator));
    }

    /**
     * See documentation of {@link CombinedGenerators#strictlyOrdered}.
     */
    public static <T> Iterable<java.util.List<T>> someStrictlyOrdered(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> input, java.util.Comparator<T> comparator,
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<Integer> size) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.strictlyOrdered(input, comparator, size));
    }
}
