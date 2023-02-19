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
package io.cui.test.generator.internal.net.java.quickcheck.generator;

import io.cui.test.generator.internal.net.java.quickcheck.Generator;
import io.cui.test.generator.internal.net.java.quickcheck.generator.support.AbstractTransformerGenerator;

/**
 * <p>
 * Generator based on mutation of prototype objects with a arbitrary generated
 * mutation values.
 * </p>
 *
 * <p>
 * Concrete implementation of this abstract class have to implement the
 * {@link MutationGenerator#mutate(Object, Object)} method.
 * </p>
 *
 * @param <T>
 *            Type of the generated value and prototype.
 * @param <M>
 *            Type of the mutation value.
 *
 */
public abstract class MutationGenerator<T, M> extends
        AbstractTransformerGenerator<T, T> {

    private final Generator<M> mutationValueGenerator;

    public MutationGenerator(Generator<T> prototypeGenerator,
            Generator<M> mutationValueGenerator) {
        super(prototypeGenerator);
        this.mutationValueGenerator = mutationValueGenerator;
    }

    @Override
    protected T transform(Generator<T> inputGenerator) {
        return mutate(inputGenerator.next(), mutationValueGenerator.next());

    }

    /**
     * <p>
     * For every generation of values this method will be called once. The
     * first parameter is a fresh prototype object and the second value is an
     * arbitrary value which can be used in concrete implementations of this
     * abstract class to change the state of prototype value.
     * </p>
     */
    protected abstract T mutate(T prototype, M mutation);
}
