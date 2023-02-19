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
package io.cui.test.generator.internal.net.java.quickcheck.generator.support;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import io.cui.test.generator.internal.net.java.quickcheck.Generator;

public class StrictlyOrderedGenerator<T> implements Generator<List<T>> {

    private final Generator<Set<T>> values;
    private final Comparator<T> comparator;

    public StrictlyOrderedGenerator(Generator<T> input, Comparator<T> comparator, Generator<Integer> sizes) {
        Objects.requireNonNull(input, "input");
        Objects.requireNonNull(comparator, "comparator");
        Objects.requireNonNull(sizes, "sizes");
        values = new SetGenerator<>(input, sizes, SetGenerator.MAX_TRIES);
        this.comparator = comparator;
    }

    @Override
    public List<T> next() {
        ArrayList<T> v = new ArrayList<>(values.next());
        v.sort(comparator);
        return v;
    }
}
