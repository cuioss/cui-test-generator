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

import static io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.sizeOf;
import static io.cui.tools.base.Preconditions.checkArgument;

import java.util.Iterator;
import java.util.Objects;

import io.cui.test.generator.internal.net.java.quickcheck.Generator;
import io.cui.test.generator.internal.net.java.quickcheck.StatefulGenerator;

public class EnsuredValuesGenerator<T> implements StatefulGenerator<T> {

    private final Iterable<T> ensured;
    private final Generator<T> otherValues;
    private Iterator<T> iterator;
    private final int size;
    private final int window;
    private int valuesLeft;
    private int generatesLeft;

    public EnsuredValuesGenerator(Iterable<T> values) {
        this(values, new FixedValuesGenerator<>(values));
    }

    public EnsuredValuesGenerator(Iterable<T> ensured, Generator<T> random) {
        this(ensured, sizeOf(ensured), random);
    }

    public EnsuredValuesGenerator(Iterable<T> ensured, int window, Generator<T> random) {
        this.size = sizeOf(ensured);
        checkArgument(this.size <= window, "window");
        this.window = window;
        Objects.requireNonNull(random, "random");
        this.ensured = ensured;
        this.otherValues = random;

        reset();
    }

    @Override
    public T next() {
        return takeEnsured() ? iterator.next() : otherValues.next();
    }

    private boolean takeEnsured() {
        if (valuesLeft > 0 && spreadOverWindow()) {
            valuesLeft--;
            return true;
        }
        generatesLeft--;
        return false;
    }

    /**
     * @return true if an ensured values should be taken. The results should be
     *         such that the ensured values are uniformly distributed over the
     *         window.
     */
    private boolean spreadOverWindow() {
        assert valuesLeft > 0 && generatesLeft >= 0;
        return new IntegerGenerator(0, valuesLeft + generatesLeft).next() <= valuesLeft;
    }

    @Override
    public void reset() {
        iterator = ensured.iterator();
        valuesLeft = size;
        generatesLeft = window - size;
        assert generatesLeft >= 0 : generatesLeft;
    }
}
