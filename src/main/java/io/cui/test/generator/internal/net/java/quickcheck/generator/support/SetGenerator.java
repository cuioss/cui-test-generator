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

import java.util.HashSet;
import java.util.Set;

import io.cui.test.generator.internal.net.java.quickcheck.Generator;
import io.cui.test.generator.internal.net.java.quickcheck.StatefulGenerator;

public class SetGenerator<T> implements Generator<Set<T>> {

    public static final int MAX_SIZE = ListGenerator.MAX_SIZE;
    public static final int MAX_TRIES = VetoableGenerator.DEFAULT_MAX_TRIES;
    private final ListGenerator<T> lists;
    private final StatefulGenerator<T> content;

    public SetGenerator(Generator<? extends T> content) {
        this(content, MAX_TRIES);
    }

    public SetGenerator(Generator<? extends T> content, int tries) {
        this(content, new IntegerGenerator(0, MAX_SIZE), tries);
    }

    public SetGenerator(Generator<? extends T> content, Generator<Integer> size, int tries) {
        this.content = new UniqueValuesGenerator<>(content, tries);
        this.lists = new ListGenerator<>(this.content, size);
    }

    @Override
    public Set<T> next() {
        content.reset();
        return new HashSet<>(lists.next());
    }
}
