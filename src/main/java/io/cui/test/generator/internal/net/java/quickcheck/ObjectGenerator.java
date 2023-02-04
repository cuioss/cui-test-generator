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
package io.cui.test.generator.internal.net.java.quickcheck;

/**
 * Declarative object generator definition.
 * <p>
 * User should call implementation of ObjectGenerator as follows:</p>
 * <pre>{@code
ObjectGenerator<T> g;
Generator<R> methodGenerator;
g.on(g.getRecorder().method()).returns(methodGenerator);}</pre>
 *
 * @param <T> type of object generated
 */
public interface ObjectGenerator<T> extends Generator<T> {

    /**
     * Implementation of T that is used to define the method a generator should
     * be defined for.
     *
     * @return an implementation of T used only to record method calls
     */
    T getRecorder();

    /**
     * Define a method a generator should be defined for.
     *
     * @param <R>
     *            type of the return type
     * @param value
     *            is ignored
     * @return {@link ReturnValue} instance to define a generator for this
     *         method
     */
    <R> ReturnValue<R> on(R value);

    interface ReturnValue<R> {

        void returns(Generator<? extends R> generator);
    }
}
