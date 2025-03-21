/*
 * Licensed to the author under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.test.generator.internal.net.java.quickcheck.generator;

import de.cuioss.test.generator.internal.net.java.quickcheck.ExtendibleGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.Generator;
import de.cuioss.test.generator.internal.net.java.quickcheck.ObjectGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.distribution.Distribution;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * {@link PrimitiveGenerators} contains factory methods for primitive value
 * generators. These can be used to build custom test case generators.
 * <p>
 * The default distribution for generators is {@link Distribution#UNIFORM}.
 * </p>
 */
@SuppressWarnings({"WeakerAccess"})
public class PrimitiveGenerators {

    public static final int DEFAULT_STRING_MAX_LENGTH = Generators.DEFAULT_STRING_MAX_LENGTH;

    /**
     * Create a new string generator.<br>
     *
     * The characters are from the Basic Latin and Latin-1 Supplement unicode
     * blocks.
     */
    public static ExtendibleGenerator<Character, String> strings() {
        return Generators.strings();
    }

    /**
     * Create a new string generator which generates strings of characters ranging
     * from lo to hi.
     *
     * @param lo lower boundary character
     * @param hi upper boundary character
     */
    public static ExtendibleGenerator<Character, String> strings(char lo, char hi) {
        return Generators.strings(lo, hi);
    }

    /**
     * Create a new string generator which generates strings of characters from the
     * given string.
     */
    public static ExtendibleGenerator<Character, String> strings(String allowedCharacters) {
        return Generators.strings(allowedCharacters);
    }

    /**
     * Create a new string generator which generates strings of characters from the
     * given string with a length between min and max.
     */
    public static ExtendibleGenerator<Character, String> strings(String allowedCharacters, int min, int max) {
        return Generators.strings(allowedCharacters, min, max);
    }

    /**
     * Creates a new String genearator which generates strings whose length ranges
     * from zero to given length.
     */
    public static ExtendibleGenerator<Character, String> strings(int max) {
        return Generators.strings(max);
    }

    /**
     * Create a new string generator which generates strings of sizes ranging from
     * loLength to hiLength.
     *
     * @param min lower size boundary
     * @param max upper size boundary
     */
    public static ExtendibleGenerator<Character, String> strings(int min, int max) {
        return Generators.strings(min, max);
    }

    /**
     * Create a new string generator which creates strings of characters generated
     * by the given character generator with a length generated by the length
     * generator.
     *
     */
    public static ExtendibleGenerator<Character, String> strings(Generator<Integer> length,
            Generator<Character> characters) {
        return Generators.strings(length, characters);
    }

    /**
     * Create a new string generator which creates strings of characters generated
     * by the given character generator.
     *
     */
    public static ExtendibleGenerator<Character, String> strings(Generator<Character> characterGenerator) {
        return Generators.strings(characterGenerator);
    }

    /**
     * Create a new string generator which creates strings of characters from a-z
     * and A-Z.
     */
    public static ExtendibleGenerator<Character, String> letterStrings() {
        return Generators.letterStrings();
    }

    /**
     * Create a new string generator which creates strings with sizes ranging from
     * loLengh to hiLength of characters from a-z and A-Z.
     */
    public static ExtendibleGenerator<Character, String> letterStrings(int min, int max) {
        return Generators.letterStrings(min, max);
    }

    /**
     * Create a new string generator which creates strings of characters generated
     * by {@link PrimitiveGenerators#basicLatinCharacters()} and
     * {@link PrimitiveGenerators#latin1SupplementCharacters()}.
     */
    public static ExtendibleGenerator<Character, String> printableStrings() {
        return Generators.printableStrings();
    }

    /**
     * Create a new string generator for strings that are not empty.
     */
    public static ExtendibleGenerator<Character, String> nonEmptyStrings() {
        return Generators.nonEmptyStrings();
    }

    /**
     * Create a new string generator for substrings of a base string.
     *
     * <p>
     * base.contains(generated string) will always be true.
     * </p>
     */
    public static Generator<String> substrings(String base) {
        return Generators.substrings(base);
    }

    /**
     * Create a new string generator for substrings of a base string.
     *
     * <p>
     * base.contains(generated string) will always be true.
     * </p>
     *
     * @param size of the generated string
     */
    public static Generator<String> substrings(String base, int size) {
        return Generators.substrings(base, size);
    }

    /**
     * Create a new string generator for substrings of a base string.
     *
     * <p>
     * base.contains(generated string) will always be true.
     * </p>
     *
     * @param minSize is the minimum size of the generated string
     * @param maxSize is the maximum size of the generated string
     */
    public static Generator<String> substrings(String base, int minSize, int maxSize) {
        return Generators.substrings(base, minSize, maxSize);
    }

