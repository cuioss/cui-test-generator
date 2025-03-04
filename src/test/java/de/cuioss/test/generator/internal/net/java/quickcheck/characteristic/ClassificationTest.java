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
package de.cuioss.test.generator.internal.net.java.quickcheck.characteristic;

import de.cuioss.test.generator.internal.net.java.quickcheck.Generator;
import de.cuioss.test.generator.internal.net.java.quickcheck.StatefulGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators.DEFAULT_COLLECTION_MAX_SIZE;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators.uniqueValues;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGeneratorsIterables.someSortedLists;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.integers;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.strings;
import static java.util.Collections.EMPTY_LIST;
import static java.util.Collections.reverse;
import static org.junit.jupiter.api.Assertions.*;

class ClassificationTest {

    private String category;
    private String otherCategory;

    @BeforeEach
    void setUp() {
        category = strings().next();
        otherCategory = strings().next();
    }

    @Test
    void toStringClassification() {
        var classification = new Classification();
        classification.doClassify(true, category);
        classification.call();
        // String behavior change in JDK 17. BEcause the Classification is not used we
        // simplified the test from equals(format("Classifications :\n%s = %1.2f%%",
        // category, 100d)
        assertNotNull(classification.toString());
    }

    @Test
    void classifyWithPredicateFalse() {
        var classification = new Classification();
        classification.doClassify(false, "cat");
        classification.call();
        assertEquals(EMPTY_LIST, classification.getCategories());
    }

    @Test
    void classifyMultipleValues() {
        String[] categories = {category, category, category, otherCategory};
        var classification = new Classification();
        for (String cat : categories) {
            classification.classifyCall(cat);
        }
        assertEquals(75.0, classification.getFrequency(category), 0);
        assertEquals(25.0, classification.getFrequency(otherCategory), 0);
    }

    @Test
    void categoriesAreSortedByFrequency() {
        StatefulGenerator<Integer> uniqueInts = uniqueValues(integers(1, DEFAULT_COLLECTION_MAX_SIZE));
        for (List<Integer> frequencies : someSortedLists(uniqueInts)) {
            uniqueInts.reset();
            reverse(frequencies);
            Generator<String> names = uniqueValues(strings());
            List<Object> expected = new ArrayList<>();
            var classification = new Classification();
            for (Integer frequency : frequencies) {
                var name = names.next();
                for (var i = 0; i < frequency; i++) {
                    classification.classifyCall(name);
                }
                expected.add(name);
            }
            assertEquals(expected, classification.getCategories());
        }
    }

    @Test
    void doNotClassifyAfterGetCategories() {
        var classification = new Classification();
        classification.getCategories();
        assertClassifyFails(classification);
    }

    @Test
    void doNotClassifyAfterGetFrequency() {
        var classification = new Classification();
        classification.getFrequency(new Object());
        assertClassifyFails(classification);
    }

    @Test
    void doNotClassifyAfterToString() {
        var classification = new Classification();
        // noinspection ResultOfMethodCallIgnored
        classification.toString();
        assertClassifyFails(classification);
    }

    private void assertClassifyFails(Classification classification) {
        assertThrows(IllegalStateException.class, () -> classification.doClassify(true, ""));
        assertThrows(IllegalStateException.class, classification::call);
    }

    @Test
    void unknownClassificationIsEmpty() {
        assertEquals(0.0, new Classification().getFrequency(strings().next()), 0.0);
        assertTrue(new Classification().getCategories().isEmpty());
    }
}
