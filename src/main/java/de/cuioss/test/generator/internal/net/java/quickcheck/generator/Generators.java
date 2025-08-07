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

import de.cuioss.test.generator.internal.net.java.quickcheck.ExtendibleGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.FrequencyGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.Generator;
import de.cuioss.test.generator.internal.net.java.quickcheck.GeneratorException;
import de.cuioss.test.generator.internal.net.java.quickcheck.ObjectGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.QuickCheck;
import de.cuioss.test.generator.internal.net.java.quickcheck.StatefulGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.collection.Pair;
import de.cuioss.test.generator.internal.net.java.quickcheck.collection.Triple;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.distribution.Distribution;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.AbstractTransformerGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.ArrayGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.ByteArrayGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.ByteGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.CharacterGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.CloningGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.DateGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.DefaultFrequencyGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.DoubleGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.DuplicateGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.EnsuredValuesGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.ExcludingGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.FixedValuesGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.IntegerArrayGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.IntegerGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.IteratorGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.ListGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.LongGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.MapGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.ObjectDefaultMappingGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.ObjectGeneratorImpl;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.SetGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.SortedListGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.StrictlyOrderedGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.StringGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.SubmapGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.SubsetGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.SubstringGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.TupleGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.UniqueComparableValuesGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.UniqueValuesGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.VetoableGenerator;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.CharacterGenerator.BASIC_LATIN;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.CharacterGenerator.LATIN_1_SUPPLEMENT;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.ListGenerator.MAX_SIZE;
import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

@SuppressWarnings({"WeakerAccess"})
public class Generators {

    public static final int DEFAULT_STRING_MAX_LENGTH = StringGenerator.MAX_LENGTH;
    public static final int DEFAULT_COLLECTION_MAX_SIZE = ListGenerator.MAX_SIZE;
    // TODO this could be a bit high
    // for runs = 200 this means 20000 tries for the worst case
    public static final int DEFAULT_MAX_TRIES = VetoableGenerator.DEFAULT_MAX_TRIES;

    private Generators() {
    }

    /**
     * Cast a generator to a super type generator.
     * <p>
     * This method can be used to cast a generator of type Generator&lt;A&gt; to a
     * generator of type Generator&lt;B&gt; given that A extends B. This operator is
     * valid as all Generator instances are covariant (are pure producers).
     * </p>
     */
    @SuppressWarnings("unchecked")
    public static <T> Generator<T> cast(Generator<? extends T> generator) {
        return (Generator<T>) generator;
    }

    /**
     * Convert a generator into a {@link Iterable iterable}.<br>
     *
     * The generator will be run {@link QuickCheck#MAX_NUMBER_OF_RUNS} times.
     */
    public static <T> Iterable<T> toIterable(final Generator<T> generator) {
        return toIterable(generator, QuickCheck.MAX_NUMBER_OF_RUNS);
    }

