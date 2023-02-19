package io.cui.test.generator;


import static io.cui.tools.collect.CollectionLiterals.mutableList;
import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

import io.cui.test.generator.impl.CollectionGenerator;
import io.cui.test.generator.impl.DecoratorGenerator;
import io.cui.test.generator.impl.FloatObjectGenerator;
import io.cui.test.generator.impl.LocalDateGenerator;
import io.cui.test.generator.impl.LocalDateTimeGenerator;
import io.cui.test.generator.impl.LocalTimeGenerator;
import io.cui.test.generator.impl.NonBlankStringGenerator;
import io.cui.test.generator.impl.NumberGenerator;
import io.cui.test.generator.impl.ShortObjectGenerator;
import io.cui.test.generator.impl.URLGenerator;
import io.cui.test.generator.impl.ZoneOffsetGenerator;
import io.cui.test.generator.impl.ZonedDateTimeGenerator;
import io.cui.test.generator.internal.net.QuickCheckGeneratorAdapter;
import io.cui.test.generator.internal.net.java.quickcheck.Generator;
import io.cui.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators;
import io.cui.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators;
import io.cui.test.generator.internal.net.java.quickcheck.generator.support.FixedValuesGenerator;
import lombok.experimental.UtilityClass;

/**
 * Provides a number of {@link TypedGenerator} for arbitrary java-types
 *
 * @author Oliver Wolff
 */
@UtilityClass
public class Generators {

