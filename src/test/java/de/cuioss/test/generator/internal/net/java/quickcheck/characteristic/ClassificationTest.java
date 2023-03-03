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

import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators.DEFAULT_COLLECTION_MAX_SIZE;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators.uniqueValues;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGeneratorsIterables.someSortedLists;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.integers;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.strings;
import static java.lang.String.format;
import static java.util.Collections.EMPTY_LIST;
import static java.util.Collections.reverse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.internal.net.java.quickcheck.Generator;
import de.cuioss.test.generator.internal.net.java.quickcheck.StatefulGenerator;

class ClassificationTest {

    private String category;
    private String otherCategory;

    @BeforeEach
    void setUp() {
        category = strings().next();
        otherCategory = strings().next();
    }

    @Test
    void testToStringClassification() {
        var classification = new Classification();
        classification.doClassify(true, category);
        classification.call();
        assertEquals(format("Classifications :\n%s = %1.2f%%", category, 100d), classification.toString());
    }

    @Test
    void testClassifyWithPredicateFalse() {
        var classification = new Classification();
        classification.doClassify(false, "cat");
        classification.call();
        assertEquals(EMPTY_LIST, classification.getCategories());
    }

    @Test
    void testClassifyMultipleValues() {
        String[] categories = { category, category, category, otherCategory };
        var classification = new Classification();
        for (String cat : categories) {
            classification.classifyCall(cat);
        }
        assertEquals(75.0, classification.getFrequency(category), 0);
        assertEquals(25.0, classification.getFrequency(otherCategory), 0);
    }

    @Test
    void testCategoriesAreSortedByFrequency() {
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
    void testDoNotClassifyAfterGetCategories() {
        var classification = new Classification();
        classification.getCategories();
        assertClassifyFails(classification);
    }

    @Test
    void testDoNotClassifyAfterGetFrequency() {
        var classification = new Classification();
        classification.getFrequency(new Object());
        assertClassifyFails(classification);
    }

    @Test
    void testDoNotClassifyAfterToString() {
        var classification = new Classification();
        // noinspection ResultOfMethodCallIgnored
        classification.toString();
        assertClassifyFails(classification);
    }

    private void assertClassifyFails(Classification classification) {
        assertThrows(IllegalStateException.class,
                () -> classification.doClassify(true, ""));
        assertThrows(IllegalStateException.class, classification::call);
    }

    @Test
    void testUnknownClassificationIsEmpty() {
        assertEquals(0.0, new Classification().getFrequency(strings().next()), 0.0);
        assertTrue(new Classification().getCategories().isEmpty());
    }
}