    /**
     * Convert a generator into an {@link Iterable iterable}.
     *
     * @param numberOfRuns to execute the runner
     */
    public static <T> Iterable<T> toIterable(final Generator<T> generator, final int numberOfRuns) {
        Objects.requireNonNull(generator, "generator");
        if (0.0 > numberOfRuns) {
            throw new IllegalArgumentException("number of runs");
        }
        return () -> new Iterator<>() {

            private int runs;

            @Override
            public boolean hasNext() {
                return runs < numberOfRuns;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                runs++;
                return generator.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    /**
     * Create a new string generator.<br>
     *
     * The characters are from the Basic Latin and Latin-1 Supplement unicode
     * blocks.
     */
    public static ExtendibleGenerator<Character, String> strings() {
        return new StringGenerator();
    }

    /**
     * Create a new string generator which generates strings of characters ranging
     * from lo to hi.
     *
     * @param lo lower boundary character
     * @param hi upper boundary character
     */
    public static ExtendibleGenerator<Character, String> strings(char lo, char hi) {
        return new StringGenerator(lo, hi);
    }

    /**
     * Create a new string generator which generates strings of characters from the
     * given string.
     */
    public static ExtendibleGenerator<Character, String> strings(String allowedCharacters) {
        return new StringGenerator(characters(allowedCharacters));
    }

    /**
     * Create a new string generator which generates strings of characters from the
     * given string with a length between min and max.
     */
    public static ExtendibleGenerator<Character, String> strings(String allowedCharacters, int min, int max) {
        return new StringGenerator(new IntegerGenerator(min, max), characters(allowedCharacters));
    }

    /**
     * Creates a new String genearator which generates strings whose length ranges
     * from zero to given length.
     */
    public static ExtendibleGenerator<Character, String> strings(int max) {
        return strings(0, max);
    }

    /**
     * Create a new string generator which generates strings of sizes ranging from
     * loLength to hiLength.
     *
     * @param min lower size boundary
     * @param max upper size boundary
     */
    public static ExtendibleGenerator<Character, String> strings(int min, int max) {
        return new StringGenerator(new IntegerGenerator(min, max), new CharacterGenerator());
    }

    /**
     * Create a new string generator which creates strings of characters generated
     * by the given character generator with a length generated by the length
     * generator.
     *
     */
    public static ExtendibleGenerator<Character, String> strings(Generator<Integer> length,
            Generator<Character> characters) {
        return new StringGenerator(length, characters);
    }

    /**
     * Create a new string generator which creates strings of characters generated
     * by the given character generator.
     *
     */
    public static ExtendibleGenerator<Character, String> strings(Generator<Character> characterGenerator) {
        return new StringGenerator(characterGenerator);
    }

    /**
     * Create a new string generator which creates strings of characters from a-z
     * and A-Z.
     */
    public static ExtendibleGenerator<Character, String> letterStrings() {
        return new StringGenerator(characters('a', 'z')).add(characters('A', 'Z'));
    }

    /**
     * Create a new string generator which creates strings with sizes ranging from
     * loLengh to hiLength of characters from a-z and A-Z.
     */
    public static ExtendibleGenerator<Character, String> letterStrings(int min, int max) {
        var generator = new StringGenerator(new IntegerGenerator(min, max), characters('a', 'z'));
        return generator.add(characters('A', 'Z'));
    }

    /**
     * Create a new string generator which creates strings of characters generated
     * by {@link Generators#basicLatinCharacters()} and
     * {@link Generators#latin1SupplementCharacters()}.
     */
    public static ExtendibleGenerator<Character, String> printableStrings() {
        return new StringGenerator(basicLatinCharacters()).add(latin1SupplementCharacters());
    }

    /**
     * Create a new string generator for strings that are not empty.
     */
    public static ExtendibleGenerator<Character, String> nonEmptyStrings() {
        return strings(1, StringGenerator.MAX_LENGTH);
    }

    /**
     * Create a new string generator for substrings of a base string.
     *
     * <p>
     * base.contains(generated string) will always be true.
     * </p>
     */
    public static Generator<String> substrings(String base) {
        return substrings(base, 0, base.length());
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
        return substrings(base, size, size);
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
        return new SubstringGenerator(base, minSize, maxSize);
    }

    /**
     * Create a new character generator which generates characters ranging from lo
     * to hi.
     */
    public static Generator<Character> characters(char lo, char hi) {
        return new CharacterGenerator(lo, hi);
    }

    /**
     * Create a new character generator.<br>
     *
     * The characters are from the Basic Latin and Latin-1 Supplement unicode
     * blocks.
     */
    public static Generator<Character> characters() {
        return new CharacterGenerator();
    }

    /**
     * Create a new character generator which generates characters from the given
     * character array.
     */
    public static Generator<Character> characters(Character... chars) {
        return characters(asList(chars));
    }

    /**
     * Create a new character generator which generates characters from the given
     * string.
     */
    public static Generator<Character> characters(String string) {
        var chars = new Character[string.length()];
        for (var i = 0; i < chars.length; i++) {
            chars[i] = string.charAt(i);
        }
        return characters(chars);
    }

    /**
     * Create a new character generator which generates characters from the given
     * characters.
     */
    public static Generator<Character> characters(Iterable<Character> chars) {
        return new FixedValuesGenerator<>(chars);
    }

    /**
     * Create a new character generator which generates latin-1 supplement
     * characters.
     */
    public static Generator<Character> latin1SupplementCharacters() {
        return characters(LATIN_1_SUPPLEMENT.getFirst(), LATIN_1_SUPPLEMENT.getSecond());
    }

    /**
     * Create a new character generator which generates latin characters.
     */
    public static Generator<Character> basicLatinCharacters() {
        return characters(BASIC_LATIN.getFirst(), BASIC_LATIN.getSecond());
    }

    /**
     * Create a new integer generator which creates integers ranging from
     * {@link Integer#MIN_VALUE} to {@link Integer#MAX_VALUE}.
     *
     */
    public static Generator<Integer> integers() {
        return new IntegerGenerator();
    }

    /**
     * Create a new integer generator which creates integers that are at equal or
     * greater than low.
     */
    public static Generator<Integer> integers(int low) {
        return new IntegerGenerator(low, Integer.MAX_VALUE);
    }

    /**
     * Create a new integer generator which creates integers ranging from lo to hi.
     */
    public static Generator<Integer> integers(int lo, int hi) {
        return new IntegerGenerator(lo, hi);
    }

    /**
     * Create a new integer generator which creates integers ranging from {@code lo}
     * to {@code hi} based on the given {@link Distribution}.
     */
    public static Generator<Integer> integers(int lo, int hi, Distribution distribution) {
        return new IntegerGenerator(lo, hi, distribution);
    }

    /**
     * Create a new integer generator which creates integers ranging from {@code 1}
     * to {@link Integer#MAX_VALUE}.
     */
    public static Generator<Integer> positiveIntegers() {
        return positiveIntegers(Integer.MAX_VALUE);
    }

    /**
     * Create a new integer generator which creates integers ranging from {@code 1}
     * to {@code max} (which must be at least 1).
     */
    public static Generator<Integer> positiveIntegers(int hi) {
        return new IntegerGenerator(1, hi);
    }

    /**
     * Create a new byte generator which creates byte values ranging from
     * {@link Byte#MIN_VALUE} to {@link Byte#MAX_VALUE}.
     */
    public static Generator<Byte> bytes() {
        return new ByteGenerator();
    }

    /**
     * Create a new byte generator which creates byte values ranging from lo to hi.
     */
    public static Generator<Byte> bytes(byte lo, byte hi) {
        return new ByteGenerator(lo, hi);
    }

    /**
     * Create a new integer generator which creates integers ranging from lo to hi
     * based on the given {@link Distribution}.
     */
    public static Generator<Byte> bytes(byte lo, byte hi, Distribution distribution) {
        return new ByteGenerator(lo, hi, distribution);
    }

    /**
     * Create a new long generator which creates longs ranging from
     * {@link Long#MIN_VALUE} to {@link Long#MAX_VALUE}.
     */
    public static Generator<Long> longs() {
        return new LongGenerator();
    }

    /**
     * Create a new long generator which creates longs ranging from lo to hi.
     */
    public static Generator<Long> longs(long lo, long hi) {
        return new LongGenerator(lo, hi, Distribution.UNIFORM);
    }

    /**
     * Create a new long generator which creates longs ranging from lo to hi based
     * on the given {@link Distribution}.
     */
    public static Generator<Long> longs(long lo, long hi, Distribution distribution) {
        return new LongGenerator(lo, hi, distribution);
    }

    /**
     * Create a new long generator which creates long values ranging from 1 to
     * {@link Long#MAX_VALUE}.
     */
    public static Generator<Long> positiveLongs() {
        return positiveLongs(Long.MAX_VALUE);
    }

    /**
     * Create a new long generator which creates long values ranging from 1 to hi.
     */
    public static Generator<Long> positiveLongs(long hi) {
        return longs(1, hi);
    }

    /**
     * Create a new double generator which creates doubles ranging from
     * {@link Double#MIN_VALUE} to {@link Double#MAX_VALUE}.
     */
    public static Generator<Double> doubles() {
        return new DoubleGenerator();
    }

    /**
     * Create a new double generator which creates doubles ranging from lo to hi.
     */
    public static Generator<Double> doubles(double lo, double hi) {
        return new DoubleGenerator(lo, hi);
    }

    /**
     * Create a new double generator which creates doubles ranging from lo to hi
     * based on the given {@link Distribution}.
     */
    public static Generator<Double> doubles(double lo, double hi, Distribution distribution) {
        return new DoubleGenerator(lo, hi, distribution);
    }

    /**
     * Create a generator for boolean values.
     */
    public static Generator<Boolean> booleans() {
        return new FixedValuesGenerator<>(asList(Boolean.TRUE, Boolean.FALSE));
    }

    /**
     * Create a generator for null values.
     */
    public static <T> Generator<T> nulls() {
        return new FixedValuesGenerator<>();
    }

    /**
     * Create a generator for date values.
     */
    public static Generator<Date> dates() {
        return dates(MILLISECONDS);
    }

    /**
     * Create a generator for date values with the given precision.
     */
    public static Generator<Date> dates(TimeUnit precision) {
        return dates(Long.MIN_VALUE, Long.MAX_VALUE, precision);
    }

    /**
     * Create a generator for date values from low to high.
     */
    public static Generator<Date> dates(Date low, Date high) {
        return dates(low.getTime(), high.getTime());
    }

    /**
     * Create a generator for date values from low to high.
     */
    public static Generator<Date> dates(long low, long high) {
        return dates(low, high, MILLISECONDS);
    }

    /**
     * Create a generator for date values from low to high with the given precision.
     */
    public static Generator<Date> dates(Long low, Long high, TimeUnit precision) {
        return new DateGenerator(precision, low, high, DEFAULT_MAX_TRIES);
    }

    /**
     * Create a generator for fixed value generator.
     */
    public static <T> Generator<T> fixedValues(T value) {
        return new FixedValuesGenerator<>(value);
    }

    /**
     * Create a fixed value generator returning one of the values from the values
     * array.
     */
    @SafeVarargs
    public static <T> Generator<T> fixedValues(T... values) {
        return fixedValues(asList(values));
    }

    /**
     * Create a fixed value generator returning one of the values from the values.
     */
    public static <T> Generator<T> fixedValues(Iterable<T> values) {
        return new FixedValuesGenerator<>(values);
    }

    /**
     * A cloning generator which uses object serialization to create clones of the
     * prototype object. For each call a new copy of the prototype will be
     * generated.
     */
    public static <T> Generator<T> clonedValues(T prototype) {
        return new CloningGenerator<>(prototype);
    }

    /**
     * Create a generator of enumeration values.
     *
     * @param <T>       Type of enumerations
     * @param enumClass class of enumeration
     * @return generator of enum values
     */
    public static <T extends Enum<T>> Generator<T> enumValues(Class<T> enumClass) {
        return enumValues(enumClass, Collections.emptyList());
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
        return enumValues(enumClass, asList(excluded));
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
        EnumSet<T> excluded = EnumSet.noneOf(enumClass);
        for (T e : excludedValues) {
            excluded.add(e);
        }
        return new FixedValuesGenerator<>(EnumSet.complementOf(excluded));
    }

    /**
     * Create a generator for {@link Object java.lang.Object} instances.
     * <p>
     * Note: every invocation of {@link Generator#next()} creates a new instance.
     * </p>
     */
    public static Generator<Object> objects() {
        return new de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.ObjectGenerator();
    }

    /**
     * Create a generator from a {@link ObjectGenerator declarative object generator
     * definition}.
     */
    public static <T> ObjectGenerator<T> objects(Class<T> objectType) {
        return new ObjectGeneratorImpl<>(objectType);
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
        return new ObjectDefaultMappingGenerator<>(objectType);
    }

    /**
     * <p>
     * Create a frequency generator. The frequency of {@link Generator} usage
     * depends on the generator weight.
     * </p>
     *
     * @param generator pairs of generators and their weights used to created the
     *                  values
     * @param <T>       type of values generated by the generators.
     */
    public static <T> FrequencyGenerator<T> frequency(Generator<T> generator, int weight) {
        return new DefaultFrequencyGenerator<>(generator, weight);
    }

    /**
     * OneOf is a convenience method for
     * {@link Generators#frequency(Generator, int)} when all generator share the
     * same weight.
     */
    public static <T> ExtendibleGenerator<T, T> oneOf(Generator<T> generator) {
        return frequency(generator, 1);
    }

    /**
     * Create a generator which will create vectors (here lists) of type T.
     *
     * @param <T>  Type of the list values.
     * @param size Number of element in the vector.
     */
    public static <T> Generator<List<T>> vectors(Generator<T> content, int size) {
        return new ListGenerator<>(content, new FixedValuesGenerator<>(size));
    }

    /**
     * Create a generator of pairs of type A for the left value and type B for the
     * right value.
     *
     * @param <A>    Type of left value.
     * @param <B>    Type of right value.
     * @param first  Generator for left values.
     * @param second Generator for right values.
     */
    @SuppressWarnings("unchecked")
    public static <A, B> Generator<Pair<A, B>> pairs(Generator<A> first, Generator<B> second) {
        final var generator = new TupleGenerator(first, second);
        return () -> {
            var next = generator.next();
            return new Pair<>((A) next[0], (B) next[1]);
        };
    }

    /**
     * Create a generator of pairs where first value &lt;= second value.
     *
     * @param <T>     Type of the pair values.
     * @param content Generator for content of the pair values.
     */
    public static <T extends Comparable<T>> Generator<Pair<T, T>> sortedPairs(Generator<T> content) {
        return new AbstractTransformerGenerator<>(sortedLists(content, 2, 2)) {

            @Override
            protected Pair<T, T> transform(Generator<List<T>> inputGenerator) {
                var next = inputGenerator.next();
                return new Pair<>(next.getFirst(), next.get(1));
            }
        };
    }

    /**
     * Create a generator of triples of the types A, B and C for first, second and
     * third value.
     *
     * @param <A>    Type of first value.
     * @param <B>    Type of second value.
     * @param <C>    Type of third value.
     * @param first  Generator for first values.
     * @param second Generator for second values.
     * @param third  Generator for third values.
     */
    @SuppressWarnings("unchecked")
    public static <A, B, C> Generator<Triple<A, B, C>> triples(Generator<A> first, Generator<B> second,
            Generator<C> third) {
        final var generator = new TupleGenerator(first, second, third);
        return () -> {
            var next = generator.next();
            return new Triple<>((A) next[0], (B) next[1], (C) next[2]);
        };
    }

    /**
     * Create a generator returning a combination of a null values and the given
     * values.
     *
     * @param <T> Type of the values generated.
     */
    @SafeVarargs
    public static <T> Generator<T> nullsAnd(T... values) {
        return nullsAnd(new FixedValuesGenerator<>(asList(values)));
    }

    /**
     * Create a generator of triples where first value &lt;= second value &lt;=
     * third value.
     *
     * @param <T>     Type of the triple values.
     * @param content Generator for content of the triple values.
     */
    public static <T extends Comparable<T>> Generator<Triple<T, T, T>> sortedTriple(Generator<T> content) {
        return new AbstractTransformerGenerator<>(sortedLists(content, 3, 3)) {

            @Override
            protected Triple<T, T, T> transform(Generator<List<T>> inputGenerator) {
                var next = inputGenerator.next();
                return new Triple<>(next.getFirst(), next.get(1), next.get(2));
            }
        };
    }

    /**
     * Create a generator as a combination of a null value generator and generator
     * of type T.
     *
     * @param <T> Type of the values generated.
     */
    public static <T> Generator<T> nullsAnd(Generator<T> generator) {
        return nullsAnd(generator, 5);
    }

    /**
     * Create a generator as a combination of a null value generator and generator
     * of type T.
     *
     * @param <T>    Type of the values generated.
     * @param weight weight of the provided generator
     */
    public static <T> Generator<T> nullsAnd(Generator<T> generator, int weight) {
        return new DefaultFrequencyGenerator<>(Generators.<T>nulls(), 1).add(generator, weight);
    }

    /**
     * Create a generator of sets with values from the content generator.
     *
     * @param <T>     type of set elements generated
     * @param content generator providing the content of sets generated
     */
    public static <T> Generator<Set<T>> sets(Generator<? extends T> content) {
        return new SetGenerator<>(content);
    }

    /**
     * Create a generator of sets with values from the content generator.
     *
     * @param <T>     type of set elements generated
     * @param content generator providing the content of sets generated
     * @param size    size of the sets generated
     */
    public static <T> Generator<Set<T>> sets(Generator<? extends T> content, Generator<Integer> size) {
        return new SetGenerator<>(content, size, DEFAULT_MAX_TRIES);
    }

    /**
     * Create a generator of sets with values from the content generator. Length is
     * between high and low.
     *
     * @param <T>     type of set elements generated
     * @param content generator providing the content of sets generated
     * @param low     minimal size
     * @param high    max size
     */
    public static <T> Generator<Set<T>> sets(Generator<? extends T> content, int low, int high) {
        return new SetGenerator<>(content, integers(low, high), DEFAULT_MAX_TRIES);
    }

    /**
     * Create a generator of sets that are not empty.
     *
     * @param <T>     type of set elements generated
     * @param content generator providing the content of sets generated
     */
    public static <T> Generator<Set<T>> nonEmptySets(Generator<? extends T> content) {
        return sets(content, 1, SetGenerator.MAX_SIZE);
    }

    @SafeVarargs
    public static <T> Generator<Set<T>> sets(T... superset) {
        return sets(asList(superset));
    }

    /**
     * Create a generator of subsets from a given set.
     *
     * @param <T>      type of set elements generated
     * @param superset of the generated set
     */
    public static <T> Generator<Set<T>> sets(Iterable<T> superset) {
        return new SubsetGenerator<>(superset);
    }

    /**
     * Create a generator of subsets from a given set.
     *
     * @param <T>      type of set elements generated
     * @param superset of the generated set
     * @param size     of the generated set
     */
    public static <T> Generator<Set<T>> sets(Iterable<T> superset, Generator<Integer> size) {
        return new SubsetGenerator<>(superset, size);
    }

    /**
     * Create a generator that produces lists of duplicates.
     *
     * @return a list derived from the input values. At least one input value is
     *         more than once in the resulting list.
     */
    @SafeVarargs
    public static <T> Generator<List<T>> duplicates(T... input) {
        return new DuplicateGenerator<>(asList(input));
    }

    /**
     * Create a generator that produces lists of duplicates.
     *
     * @return a list derived from the input values. At least one input value is
     *         more than once in the resulting list.
     */
    public static <T> Generator<List<T>> duplicates(Iterable<T> input) {
        return new DuplicateGenerator<>(input);
    }

    /**
     * Create a generator of iterators.
     *
     * <p>
     * Values of the elements will be taken from the content generator.
     * </p>
     *
     * @param <T>     type of iterator elements generated
     * @param content generator providing the content of iterators generated
     */
    public static <T> Generator<Iterator<T>> iterators(Generator<? extends T> content) {
        return new IteratorGenerator<>(content);
    }

    /**
     * Create a generator of iterators.
     *
     * <p>
     * Values of the elements will be taken from the content generator. The
     * generated iterator will have at least one element.
     * </p>
     *
     * @param <T>     type of iterator elements generated
     * @param content generator providing the content of iterators generated
     */
    public static <T> Generator<Iterator<T>> nonEmptyIterators(Generator<T> content) {
        return new IteratorGenerator<>(content, 1, IteratorGenerator.MAX_SIZE);
    }

    /**
     * Create a generator of iterators.
     *
     * <p>
     * Values of the elements will be taken from the content generator. The length
     * of the iterators will be determined with the size generator.
     * </p>
     *
     * @param <T>     type of iterator elements generated
     * @param content generator providing the content of iterators generated
     * @param size    used to determine the number of elements of the iterator
     */
    public static <T> Generator<Iterator<T>> iterators(Generator<? extends T> content, Generator<Integer> size) {
        return new IteratorGenerator<>(content, size);
    }

    /**
     * Create a generator of lists with values from the content generator. Length
     * values of lists generated will be created with {@link Distribution#UNIFORM}.
     *
     * @param <T>     type of list elements generated
     * @param content generator providing the content of lists generated
     */
    public static <T> Generator<List<T>> lists(Generator<? extends T> content) {
        return new ListGenerator<>(content);
    }

    /**
     * Create a generator of non-empty lists with values from the content generator.
     * Length values of lists generated will be created with
     * {@link Distribution#UNIFORM}.
     *
     * @param <T>     type of list elements generated
     * @param content generator providing the content of lists generated
     */
    public static <T> Generator<List<T>> nonEmptyLists(Generator<? extends T> content) {
        return lists(content, positiveIntegers(MAX_SIZE));
    }

    /**
     * Create a generator of lists with values from the content generator. Length
     * values of lists generated will be created with size generator.
     *
     * @param <T>     type of list elements generated
     * @param content generator providing the content of lists generated
     * @param size    integer used to determine the list size
     */
    public static <T> Generator<List<T>> lists(Generator<? extends T> content, Generator<Integer> size) {
        return new ListGenerator<>(content, size);
    }

    /**
     * Create a generator of lists with values from the content generator. Length is
     * between high and low.
     *
     * @param <T>     type of list elements generated
     * @param content generator providing the content of lists generated
     * @param low     minimal size
     * @param high    max size
     */
    public static <T> Generator<List<T>> lists(Generator<? extends T> content, int low, int high) {
        return lists(content, new IntegerGenerator(low, high));
    }

    /**
     * Create a generator of lists with values from the content generator. Length is
     * at least low.
     *
     * @param <T>     type of list elements generated
     * @param content generator providing the content of lists generated
     * @param low     minimal size. If low is larger than
     *                {@link Generators#DEFAULT_COLLECTION_MAX_SIZE} then it is the
     *                upper size bound as well.
     */
    public static <T> Generator<List<T>> lists(Generator<? extends T> content, int low) {
        return lists(content, low, Math.max(low, ListGenerator.MAX_SIZE));
    }

    /**
     * Create a generator of sorted lists with values from the content generator.
     *
     * @param <T>     type of list elements generated
     * @param content generator providing the content of lists generated
     */
    public static <T extends Comparable<T>> Generator<List<T>> sortedLists(Generator<T> content) {
        return new SortedListGenerator<>(content);

    }

    /**
     * Create a generator of sorted lists with values from the content generator.
     * Length is between high and low.
     *
     * @param <T>     type of list elements generated
     * @param content generator providing the content of lists generated
     * @param low     minimal size
     * @param high    max size
     */
    public static <T extends Comparable<T>> Generator<List<T>> sortedLists(Generator<T> content, int low, int high) {
        return sortedLists(content, integers(low, high));

    }

    /**
     * Create a generator of sorted lists with values from the content generator.
     * Length is between high and low.
     *
     * @param <T>     type of list elements generated
     * @param content generator providing the content of lists generated
     * @param size    integer used to determine the list size
     */
    public static <T extends Comparable<T>> Generator<List<T>> sortedLists(Generator<T> content,
            Generator<Integer> size) {
        return new SortedListGenerator<>(content, size);
    }

    /**
     * Create a generator of arrays with values from the content generator. Length
     * values of array generated will be created with {@link Distribution#UNIFORM}.
     *
     * @param <T>     type of arrays elements generated
     * @param content generator providing the content of arrays generated
     * @param type    type of arrays generated
     */
    public static <T> Generator<T[]> arrays(Generator<? extends T> content, Class<T> type) {
        return new ArrayGenerator<>(content, type);
    }

    /**
     * Create a generator of arrays that are not empty.
     *
     * @param <T>     type of arrays elements generated
     * @param content generator providing the content of arrays generated
     * @param type    type of arrays generated
     */
    public static <T> Generator<T[]> nonEmptyArrays(Generator<? extends T> content, Class<T> type) {
        return arrays(content, positiveIntegers(MAX_SIZE), type);
    }

    /**
     * Create a generator of arrays with values from the content generator. Length
     * values of arrays generated will be created with size generator.
     *
     * @param <T>     type of arrays elements generated
     * @param content generator providing the content of arrays generated
     * @param size    integer used to determine the array size
     * @param type    type of arrays generated
     */
    public static <T> Generator<T[]> arrays(Generator<? extends T> content, Generator<Integer> size, Class<T> type) {
        return new ArrayGenerator<>(content, size, type);
    }

    /**
     * Create a generator of byte arrays. The length of arrays generated will be
     * determined by the {@link ByteArrayGenerator#MIN_SIZE} and
     * {@link ByteArrayGenerator#MAX_SIZE} constants.
     *
     */
    public static Generator<byte[]> byteArrays() {
        return new ByteArrayGenerator();
    }

    /**
     * Create a generator of byte arrays. Length values of arrays generated will be
     * created with size generator.
     *
     * @param size integer used to determine the array size
     */
    public static Generator<byte[]> byteArrays(Generator<Integer> size) {
        return new ByteArrayGenerator(size);
    }

    /**
     * Create a generator of byte arrays. Length values of arrays generated will be
     * created with size generator.
     *
     * @param size    integer used to determine the array size
     * @param content generator for the byte array content
     */
    public static Generator<byte[]> byteArrays(Generator<Byte> content, Generator<Integer> size) {
        return new ByteArrayGenerator(content, size);
    }

    /**
     * Create a generator of integer arrays.
     *
     */
    public static Generator<int[]> intArrays() {
        return new IntegerArrayGenerator();
    }

    /**
     * Create a generator of integer arrays. Length values of arrays generated will
     * be created with size generator.
     *
     * @param size integer used to determine the array size
     */
    public static Generator<int[]> intArrays(Generator<Integer> size) {
        return new IntegerArrayGenerator(size);
    }

    /**
     * Create a generator of integer arrays. Length values of arrays generated will
     * be created with size generator.
     *
     * @param size    integer used to determine the array size
     * @param content generator for the integer array content
     */
    public static Generator<int[]> intArrays(Generator<Integer> content, Generator<Integer> size) {
        return new IntegerArrayGenerator(content, size);
    }

    /**
     * Create a generator of {@link Map maps}.
     *
     * <p>
     * This is a generator for simple maps where the values are not related to the
     * keys.
     * </p>
     *
     * @param keys   {@link Generator} for the keys of the map
     * @param values {@link Generator} for the values of the map
     */
    public static <K, V> Generator<Map<K, V>> maps(Generator<K> keys, Generator<V> values) {
        return new MapGenerator<>(keys, values);
    }

    /**
     * Create a generator of {@link Map maps}.
     *
     * <p>
     * This is a generator for simple maps where the values are not related to the
     * keys.
     * </p>
     *
     * @param keys   {@link Generator} for the keys of the map
     * @param values {@link Generator} for the values of the map
     * @param size   integer used to determine the size of the generated map
     */
    public static <K, V> Generator<Map<K, V>> maps(Generator<K> keys, Generator<V> values, Generator<Integer> size) {
        return new MapGenerator<>(keys, values, size);
    }

    /**
     * Create a generator of maps from a given map.
     *
     * <p>
     * The entry set of the generated maps are subsets of the given map's entry set.
     * </p>
     * 
     * @param supermap of the generated maps
     */
    public static <K, V> Generator<Map<K, V>> maps(Map<K, V> supermap) {
        return new SubmapGenerator<>(supermap);
    }

    /**
     * Create a generator of maps from a given map.
     * <p>
     * The entry set of the generated maps are subsets of the given map's entry set.
     * </p>
     *
     * @param supermap of the generated maps
     * @param sizes    of the generated maps
     */
    public static <K, V> Generator<Map<K, V>> maps(Map<K, V> supermap, Generator<Integer> sizes) {
        return new SubmapGenerator<>(supermap, sizes);
    }

    /**
     * Create a deterministic generator which guarantees that all values from the
     * ensuredValues collection will be returned if enough calls to
     * {@link Generator#next()} are issued (i.e. ensuredValues.size() &lt;= # of
     * runs). The order of values is undefined.
     *
     * @param <T> type of values return by the generator
     */
    public static <T> StatefulGenerator<T> ensureValues(Iterable<T> ensuredValues) {
        return new EnsuredValuesGenerator<>(ensuredValues);
    }

    /**
     * Create a deterministic generator which guarantees that all values from the
     * ensuredValues array will be returned if enough calls to
     * {@link Generator#next()} are issued (i.e. ensuredValues.size() &lt;= # of
     * runs). The order of values is undefined.
     *
     * @param <T> type of values return by the generator
     */
    @SafeVarargs
    public static <T> StatefulGenerator<T> ensureValues(T... content) {
        return ensureValues(asList(content));
    }

    /**
     * <p>
     * Create a deterministic generator which guarantees that all values from the
     * ensuredValues collection will be returned if enough calls to
     * {@link Generator#next()} are issued (i.e. ensuredValues.size() &lt;= # of
     * runs). The order of values is undefined.
     * </p>
     * <p>
     * If all values of ensuredValues are generated calls to
     * {@link Generator#next()} will return values from the otherValues generator.
     * </p>
     *
     * @param <T> type of values return by the generator
     */
    public static <T> StatefulGenerator<T> ensureValues(Iterable<T> ensuredValues, Generator<T> otherValues) {
        return new EnsuredValuesGenerator<>(ensuredValues, otherValues);
    }

    /**
     * <p>
     * Create a generator which guarantees that all values from the ensuredValues
     * will be returned in a defined window when enough calls to
     * {@link Generator#next()} are issued.
     * </p>
     * <p>
     * The order of values is undefined. All other values in the window and after
     * the window are taken from the {@link Generator generator otherValues}.
     * </p>
     *
     * @param <T>    type of values return by the generator
     * @param window After window number of calls to {@link Generator#next()} it is
     *               guaranteed that all ensured values were returned.
     */
    public static <T> StatefulGenerator<T> ensureValues(Iterable<T> ensuredValues, int window,
            Generator<T> otherValues) {
        return new EnsuredValuesGenerator<>(ensuredValues, window, otherValues);
    }

    /**
     * <p>
     * Create a generator that ensures unique values.
     * </p>
     * <p>
     * The actual values are created with an arbitrary generator.
     * </p>
     * <p>
     * Note: unique generator depends on valid implementation of equals and hashCode
     * method of the content type generated.
     * </p>
     *
     * @param <T>       type of values return by the generator
     * @param generator used to create the raw values. This generator can create
     *                  duplicate values
     * @param tries     Number of tries to create a new unique value. After this
     *                  number of tries is exceeded the generation aborts with a
     *                  {@link GeneratorException}.
     * @return unique generator instance
     */
    public static <T> StatefulGenerator<T> uniqueValues(Generator<T> generator, int tries) {
        return new UniqueValuesGenerator<>(generator, tries);
    }

    /**
     * <p>
     * Create a generator that ensures unique values.
     * </p>
     * <p>
     * The actual values are created with an arbitrary generator.
     * </p>
     * <p>
     * Unique generator depends on the {@link Comparator} implementation to decide
     * if two instances are the same (i.e. when the comparator returns 0 for
     * {@link Comparator#compare(Object, Object)}).
     * </p>
     *
     * @param <T>        type of values returned by the generator
     * @param generator  used to create the raw values. This generator can create
     *                   duplicate values
     * @param comparator that decides if two values are of the same equivalence
     *                   class.
     * @param tries      Number of tries to create a new unique value. After this
     *                   number of tries is exceeded the generation aborts with a
     *                   {@link GeneratorException}.
     * @return unique generator instance
     */
    public static <T> StatefulGenerator<T> uniqueValues(Generator<T> generator, Comparator<? super T> comparator,
            int tries) {
        return new UniqueComparableValuesGenerator<>(generator, comparator, tries);
    }

    /**
     * <p>
     * Create a generator that ensures unique values.
     * </p>
     * <p>
     * The actual values are created with an arbitrary generator.
     * </p>
     * <p>
     * Unique generator depends on the {@link Comparator} implementation to decide
     * if two instances are the same (i.e. when the comparator returns 0 for
     * {@link Comparator#compare(Object, Object)}).
     * </p>
     *
     * @param <T>        type of values returned by the generator
     * @param generator  used to create the raw values. This generator can create
     *                   duplicate values
     * @param comparator that decides if two values are of the same equivalence
     *                   class.
     * @return unique generator instance
     */
    public static <T> StatefulGenerator<T> uniqueValues(Generator<T> generator, Comparator<? super T> comparator) {
        return uniqueValues(generator, comparator, DEFAULT_MAX_TRIES);
    }

    /**
     * <p>
     * Create a generator that ensures unique values
     * </p>
     * <p>
     * The actual values are created with an arbitrary generator.
     * </p>
     * <p>
     * Note: unique generator depends on valid implementation of equals and hashCode
     * method of the content type generated.
     * </p>
     *
     * @param <T>       type of values return by the generator
     * @param generator used to create the raw values. This generator can create
     *                  duplicate values
     * @return unique generator instance
     */
    public static <T> StatefulGenerator<T> uniqueValues(Generator<T> generator) {
        return new UniqueValuesGenerator<>(generator, DEFAULT_MAX_TRIES);
    }

    /**
     * Create a generator that omits a given value.
     *
     * @param generator used to create the raw values.
     * @param excluded  value. This value will not be returned.
     */
    public static <T> Generator<T> excludeValues(Generator<T> generator, T excluded) {
        return excludeValues(generator, List.of(excluded));
    }

    /**
     * Create a generator that omits a given set of values.
     *
     * @param generator used to create the raw values.
     * @param excluded  values. These values will not be returned.
     */
    @SafeVarargs
    public static <T> Generator<T> excludeValues(Generator<T> generator, T... excluded) {
        return excludeValues(generator, asList(excluded));
    }

    /**
     * Create a generator that omits a given set of values.
     *
     * @param values   of generator
     * @param excluded values. These values will not be returned.
     */
    @SafeVarargs
    public static <T> Generator<T> excludeValues(Iterable<T> values, T... excluded) {
        return excludeValues(values, asList(excluded));
    }

    /**
     * Create a generator that omits a given set of values.
     *
     * @param values   of generator
     * @param excluded values. These values will not be returned.
     */
    public static <T> Generator<T> excludeValues(Iterable<T> values, Iterable<T> excluded) {
        return excludeValues(fixedValues(values), excluded);
    }

    /**
     * Create a generator that omits a given set of values.
     *
     * @param generator used to create the raw values.
     * @param excluded  values. These values will not be returned.
     */
    public static <T> Generator<T> excludeValues(Generator<T> generator, Iterable<T> excluded) {
        return new ExcludingGenerator<>(generator, excluded, DEFAULT_MAX_TRIES);
    }

    /**
     * A generator for a lists. The values in the lists are strictly increasing.
     *
     * <p>
     * For every element x in the list: x(n) &lt; x(n+1).
     * </p>
     *
     * @param input values generator
     */
    public static <T extends Comparable<T>> Generator<List<T>> strictlyOrdered(Generator<T> input) {
        return strictlyOrdered(input, 0, DEFAULT_COLLECTION_MAX_SIZE);
    }

    /**
     * A generator for a lists. The values in the lists are strictly increasing.
     *
     * <p>
     * For every element x in the list: x(n) &lt; x(n+1).
     * </p>
     *
     * @param input values generator
     * @param low   minimum size of the lists
     * @param high  maximum size of the lists
     */
    public static <T extends Comparable<T>> Generator<List<T>> strictlyOrdered(Generator<T> input, int low, int high) {
        Comparator<T> natural = Comparable::compareTo;
        return strictlyOrdered(input, natural, new IntegerGenerator(low, high));
    }

    /**
     * A generator for a lists. The values in the lists are strictly increasing.
     *
     * <p>
     * For every element x in the list: x(n) &lt; x(n+1).
     * </p>
     *
     * <p>
     * This {@link Generator} can be used to generate a list of strictly decreasing
     * values:
     * {@code Generators.strictlyOrdered(ts, Collections.<T> reverseOrder());}
     * </p>
     *
     *
     * @param input      values generator
     * @param comparator that orders the values
     */
    public static <T> Generator<List<T>> strictlyOrdered(Generator<T> input, Comparator<T> comparator) {
        return strictlyOrdered(input, comparator, new IntegerGenerator(0, DEFAULT_COLLECTION_MAX_SIZE));
    }

    /**
     * A generator for a lists. The values in the lists are strictly increasing.
     * <p>
     * For every element x in the list: x(n) &lt; x(n+1).
     * </p>
     *
     * @param input      values generator
     * @param comparator that orders the values
     * @param size       of the resulting lists
     */
    public static <T> Generator<List<T>> strictlyOrdered(Generator<T> input, Comparator<T> comparator,
            Generator<Integer> size) {
        return new StrictlyOrderedGenerator<>(input, comparator, size);
    }
}