    /**
     * Create a new character generator which generates characters ranging from lo
     * to hi.
     */
    public static Generator<Character> characters(char lo, char hi) {
        return Generators.characters(lo, hi);
    }

    /**
     * Create a new character generator.<br>
     *
     * The characters are from the Basic Latin and Latin-1 Supplement unicode
     * blocks.
     */
    public static Generator<Character> characters() {
        return Generators.characters();
    }

    /**
     * Create a new character generator which generates characters from the given
     * character array.
     */
    public static Generator<Character> characters(Character... chars) {
        return Generators.characters(chars);
    }

    /**
     * Create a new character generator which generates characters from the given
     * string.
     */
    public static Generator<Character> characters(String string) {
        return Generators.characters(string);
    }

    /**
     * Create a new character generator which generates characters from the given
     * characters.
     */
    public static Generator<Character> characters(Iterable<Character> chars) {
        return Generators.characters(chars);
    }

    /**
     * Create a new character generator which generates latin-1 supplement
     * characters.
     */
    public static Generator<Character> latin1SupplementCharacters() {
        return Generators.latin1SupplementCharacters();
    }

    /**
     * Create a new character generator which generates latin characters.
     */
    public static Generator<Character> basicLatinCharacters() {
        return Generators.basicLatinCharacters();
    }

    /**
     * Create a new integer generator which creates integers ranging from
     * {@link Integer#MIN_VALUE} to {@link Integer#MAX_VALUE}.
     *
     */
    public static Generator<Integer> integers() {
        return Generators.integers();
    }

    /**
     * Create a new integer generator which creates integers that are at equal or
     * greater than low.
     */
    public static Generator<Integer> integers(int low) {
        return Generators.integers(low);
    }

    /**
     * Create a new integer generator which creates integers ranging from lo to hi.
     */
    public static Generator<Integer> integers(int lo, int hi) {
        return Generators.integers(lo, hi);
    }

    /**
     * Create a new integer generator which creates integers ranging from {@code lo}
     * to {@code hi} based on the given {@link Distribution}.
     */
    public static Generator<Integer> integers(int lo, int hi, Distribution distribution) {
        return Generators.integers(lo, hi, distribution);
    }

    /**
     * Create a new integer generator which creates integers ranging from {@code 1}
     * to {@link Integer#MAX_VALUE}.
     */
    public static Generator<Integer> positiveIntegers() {
        return Generators.positiveIntegers();
    }

    /**
     * Create a new integer generator which creates integers ranging from {@code 1}
     * to {@code max} (which must be at least 1).
     */
    public static Generator<Integer> positiveIntegers(int hi) {
        return Generators.positiveIntegers(hi);
    }

    /**
     * Create a new byte generator which creates byte values ranging from
     * {@link Byte#MIN_VALUE} to {@link Byte#MAX_VALUE}.
     */
    public static Generator<Byte> bytes() {
        return Generators.bytes();
    }

    /**
     * Create a new byte generator which creates byte values ranging from lo to hi.
     */
    public static Generator<Byte> bytes(byte lo, byte hi) {
        return Generators.bytes(lo, hi);
    }

    /**
     * Create a new integer generator which creates integers ranging from lo to hi
     * based on the given {@link Distribution}.
     */
    public static Generator<Byte> bytes(byte lo, byte hi, Distribution distribution) {
        return Generators.bytes(lo, hi, distribution);
    }

    /**
     * Create a new long generator which creates longs ranging from
     * {@link Long#MIN_VALUE} to {@link Long#MAX_VALUE}.
     */
    public static Generator<Long> longs() {
        return Generators.longs();
    }

    /**
     * Create a new long generator which creates longs ranging from lo to hi.
     */
    public static Generator<Long> longs(long lo, long hi) {
        return Generators.longs(lo, hi);
    }

    /**
     * Create a new long generator which creates longs ranging from lo to hi based
     * on the given {@link Distribution}.
     */
    public static Generator<Long> longs(long lo, long hi, Distribution distribution) {
        return Generators.longs(lo, hi, distribution);
    }

    /**
     * Create a new long generator which creates long values ranging from 1 to
     * {@link Long#MAX_VALUE}.
     */
    public static Generator<Long> positiveLongs() {
        return Generators.positiveLongs();
    }

    /**
     * Create a new long generator which creates long values ranging from 1 to hi.
     */
    public static Generator<Long> positiveLongs(long hi) {
        return Generators.positiveLongs(hi);
    }

