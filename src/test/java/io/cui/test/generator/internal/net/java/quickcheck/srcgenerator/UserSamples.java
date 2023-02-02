package io.cui.test.generator.internal.net.java.quickcheck.srcgenerator;

class UserSamples {

    /**
     * See documentation of {@link Users#multipleTypeParameters}.
     */
    public static io.cui.test.generator.internal.net.java.quickcheck.collection.Triple<Integer, Double, String> anyMultipleTypeParameter() {
        return Users.multipleTypeParameters().next();
    }

    /**
     * See documentation of {@link Users#primitives}.
     */
    public static Integer anyPrimitive() {
        return Users.primitives().next();
    }

    /**
     * See documentation of {@link Users#generics}.
     */
    public static java.util.List<Integer> anyGeneric(java.util.List<Integer> is) {
        return Users.generics(is).next();
    }

    /**
     * See documentation of {@link Users#noVarArgsArrays}.
     */
    public static String anyNoVarArgsArray(String[] a, String x) {
        return Users.noVarArgsArrays(a, x).next();
    }

    /**
     * See documentation of {@link Users#multipleTypeVariable}.
     */
    public static <A, B> io.cui.test.generator.internal.net.java.quickcheck.collection.Pair<A, B> anyMultipleTypeVariable(A a, B b) {
        return Users.multipleTypeVariable(a, b).next();
    }

    /**
     * See documentation of {@link Users#primitiveSubtypes}.
     */
    public static Integer anyPrimitiveSubtype() {
        return Users.primitiveSubtypes().next();
    }

    /**
     * See documentation of {@link Users#bounds}.
     */
    public static <T extends Enum<T>> T anyBound(Class<T> enumClass) {
        return Users.bounds(enumClass).next();
    }

    /**
     * See documentation of {@link Users#complexSubtypes}.
     */
    public static String anyComplexSubtype() {
        return Users.complexSubtypes().next();
    }

    /**
     * See documentation of {@link Users#varArgsArrays}.
     */
    public static String anyVarArgsArray(String... a) {
        return Users.varArgsArrays(a).next();
    }

    /**
     * See documentation of {@link Users#parameters}.
     */
    public static Integer anyParameter(int min, int max) {
        return Users.parameters(min, max).next();
    }
}
