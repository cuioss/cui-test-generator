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

import java.lang.reflect.Array;
import java.util.List;
import java.util.Objects;

import de.cuioss.test.generator.internal.net.java.quickcheck.Generator;

public class ArrayGenerator<T> extends
        AbstractTransformerGenerator<List<T>, T[]> {

    public static final int MAX_SIZE = ListGenerator.MAX_SIZE;
    public static final int MIN_SIZE = ListGenerator.MIN_SIZE;

    private T[] emptyArrayOfT;

    public ArrayGenerator(Generator<? extends T> content, Class<T> type) {
        super(new ListGenerator<>(content));
        setEmptyArrayOfT(type);
    }

    public ArrayGenerator(Generator<? extends T> content, Generator<Integer> size,
            Class<T> type) {
        super(new ListGenerator<>(content, size));
        setEmptyArrayOfT(type);
    }

    private void setEmptyArrayOfT(Class<T> type) {
        Objects.requireNonNull(type, "content type");
        this.emptyArrayOfT = emptyArrayOfT(type);
    }

    @Override
    protected T[] transform(Generator<List<T>> inputGenerator) {
        return inputGenerator.next().toArray(emptyArrayOfT);
    }

    @SuppressWarnings("unchecked")
    private T[] emptyArrayOfT(Class<T> type) {
        return (T[]) Array.newInstance(type, 0);
    }
}