    /**
     * Create a new double generator which creates doubles ranging from
     * {@link Double#MIN_VALUE} to {@link Double#MAX_VALUE}.
     */
    public static Generator<Double> doubles() {
        return Generators.doubles();
    }

    /**
     * Create a new double generator which creates doubles ranging from lo to hi.
     */
    public static Generator<Double> doubles(double lo, double hi) {
        return Generators.doubles(lo, hi);
    }

    /**
     * Create a new double generator which creates doubles ranging from lo to hi
     * based on the given {@link Distribution}.
     */
    public static Generator<Double> doubles(double lo, double hi, Distribution distribution) {
        return Generators.doubles(lo, hi, distribution);
    }

    /**
     * Create a generator for boolean values.
     */
    public static Generator<Boolean> booleans() {
        return Generators.booleans();
    }

    /**
     * Create a generator for null values.
     */
    public static <T> Generator<T> nulls() {
        return Generators.nulls();
    }

    /**
     * Create a generator for date values.
     */
    public static Generator<Date> dates() {
        return Generators.dates();
    }

    /**
     * Create a generator for date values with the given precision.
     */
    public static Generator<Date> dates(TimeUnit precision) {
        return Generators.dates(precision);
    }

    /**
     * Create a generator for date values from low to high.
     */
    public static Generator<Date> dates(Date low, Date high) {
        return Generators.dates(low, high);
    }

    /**
     * Create a generator for date values from low to high.
     */
    public static Generator<Date> dates(long low, long high) {
        return Generators.dates(low, high);
    }

    /**
     * Create a generator for date values from low to high with the given precision.
     */
    public static Generator<Date> dates(Long low, Long high, TimeUnit precision) {
        return Generators.dates(low, high, precision);
    }

    /**
     * Create a generator for fixed value generator.
     */
    public static <T> Generator<T> fixedValues(T value) {
        return Generators.fixedValues(value);
    }

    /**
     * Create a fixed value generator return values array.
     */
    @SafeVarargs
    public static <T> Generator<T> fixedValues(T... values) {
        return Generators.fixedValues(values);
    }

    /**
     * Create a fixed value generator return values.
     */
    public static <T> Generator<T> fixedValues(Iterable<T> values) {
        return Generators.fixedValues(values);
    }

    /**
     * A cloning generator which uses object serialization to create clones of the
     * prototype object. For each call a new copy of the prototype will be
     * generated.
     */
    public static <T> Generator<T> clonedValues(T prototype) {
        return Generators.clonedValues(prototype);
    }

    /**
     * Create a generator of enumeration values.
     *
     * @param <T>       Type of enumerations
     * @param enumClass class of enumeration
     * @return generator of enum values
     */
    public static <T extends Enum<T>> Generator<T> enumValues(Class<T> enumClass) {
        return Generators.enumValues(enumClass);
    }

    /**
     * Create a generator of enumeration values.
     *
     * @param <T>       Type of enumerations
     * @param enumClass class of enumeration
     * @param excluded  excluded values of enumeration
     * @return generator of enum values
     */
    @SafeVarargs
    public static <T extends Enum<T>> Generator<T> enumValues(Class<T> enumClass, T... excluded) {
        return Generators.enumValues(enumClass, excluded);
    }

    /**
     * Create a generator of enumeration values.
     *
     * @param <T>            Type of enumerations
     * @param enumClass      class of enumeration
     * @param excludedValues excluded values of enumeration
     * @return generator of enum values
     */
    public static <T extends Enum<T>> Generator<T> enumValues(Class<T> enumClass, Iterable<T> excludedValues) {
        return Generators.enumValues(enumClass, excludedValues);
    }

    /**
     * Create a generator for {@link Object java.lang.Object} instances.
     * <p>
     * Note: every invocation of {@link Generator#next()} creates a new instance.
     * </p>
     */
    public static Generator<Object> objects() {
        return Generators.objects();
    }

    /**
     * Create a generator from a {@link ObjectGenerator declarative object generator
     * definition}.
     */
    public static <T> ObjectGenerator<T> objects(Class<T> objectType) {
        return Generators.objects(objectType);
    }

    /**
     * Create a generator from a {@link ObjectGenerator declarative object generator
     * definition}.
     * <p>
     * Default values will be used for all {@link ObjectGenerator#on(Object)
     * undefined methods}.
     * </p>
     */
    public static <T> ObjectGenerator<T> defaultObjects(Class<T> objectType) {
        return Generators.defaultObjects(objectType);
    }
}
