/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.test.generator.internal.net.java.quickcheck;

import java.io.PrintWriter;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

class RunnerImpl<T> implements Runner<T> {

    private int maxRuns;
    private int currentRuns;
    private int runs;
    private final PrintWriter writer;
    private final Characteristic<T> characteristic;
    private final Generator<T> generator;

    public RunnerImpl(Characteristic<T> characteristic, int runs, Generator<T> generator, PrintWriter writer) {
        this.characteristic = characteristic;
        this.generator = generator;
        this.writer = writer;
        setRuns(runs);
    }

    @Override
    public void forAll() {
        Objects.requireNonNull(characteristic, "characteristic");
        Objects.requireNonNull(generator, "generator");
        for (var count = 0; count < runs; count++) {
            try {
                doSetup();
                check(generator);
            } finally {
                doTearDown();
            }
        }
    }

    // cui-rewrite:disable InvalidExceptionUsageRecipe
    private void check(Generator<T> generator) {
        requireNonNull(generator, "generator");

        while (currentRuns < maxRuns) {
            currentRuns++;
            var value = generator.next();
            printCurrentValue(currentRuns, value);
            try {
                characteristic.specify(value);
                return;
            } catch (GuardException e) {
                print("%d: skipped [%s]".formatted(currentRuns, value));
            } catch (Throwable e) {
                throwFailedException(value, e, characteristic, currentRuns);
            }
        }
        var message = "Generator max tries (%s) reached. Check your guard expressions.".formatted(maxRuns);
        throw new GeneratorException(message, generator);
    }

    public static int getMaxGeneratorTries(int maxRuns) {
        return maxRuns * 10;
    }

    // cui-rewrite:disable InvalidExceptionUsageRecipe
    private void doTearDown() {
        try {
            characteristic.tearDown();
        } catch (Exception e) {
            throw new CharacteristicException("teardown failed", e, characteristic);
        }
    }

    // cui-rewrite:disable InvalidExceptionUsageRecipe
    private void doSetup() {
        try {
            characteristic.setUp();
        } catch (Exception e) {
            throw new CharacteristicException("setup failed", e, characteristic);
        }
    }

    private void print(String msg) {
        writer.println(msg);
        writer.flush();
    }

    private void printCurrentValue(int currentRuns, T value) {
        print("%s:[%s]".formatted(currentRuns, value));
    }

    private void throwFailedException(T value, Throwable e, Characteristic<?> characteristic, int currentRuns) {
        var characteristicName = characteristic.name() == null ? "" : characteristic.name();
        var message = "Characteristic %s is not true for value(# %s):[%s] message: >%s<";
        var msg = message.formatted(characteristicName, currentRuns, value, e.getMessage());
        print(msg);
        throw new CharacteristicException(msg, e, characteristic, value);
    }

    private void setRuns(int runs) {
        if (QuickCheck.MIN_NUMBER_OF_RUNS > runs) {
            throw new IllegalArgumentException("runs");
        }
        maxRuns = 10 * runs;
        this.runs = runs;
    }
}
