package io.cui.test.generator.internal.net.java.quickcheck.generator;

@SuppressWarnings("unused")
public class CombinedGeneratorsIterables{

    /**
    See documentation of {@link CombinedGenerators#uniqueValues}.
    */
    public static <T>  Iterable<T> someUniqueValues(io.cui.test.generator.internal.net.java.quickcheck.Generator<T> generator, int tries){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.uniqueValues(generator, tries));
    }

    /**
    See documentation of {@link CombinedGenerators#uniqueValues}.
    */
    public static <T>  Iterable<T> someUniqueValues(io.cui.test.generator.internal.net.java.quickcheck.Generator<T> generator, java.util.Comparator<? super T> comparator, int tries){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.uniqueValues(generator, comparator, tries));
    }

    /**
    See documentation of {@link CombinedGenerators#uniqueValues}.
    */
    public static <T>  Iterable<T> someUniqueValues(io.cui.test.generator.internal.net.java.quickcheck.Generator<T> generator, java.util.Comparator<? super T> comparator){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.uniqueValues(generator, comparator));
    }

    /**
    See documentation of {@link CombinedGenerators#uniqueValues}.
    */
    public static <T>  Iterable<T> someUniqueValues(io.cui.test.generator.internal.net.java.quickcheck.Generator<T> generator){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(CombinedGenerators.uniqueValues(generator));
    }

    /**
    See documentation of {@link CombinedGenerators#triples}.
    */
    public static <A, B, C>  Iterable<io.cui.test.generator.internal.net.java.quickcheck.collection.Triple<A,B,C>> someTriples(io.cui.test.generator.internal.net.java.quickcheck.Generator<A> first, io.cui.test.generator.internal.net.java.quickcheck.Generator<B> second, io.cui.test.generator.internal.net.java.quickcheck.Generator<C> third){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.triples(first, second, third));
    }

    /**
    See documentation of {@link CombinedGenerators#intArrays}.
    */
    public static  Iterable<int[]> someIntArrays(){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(CombinedGenerators.intArrays());
    }

    /**
    See documentation of {@link CombinedGenerators#intArrays}.
    */
    public static  Iterable<int[]> someIntArrays(io.cui.test.generator.internal.net.java.quickcheck.Generator<Integer> size){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(CombinedGenerators.intArrays(size));
    }

    /**
    See documentation of {@link CombinedGenerators#intArrays}.
    */
    public static  Iterable<int[]> someIntArrays(io.cui.test.generator.internal.net.java.quickcheck.Generator<Integer> content, io.cui.test.generator.internal.net.java.quickcheck.Generator<Integer> size){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.intArrays(content, size));
    }

    /**
    See documentation of {@link CombinedGenerators#ensureValues}.
    */
    public static <T>  Iterable<T> someEnsureValues(Iterable<T> ensuredValues){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(CombinedGenerators.ensureValues(ensuredValues));
    }

    /**
    See documentation of {@link CombinedGenerators#ensureValues}.
    */
    @SafeVarargs
    public static <T>  Iterable<T> someEnsureValues(T... content){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(CombinedGenerators.ensureValues(content));
    }

    /**
    See documentation of {@link CombinedGenerators#ensureValues}.
    */
    public static <T>  Iterable<T> someEnsureValues(Iterable<T> ensuredValues, io.cui.test.generator.internal.net.java.quickcheck.Generator<T> otherValues){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.ensureValues(ensuredValues, otherValues));
    }

    /**
    See documentation of {@link CombinedGenerators#ensureValues}.
    */
    public static <T>  Iterable<T> someEnsureValues(Iterable<T> ensuredValues, int window, io.cui.test.generator.internal.net.java.quickcheck.Generator<T> otherValues){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.ensureValues(ensuredValues, window, otherValues));
    }

    /**
    See documentation of {@link CombinedGenerators#nullsAnd}.
    */
    @SafeVarargs
    public static <T>  Iterable<T> someNullsAnd(T... values){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(CombinedGenerators.nullsAnd(values));
    }

    /**
    See documentation of {@link CombinedGenerators#nullsAnd}.
    */
    public static <T>  Iterable<T> someNullsAnd(io.cui.test.generator.internal.net.java.quickcheck.Generator<T> generator){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(CombinedGenerators.nullsAnd(generator));
    }

    /**
    See documentation of {@link CombinedGenerators#nullsAnd}.
    */
    public static <T>  Iterable<T> someNullsAnd(io.cui.test.generator.internal.net.java.quickcheck.Generator<T> generator, int weight){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.nullsAnd(generator, weight));
    }

    /**
    See documentation of {@link CombinedGenerators#pairs}.
    */
    public static <A, B>  Iterable<io.cui.test.generator.internal.net.java.quickcheck.collection.Pair<A,B>> somePairs(io.cui.test.generator.internal.net.java.quickcheck.Generator<A> first, io.cui.test.generator.internal.net.java.quickcheck.Generator<B> second){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.pairs(first, second));
    }

    /**
    See documentation of {@link CombinedGenerators#frequency}.
    */
    public static <T>  Iterable<T> someFrequency(io.cui.test.generator.internal.net.java.quickcheck.Generator<T> generator, int weight){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.frequency(generator, weight));
    }

    /**
    See documentation of {@link CombinedGenerators#oneOf}.
    */
    public static <T>  Iterable<T> someOneOf(io.cui.test.generator.internal.net.java.quickcheck.Generator<T> generator){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(CombinedGenerators.oneOf(generator));
    }

    /**
    See documentation of {@link CombinedGenerators#duplicates}.
    */
    @SafeVarargs
    public static <T>  Iterable<java.util.List<T>> someDuplicates(T... input){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(CombinedGenerators.duplicates(input));
    }

    /**
    See documentation of {@link CombinedGenerators#duplicates}.
    */
    public static <T>  Iterable<java.util.List<T>> someDuplicates(Iterable<T> input){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(CombinedGenerators.duplicates(input));
    }

    /**
    See documentation of {@link CombinedGenerators#maps}.
    */
    public static <K, V>  Iterable<java.util.Map<K,V>> someMaps(io.cui.test.generator.internal.net.java.quickcheck.Generator<K> keys, io.cui.test.generator.internal.net.java.quickcheck.Generator<V> values){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.maps(keys, values));
    }

    /**
    See documentation of {@link CombinedGenerators#maps}.
    */
    public static <K, V>  Iterable<java.util.Map<K,V>> someMaps(io.cui.test.generator.internal.net.java.quickcheck.Generator<K> keys, io.cui.test.generator.internal.net.java.quickcheck.Generator<V> values, io.cui.test.generator.internal.net.java.quickcheck.Generator<Integer> size){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.maps(keys, values, size));
    }

    /**
    See documentation of {@link CombinedGenerators#maps}.
    */
    public static <K, V>  Iterable<java.util.Map<K,V>> someMaps(java.util.Map<K,V> supermap){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(CombinedGenerators.maps(supermap));
    }

    /**
    See documentation of {@link CombinedGenerators#maps}.
    */
    public static <K, V>  Iterable<java.util.Map<K,V>> someMaps(java.util.Map<K,V> supermap, io.cui.test.generator.internal.net.java.quickcheck.Generator<Integer> sizes){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.maps(supermap, sizes));
    }

    /**
    See documentation of {@link CombinedGenerators#sortedLists}.
    */
    public static <T extends Comparable<T>>  Iterable<java.util.List<T>> someSortedLists(io.cui.test.generator.internal.net.java.quickcheck.Generator<T> content){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(CombinedGenerators.sortedLists(content));
    }

    /**
    See documentation of {@link CombinedGenerators#sortedLists}.
    */
    public static <T extends Comparable<T>>  Iterable<java.util.List<T>> someSortedLists(io.cui.test.generator.internal.net.java.quickcheck.Generator<T> content, int low, int high){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.sortedLists(content, low, high));
    }

    /**
    See documentation of {@link CombinedGenerators#sortedLists}.
    */
    public static <T extends Comparable<T>>  Iterable<java.util.List<T>> someSortedLists(io.cui.test.generator.internal.net.java.quickcheck.Generator<T> content, io.cui.test.generator.internal.net.java.quickcheck.Generator<Integer> size){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.sortedLists(content, size));
    }

    /**
    See documentation of {@link CombinedGenerators#nonEmptySets}.
    */
    public static <T>  Iterable<java.util.Set<T>> someNonEmptySets(io.cui.test.generator.internal.net.java.quickcheck.Generator<? extends T> content){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(CombinedGenerators.nonEmptySets(content));
    }

    /**
    See documentation of {@link CombinedGenerators#vectors}.
    */
    public static <T>  Iterable<java.util.List<T>> someVectors(io.cui.test.generator.internal.net.java.quickcheck.Generator<T> content, int size){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.vectors(content, size));
    }

    /**
    See documentation of {@link CombinedGenerators#lists}.
    */
    public static <T>  Iterable<java.util.List<T>> someLists(io.cui.test.generator.internal.net.java.quickcheck.Generator<? extends T> content){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(CombinedGenerators.lists(content));
    }

    /**
    See documentation of {@link CombinedGenerators#lists}.
    */
    public static <T>  Iterable<java.util.List<T>> someLists(io.cui.test.generator.internal.net.java.quickcheck.Generator<? extends T> content, io.cui.test.generator.internal.net.java.quickcheck.Generator<Integer> size){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.lists(content, size));
    }

    /**
    See documentation of {@link CombinedGenerators#lists}.
    */
    public static <T>  Iterable<java.util.List<T>> someLists(io.cui.test.generator.internal.net.java.quickcheck.Generator<? extends T> content, int low, int high){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.lists(content, low, high));
    }

    /**
    See documentation of {@link CombinedGenerators#lists}.
    */
    public static <T>  Iterable<java.util.List<T>> someLists(io.cui.test.generator.internal.net.java.quickcheck.Generator<? extends T> content, int low){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.lists(content, low));
    }

    /**
    See documentation of {@link CombinedGenerators#sortedPairs}.
    */
    public static <T extends Comparable<T>>  Iterable<io.cui.test.generator.internal.net.java.quickcheck.collection.Pair<T,T>> someSortedPairs(io.cui.test.generator.internal.net.java.quickcheck.Generator<T> content){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(CombinedGenerators.sortedPairs(content));
    }

    /**
    See documentation of {@link CombinedGenerators#sortedTriple}.
    */
    public static <T extends Comparable<T>>  Iterable<io.cui.test.generator.internal.net.java.quickcheck.collection.Triple<T,T,T>> someSortedTriple(io.cui.test.generator.internal.net.java.quickcheck.Generator<T> content){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(CombinedGenerators.sortedTriple(content));
    }

    /**
    See documentation of {@link CombinedGenerators#nonEmptyIterators}.
    */
    public static <T>  Iterable<java.util.Iterator<T>> someNonEmptyIterators(io.cui.test.generator.internal.net.java.quickcheck.Generator<T> content){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(CombinedGenerators.nonEmptyIterators(content));
    }

    /**
    See documentation of {@link CombinedGenerators#arrays}.
    */
    public static <T>  Iterable<T[]> someArrays(io.cui.test.generator.internal.net.java.quickcheck.Generator<? extends T> content, Class<T> type){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.arrays(content, type));
    }

    /**
    See documentation of {@link CombinedGenerators#arrays}.
    */
    public static <T>  Iterable<T[]> someArrays(io.cui.test.generator.internal.net.java.quickcheck.Generator<? extends T> content, io.cui.test.generator.internal.net.java.quickcheck.Generator<Integer> size, Class<T> type){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.arrays(content, size, type));
    }

    /**
    See documentation of {@link CombinedGenerators#iterators}.
    */
    public static <T>  Iterable<java.util.Iterator<T>> someIterators(io.cui.test.generator.internal.net.java.quickcheck.Generator<? extends T> content){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(CombinedGenerators.iterators(content));
    }

    /**
    See documentation of {@link CombinedGenerators#iterators}.
    */
    public static <T>  Iterable<java.util.Iterator<T>> someIterators(io.cui.test.generator.internal.net.java.quickcheck.Generator<? extends T> content, io.cui.test.generator.internal.net.java.quickcheck.Generator<Integer> size){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.iterators(content, size));
    }

    /**
    See documentation of {@link CombinedGenerators#nonEmptyLists}.
    */
    public static <T>  Iterable<java.util.List<T>> someNonEmptyLists(io.cui.test.generator.internal.net.java.quickcheck.Generator<? extends T> content){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(CombinedGenerators.nonEmptyLists(content));
    }

    /**
    See documentation of {@link CombinedGenerators#nonEmptyArrays}.
    */
    public static <T>  Iterable<T[]> someNonEmptyArrays(io.cui.test.generator.internal.net.java.quickcheck.Generator<? extends T> content, Class<T> type){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.nonEmptyArrays(content, type));
    }

    /**
    See documentation of {@link CombinedGenerators#byteArrays}.
    */
    public static  Iterable<byte[]> someByteArrays(){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(CombinedGenerators.byteArrays());
    }

    /**
    See documentation of {@link CombinedGenerators#byteArrays}.
    */
    public static  Iterable<byte[]> someByteArrays(io.cui.test.generator.internal.net.java.quickcheck.Generator<Integer> size){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(CombinedGenerators.byteArrays(size));
    }

    /**
    See documentation of {@link CombinedGenerators#byteArrays}.
    */
    public static  Iterable<byte[]> someByteArrays(io.cui.test.generator.internal.net.java.quickcheck.Generator<Byte> content, io.cui.test.generator.internal.net.java.quickcheck.Generator<Integer> size){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.byteArrays(content, size));
    }

    /**
    See documentation of {@link CombinedGenerators#excludeValues}.
    */
    @SafeVarargs
    public static <T>  Iterable<T> someExcludeValues(io.cui.test.generator.internal.net.java.quickcheck.Generator<T> generator, T... excluded){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.excludeValues(generator, excluded));
    }

    /**
    See documentation of {@link CombinedGenerators#excludeValues}.
    */
    @SafeVarargs
    public static <T>  Iterable<T> someExcludeValues(Iterable<T> values, T... excluded){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.excludeValues(values, excluded));
    }

    /**
    See documentation of {@link CombinedGenerators#excludeValues}.
    */
    public static <T>  Iterable<T> someExcludeValues(Iterable<T> values, Iterable<T> excluded){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.excludeValues(values, excluded));
    }

    /**
    See documentation of {@link CombinedGenerators#excludeValues}.
    */
    public static <T>  Iterable<T> someExcludeValues(io.cui.test.generator.internal.net.java.quickcheck.Generator<T> generator, Iterable<T> excluded){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.excludeValues(generator, excluded));
    }

    /**
    See documentation of {@link CombinedGenerators#sets}.
    */
    public static <T>  Iterable<java.util.Set<T>> someSets(io.cui.test.generator.internal.net.java.quickcheck.Generator<? extends T> content){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(CombinedGenerators.sets(content));
    }

    /**
    See documentation of {@link CombinedGenerators#sets}.
    */
    public static <T>  Iterable<java.util.Set<T>> someSets(io.cui.test.generator.internal.net.java.quickcheck.Generator<? extends T> content, io.cui.test.generator.internal.net.java.quickcheck.Generator<Integer> size){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.sets(content, size));
    }

    /**
    See documentation of {@link CombinedGenerators#sets}.
    */
    public static <T>  Iterable<java.util.Set<T>> someSets(io.cui.test.generator.internal.net.java.quickcheck.Generator<? extends T> content, int low, int high){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.sets(content, low, high));
    }

    /**
    See documentation of {@link CombinedGenerators#sets}.
    */
    @SafeVarargs
    public static <T>  Iterable<java.util.Set<T>> someSets(T... superset){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(CombinedGenerators.sets(superset));
    }

    /**
    See documentation of {@link CombinedGenerators#sets}.
    */
    public static <T>  Iterable<java.util.Set<T>> someSets(Iterable<T> superset){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(CombinedGenerators.sets(superset));
    }

    /**
    See documentation of {@link CombinedGenerators#sets}.
    */
    public static <T>  Iterable<java.util.Set<T>> someSets(Iterable<T> superset, io.cui.test.generator.internal.net.java.quickcheck.Generator<Integer> size){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.sets(superset, size));
    }

    /**
    See documentation of {@link CombinedGenerators#strictlyOrdered}.
    */
    public static <T extends Comparable<T>>  Iterable<java.util.List<T>> someStrictlyOrdered(io.cui.test.generator.internal.net.java.quickcheck.Generator<T> input){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(CombinedGenerators.strictlyOrdered(input));
    }

    /**
    See documentation of {@link CombinedGenerators#strictlyOrdered}.
    */
    public static <T extends Comparable<T>>  Iterable<java.util.List<T>> someStrictlyOrdered(io.cui.test.generator.internal.net.java.quickcheck.Generator<T> input, int low, int high){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.strictlyOrdered(input, low, high));
    }

    /**
    See documentation of {@link CombinedGenerators#strictlyOrdered}.
    */
    public static <T>  Iterable<java.util.List<T>> someStrictlyOrdered(io.cui.test.generator.internal.net.java.quickcheck.Generator<T> input, java.util.Comparator<T> comparator){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.strictlyOrdered(input, comparator));
    }

    /**
    See documentation of {@link CombinedGenerators#strictlyOrdered}.
    */
    public static <T>  Iterable<java.util.List<T>> someStrictlyOrdered(io.cui.test.generator.internal.net.java.quickcheck.Generator<T> input, java.util.Comparator<T> comparator, io.cui.test.generator.internal.net.java.quickcheck.Generator<Integer> size){
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                CombinedGenerators.strictlyOrdered(input, comparator, size));
    }
}
