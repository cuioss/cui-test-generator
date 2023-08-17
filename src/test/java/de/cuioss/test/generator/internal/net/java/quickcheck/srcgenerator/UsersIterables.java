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
package de.cuioss.test.generator.internal.net.java.quickcheck.srcgenerator;

class UsersIterables {

    /**
     * See documentation of {@link Users#multipleTypeParameters}.
     */
    public static Iterable<de.cuioss.test.generator.internal.net.java.quickcheck.collection.Triple<Integer, Double, String>> someMultipleTypeParameters() {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(Users.multipleTypeParameters());
    }

    /**
     * See documentation of {@link Users#primitives}.
     */
    public static Iterable<Integer> somePrimitives() {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(Users.primitives());
    }

    /**
     * See documentation of {@link Users#generics}.
     */
    public static Iterable<java.util.List<Integer>> someGenerics(java.util.List<Integer> is) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(Users.generics(is));
    }

    /**
     * See documentation of {@link Users#noVarArgsArrays}.
     */
    public static Iterable<String> someNoVarArgsArrays(String[] a, String x) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(Users.noVarArgsArrays(a, x));
    }

    /**
     * See documentation of {@link Users#multipleTypeVariable}.
     */
    public static <A, B> Iterable<de.cuioss.test.generator.internal.net.java.quickcheck.collection.Pair<A, B>> someMultipleTypeVariable(
            A a, B b) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(Users.multipleTypeVariable(a, b));
    }

    /**
     * See documentation of {@link Users#primitiveSubtypes}.
     */
    public static Iterable<Integer> somePrimitiveSubtypes() {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(Users.primitiveSubtypes());
    }

    /**
     * See documentation of {@link Users#bounds}.
     */
    public static <T extends Enum<T>> Iterable<T> someBounds(Class<T> enumClass) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(Users.bounds(enumClass));
    }

    /**
     * See documentation of {@link Users#complexSubtypes}.
     */
    public static Iterable<String> someComplexSubtypes() {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(Users.complexSubtypes());
    }

    /**
     * See documentation of {@link Users#varArgsArrays}.
     */
    public static Iterable<String> someVarArgsArrays(String... a) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(Users.varArgsArrays(a));
    }

    /**
     * See documentation of {@link Users#parameters}.
     */
    public static Iterable<Integer> someParameters(int min, int max) {
        return de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables
                .toIterable(Users.parameters(min, max));
    }
}
