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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;
import java.util.List;

import javax.tools.Diagnostic.Kind;

import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators;

class SamplesProcessorTest {

    @Test
    void primitive() {
        assertNotNull(UserSamples.anyPrimitive());
        assertEquals(Integer.class, UserSamples.anyPrimitive().getClass());
    }

    @Test
    void primitiveSubtype() {
        assertNotNull(UserSamples.anyPrimitiveSubtype());
        assertEquals(Integer.class, UserSamples.anyPrimitiveSubtype().getClass());
    }

    @Test
    void complexSubtype() {
        var anyComplexSubtype = UserSamples.anyComplexSubtype();
        assertEquals(String.class, anyComplexSubtype.getClass());
    }

    @Test
    void parameters() {
        var parameter = UserSamples.anyParameter(1, 2);
        assertEquals(Integer.class, parameter.getClass());
    }

    @Test
    void bounds() {
        var bounded = UserSamples.anyBound(Kind.class);
        assertEquals(Kind.class, bounded.getClass());
    }

    @Test
    void multipleTypeParameter() {
        var actual = UserSamples.anyMultipleTypeParameter();
        assertEquals(Integer.class, actual.getFirst().getClass());
        assertEquals(Double.class, actual.getSecond().getClass());
        assertEquals(String.class, actual.getThird().getClass());
    }

    @Test
    void generics() {
        List<Integer> in = Collections.singletonList(1);
        var is = UserSamples.anyGeneric(in);
        assertEquals(in, is);
    }

    @Test
    void varArgsArray() {
        var s = PrimitiveGenerators.strings().next();
        assertEquals(s, UserSamples.anyVarArgsArray(s));
    }

    @Test
    void noVarArgsArray() {
        var s = PrimitiveGenerators.strings().next();
        assertEquals(s, UserSamples.anyNoVarArgsArray(new String[] { s }, "x"));
    }

}
