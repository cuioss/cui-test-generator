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
package de.cuioss.test.generator.internal.net.java.quickcheck.generator.support;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.internal.net.java.quickcheck.MockFactory;

class TupleGeneratorTest {

    @Test
    void it() {
        var generator1 = MockFactory.createObjectGenerator();
        var generator2 = MockFactory.createObjectGenerator();
        var return1 = new Object();
        var return2 = new Object();
        expect(generator1.next()).andReturn(return1);
        expect(generator2.next()).andReturn(return2);
        replay(generator1, generator2);
        var inhomogeneousGenerator = new TupleGenerator(generator1, generator2);
        var next = inhomogeneousGenerator.next();
        verify(generator1, generator2);
        assertEquals(2, next.length);
        assertSame(return1, next[0]);
        assertSame(return2, next[1]);
    }
}
