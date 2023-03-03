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

import java.util.Collections;
import java.util.List;

import de.cuioss.test.generator.internal.net.java.quickcheck.Generator;

public class SortedListGenerator<T extends Comparable<T>> implements
        Generator<List<T>> {

    private final ListGenerator<T> listGenerator;

    public SortedListGenerator(Generator<T> input) {
        listGenerator = new ListGenerator<>(input);
    }

    public SortedListGenerator(Generator<T> input, Generator<Integer> size) {
        listGenerator = new ListGenerator<>(input, size);
    }

    @Override
    public List<T> next() {
        List<T> next = listGenerator.next();
        Collections.sort(next);
        return next;
    }
}
