package io.cui.test.generator.internal.net.java.quickcheck.generator;

@SuppressWarnings("unused")
public class PrimitiveGeneratorSamples {

    /**
     * See documentation of {@link PrimitiveGenerators#objects}.
     */
    public static Object anyObject() {
        return PrimitiveGenerators.objects().next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#objects}.
     */
    public static <T> T anyObject(Class<T> objectType) {
        return PrimitiveGenerators.objects(objectType).next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#nulls}.
     */
    public static <T> T anyNull() {
        return PrimitiveGenerators.<T> nulls().next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#substrings}.
     */
    public static String anySubstring(String base) {
        return PrimitiveGenerators.substrings(base).next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#substrings}.
     */
    public static String anySubstring(String base, int size) {
        return PrimitiveGenerators.substrings(base, size).next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#substrings}.
     */
    public static String anySubstring(String base, int minSize, int maxSize) {
        return PrimitiveGenerators.substrings(base, minSize, maxSize).next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#integers}.
     */
    public static Integer anyInteger() {
        return PrimitiveGenerators.integers().next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#integers}.
     */
    public static Integer anyInteger(int low) {
        return PrimitiveGenerators.integers(low).next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#integers}.
     */
    public static Integer anyInteger(int lo, int hi) {
        return PrimitiveGenerators.integers(lo, hi).next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#integers}.
     */
    public static Integer anyInteger(int lo, int hi,
            io.cui.test.generator.internal.net.java.quickcheck.generator.distribution.Distribution distribution) {
        return PrimitiveGenerators.integers(lo, hi, distribution).next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#positiveLongs}.
     */
    public static Long anyPositiveLong() {
        return PrimitiveGenerators.positiveLongs().next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#positiveLongs}.
     */
    public static Long anyPositiveLong(long hi) {
        return PrimitiveGenerators.positiveLongs(hi).next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#defaultObjects}.
     */
    public static <T> T anyDefaultObject(Class<T> objectType) {
        return PrimitiveGenerators.defaultObjects(objectType).next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#enumValues}.
     */
    public static <T extends Enum<T>> T anyEnumValue(Class<T> enumClass) {
        return PrimitiveGenerators.enumValues(enumClass).next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#enumValues}.
     */
    @SafeVarargs
    public static <T extends Enum<T>> T anyEnumValue(Class<T> enumClass, T... excluded) {
        return PrimitiveGenerators.enumValues(enumClass, excluded).next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#enumValues}.
     */
    public static <T extends Enum<T>> T anyEnumValue(Class<T> enumClass, Iterable<T> excludedValues) {
        return PrimitiveGenerators.enumValues(enumClass, excludedValues).next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#printableStrings}.
     */
    public static String anyPrintableString() {
        return PrimitiveGenerators.printableStrings().next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#dates}.
     */
    public static java.util.Date anyDate() {
        return PrimitiveGenerators.dates().next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#dates}.
     */
    public static java.util.Date anyDate(java.util.concurrent.TimeUnit precision) {
        return PrimitiveGenerators.dates(precision).next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#dates}.
     */
    public static java.util.Date anyDate(java.util.Date low, java.util.Date high) {
        return PrimitiveGenerators.dates(low, high).next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#dates}.
     */
    public static java.util.Date anyDate(long low, long high) {
        return PrimitiveGenerators.dates(low, high).next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#dates}.
     */
    public static java.util.Date anyDate(Long low, Long high, java.util.concurrent.TimeUnit precision) {
        return PrimitiveGenerators.dates(low, high, precision).next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#latin1SupplementCharacters}.
     */
    public static Character anyLatin1SupplementCharacter() {
        return PrimitiveGenerators.latin1SupplementCharacters().next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#doubles}.
     */
    public static Double anyDouble() {
        return PrimitiveGenerators.doubles().next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#doubles}.
     */
    public static Double anyDouble(double lo, double hi) {
        return PrimitiveGenerators.doubles(lo, hi).next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#doubles}.
     */
    public static Double anyDouble(double lo, double hi,
            io.cui.test.generator.internal.net.java.quickcheck.generator.distribution.Distribution distribution) {
        return PrimitiveGenerators.doubles(lo, hi, distribution).next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#positiveIntegers}.
     */
    public static Integer anyPositiveInteger() {
        return PrimitiveGenerators.positiveIntegers().next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#positiveIntegers}.
     */
    public static Integer anyPositiveInteger(int hi) {
        return PrimitiveGenerators.positiveIntegers(hi).next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#nonEmptyStrings}.
     */
    public static String anyNonEmptyString() {
        return PrimitiveGenerators.nonEmptyStrings().next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#characters}.
     */
    public static Character anyCharacter(char lo, char hi) {
        return PrimitiveGenerators.characters(lo, hi).next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#characters}.
     */
    public static Character anyCharacter() {
        return PrimitiveGenerators.characters().next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#characters}.
     */
    public static Character anyCharacter(Character... chars) {
        return PrimitiveGenerators.characters(chars).next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#characters}.
     */
    public static Character anyCharacter(String string) {
        return PrimitiveGenerators.characters(string).next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#characters}.
     */
    public static Character anyCharacter(Iterable<Character> chars) {
        return PrimitiveGenerators.characters(chars).next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#longs}.
     */
    public static Long anyLong() {
        return PrimitiveGenerators.longs().next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#longs}.
     */
    public static Long anyLong(long lo, long hi) {
        return PrimitiveGenerators.longs(lo, hi).next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#longs}.
     */
    public static Long anyLong(long lo, long hi,
            io.cui.test.generator.internal.net.java.quickcheck.generator.distribution.Distribution distribution) {
        return PrimitiveGenerators.longs(lo, hi, distribution).next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#strings}.
     */
    public static String anyString() {
        return PrimitiveGenerators.strings().next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#strings}.
     */
    public static String anyString(char lo, char hi) {
        return PrimitiveGenerators.strings(lo, hi).next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#strings}.
     */
    public static String anyString(String allowedCharacters) {
        return PrimitiveGenerators.strings(allowedCharacters).next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#strings}.
     */
    public static String anyString(String allowedCharacters, int min, int max) {
        return PrimitiveGenerators.strings(allowedCharacters, min, max).next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#strings}.
     */
    public static String anyString(int max) {
        return PrimitiveGenerators.strings(max).next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#strings}.
     */
    public static String anyString(int min, int max) {
        return PrimitiveGenerators.strings(min, max).next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#strings}.
     */
    public static String anyString(io.cui.test.generator.internal.net.java.quickcheck.Generator<Integer> length,
            io.cui.test.generator.internal.net.java.quickcheck.Generator<Character> characters) {
        return PrimitiveGenerators.strings(length, characters).next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#strings}.
     */
    public static String anyString(
            io.cui.test.generator.internal.net.java.quickcheck.Generator<Character> characterGenerator) {
        return PrimitiveGenerators.strings(characterGenerator).next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#fixedValues}.
     */
    public static <T> T anyFixedValue(T value) {
        return PrimitiveGenerators.fixedValues(value).next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#fixedValues}.
     */
    @SafeVarargs
    public static <T> T anyFixedValue(T... values) {
        return PrimitiveGenerators.fixedValues(values).next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#fixedValues}.
     */
    public static <T> T anyFixedValue(Iterable<T> values) {
        return PrimitiveGenerators.fixedValues(values).next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#clonedValues}.
     */
    public static <T> T anyClonedValue(T prototype) {
        return PrimitiveGenerators.clonedValues(prototype).next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#booleans}.
     */
    public static Boolean anyBoolean() {
        return PrimitiveGenerators.booleans().next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#basicLatinCharacters}.
     */
    public static Character anyBasicLatinCharacter() {
        return PrimitiveGenerators.basicLatinCharacters().next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#letterStrings}.
     */
    public static String anyLetterString() {
        return PrimitiveGenerators.letterStrings().next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#letterStrings}.
     */
    public static String anyLetterString(int min, int max) {
        return PrimitiveGenerators.letterStrings(min, max).next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#bytes}.
     */
    public static Byte anyByte() {
        return PrimitiveGenerators.bytes().next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#bytes}.
     */
    public static Byte anyByte(byte lo, byte hi) {
        return PrimitiveGenerators.bytes(lo, hi).next();
    }

    /**
     * See documentation of {@link PrimitiveGenerators#bytes}.
     */
    public static Byte anyByte(byte lo, byte hi,
            io.cui.test.generator.internal.net.java.quickcheck.generator.distribution.Distribution distribution) {
        return PrimitiveGenerators.bytes(lo, hi, distribution).next();
    }
}