    /**
     * Factory method for creating a generator for a possible given enum.
     *
     * @param type to be checked must represent an enum
     * @return an {@link Optional} on the corresponding {@link TypedGenerator} if the given type is
     *         an enum can be found, {@link Optional#empty()} otherwise
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <T> Optional<TypedGenerator<T>> enumValuesIfAvailable(
            final Class<T> type) {
        if (null == type || !type.isEnum()) {
            return Optional.empty();
        }
        return Optional
                .of(new QuickCheckGeneratorAdapter(type, PrimitiveGenerators.enumValues((Class<Enum>) type)));
    }

    /**
     * Factory method for creating a generator for a given enum.
     *
     * @param type to be checked must represent an enum
     * @return A {@link TypedGenerator} for the given enmu
     */
    public static <T extends Enum<T>> TypedGenerator<T> enumValues(
            final Class<T> type) {
        requireNonNull(type);
        return new QuickCheckGeneratorAdapter<>(type, PrimitiveGenerators.enumValues(type));
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for non-empty Strings.
     *
     * @return a {@link TypedGenerator} for non-empty Strings
     */
    public static TypedGenerator<String> nonEmptyStrings() {
        return new QuickCheckGeneratorAdapter<>(String.class, PrimitiveGenerators.nonEmptyStrings());
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for non-blank Strings.
     *
     * @return a {@link TypedGenerator} for non-blank Strings.
     */
    public static TypedGenerator<String> nonBlankStrings() {
        return new NonBlankStringGenerator();
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for Strings.
     *
     * @param minSize lower bound of size
     * @param maxSize upper bound of size
     * @return a {@link TypedGenerator} for Strings
     */
    public static TypedGenerator<String> strings(final int minSize, final int maxSize) {
        return new QuickCheckGeneratorAdapter<>(String.class, PrimitiveGenerators.strings(minSize, maxSize));
    }

    /**
     * Factory method for creating strings with given characters and size.
     *
     * @param chars to be generated
     * @param minSize lower bound of size
     * @param maxSize upper bound of size
     * @return a {@link TypedGenerator} for Strings
     */
    public static TypedGenerator<String> strings(final String chars, final int minSize, final int maxSize) {
        return new QuickCheckGeneratorAdapter<>(String.class, PrimitiveGenerators.strings(chars, minSize, maxSize));
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for any Strings, may be null or empty.
     *
     * @return a {@link TypedGenerator} for Strings
     */
    public static TypedGenerator<String> strings() {
        return new QuickCheckGeneratorAdapter<>(String.class, PrimitiveGenerators.strings());
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for letter Strings.
     *
     * @param minSize lower bound of size
     * @param maxSize upper bound of size
     * @return a {@link TypedGenerator} for Strings
     */
    public static TypedGenerator<String> letterStrings(final int minSize, final int maxSize) {
        return new QuickCheckGeneratorAdapter<>(String.class,
                PrimitiveGenerators.letterStrings(minSize, maxSize));
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for sensible / simple non empty letter
     * Strings. The mininmal size is 3, the maximal size between 3 and 256 characters
     *
     * @return a {@link TypedGenerator} for Strings
     */
    public static TypedGenerator<String> letterStrings() {
        return letterStrings(3, integers(3, 256).next());
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for a number of fixed values.
     *
     * @param type of the value
     * @param values to be generated from.
     * @return a {@link TypedGenerator} for the given values
     */
    @SafeVarargs
    public static <T> TypedGenerator<T> fixedValues(final Class<T> type, final T... values) {
        return fixedValues(type, Arrays.asList(values));
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for a number of fixed values.
     *
     * @param values to be generated from.
     * @return a {@link TypedGenerator} for the given values
     */
    @SafeVarargs
    public static <T> TypedGenerator<T> fixedValues(final T... values) {
        return fixedValues(Arrays.asList(values));
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for a number of fixed values.
     *
     * @param type of the value
     * @param values to be generated from.
     * @return a {@link TypedGenerator} for the given values
     */
    public static <T> TypedGenerator<T> fixedValues(final Class<T> type, final Iterable<T> values) {
        return new QuickCheckGeneratorAdapter<>(type, new FixedValuesGenerator<>(mutableList(values)));
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for a number of fixed values.
     *
     * @param values to be generated from.
     * @return a {@link TypedGenerator} for the given values
     */
    public static <T> TypedGenerator<T> fixedValues(final Iterable<T> values) {
        return fixedValues(determineSupertypeFromIterable(values), values);
    }

    /* Combined generators */

    /**
     * Factory method for creating a {@link TypedGenerator} generating unique values. In case this
     * does not work it will throw an {@link RuntimeException}
     *
     * @param source to be generated from.
     * @return a {@link TypedGenerator} for the given values
     */
    @SuppressWarnings("unchecked") // Check not needed, because given TypedGenerator provides
    // correct type
    public static <T> TypedGenerator<T> uniqueValues(final TypedGenerator<T> source) {
        return new QuickCheckGeneratorAdapter<>((Class<T>) source.getClass(),
                CombinedGenerators.uniqueValues(unwrap(source)));
    }

    /**
     * Factory method for creating a {@link CollectionGenerator} generating {@link Collection}s from
     * the given {@link TypedGenerator} .
     *
     * @param source to be generated from.
     * @return a {@link TypedGenerator} for the given values
     */
    public static <T> CollectionGenerator<T> asCollectionGenerator(final TypedGenerator<T> source) {
        return new CollectionGenerator<>(source);
    }

    // Basic Java Types

    /**
     * Factory method for creating a {@link TypedGenerator} for boolean primitives.
     *
     * @return a {@link TypedGenerator} for boolean primitives
     */
    public static TypedGenerator<Boolean> booleans() {
        return new QuickCheckGeneratorAdapter<>(boolean.class, PrimitiveGenerators.booleans());
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for {@link Boolean}.
     *
     * @return a {@link TypedGenerator} for {@link Boolean}
     */
    public static TypedGenerator<Boolean> booleanObjects() {
        return new QuickCheckGeneratorAdapter<>(Boolean.class, PrimitiveGenerators.booleans());
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for byte primitives.
     *
     * @return a {@link TypedGenerator} for byte primitives
     */
    public static TypedGenerator<Byte> bytes() {
        return new QuickCheckGeneratorAdapter<>(byte.class, PrimitiveGenerators.bytes());
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for {@link Byte}.
     *
     * @return a {@link TypedGenerator} for {@link Byte}
     */
    public static TypedGenerator<Byte> byteObjects() {
        return new QuickCheckGeneratorAdapter<>(Byte.class, PrimitiveGenerators.bytes());
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for char primitives.
     *
     * @return a {@link TypedGenerator} for char primitives
     */
    public static TypedGenerator<Character> characters() {
        return new QuickCheckGeneratorAdapter<>(char.class, PrimitiveGenerators.characters());
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for {@link Character}.
     *
     * @return a {@link TypedGenerator} for {@link Character}
     */
    public static TypedGenerator<Character> characterObjects() {
        return new QuickCheckGeneratorAdapter<>(Character.class, PrimitiveGenerators.characters());
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for double primitives.
     *
     * @return a {@link TypedGenerator} for double primitives
     */
    public static TypedGenerator<Double> doubles() {
        return new QuickCheckGeneratorAdapter<>(double.class, PrimitiveGenerators.doubles());
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for {@link Double}.
     *
     * @param low lower bound of range
     * @param high upper bound of range
     * @return a {@link TypedGenerator} for {@link Double}
     */
    public static TypedGenerator<Double> doubles(final double low, final double high) {
        return new QuickCheckGeneratorAdapter<>(Double.class, PrimitiveGenerators.doubles(low, high));
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for {@link Double}.
     *
     * @return a {@link TypedGenerator} for {@link Double}
     */
    public static TypedGenerator<Double> doubleObjects() {
        return new QuickCheckGeneratorAdapter<>(Double.class, PrimitiveGenerators.doubles());
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for float primitives.
     *
     * @return a {@link TypedGenerator} for float primitives
     */
    public static TypedGenerator<Float> floats() {
        return new DecoratorGenerator<>(float.class, floatObjects());
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for {@link Float}.
     *
     * @param low lower bound of range
     * @param high upper bound of range
     * @return a {@link TypedGenerator} for {@link Float}
     */
    public static TypedGenerator<Float> floats(final float low, final float high) {
        return new FloatObjectGenerator(low, high);
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for {@link Float}.
     *
     * @return a {@link TypedGenerator} for {@link Float}
     */
    public static TypedGenerator<Float> floatObjects() {
        return new FloatObjectGenerator();
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for integer primitives.
     *
     * @return a {@link TypedGenerator} for integer primitives
     */
    public static TypedGenerator<Integer> integers() {
        return new QuickCheckGeneratorAdapter<>(int.class, PrimitiveGenerators.integers());
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for {@link Integer}.
     *
     * @param low lower bound of range
     * @param high upper bound of range
     * @return a {@link TypedGenerator} for {@link Integer}
     */
    public static TypedGenerator<Integer> integers(final int low, final int high) {
        return new QuickCheckGeneratorAdapter<>(Integer.class, PrimitiveGenerators.integers(low, high));
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for {@link Integer}.
     *
     * @return a {@link TypedGenerator} for {@link Integer}
     */
    public static TypedGenerator<Integer> integerObjects() {
        return new QuickCheckGeneratorAdapter<>(Integer.class, PrimitiveGenerators.integers());
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for {@link Number}.
     *
     * @return a {@link TypedGenerator} for {@link Number}
     */
    public static TypedGenerator<Number> numbers() {
        return new NumberGenerator();
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for short primitives.
     *
     * @return a {@link TypedGenerator} for short primitives
     */
    public static TypedGenerator<Short> shorts() {
        return new DecoratorGenerator<>(short.class, shortObjects());
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for {@link Short}.
     *
     * @return a {@link TypedGenerator} for {@link Short}
     */
    public static TypedGenerator<Short> shortObjects() {
        return new ShortObjectGenerator();
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for long primitives.
     *
     * @return a {@link TypedGenerator} for long primitives
     */
    public static TypedGenerator<Long> longs() {
        return new QuickCheckGeneratorAdapter<>(long.class, PrimitiveGenerators.longs());
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for Long primitives.
     *
     * @param low lower bound of range
     * @param high upper bound of range
     * @return a {@link TypedGenerator} for long primitives
     */
    public static TypedGenerator<Long> longs(final long low, final long high) {
        return new QuickCheckGeneratorAdapter<>(long.class, PrimitiveGenerators.longs(low, high));
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for {@link Long}.
     *
     * @return a {@link TypedGenerator} for {@link Long}
     */
    public static TypedGenerator<Long> longObjects() {
        return new QuickCheckGeneratorAdapter<>(Long.class, PrimitiveGenerators.longs());
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for {@link Date}.
     *
     * @return a {@link TypedGenerator} for {@link Date}
     */
    public static TypedGenerator<Date> dates() {
        return new QuickCheckGeneratorAdapter<>(Date.class, PrimitiveGenerators.dates());
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for {@link LocalDate}.
     *
     * @return a {@link TypedGenerator} for {@link LocalDate}
     */
    public static TypedGenerator<LocalDate> localDates() {
        return new LocalDateGenerator();
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for {@link LocalTime}.
     *
     * @return a {@link TypedGenerator} for {@link LocalTime}
     */
    public static TypedGenerator<LocalTime> localTimes() {
        return new LocalTimeGenerator();
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for {@link LocalDateTime}.
     *
     * @return a {@link TypedGenerator} for {@link LocalDateTime}
     */
    public static TypedGenerator<LocalDateTime> localDateTimes() {
        return new LocalDateTimeGenerator();
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for {@link ZonedDateTime}.
     *
     * @return a {@link TypedGenerator} for {@link ZonedDateTime}
     */
    public static TypedGenerator<ZonedDateTime> zonedDateTimes() {
        return new ZonedDateTimeGenerator();
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for {@link TimeZone}.
     *
     * @return a {@link TypedGenerator} for {@link TimeZone}
     */
    public static TypedGenerator<TimeZone> timeZones() {
        final List<TimeZone> timezones = new ArrayList<>();
        for (final String id : TimeZone.getAvailableIDs()) {
            timezones.add(TimeZone.getTimeZone(id));
        }
        return fixedValues(TimeZone.class, timezones);
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for {@link ZoneId}.
     *
     * @return a {@link TypedGenerator} for {@link ZoneId}
     */
    public static TypedGenerator<ZoneId> zoneIds() {
        return fixedValues(ZoneId.class, ZoneId.getAvailableZoneIds().stream()
                .map(ZoneId::of).collect(Collectors.toList()));
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for {@link ZoneOffset}.
     *
     * @return a {@link TypedGenerator} for {@link ZoneOffset}
     */
    public static TypedGenerator<ZoneOffset> zoneOffsets() {
        return new ZoneOffsetGenerator();
    }

    /**
     * Factory method for creating a {@link TypedGenerator} for {@link Temporal}s.
     *
     * @return a {@link TypedGenerator} for {@link Temporal}s
     */
    public static TypedGenerator<Temporal> temporals() {
        return new TypedGenerator<>() {

            @Override
            public Class<Temporal> getType() {
                return Temporal.class;
            }

            @Override
            public Temporal next() {
                return zonedDateTimes().next().toInstant();
            }
        };
    }

    // Advanced Java Types

    /**
     * Factory method for creating a {@link TypedGenerator} arbitrary {@link Class} Objects
     *
     * @return a {@link TypedGenerator} for the given values
     */
    @SuppressWarnings("rawtypes")
    public static TypedGenerator<Class> classTypes() {
        return fixedValues(Class.class, Integer.class, String.class, Boolean.class, Float.class);
    }

    /**
     * Factory method for creating a {@link TypedGenerator} arbitrary {@link Locale} Objects
     *
     * @return a {@link TypedGenerator} for all {@link Locale}s
     */
    public static TypedGenerator<Locale> locales() {
        return fixedValues(Locale.class, Locale.getAvailableLocales());
    }

    /**
     * Factory method for creating a {@link TypedGenerator} arbitrary {@link Serializable} Objects
     *
     * @return a {@link TypedGenerator} for all {@link Serializable}s
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static TypedGenerator<Serializable> serializables() {
        return new QuickCheckGeneratorAdapter(Serializable.class, PrimitiveGenerators.nonEmptyStrings());
    }

    /**
     * Factory method for creating a {@link TypedGenerator} arbitrary {@link RuntimeException}
     * Objects
     *
     * @return a {@link TypedGenerator} for all {@link RuntimeException}s
     */
    public static TypedGenerator<RuntimeException> runtimeExceptions() {
        return fixedValues(RuntimeException.class, new RuntimeException(), new IllegalArgumentException(),
                new IllegalStateException(), new NullPointerException());
    }

    /**
     * Factory method for creating a {@link TypedGenerator} arbitrary {@link Throwable}
     * Objects
     *
     * @return a {@link TypedGenerator} for all {@link Throwable}s
     */
    public static TypedGenerator<Throwable> throwables() {
        return fixedValues(Throwable.class, new RuntimeException(), new IllegalArgumentException(),
                new IllegalStateException(), new NullPointerException());
    }

    /**
     * Factory method for creating a {@link TypedGenerator} arbitrary {@link URL}s
     * Objects
     *
     * @return a {@link TypedGenerator} for all {@link URL}s
     */
    public static TypedGenerator<URL> urls() {
        return new URLGenerator();
    }

    /**
     * Factory method for creating a {@link TypedGenerator} from an existing QuickCheck
     * {@link Generator}. Note: This method is for internal use only and will be removed soon!!!
     *
     * @param qcGenerator to be wrapped
     * @param type of the value
     * @return a {@link TypedGenerator} for the given {@link Generator}
     */
    static <T> TypedGenerator<T> wrap(final Class<T> type, final Generator<T> qcGenerator) {
        return new QuickCheckGeneratorAdapter<>(type, qcGenerator);
    }

    /**
     * Factory method for creating a QuickCheck {@link Generator} from an existing
     * {@link TypedGenerator}. Note: This method is for internal
     * use only and will be removed soon!!!
     *
     * @param generator to be un-wrapped
     * @return a {@link TypedGenerator} for the given {@link Generator}
     */
    static <T> Generator<T> unwrap(final TypedGenerator<T> generator) {
        return generator::next;
    }

    /**
     * Helper method that determines the actual type of a given {@link Iterable} by peeking into it.
     * <em>For testing only, should never be used in productive code</em>
     *
     * @param iterable must not be null nor empty, the iterator must be reentrant.
     * @return The Class of the given {@link Iterable}.
     */
    @SuppressWarnings("unchecked")
    private static <T> Class<T> determineSupertypeFromIterable(final Iterable<T> iterable) {
        requireNonNull(iterable, "iterable must not be null");
        final var iterator = iterable.iterator();
        if (iterator.hasNext()) {
            return (Class<T>) iterator.next().getClass();
        }
        throw new IllegalArgumentException("Must contain at least a single element");
    }
}
