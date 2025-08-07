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
package de.cuioss.test.generator.internal.net.java.quickcheck.generator;

import de.cuioss.test.generator.internal.net.java.quickcheck.Generator;
import de.cuioss.test.generator.internal.net.java.quickcheck.collection.Pair;
import de.cuioss.test.generator.internal.net.java.quickcheck.collection.Triple;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CombinedGeneratorsIterables {

    /**
     * See documentation of {@link CombinedGenerators#uniqueValues}.
     */
    public static <T> Iterable<T> someUniqueValues(
            Generator<T> generator, int tries) {
        return Iterables
                .toIterable(CombinedGenerators.uniqueValues(generator, tries));
    }

    /**
     * See documentation of {@link CombinedGenerators#uniqueValues}.
     */
    public static <T> Iterable<T> someUniqueValues(
            Generator<T> generator,
            Comparator<? super T> comparator, int tries) {
        return Iterables
                .toIterable(CombinedGenerators.uniqueValues(generator, comparator, tries));
    }

    /**
     * See documentation of {@link CombinedGenerators#uniqueValues}.
     */
    public static <T> Iterable<T> someUniqueValues(
            Generator<T> generator,
            Comparator<? super T> comparator) {
        return Iterables
                .toIterable(CombinedGenerators.uniqueValues(generator, comparator));
    }

    /**
     * See documentation of {@link CombinedGenerators#uniqueValues}.
     */
    public static <T> Iterable<T> someUniqueValues(
            Generator<T> generator) {
        return Iterables
                .toIterable(CombinedGenerators.uniqueValues(generator));
    }

    /**
     * See documentation of {@link CombinedGenerators#triples}.
     */
    public static <A, B, C> Iterable<Triple<A, B, C>> someTriples(
            Generator<A> first,
            Generator<B> second,
            Generator<C> third) {
        return Iterables
                .toIterable(CombinedGenerators.triples(first, second, third));
    }

    /**
     * See documentation of {@link CombinedGenerators#intArrays}.
     */
    public static Iterable<int[]> someIntArrays() {
        return Iterables
                .toIterable(CombinedGenerators.intArrays());
    }

    /**
     * See documentation of {@link CombinedGenerators#intArrays}.
     */
    public static Iterable<int[]> someIntArrays(
            Generator<Integer> size) {
        return Iterables
                .toIterable(CombinedGenerators.intArrays(size));
    }

    /**
     * See documentation of {@link CombinedGenerators#intArrays}.
     */
    public static Iterable<int[]> someIntArrays(
            Generator<Integer> content,
            Generator<Integer> size) {
        return Iterables
                .toIterable(CombinedGenerators.intArrays(content, size));
    }

    /**
     * See documentation of {@link CombinedGenerators#ensureValues}.
     */
    public static <T> Iterable<T> someEnsureValues(Iterable<T> ensuredValues) {
        return Iterables
                .toIterable(CombinedGenerators.ensureValues(ensuredValues));
    }

    /**
     * See documentation of {@link CombinedGenerators#ensureValues}.
     */
    @SafeVarargs
    public static <T> Iterable<T> someEnsureValues(T... content) {
        return Iterables
                .toIterable(CombinedGenerators.ensureValues(content));
    }

    /**
     * See documentation of {@link CombinedGenerators#ensureValues}.
     */
    public static <T> Iterable<T> someEnsureValues(Iterable<T> ensuredValues,
            Generator<T> otherValues) {
        return Iterables
                .toIterable(CombinedGenerators.ensureValues(ensuredValues, otherValues));
    }

    /**
     * See documentation of {@link CombinedGenerators#ensureValues}.
     */
    public static <T> Iterable<T> someEnsureValues(Iterable<T> ensuredValues, int window,
            Generator<T> otherValues) {
        return Iterables
                .toIterable(CombinedGenerators.ensureValues(ensuredValues, window, otherValues));
    }

    /**
     * See documentation of {@link CombinedGenerators#nullsAnd}.
     */
    @SafeVarargs
    public static <T> Iterable<T> someNullsAnd(T... values) {
        return Iterables
                .toIterable(CombinedGenerators.nullsAnd(values));
    }

    /**
     * See documentation of {@link CombinedGenerators#nullsAnd}.
     */
    public static <T> Iterable<T> someNullsAnd(
            Generator<T> generator) {
        return Iterables
                .toIterable(CombinedGenerators.nullsAnd(generator));
    }

    /**
     * See documentation of {@link CombinedGenerators#nullsAnd}.
     */
    public static <T> Iterable<T> someNullsAnd(
            Generator<T> generator, int weight) {
        return Iterables
                .toIterable(CombinedGenerators.nullsAnd(generator, weight));
    }

    /**
     * See documentation of {@link CombinedGenerators#pairs}.
     */
    public static <A, B> Iterable<Pair<A, B>> somePairs(
            Generator<A> first,
            Generator<B> second) {
        return Iterables
                .toIterable(CombinedGenerators.pairs(first, second));
    }

    /**
     * See documentation of {@link CombinedGenerators#frequency}.
     */
    public static <T> Iterable<T> someFrequency(
            Generator<T> generator, int weight) {
        return Iterables
                .toIterable(CombinedGenerators.frequency(generator, weight));
    }

    /**
     * See documentation of {@link CombinedGenerators#oneOf}.
     */
    public static <T> Iterable<T> someOneOf(
            Generator<T> generator) {
        return Iterables
                .toIterable(CombinedGenerators.oneOf(generator));
    }

    /**
     * See documentation of {@link CombinedGenerators#duplicates}.
     */
    @SafeVarargs
    public static <T> Iterable<List<T>> someDuplicates(T... input) {
        return Iterables
                .toIterable(CombinedGenerators.duplicates(input));
    }

    /**
     * See documentation of {@link CombinedGenerators#duplicates}.
     */
    public static <T> Iterable<List<T>> someDuplicates(Iterable<T> input) {
        return Iterables
                .toIterable(CombinedGenerators.duplicates(input));
    }

    /**
     * See documentation of {@link CombinedGenerators#maps}.
     */
    public static <K, V> Iterable<Map<K, V>> someMaps(
            Generator<K> keys,
            Generator<V> values) {
        return Iterables
                .toIterable(CombinedGenerators.maps(keys, values));
    }

    /**
     * See documentation of {@link CombinedGenerators#maps}.
     */
    public static <K, V> Iterable<Map<K, V>> someMaps(
            Generator<K> keys,
            Generator<V> values,
            Generator<Integer> size) {
        return Iterables
                .toIterable(CombinedGenerators.maps(keys, values, size));
    }

    /**
     * See documentation of {@link CombinedGenerators#maps}.
     */
    public static <K, V> Iterable<Map<K, V>> someMaps(Map<K, V> supermap) {
        return Iterables
                .toIterable(CombinedGenerators.maps(supermap));
    }

    /**
     * See documentation of {@link CombinedGenerators#maps}.
     */
    public static <K, V> Iterable<Map<K, V>> someMaps(Map<K, V> supermap,
            Generator<Integer> sizes) {
        return Iterables
                .toIterable(CombinedGenerators.maps(supermap, sizes));
    }

    /**
     * See documentation of {@link CombinedGenerators#sortedLists}.
     */
    public static <T extends Comparable<T>> Iterable<List<T>> someSortedLists(
            Generator<T> content) {
        return Iterables
                .toIterable(CombinedGenerators.sortedLists(content));
    }

    /**
     * See documentation of {@link CombinedGenerators#sortedLists}.
     */
    public static <T extends Comparable<T>> Iterable<List<T>> someSortedLists(
            Generator<T> content, int low, int high) {
        return Iterables
                .toIterable(CombinedGenerators.sortedLists(content, low, high));
    }

    /**
     * See documentation of {@link CombinedGenerators#sortedLists}.
     */
    public static <T extends Comparable<T>> Iterable<List<T>> someSortedLists(
            Generator<T> content,
            Generator<Integer> size) {
        return Iterables
                .toIterable(CombinedGenerators.sortedLists(content, size));
    }

    /**
     * See documentation of {@link CombinedGenerators#nonEmptySets}.
     */
    public static <T> Iterable<Set<T>> someNonEmptySets(
            Generator<? extends T> content) {
        return Iterables
                .toIterable(CombinedGenerators.nonEmptySets(content));
    }

    /**
     * See documentation of {@link CombinedGenerators#vectors}.
     */
    public static <T> Iterable<List<T>> someVectors(
            Generator<T> content, int size) {
        return Iterables
                .toIterable(CombinedGenerators.vectors(content, size));
    }

    /**
     * See documentation of {@link CombinedGenerators#lists}.
     */
    public static <T> Iterable<List<T>> someLists(
            Generator<? extends T> content) {
        return Iterables
                .toIterable(CombinedGenerators.lists(content));
    }

    /**
     * See documentation of {@link CombinedGenerators#lists}.
     */
    public static <T> Iterable<List<T>> someLists(
            Generator<? extends T> content,
            Generator<Integer> size) {
        return Iterables
                .toIterable(CombinedGenerators.lists(content, size));
    }

    /**
     * See documentation of {@link CombinedGenerators#lists}.
     */
    public static <T> Iterable<List<T>> someLists(
            Generator<? extends T> content, int low, int high) {
        return Iterables
                .toIterable(CombinedGenerators.lists(content, low, high));
    }

    /**
     * See documentation of {@link CombinedGenerators#lists}.
     */
    public static <T> Iterable<List<T>> someLists(
            Generator<? extends T> content, int low) {
        return Iterables
                .toIterable(CombinedGenerators.lists(content, low));
    }

    /**
     * See documentation of {@link CombinedGenerators#sortedPairs}.
     */
    public static <T extends Comparable<T>> Iterable<Pair<T, T>> someSortedPairs(
            Generator<T> content) {
        return Iterables
                .toIterable(CombinedGenerators.sortedPairs(content));
    }

    /**
     * See documentation of {@link CombinedGenerators#sortedTriple}.
     */
    public static <T extends Comparable<T>> Iterable<Triple<T, T, T>> someSortedTriple(
            Generator<T> content) {
        return Iterables
                .toIterable(CombinedGenerators.sortedTriple(content));
    }

    /**
     * See documentation of {@link CombinedGenerators#nonEmptyIterators}.
     */
    public static <T> Iterable<Iterator<T>> someNonEmptyIterators(
            Generator<T> content) {
        return Iterables
                .toIterable(CombinedGenerators.nonEmptyIterators(content));
    }

    /**
     * See documentation of {@link CombinedGenerators#arrays}.
     */
    public static <T> Iterable<T[]> someArrays(
            Generator<? extends T> content, Class<T> type) {
        return Iterables
                .toIterable(CombinedGenerators.arrays(content, type));
    }

    /**
     * See documentation of {@link CombinedGenerators#arrays}.
     */
    public static <T> Iterable<T[]> someArrays(
            Generator<? extends T> content,
            Generator<Integer> size, Class<T> type) {
        return Iterables
                .toIterable(CombinedGenerators.arrays(content, size, type));
    }

    /**
     * See documentation of {@link CombinedGenerators#iterators}.
     */
    public static <T> Iterable<Iterator<T>> someIterators(
            Generator<? extends T> content) {
        return Iterables
                .toIterable(CombinedGenerators.iterators(content));
    }

    /**
     * See documentation of {@link CombinedGenerators#iterators}.
     */
    public static <T> Iterable<Iterator<T>> someIterators(
            Generator<? extends T> content,
            Generator<Integer> size) {
        return Iterables
                .toIterable(CombinedGenerators.iterators(content, size));
    }

    /**
     * See documentation of {@link CombinedGenerators#nonEmptyLists}.
     */
    public static <T> Iterable<List<T>> someNonEmptyLists(
            Generator<? extends T> content) {
        return Iterables
                .toIterable(CombinedGenerators.nonEmptyLists(content));
    }

    /**
     * See documentation of {@link CombinedGenerators#nonEmptyArrays}.
     */
    public static <T> Iterable<T[]> someNonEmptyArrays(
            Generator<? extends T> content, Class<T> type) {
        return Iterables
                .toIterable(CombinedGenerators.nonEmptyArrays(content, type));
    }

    /**
     * See documentation of {@link CombinedGenerators#byteArrays}.
     */
    public static Iterable<byte[]> someByteArrays() {
        return Iterables
                .toIterable(CombinedGenerators.byteArrays());
    }

    /**
     * See documentation of {@link CombinedGenerators#byteArrays}.
     */
    public static Iterable<byte[]> someByteArrays(
            Generator<Integer> size) {
        return Iterables
                .toIterable(CombinedGenerators.byteArrays(size));
    }

    /**
     * See documentation of {@link CombinedGenerators#byteArrays}.
     */
    public static Iterable<byte[]> someByteArrays(
            Generator<Byte> content,
            Generator<Integer> size) {
        return Iterables
                .toIterable(CombinedGenerators.byteArrays(content, size));
    }

    /**
     * See documentation of {@link CombinedGenerators#excludeValues}.
     */
    @SafeVarargs
    public static <T> Iterable<T> someExcludeValues(
            Generator<T> generator, T... excluded) {
        return Iterables
                .toIterable(CombinedGenerators.excludeValues(generator, excluded));
    }

    /**
     * See documentation of {@link CombinedGenerators#excludeValues}.
     */
    @SafeVarargs
    public static <T> Iterable<T> someExcludeValues(Iterable<T> values, T... excluded) {
        return Iterables
                .toIterable(CombinedGenerators.excludeValues(values, excluded));
    }

    /**
     * See documentation of {@link CombinedGenerators#excludeValues}.
     */
    public static <T> Iterable<T> someExcludeValues(Iterable<T> values, Iterable<T> excluded) {
        return Iterables
                .toIterable(CombinedGenerators.excludeValues(values, excluded));
    }

    /**
     * See documentation of {@link CombinedGenerators#excludeValues}.
     */
    public static <T> Iterable<T> someExcludeValues(
            Generator<T> generator, Iterable<T> excluded) {
        return Iterables
                .toIterable(CombinedGenerators.excludeValues(generator, excluded));
    }

    /**
     * See documentation of {@link CombinedGenerators#sets}.
     */
    public static <T> Iterable<Set<T>> someSets(
            Generator<? extends T> content) {
        return Iterables
                .toIterable(CombinedGenerators.sets(content));
    }

    /**
     * See documentation of {@link CombinedGenerators#sets}.
     */
    public static <T> Iterable<Set<T>> someSets(
            Generator<? extends T> content,
            Generator<Integer> size) {
        return Iterables
                .toIterable(CombinedGenerators.sets(content, size));
    }

    /**
     * See documentation of {@link CombinedGenerators#sets}.
     */
    public static <T> Iterable<Set<T>> someSets(
            Generator<? extends T> content, int low, int high) {
        return Iterables
                .toIterable(CombinedGenerators.sets(content, low, high));
    }

    /**
     * See documentation of {@link CombinedGenerators#sets}.
     */
    @SafeVarargs
    public static <T> Iterable<Set<T>> someSets(T... superset) {
        return Iterables
                .toIterable(CombinedGenerators.sets(superset));
    }

    /**
     * See documentation of {@link CombinedGenerators#sets}.
     */
    public static <T> Iterable<Set<T>> someSets(Iterable<T> superset) {
        return Iterables
                .toIterable(CombinedGenerators.sets(superset));
    }

    /**
     * See documentation of {@link CombinedGenerators#sets}.
     */
    public static <T> Iterable<Set<T>> someSets(Iterable<T> superset,
            Generator<Integer> size) {
        return Iterables
                .toIterable(CombinedGenerators.sets(superset, size));
    }

    /**
     * See documentation of {@link CombinedGenerators#strictlyOrdered}.
     */
    public static <T extends Comparable<T>> Iterable<List<T>> someStrictlyOrdered(
            Generator<T> input) {
        return Iterables
                .toIterable(CombinedGenerators.strictlyOrdered(input));
    }

    /**
     * See documentation of {@link CombinedGenerators#strictlyOrdered}.
     */
    public static <T extends Comparable<T>> Iterable<List<T>> someStrictlyOrdered(
            Generator<T> input, int low, int high) {
        return Iterables
                .toIterable(CombinedGenerators.strictlyOrdered(input, low, high));
    }

    /**
     * See documentation of {@link CombinedGenerators#strictlyOrdered}.
     */
    public static <T> Iterable<List<T>> someStrictlyOrdered(
            Generator<T> input,
            Comparator<T> comparator) {
        return Iterables
                .toIterable(CombinedGenerators.strictlyOrdered(input, comparator));
    }

    /**
     * See documentation of {@link CombinedGenerators#strictlyOrdered}.
     */
    public static <T> Iterable<List<T>> someStrictlyOrdered(
            Generator<T> input,
            Comparator<T> comparator,
            Generator<Integer> size) {
        return Iterables
                .toIterable(CombinedGenerators.strictlyOrdered(input, comparator, size));
    }
}
