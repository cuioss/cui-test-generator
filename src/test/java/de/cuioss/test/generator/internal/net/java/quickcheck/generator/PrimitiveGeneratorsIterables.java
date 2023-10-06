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

public class PrimitiveGeneratorsIterables {

    /**
     * See documentation of {@link PrimitiveGenerators#objects}.
     */
    public static Iterable<Object> someObjects() {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.objects());
    }

    /**
     * See documentation of {@link PrimitiveGenerators#objects}.
     */
    public static <T> Iterable<T> someObjects(Class<T> objectType) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.objects(objectType));
    }

    /**
     * See documentation of {@link PrimitiveGenerators#nulls}.
     */
    public static <T> Iterable<T> someNulls() {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.nulls());
    }

    /**
     * See documentation of {@link PrimitiveGenerators#substrings}.
     */
    public static Iterable<String> someSubstrings(String base) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.substrings(base));
    }

    /**
     * See documentation of {@link PrimitiveGenerators#substrings}.
     */
    public static Iterable<String> someSubstrings(String base, int size) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.substrings(base, size));
    }

    /**
     * See documentation of {@link PrimitiveGenerators#substrings}.
     */
    public static Iterable<String> someSubstrings(String base, int minSize, int maxSize) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.substrings(base, minSize, maxSize));
    }

    /**
     * See documentation of {@link PrimitiveGenerators#integers}.
     */
    public static Iterable<Integer> someIntegers() {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.integers());
    }

    /**
     * See documentation of {@link PrimitiveGenerators#integers}.
     */
    public static Iterable<Integer> someIntegers(int low) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.integers(low));
    }

    /**
     * See documentation of {@link PrimitiveGenerators#integers}.
     */
    public static Iterable<Integer> someIntegers(int lo, int hi) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.integers(lo, hi));
    }

    /**
     * See documentation of {@link PrimitiveGenerators#integers}.
     */
    public static Iterable<Integer> someIntegers(int lo, int hi,
            de.cuioss.test.generator.internal.net.java.quickcheck.generator.distribution.Distribution distribution) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.integers(lo, hi, distribution));
    }

    /**
     * See documentation of {@link PrimitiveGenerators#positiveLongs}.
     */
    public static Iterable<Long> somePositiveLongs() {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.positiveLongs());
    }

    /**
     * See documentation of {@link PrimitiveGenerators#positiveLongs}.
     */
    public static Iterable<Long> somePositiveLongs(long hi) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.positiveLongs(hi));
    }

    /**
     * See documentation of {@link PrimitiveGenerators#defaultObjects}.
     */
    public static <T> Iterable<T> someDefaultObjects(Class<T> objectType) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.defaultObjects(objectType));
    }

    /**
     * See documentation of {@link PrimitiveGenerators#enumValues}.
     */
    public static <T extends Enum<T>> Iterable<T> someEnumValues(Class<T> enumClass) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.enumValues(enumClass));
    }

    /**
     * See documentation of {@link PrimitiveGenerators#enumValues}.
     */
    @SafeVarargs
    public static <T extends Enum<T>> Iterable<T> someEnumValues(Class<T> enumClass, T... excluded) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.enumValues(enumClass, excluded));
    }

    /**
     * See documentation of {@link PrimitiveGenerators#enumValues}.
     */
    public static <T extends Enum<T>> Iterable<T> someEnumValues(Class<T> enumClass, Iterable<T> excludedValues) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.enumValues(enumClass, excludedValues));
    }

    /**
     * See documentation of {@link PrimitiveGenerators#printableStrings}.
     */
    public static Iterable<String> somePrintableStrings() {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.printableStrings());
    }

    /**
     * See documentation of {@link PrimitiveGenerators#dates}.
     */
    public static Iterable<java.util.Date> someDates() {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.dates());
    }

    /**
     * See documentation of {@link PrimitiveGenerators#dates}.
     */
    public static Iterable<java.util.Date> someDates(java.util.concurrent.TimeUnit precision) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.dates(precision));
    }

    /**
     * See documentation of {@link PrimitiveGenerators#dates}.
     */
    public static Iterable<java.util.Date> someDates(java.util.Date low, java.util.Date high) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.dates(low, high));
    }

    /**
     * See documentation of {@link PrimitiveGenerators#dates}.
     */
    public static Iterable<java.util.Date> someDates(long low, long high) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.dates(low, high));
    }

    /**
     * See documentation of {@link PrimitiveGenerators#dates}.
     */
    public static Iterable<java.util.Date> someDates(Long low, Long high, java.util.concurrent.TimeUnit precision) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.dates(low, high, precision));
    }

    /**
     * See documentation of {@link PrimitiveGenerators#latin1SupplementCharacters}.
     */
    public static Iterable<Character> someLatin1SupplementCharacters() {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.latin1SupplementCharacters());
    }

    /**
     * See documentation of {@link PrimitiveGenerators#doubles}.
     */
    public static Iterable<Double> someDoubles() {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.doubles());
    }

    /**
     * See documentation of {@link PrimitiveGenerators#doubles}.
     */
    public static Iterable<Double> someDoubles(double lo, double hi) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.doubles(lo, hi));
    }

    /**
     * See documentation of {@link PrimitiveGenerators#doubles}.
     */
    public static Iterable<Double> someDoubles(double lo, double hi,
            de.cuioss.test.generator.internal.net.java.quickcheck.generator.distribution.Distribution distribution) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.doubles(lo, hi, distribution));
    }

    /**
     * See documentation of {@link PrimitiveGenerators#positiveIntegers}.
     */
    public static Iterable<Integer> somePositiveIntegers() {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.positiveIntegers());
    }

    /**
     * See documentation of {@link PrimitiveGenerators#positiveIntegers}.
     */
    public static Iterable<Integer> somePositiveIntegers(int hi) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.positiveIntegers(hi));
    }

    /**
     * See documentation of {@link PrimitiveGenerators#nonEmptyStrings}.
     */
    public static Iterable<String> someNonEmptyStrings() {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.nonEmptyStrings());
    }

    /**
     * See documentation of {@link PrimitiveGenerators#characters}.
     */
    public static Iterable<Character> someCharacters(char lo, char hi) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.characters(lo, hi));
    }

    /**
     * See documentation of {@link PrimitiveGenerators#characters}.
     */
    public static Iterable<Character> someCharacters() {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.characters());
    }

    /**
     * See documentation of {@link PrimitiveGenerators#characters}.
     */
    public static Iterable<Character> someCharacters(Character... chars) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.characters(chars));
    }

    /**
     * See documentation of {@link PrimitiveGenerators#characters}.
     */
    public static Iterable<Character> someCharacters(String string) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.characters(string));
    }

    /**
     * See documentation of {@link PrimitiveGenerators#characters}.
     */
    public static Iterable<Character> someCharacters(Iterable<Character> chars) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.characters(chars));
    }

    /**
     * See documentation of {@link PrimitiveGenerators#longs}.
     */
    public static Iterable<Long> someLongs() {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.longs());
    }

    /**
     * See documentation of {@link PrimitiveGenerators#longs}.
     */
    public static Iterable<Long> someLongs(long lo, long hi) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.longs(lo, hi));
    }

    /**
     * See documentation of {@link PrimitiveGenerators#longs}.
     */
    public static Iterable<Long> someLongs(long lo, long hi,
            de.cuioss.test.generator.internal.net.java.quickcheck.generator.distribution.Distribution distribution) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.longs(lo, hi, distribution));
    }

    /**
     * See documentation of {@link PrimitiveGenerators#strings}.
     */
    public static Iterable<String> someStrings() {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.strings());
    }

    /**
     * See documentation of {@link PrimitiveGenerators#strings}.
     */
    public static Iterable<String> someStrings(char lo, char hi) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.strings(lo, hi));
    }

    /**
     * See documentation of {@link PrimitiveGenerators#strings}.
     */
    public static Iterable<String> someStrings(String allowedCharacters) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.strings(allowedCharacters));
    }

    /**
     * See documentation of {@link PrimitiveGenerators#strings}.
     */
    public static Iterable<String> someStrings(String allowedCharacters, int min, int max) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.strings(allowedCharacters, min, max));
    }

    /**
     * See documentation of {@link PrimitiveGenerators#strings}.
     */
    public static Iterable<String> someStrings(int max) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.strings(max));
    }

    /**
     * See documentation of {@link PrimitiveGenerators#strings}.
     */
    public static Iterable<String> someStrings(int min, int max) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.strings(min, max));
    }

    /**
     * See documentation of {@link PrimitiveGenerators#strings}.
     */
    public static Iterable<String> someStrings(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<Integer> length,
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<Character> characters) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.strings(length, characters));
    }

    /**
     * See documentation of {@link PrimitiveGenerators#strings}.
     */
    public static Iterable<String> someStrings(
            de.cuioss.test.generator.internal.net.java.quickcheck.Generator<Character> characterGenerator) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.strings(characterGenerator));
    }

    /**
     * See documentation of {@link PrimitiveGenerators#fixedValues}.
     */
    public static <T> Iterable<T> someFixedValues(T value) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.fixedValues(value));
    }

    /**
     * See documentation of {@link PrimitiveGenerators#fixedValues}.
     */
    @SafeVarargs
    public static <T> Iterable<T> someFixedValues(T... values) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.fixedValues(values));
    }

    /**
     * See documentation of {@link PrimitiveGenerators#fixedValues}.
     */
    public static <T> Iterable<T> someFixedValues(Iterable<T> values) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.fixedValues(values));
    }

    /**
     * See documentation of {@link PrimitiveGenerators#clonedValues}.
     */
    public static <T> Iterable<T> someClonedValues(T prototype) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.clonedValues(prototype));
    }

    /**
     * See documentation of {@link PrimitiveGenerators#booleans}.
     */
    public static Iterable<Boolean> someBooleans() {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.booleans());
    }

    /**
     * See documentation of {@link PrimitiveGenerators#basicLatinCharacters}.
     */
    public static Iterable<Character> someBasicLatinCharacters() {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.basicLatinCharacters());
    }

    /**
     * See documentation of {@link PrimitiveGenerators#letterStrings}.
     */
    public static Iterable<String> someLetterStrings() {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.letterStrings());
    }

    /**
     * See documentation of {@link PrimitiveGenerators#letterStrings}.
     */
    public static Iterable<String> someLetterStrings(int min, int max) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.letterStrings(min, max));
    }

    /**
     * See documentation of {@link PrimitiveGenerators#bytes}.
     */
    public static Iterable<Byte> someBytes() {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.bytes());
    }

    /**
     * See documentation of {@link PrimitiveGenerators#bytes}.
     */
    public static Iterable<Byte> someBytes(byte lo, byte hi) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.bytes(lo, hi));
    }

    /**
     * See documentation of {@link PrimitiveGenerators#bytes}.
     */
    public static Iterable<Byte> someBytes(byte lo, byte hi,
            de.cuioss.test.generator.internal.net.java.quickcheck.generator.distribution.Distribution distribution) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(PrimitiveGenerators.bytes(lo, hi, distribution));
    }
}
