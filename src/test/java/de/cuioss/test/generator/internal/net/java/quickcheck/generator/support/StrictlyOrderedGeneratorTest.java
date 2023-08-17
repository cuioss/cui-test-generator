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

import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.bytes;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.integers;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators;

class StrictlyOrderedGeneratorTest {

    @Test
    void ordered() {
        assertIsStrictlyOrder(CombinedGenerators.strictlyOrdered(bytes()).next());
    }

    @Test
    void orderedSized() {
        var lowHigh = SizeGenerator.anyMinMax();
        int low = lowHigh.get(0);
        int high = lowHigh.get(1);
        var actual = CombinedGenerators.strictlyOrdered(integers(), low, high).next();
        assertIsStrictlyOrder(actual);
        assertTrue(low <= actual.size());
        assertTrue(high >= actual.size());
    }

    @Test
    void orderedComparator() {
        var actual = CombinedGenerators.strictlyOrdered(bytes(), Collections.reverseOrder()).next();
        Collections.reverse(actual);
        assertIsStrictlyOrder(actual);
    }

    private <T extends Comparable<T>> void assertIsStrictlyOrder(List<T> l) {
        for (var i = 1; i < l.size(); i++) {
            assertTrue(l.get(i - 1).compareTo(l.get(i)) < 0, l.toString());
        }
    }
}
