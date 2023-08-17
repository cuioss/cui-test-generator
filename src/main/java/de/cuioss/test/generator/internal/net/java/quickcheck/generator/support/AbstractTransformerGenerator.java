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

import java.util.Objects;

import de.cuioss.test.generator.internal.net.java.quickcheck.Generator;

/**
 * {@link Generator} implementation that transforms input generator test cases
 * of type I to output test cases of type T.
 *
 * @param <I> input generator test case type
 * @param <T> output generator test case type
 *
 */
public abstract class AbstractTransformerGenerator<I, T> implements Generator<T> {

    private Generator<I> inputGenerator;

    protected AbstractTransformerGenerator(Generator<I> inputGenerator) {
        setInputGenerator(inputGenerator);
    }

    protected abstract T transform(Generator<I> inputGenerator);

    @Override
    public T next() {
        return transform(inputGenerator);
    }

    Generator<I> getInputGenerator() {
        return inputGenerator;
    }

    void setInputGenerator(Generator<I> inputGenerator) {
        Objects.requireNonNull(inputGenerator, "inputGenerator");
        this.inputGenerator = inputGenerator;
    }
}
