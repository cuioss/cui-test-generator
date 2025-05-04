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

import de.cuioss.test.generator.internal.net.java.quickcheck.Generator;
import de.cuioss.test.generator.internal.net.java.quickcheck.collection.Pair;
import de.cuioss.test.generator.internal.net.java.quickcheck.collection.Triple;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CombinedGeneratorSamples {

    /**
     * See documentation of {@link CombinedGenerators#uniqueValues}.
     */
    public static <T> T anyUniqueValue(Generator<T> generator,
            int tries) {
        return CombinedGenerators.uniqueValues(generator, tries).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#uniqueValues}.
     */
    public static <T> T anyUniqueValue(Generator<T> generator,
            Comparator<? super T> comparator, int tries) {
        return CombinedGenerators.uniqueValues(generator, comparator, tries).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#uniqueValues}.
     */
    public static <T> T anyUniqueValue(Generator<T> generator,
            Comparator<? super T> comparator) {
        return CombinedGenerators.uniqueValues(generator, comparator).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#uniqueValues}.
     */
    public static <T> T anyUniqueValue(Generator<T> generator) {
        return CombinedGenerators.uniqueValues(generator).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#triples}.
     */
    public static <A, B, C> Triple<A, B, C> anyTriple(
            Generator<A> first,
            Generator<B> second,
            Generator<C> third) {
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
    public static int[] anyIntArray(Generator<Integer> size) {
        return CombinedGenerators.intArrays(size).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#intArrays}.
     */
    public static int[] anyIntArray(Generator<Integer> content,
            Generator<Integer> size) {
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
            Generator<T> otherValues) {
        return CombinedGenerators.ensureValues(ensuredValues, otherValues).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#ensureValues}.
     */
    public static <T> T anyEnsureValue(Iterable<T> ensuredValues, int window,
            Generator<T> otherValues) {
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
    public static <T> T anyNullsAnd(Generator<T> generator) {
        return CombinedGenerators.nullsAnd(generator).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#nullsAnd}.
     */
    public static <T> T anyNullsAnd(Generator<T> generator,
            int weight) {
        return CombinedGenerators.nullsAnd(generator, weight).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#pairs}.
     */
    public static <A, B> Pair<A, B> anyPair(
            Generator<A> first,
            Generator<B> second) {
        return CombinedGenerators.pairs(first, second).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#frequency}.
     */
    public static <T> T anyFrequency(Generator<T> generator,
            int weight) {
        return CombinedGenerators.frequency(generator, weight).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#oneOf}.
     */
    public static <T> T anyOneOf(Generator<T> generator) {
        return CombinedGenerators.oneOf(generator).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#duplicates}.
     */
    @SafeVarargs
    public static <T> List<T> anyDuplicate(T... input) {
        return CombinedGenerators.duplicates(input).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#duplicates}.
     */
    public static <T> List<T> anyDuplicate(Iterable<T> input) {
        return CombinedGenerators.duplicates(input).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#maps}.
     */
    public static <K, V> Map<K, V> anyMap(
            Generator<K> keys,
            Generator<V> values) {
        return CombinedGenerators.maps(keys, values).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#maps}.
     */
    public static <K, V> Map<K, V> anyMap(Map<K, V> supermap,
            Generator<Integer> sizes) {
        return CombinedGenerators.maps(supermap, sizes).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#maps}.
     */
    public static <K, V> Map<K, V> anyMap(
            Generator<K> keys,
            Generator<V> values,
            Generator<Integer> size) {
        return CombinedGenerators.maps(keys, values, size).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#maps}.
     */
    public static <K, V> Map<K, V> anyMap(Map<K, V> supermap) {
        return CombinedGenerators.maps(supermap).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#sortedLists}.
     */
    public static <T extends Comparable<T>> List<T> anySortedList(
            Generator<T> content) {
        return CombinedGenerators.sortedLists(content).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#sortedLists}.
     */
    public static <T extends Comparable<T>> List<T> anySortedList(
            Generator<T> content, int low, int high) {
        return CombinedGenerators.sortedLists(content, low, high).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#sortedLists}.
     */
    public static <T extends Comparable<T>> List<T> anySortedList(
            Generator<T> content,
            Generator<Integer> size) {
        return CombinedGenerators.sortedLists(content, size).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#nonEmptySets}.
     */
    public static <T> Set<T> anyNonEmptySet(
            Generator<T> content) {
        return CombinedGenerators.nonEmptySets(content).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#vectors}.
     */
    public static <T> List<T> anyVector(
            Generator<T> content, int size) {
        return CombinedGenerators.vectors(content, size).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#lists}.
     */
    public static <T> List<T> anyList(
            Generator<T> content) {
        return CombinedGenerators.lists(content).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#lists}.
     */
    public static <T> List<T> anyList(
            Generator<T> content,
            Generator<Integer> size) {
        return CombinedGenerators.lists(content, size).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#lists}.
     */
    public static <T> List<T> anyList(
            Generator<T> content, int low, int high) {
        return CombinedGenerators.lists(content, low, high).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#lists}.
     */
    public static <T> List<T> anyList(
            Generator<T> content, int low) {
        return CombinedGenerators.lists(content, low).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#sortedPairs}.
     */
    public static <T extends Comparable<T>> Pair<T, T> anySortedPair(
            Generator<T> content) {
        return CombinedGenerators.sortedPairs(content).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#sortedTriple}.
     */
    public static <T extends Comparable<T>> Triple<T, T, T> anySortedTriple(
            Generator<T> content) {
        return CombinedGenerators.sortedTriple(content).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#nonEmptyIterators}.
     */
    public static <T> Iterator<T> anyNonEmptyIterator(
            Generator<T> content) {
        return CombinedGenerators.nonEmptyIterators(content).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#arrays}.
     */
    public static <T> T[] anyArray(Generator<? extends T> content,
            Class<T> type) {
        return CombinedGenerators.arrays(content, type).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#arrays}.
     */
    public static <T> T[] anyArray(Generator<? extends T> content,
            Generator<Integer> size, Class<T> type) {
        return CombinedGenerators.arrays(content, size, type).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#iterators}.
     */
    public static <T> Iterator<T> anyIterator(
            Generator<T> content) {
        return CombinedGenerators.iterators(content).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#iterators}.
     */
    public static <T> Iterator<T> anyIterator(
            Generator<T> content,
            Generator<Integer> size) {
        return CombinedGenerators.iterators(content, size).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#nonEmptyLists}.
     */
    public static <T> List<T> anyNonEmptyList(
            Generator<T> content) {
        return CombinedGenerators.nonEmptyLists(content).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#nonEmptyArrays}.
     */
    public static <T> T[] anyNonEmptyArray(
            Generator<? extends T> content, Class<T> type) {
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
    public static byte[] anyByteArray(Generator<Integer> size) {
        return CombinedGenerators.byteArrays(size).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#byteArrays}.
     */
    public static byte[] anyByteArray(Generator<Byte> content,
            Generator<Integer> size) {
        return CombinedGenerators.byteArrays(content, size).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#excludeValues}.
     */
    @SafeVarargs
    public static <T> T anyExcludeValue(Generator<T> generator,
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
    public static <T> T anyExcludeValue(Generator<T> generator,
            Iterable<T> excluded) {
        return CombinedGenerators.excludeValues(generator, excluded).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#sets}.
     */
    public static <T> Set<T> anySet(
            Generator<T> content, int low, int high) {
        return CombinedGenerators.sets(content, low, high).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#sets}.
     */
    public static <T> Set<T> anySet(
            Generator<T> content,
            Generator<Integer> size) {
        return CombinedGenerators.sets(content, size).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#sets}.
     */
    public static <T> Set<T> anySet(
            Generator<T> content) {
        return CombinedGenerators.sets(content).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#sets}.
     */
    @SafeVarargs
    public static <T> Set<T> anySet(T... superset) {
        return CombinedGenerators.sets(superset).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#sets}.
     */
    public static <T> Set<T> anySet(Iterable<T> superset) {
        return CombinedGenerators.sets(superset).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#sets}.
     */
    public static <T> Set<T> anySet(Iterable<T> superset,
            Generator<Integer> size) {
        return CombinedGenerators.sets(superset, size).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#strictlyOrdered}.
     */
    public static <T extends Comparable<T>> List<T> anyStrictlyOrdered(
            Generator<T> input) {
        return CombinedGenerators.strictlyOrdered(input).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#strictlyOrdered}.
     */
    public static <T extends Comparable<T>> List<T> anyStrictlyOrdered(
            Generator<T> input, int low, int high) {
        return CombinedGenerators.strictlyOrdered(input, low, high).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#strictlyOrdered}.
     */
    public static <T> List<T> anyStrictlyOrdered(
            Generator<T> input,
            Comparator<T> comparator) {
        return CombinedGenerators.strictlyOrdered(input, comparator).next();
    }

    /**
     * See documentation of {@link CombinedGenerators#strictlyOrdered}.
     */
    public static <T> List<T> anyStrictlyOrdered(
            Generator<T> input,
            Comparator<T> comparator,
            Generator<Integer> size) {
        return CombinedGenerators.strictlyOrdered(input, comparator, size).next();
    }
}
