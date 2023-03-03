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
package de.cuioss.test.generator.internal.net.java.quickcheck.examples;

import static de.cuioss.test.generator.internal.net.java.quickcheck.QuickCheck.forAll;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators.lists;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators.pairs;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.integers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.internal.net.java.quickcheck.characteristic.AbstractCharacteristic;
import de.cuioss.test.generator.internal.net.java.quickcheck.collection.Pair;

class JavaCollectionContractTest {

    @Test
    void collectionAdd() {
        forAll(pairs(integers(), lists(integers())),
                new AbstractCharacteristic<>() {

                    @Override
                    protected void doSpecify(Pair<Integer, List<Integer>> any) {
                        var element = any.getFirst();
                        Collection<Integer> collection = any.getSecond();

                        var changedCollection = false;
                        var exceptionThrown = false;
                        try {
                            changedCollection = collection.add(element);
                        } catch (Exception e) {
                            assertException(e);
                            exceptionThrown = true;
                        }
                        assertNotEquals(collection.contains(element), exceptionThrown);
                        assertEquals(changedCollection, containsInstance(
                                collection, element));
                    }
                });
    }

    private void assertException(Exception e) {
        assertTrue(e instanceof UnsupportedOperationException
                || e instanceof ClassCastException
                || e instanceof IllegalArgumentException
                || e instanceof IllegalStateException);
    }

    private boolean containsInstance(Collection<?> collection, Object element) {
        for (Object e : collection) {
            if (e == element) {
                return true;
            }
        }
        return false;
    }
}
