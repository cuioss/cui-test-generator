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
package de.cuioss.test.generator.internal.net.java.quickcheck.characteristic;

import de.cuioss.test.generator.internal.net.java.quickcheck.Characteristic;

/**
 * {@link AbstractCharacteristic} is an implementation of {@link Characteristic}
 * with {@link Classification} handling. The methods
 * {@link AbstractCharacteristic#classify(Object)} and
 * {@link AbstractCharacteristic#classify(boolean, Object)} can be used to
 * classify test data.
 *
 * @param <T> Type of generated random test instances.
 */
@SuppressWarnings("ProhibitedExceptionDeclared")
public abstract class AbstractCharacteristic<T> implements Characteristic<T> {

    private final Classification classification = new Classification();
    private final String name;

    public AbstractCharacteristic() {
        this(null);
    }

    public AbstractCharacteristic(String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     *
     * This method will call {@link AbstractCharacteristic#doSpecify(Object)}.
     */
    @Override
    public void specify(T any) throws Throwable {
        doSpecify(any);
        this.classification.call();
    }

    /**
     * Add a classification with the given key if the predicate is true.
     *
     * @param predicate      Predicate for the classification.
     * @param classification classification key.
     */
    protected void classify(boolean predicate, Object classification) {
        this.classification.doClassify(predicate, classification);
    }

    /**
     * Add a classification with the given key.
     *
     * @param classification classification key.
     */
    protected void classify(Object classification) {
        classify(true, classification);
    }

    /**
     * Implement this method to specify the characteristic
     * ({@link Characteristic#specify(Object)}).
     */
    protected abstract void doSpecify(T any) throws Throwable;

    /**
     * {@link Classification} data about the test cases executed.
     */
    public Classification getClassification() {
        return classification;
    }

    @Override
    public void setUp() throws Exception {
    }

    @Override
    public void tearDown() throws Exception {
    }

    @Override
    public String name() {
        return name;
    }
}
