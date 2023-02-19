package io.cui.test.generator.internal.net.java.quickcheck.srcgenerator;

class UsersIterables {

    /**
     * See documentation of {@link Users#multipleTypeParameters}.
     */
    public static Iterable<io.cui.test.generator.internal.net.java.quickcheck.collection.Triple<Integer, Double, String>> someMultipleTypeParameters() {
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(Users.multipleTypeParameters());
    }

    /**
     * See documentation of {@link Users#primitives}.
     */
    public static Iterable<Integer> somePrimitives() {
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(Users.primitives());
    }

    /**
     * See documentation of {@link Users#generics}.
     */
    public static Iterable<java.util.List<Integer>> someGenerics(java.util.List<Integer> is) {
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(Users.generics(is));
    }

    /**
     * See documentation of {@link Users#noVarArgsArrays}.
     */
    public static Iterable<String> someNoVarArgsArrays(String[] a, String x) {
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                Users.noVarArgsArrays(a, x));
    }

    /**
     * See documentation of {@link Users#multipleTypeVariable}.
     */
    public static <A, B> Iterable<io.cui.test.generator.internal.net.java.quickcheck.collection.Pair<A, B>> someMultipleTypeVariable(
            A a, B b) {
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable(
                Users.multipleTypeVariable(a, b));
    }

    /**
     * See documentation of {@link Users#primitiveSubtypes}.
     */
    public static Iterable<Integer> somePrimitiveSubtypes() {
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(Users.primitiveSubtypes());
    }

    /**
     * See documentation of {@link Users#bounds}.
     */
    public static <T extends Enum<T>> Iterable<T> someBounds(Class<T> enumClass) {
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(Users.bounds(enumClass));
    }

    /**
     * See documentation of {@link Users#complexSubtypes}.
     */
    public static Iterable<String> someComplexSubtypes() {
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(Users.complexSubtypes());
    }

    /**
     * See documentation of {@link Users#varArgsArrays}.
     */
    public static Iterable<String> someVarArgsArrays(String... a) {
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(Users.varArgsArrays(a));
    }

    /**
     * See documentation of {@link Users#parameters}.
     */
    public static Iterable<Integer> someParameters(int min, int max) {
        return io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(Users.parameters(min, max));
    }
}
