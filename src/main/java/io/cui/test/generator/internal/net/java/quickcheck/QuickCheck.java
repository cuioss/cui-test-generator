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
package io.cui.test.generator.internal.net.java.quickcheck;

import java.io.PrintWriter;

import lombok.experimental.UtilityClass;

/**
 * QuickCheck is an implementation of the Haskell QuickCheck generator based
 * test tool (<a href="http://www.cs.chalmers.se/~rjmh/QuickCheck/">...</a>).
 */
@UtilityClass
@SuppressWarnings("java:S106") // owolff ok for testcode
public class QuickCheck {

    
    public static final int MAX_NUMBER_OF_RUNS = 200;
    public static final int MIN_NUMBER_OF_RUNS = 1;

    public static final String SYSTEM_PROPERTY_RUNS = propertyName("RUNS");

    /**
     * Check the {@link Characteristic} for all values generated by the given
     * {@link Generator}. The execution will fail fast if any of the calls of
     * the {@link Characteristic#specify(Object)} method throws an exception.
     *
     * @param <T>
     *            type of the generated values
     * @throws CharacteristicException
     *             if a characteristic is not {@code true} for a generated
     *             value
     * @throws GeneratorException
     *             if generation of the next value failed.
     */
    public static <T> void forAll(Generator<T> generator,
            Characteristic<T> characteristic)
        throws GeneratorException,
        CharacteristicException {
        runner(characteristic, MAX_NUMBER_OF_RUNS, generator, new PrintWriter(new NullWriter()))
                .forAll();
    }

    public static int getDefaultNumberOfRuns() {
        Integer runs = Integer.getInteger(SYSTEM_PROPERTY_RUNS,
                MAX_NUMBER_OF_RUNS);
        return Math.max(MIN_NUMBER_OF_RUNS, runs);
    }

    private static String propertyName(String name) {
        return QuickCheck.class.getSimpleName() + "." + name;
    }

    /**
     * Check the {@link Characteristic} for all values generated by the given
     * {@link Generator}. The execution will fail fast if any of the calls of
     * the {@link Characteristic#specify(Object)} method throws an exception.
     *
     * @param runs
     *            number of runs and generated values for this characteristic
     * @param <T>
     *            type of the generated values
     * @throws CharacteristicException
     *             if a characteristic is not {@code true} for a generated
     *             value
     * @throws GeneratorException
     *             if generation of the next value failed.
     */
    public static <T> void forAll(int runs, Generator<T> generator,
            Characteristic<T> characteristic)
        throws GeneratorException,
        CharacteristicException {
        runner(characteristic, runs, generator, new PrintWriter(new NullWriter())).forAll();
    }

    /**
     * Check the {@link Characteristic} for all values generated by the given
     * {@link Generator}. The execution will fail fast if any of the calls of
     * the {@link Characteristic#specify(Object)} method throws an exception.
     *
     * @param <T>
     *            type of the generated values
     * @throws CharacteristicException
     *             if a characteristic is not {@code true} for a generated
     *             value
     * @throws GeneratorException
     *             if generation of the next value failed.
     */
    public static <T> void forAllVerbose(Generator<T> generator,
            Characteristic<T> characteristic)
        throws GeneratorException,
        CharacteristicException {
        runner(characteristic, MAX_NUMBER_OF_RUNS, generator, new PrintWriter(new PrintWriter(System.out))).forAll();
    }

    /**
     * Check the {@link Characteristic} for all values generated by the given
     * {@link Generator}. The execution will fail fast if any of the calls of
     * the {@link Characteristic#specify(Object)} method throws an exception.
     *
     * @param runs
     *            number of runs and generated values for this characteristic
     * @param <T>
     *            type of the generated values
     * @throws CharacteristicException
     *             if a characteristic is not {@code true} for a generated
     *             value
     * @throws GeneratorException
     *             if generation of the next value failed.
     */
    public static <T> void forAllVerbose(int runs, Generator<T> generator,
            Characteristic<T> characteristic)
        throws GeneratorException,
        CharacteristicException {
        runner(characteristic, runs, generator, new PrintWriter(new PrintWriter(System.out))).forAll();
    }

    /**
     * All executions of {@link Characteristic#specify(Object)} which execute
     * this method will be skipped and a new test case
     * will be generated. Execution will be stopped if it is not possible to
     * create a new test cases after a reasonable amount of tries.
     *
     * @param predicate
     *            Skip the current test case if the predicate is true.
     */
    public static void guard(boolean predicate) {
        if (!predicate)
            throw new GuardException();
    }

    private static <T> Runner<T> runner(Characteristic<T> characteristic, int runs, Generator<T> generator,
            PrintWriter writer) {
        return new RunnerImpl<>(characteristic, runs, generator, writer);
    }
}
