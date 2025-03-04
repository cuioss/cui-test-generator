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

import de.cuioss.test.generator.internal.net.java.quickcheck.Generator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators.ensureValues;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RoundRobinGeneratorTest {

    @Test
    void empty() {
        List<Generator<Object>> emptyList = Collections.emptyList();

        assertThrows(IllegalArgumentException.class, () -> new RoundRobinGenerator<>(emptyList));
    }

    @Test
    void generateRoundRobin() {

        List<Generator<Integer>> generators = new ArrayList<>();
        generators.add(ensureValues(asList(1, 2)));
        generators.add(ensureValues(asList(11, 12)));
        var roundRobinGenerator = new RoundRobinGenerator<>(generators);
        List<Integer> actual = new ArrayList<>();
        for (var i = 0; i < 4; i++) {
            actual.add(roundRobinGenerator.next());
        }
        assertEquals(Arrays.asList(1, 11, 2, 12), actual);
    }
}
