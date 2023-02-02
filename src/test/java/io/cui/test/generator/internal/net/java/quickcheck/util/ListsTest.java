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
package io.cui.test.generator.internal.net.java.quickcheck.util;

import static io.cui.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators.lists;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.objects;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables;

class ListsTest {

    @Test
    void toList() {
        for (List<Object> ls : Iterables.toIterable(lists(objects()))) {
            assertEquals(ls, Lists.toList(ls));
        }
    }

    @Test
    void toListFromIterable() {
        for (final List<Object> ls : Iterables.toIterable(lists(objects()))) {
            List<Object> actual = Lists.toList(ls);
            assertEquals(ls, actual);
        }
    }
}
