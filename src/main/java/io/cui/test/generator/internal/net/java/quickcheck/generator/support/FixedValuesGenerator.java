/*
 *  Licensed to the author under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.cui.test.generator.internal.net.java.quickcheck.generator.support;

import static io.cui.tools.collect.CollectionLiterals.mutableList;
import static io.cui.tools.collect.MoreCollections.requireNotEmpty;

import java.util.Collections;
import java.util.List;

import io.cui.test.generator.internal.net.java.quickcheck.Generator;

public class FixedValuesGenerator<T> implements Generator<T> {

    private final List<T> values;
    private final IntegerGenerator index;

    public FixedValuesGenerator() {
        this(Collections.singleton(null));
    }

    public FixedValuesGenerator(T value) {
        this(Collections.singleton(value));
    }

    public FixedValuesGenerator(Iterable<T> values) {
        requireNotEmpty(values, "values");

        this.values = mutableList(values);
        this.index = new IntegerGenerator(0, this.values.size() - 1);
    }

    @Override
    public T next() {
        return this.values.get(this.index.nextInt());
    }
}
