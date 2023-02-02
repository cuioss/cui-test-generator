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

import static java.lang.Math.max;

import java.util.ArrayList;
import java.util.List;

import io.cui.test.generator.internal.net.java.quickcheck.Generator;
import io.cui.test.generator.internal.net.java.quickcheck.util.Assert;

public class ListGenerator<T> implements Generator<List<T>> {

    public static final int MIN_SIZE = 0;
    // why call the default size max_size if this size does not limit the upper
    // bound for all lists? ListGenerator(Generator, int int) does not define
    // any limit on max, so i think max_size does not reflect what the value
    // is used for. previously it was named default_size but should better
    // be named like default_max_size
    public static final int MAX_SIZE = 10;

    private final Generator<? extends T> content;
    private final Generator<Integer> size;

    public ListGenerator(Generator<? extends T> content) {
        this(content, MIN_SIZE, MAX_SIZE);
    }

    public ListGenerator(Generator<? extends T> content, int min, int max) {
        this(content, new IntegerGenerator(min, max));
    }

    public ListGenerator(Generator<? extends T> content, Generator<Integer> size) {
        Assert.notNull(content, "content");
        Assert.notNull(size, "size");

        this.content = content;
        this.size = size;
    }

    @Override
    public List<T> next() {
        int size = max(MIN_SIZE, this.size.next());
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++)
            list.add(this.content.next());
        return list;
    }
}
