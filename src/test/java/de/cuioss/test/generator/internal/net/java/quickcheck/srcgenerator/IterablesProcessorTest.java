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
package de.cuioss.test.generator.internal.net.java.quickcheck.srcgenerator;

import de.cuioss.test.generator.internal.net.java.quickcheck.collection.Pair;
import org.junit.jupiter.api.Test;

import javax.tools.Diagnostic.Kind;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IterablesProcessorTest {

    @Test
    void primitive() {
        assertIteratorReturnType(Integer.class, UsersIterables.somePrimitives());
    }

    @Test
    void primitiveSubtype() {
        assertIteratorReturnType(Integer.class, UsersIterables.somePrimitiveSubtypes());
    }

    @Test
    void complexSubtype() {
        assertIteratorReturnType(String.class, UsersIterables.someComplexSubtypes());
    }

    @Test
    void parameters() {
        assertIteratorReturnType(Integer.class, UsersIterables.someParameters(1, 2));
    }

    @Test
    void bounds() {
        assertIteratorReturnType(Kind.class, UsersIterables.someBounds(Kind.class));
    }

    @Test
    void multipleTypeParameter() {
        var actual = UsersIterables.someMultipleTypeParameters().iterator().next();
        assertEquals(Integer.class, actual.getFirst().getClass());
        assertEquals(Double.class, actual.getSecond().getClass());
        assertEquals(String.class, actual.getThird().getClass());
    }

    @Test
    void generics() {
        List<Integer> in = List.of(1);
        var is = UsersIterables.someGenerics(in).iterator().next();
        assertEquals(in, is);
    }

    @Test
    void multipleTypeVariables() {
        var next = Users.multipleTypeVariable(1, "two").next();
        assertEquals(new Pair<>(1, "two"), next);
    }

    private <T> void assertIteratorReturnType(Class<T> expectedType, Iterable<T> iterable) {
        assertEquals(expectedType, iterable.iterator().next().getClass());
    }
}
