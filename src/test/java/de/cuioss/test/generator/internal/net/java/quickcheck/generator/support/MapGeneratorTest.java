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

import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators.DEFAULT_COLLECTION_MAX_SIZE;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGeneratorSamples.anyPositiveInteger;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.fixedValues;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.Map.Entry;

import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.internal.net.java.quickcheck.Generator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators;

class MapGeneratorTest {

    @Test
    void maps() {
        Generator<Map<String, Long>> maps = CombinedGenerators.maps(new StringGenerator(), new LongGenerator());
        var next = maps.next();
        assertTrue(next.size() <= DEFAULT_COLLECTION_MAX_SIZE);
        for (Entry<String, Long> e : next.entrySet()) {
            assertInstanceOf(String.class, e.getKey());
            assertInstanceOf(Long.class, e.getValue());
        }
    }

    @Test
    void mapsWithSize() {
        int size = anyPositiveInteger(DEFAULT_COLLECTION_MAX_SIZE);
        Generator<Map<String, Long>> maps = CombinedGenerators.maps(new StringGenerator(), new LongGenerator(),
                fixedValues(size));
        assertEquals(size, maps.next().size());
    }
}
