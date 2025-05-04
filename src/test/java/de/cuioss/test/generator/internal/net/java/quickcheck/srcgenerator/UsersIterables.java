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

import de.cuioss.test.generator.internal.net.java.quickcheck.collection.Pair;
import de.cuioss.test.generator.internal.net.java.quickcheck.collection.Triple;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables;

import java.util.List;

class UsersIterables {

    /**
     * See documentation of {@link Users#multipleTypeParameters}.
     */
    public static Iterable<Triple<Integer, Double, String>> someMultipleTypeParameters() {
        return Iterables
                .toIterable(Users.multipleTypeParameters());
    }

    /**
     * See documentation of {@link Users#primitives}.
     */
    public static Iterable<Integer> somePrimitives() {
        return Iterables
                .toIterable(Users.primitives());
    }

    /**
     * See documentation of {@link Users#generics}.
     */
    public static Iterable<List<Integer>> someGenerics(List<Integer> is) {
        return Iterables
                .toIterable(Users.generics(is));
    }

    /**
     * See documentation of {@link Users#noVarArgsArrays}.
     */
    public static Iterable<String> someNoVarArgsArrays(String[] a, String x) {
        return Iterables
                .toIterable(Users.noVarArgsArrays(a, x));
    }

    /**
     * See documentation of {@link Users#multipleTypeVariable}.
     */
    public static <A, B> Iterable<Pair<A, B>> someMultipleTypeVariable(
            A a, B b) {
        return Iterables
                .toIterable(Users.multipleTypeVariable(a, b));
    }

    /**
     * See documentation of {@link Users#primitiveSubtypes}.
     */
    public static Iterable<Integer> somePrimitiveSubtypes() {
        return Iterables
                .toIterable(Users.primitiveSubtypes());
    }

    /**
     * See documentation of {@link Users#bounds}.
     */
    public static <T extends Enum<T>> Iterable<T> someBounds(Class<T> enumClass) {
        return Iterables
                .toIterable(Users.bounds(enumClass));
    }

    /**
     * See documentation of {@link Users#complexSubtypes}.
     */
    public static Iterable<String> someComplexSubtypes() {
        return Iterables
                .toIterable(Users.complexSubtypes());
    }

    /**
     * See documentation of {@link Users#varArgsArrays}.
     */
    public static Iterable<String> someVarArgsArrays(String... a) {
        return Iterables
                .toIterable(Users.varArgsArrays(a));
    }

    /**
     * See documentation of {@link Users#parameters}.
     */
    public static Iterable<Integer> someParameters(int min, int max) {
        return Iterables
                .toIterable(Users.parameters(min, max));
    }
}
