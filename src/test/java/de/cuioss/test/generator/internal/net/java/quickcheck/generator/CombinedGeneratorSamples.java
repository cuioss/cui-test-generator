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
package de.cuioss.test.generator.internal.net.java.quickcheck.generator;

public class CombinedGeneratorSamples {

    /**
     * See documentation of {@link CombinedGenerators#uniqueValues}.
     */
    public static <T> T anyUniqueValue(de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> generator,
            int tries) {
        return CombinedGenerators.uniqueValues(generator, tries).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#uniqueValues}.
     */
    public static <T> T anyUniqueValue(de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> generator,
            java.util.Comparator<? super T> comparator, int tries) {
        return CombinedGenerators.uniqueValues(generator, comparator, tries).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#uniqueValues}.
     */
    public static <T> T anyUniqueValue(de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> generator,
            java.util.Comparator<? super T> comparator) {
        return CombinedGenerators.uniqueValues(generator, comparator).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#uniqueValues}.
     */
    public static <T> T anyUniqueValue(de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> generator) {
        return CombinedGenerators.uniqueValues(generator).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#triples}.
     */
    public static <A, B, C> de.cuioss.test.generator.internal.net.java.quickcheck.collection.Triple<A, B, C> anyTriple(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<A> first,
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<B> second,
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<C> third) {
        return CombinedGenerators.triples(first, second, third).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#intArrays}.
     */
    public static int[] anyIntArray() {
        return CombinedGenerators.intArrays().next();
    }

    /**
     * See documentation of {@link CombinedGenerators#intArrays}.
     */
    public static int[] anyIntArray(de.cuioss.test.generator.internal.net.java.quickcheck.Generator<Integer> size) {
        return CombinedGenerators.intArrays(size).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#intArrays}.
     */
    public static int[] anyIntArray(de.cuioss.test.generator.internal.net.java.quickcheck.Generator<Integer> content,
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<Integer> size) {
        return CombinedGenerators.intArrays(content, size).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#ensureValues}.
     */
    public static <T> T anyEnsureValue(Iterable<T> ensuredValues) {
        return CombinedGenerators.ensureValues(ensuredValues).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#ensureValues}.
     */
    @SafeVarargs
    public static <T> T anyEnsureValue(T... content) {
        return CombinedGenerators.ensureValues(content).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#ensureValues}.
     */
    public static <T> T anyEnsureValue(Iterable<T> ensuredValues,
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> otherValues) {
        return CombinedGenerators.ensureValues(ensuredValues, otherValues).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#ensureValues}.
     */
    public static <T> T anyEnsureValue(Iterable<T> ensuredValues, int window,
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> otherValues) {
        return CombinedGenerators.ensureValues(ensuredValues, window, otherValues).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#nullsAnd}.
     */
    @SafeVarargs
    public static <T> T anyNullsAnd(T... values) {
        return CombinedGenerators.nullsAnd(values).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#nullsAnd}.
     */
    public static <T> T anyNullsAnd(de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> generator) {
        return CombinedGenerators.nullsAnd(generator).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#nullsAnd}.
     */
    public static <T> T anyNullsAnd(de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> generator,
            int weight) {
        return CombinedGenerators.nullsAnd(generator, weight).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#pairs}.
     */
    public static <A, B> de.cuioss.test.generator.internal.net.java.quickcheck.collection.Pair<A, B> anyPair(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<A> first,
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<B> second) {
        return CombinedGenerators.pairs(first, second).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#frequency}.
     */
    public static <T> T anyFrequency(de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> generator,
            int weight) {
        return CombinedGenerators.frequency(generator, weight).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#oneOf}.
     */
    public static <T> T anyOneOf(de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> generator) {
        return CombinedGenerators.oneOf(generator).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#duplicates}.
     */
    @SafeVarargs
    public static <T> java.util.List<T> anyDuplicate(T... input) {
        return CombinedGenerators.duplicates(input).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#duplicates}.
     */
    public static <T> java.util.List<T> anyDuplicate(Iterable<T> input) {
        return CombinedGenerators.duplicates(input).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#maps}.
     */
    public static <K, V> java.util.Map<K, V> anyMap(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<K> keys,
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<V> values) {
        return CombinedGenerators.maps(keys, values).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#maps}.
     */
    public static <K, V> java.util.Map<K, V> anyMap(java.util.Map<K, V> supermap,
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<Integer> sizes) {
        return CombinedGenerators.maps(supermap, sizes).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#maps}.
     */
    public static <K, V> java.util.Map<K, V> anyMap(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<K> keys,
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<V> values,
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<Integer> size) {
        return CombinedGenerators.maps(keys, values, size).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#maps}.
     */
    public static <K, V> java.util.Map<K, V> anyMap(java.util.Map<K, V> supermap) {
        return CombinedGenerators.maps(supermap).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#sortedLists}.
     */
    public static <T extends Comparable<T>> java.util.List<T> anySortedList(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> content) {
        return CombinedGenerators.sortedLists(content).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#sortedLists}.
     */
    public static <T extends Comparable<T>> java.util.List<T> anySortedList(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> content, int low, int high) {
        return CombinedGenerators.sortedLists(content, low, high).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#sortedLists}.
     */
    public static <T extends Comparable<T>> java.util.List<T> anySortedList(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> content,
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<Integer> size) {
        return CombinedGenerators.sortedLists(content, size).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#nonEmptySets}.
     */
    public static <T> java.util.Set<T> anyNonEmptySet(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> content) {
        return CombinedGenerators.nonEmptySets(content).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#vectors}.
     */
    public static <T> java.util.List<T> anyVector(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> content, int size) {
        return CombinedGenerators.vectors(content, size).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#lists}.
     */
    public static <T> java.util.List<T> anyList(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> content) {
        return CombinedGenerators.lists(content).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#lists}.
     */
    public static <T> java.util.List<T> anyList(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> content,
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<Integer> size) {
        return CombinedGenerators.lists(content, size).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#lists}.
     */
    public static <T> java.util.List<T> anyList(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> content, int low, int high) {
        return CombinedGenerators.lists(content, low, high).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#lists}.
     */
    public static <T> java.util.List<T> anyList(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> content, int low) {
        return CombinedGenerators.lists(content, low).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#sortedPairs}.
     */
    public static <T extends Comparable<T>> de.cuioss.test.generator.internal.net.java.quickcheck.collection.Pair<T, T> anySortedPair(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> content) {
        return CombinedGenerators.sortedPairs(content).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#sortedTriple}.
     */
    public static <T extends Comparable<T>> de.cuioss.test.generator.internal.net.java.quickcheck.collection.Triple<T, T, T> anySortedTriple(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> content) {
        return CombinedGenerators.sortedTriple(content).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#nonEmptyIterators}.
     */
    public static <T> java.util.Iterator<T> anyNonEmptyIterator(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> content) {
        return CombinedGenerators.nonEmptyIterators(content).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#arrays}.
     */
    public static <T> T[] anyArray(de.cuioss.test.generator.internal.net.java.quickcheck.Generator<? extends T> content,
            Class<T> type) {
        return CombinedGenerators.arrays(content, type).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#arrays}.
     */
    public static <T> T[] anyArray(de.cuioss.test.generator.internal.net.java.quickcheck.Generator<? extends T> content,
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<Integer> size, Class<T> type) {
        return CombinedGenerators.arrays(content, size, type).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#iterators}.
     */
    public static <T> java.util.Iterator<T> anyIterator(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> content) {
        return CombinedGenerators.iterators(content).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#iterators}.
     */
    public static <T> java.util.Iterator<T> anyIterator(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> content,
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<Integer> size) {
        return CombinedGenerators.iterators(content, size).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#nonEmptyLists}.
     */
    public static <T> java.util.List<T> anyNonEmptyList(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> content) {
        return CombinedGenerators.nonEmptyLists(content).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#nonEmptyArrays}.
     */
    public static <T> T[] anyNonEmptyArray(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<? extends T> content, Class<T> type) {
        return CombinedGenerators.nonEmptyArrays(content, type).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#byteArrays}.
     */
    public static byte[] anyByteArray() {
        return CombinedGenerators.byteArrays().next();
    }

    /**
     * See documentation of {@link CombinedGenerators#byteArrays}.
     */
    public static byte[] anyByteArray(de.cuioss.test.generator.internal.net.java.quickcheck.Generator<Integer> size) {
        return CombinedGenerators.byteArrays(size).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#byteArrays}.
     */
    public static byte[] anyByteArray(de.cuioss.test.generator.internal.net.java.quickcheck.Generator<Byte> content,
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<Integer> size) {
        return CombinedGenerators.byteArrays(content, size).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#excludeValues}.
     */
    @SafeVarargs
    public static <T> T anyExcludeValue(de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> generator,
            T... excluded) {
        return CombinedGenerators.excludeValues(generator, excluded).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#excludeValues}.
     */
    @SafeVarargs
    public static <T> T anyExcludeValue(Iterable<T> values, T... excluded) {
        return CombinedGenerators.excludeValues(values, excluded).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#excludeValues}.
     */
    public static <T> T anyExcludeValue(Iterable<T> values, Iterable<T> excluded) {
        return CombinedGenerators.excludeValues(values, excluded).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#excludeValues}.
     */
    public static <T> T anyExcludeValue(de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> generator,
            Iterable<T> excluded) {
        return CombinedGenerators.excludeValues(generator, excluded).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#sets}.
     */
    public static <T> java.util.Set<T> anySet(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> content, int low, int high) {
        return CombinedGenerators.sets(content, low, high).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#sets}.
     */
    public static <T> java.util.Set<T> anySet(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> content,
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<Integer> size) {
        return CombinedGenerators.sets(content, size).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#sets}.
     */
    public static <T> java.util.Set<T> anySet(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> content) {
        return CombinedGenerators.sets(content).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#sets}.
     */
    @SafeVarargs
    public static <T> java.util.Set<T> anySet(T... superset) {
        return CombinedGenerators.sets(superset).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#sets}.
     */
    public static <T> java.util.Set<T> anySet(Iterable<T> superset) {
        return CombinedGenerators.sets(superset).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#sets}.
     */
    public static <T> java.util.Set<T> anySet(Iterable<T> superset,
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<Integer> size) {
        return CombinedGenerators.sets(superset, size).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#strictlyOrdered}.
     */
    public static <T extends Comparable<T>> java.util.List<T> anyStrictlyOrdered(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> input) {
        return CombinedGenerators.strictlyOrdered(input).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#strictlyOrdered}.
     */
    public static <T extends Comparable<T>> java.util.List<T> anyStrictlyOrdered(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> input, int low, int high) {
        return CombinedGenerators.strictlyOrdered(input, low, high).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#strictlyOrdered}.
     */
    public static <T> java.util.List<T> anyStrictlyOrdered(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> input,
            java.util.Comparator<T> comparator) {
        return CombinedGenerators.strictlyOrdered(input, comparator).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#strictlyOrdered}.
     */
    public static <T> java.util.List<T> anyStrictlyOrdered(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<T> input,
            java.util.Comparator<T> comparator,
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<Integer> size) {
        return CombinedGenerators.strictlyOrdered(input, comparator, size).next();
    }
}
