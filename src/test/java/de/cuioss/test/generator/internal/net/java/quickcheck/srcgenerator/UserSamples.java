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
package de.cuioss.test.generator.internal.net.java.quickcheck.srcgenerator;

import de.cuioss.test.generator.internal.net.java.quickcheck.collection.Pair;
import de.cuioss.test.generator.internal.net.java.quickcheck.collection.Triple;

import java.util.List;

class UserSamples {

    /**
     * See documentation of {@link Users#multipleTypeParameters}.
     */
    public static Triple<Integer, Double, String> anyMultipleTypeParameter() {
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
    public static List<Integer> anyGeneric(List<Integer> is) {
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
    public static <A, B> Pair<A, B> anyMultipleTypeVariable(
            A a, B b) {
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
