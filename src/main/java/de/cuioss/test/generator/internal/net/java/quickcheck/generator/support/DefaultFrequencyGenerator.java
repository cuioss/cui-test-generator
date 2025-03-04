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

import de.cuioss.test.generator.internal.net.java.quickcheck.FrequencyGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.Generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static de.cuioss.tools.base.Preconditions.checkArgument;

public class DefaultFrequencyGenerator<T> implements FrequencyGenerator<T> {

    /**
     * Weight used to generate equal weighted frequency generator.
     */
    public static final int EQUAL_WEIGHT_OF_GENERATORS = 1;

    private final List<Frequency<T>> frequencies = new ArrayList<>();
    private IntegerGenerator choose;
    private int sum = 0;

    public DefaultFrequencyGenerator(Generator<T> generator) {
        this(generator, EQUAL_WEIGHT_OF_GENERATORS);
    }

    public DefaultFrequencyGenerator(Generator<T> generator, int weight) {
        add(generator, weight);
    }

    @Override
    public FrequencyGenerator<T> add(Generator<T> generator) {
        return add(generator, EQUAL_WEIGHT_OF_GENERATORS);
    }

    @Override
    public FrequencyGenerator<T> add(Generator<T> generator, int weight) {
        Objects.requireNonNull(generator, "generator");
        checkArgument(EQUAL_WEIGHT_OF_GENERATORS <= weight, "weight");

        this.frequencies.add(new Frequency<>(generator, weight));
        this.sum += weight;
        this.choose = null;
        return this;
    }

    @Override
    public T next() {
        checkArgument(1 <= this.sum, "number of generators");

        int next = choose().nextInt();
        for (Frequency<T> pair : this.frequencies) {
            int weight = pair.getWeight();
            if (next <= weight) {
                return pair.getGenerator().next();
            }
            next -= weight;
        }
        throw new IllegalStateException();
    }

    private IntegerGenerator choose() {
        if (this.choose == null) {
            this.choose = new IntegerGenerator(1, this.sum);
        }
        return this.choose;
    }

    private static class Frequency<T> {

        private final Generator<T> generator;
        private final int weight;

        private Frequency(Generator<T> generator, int weight) {
            this.generator = generator;
            this.weight = weight;
        }

        private Generator<T> getGenerator() {
            return this.generator;
        }

        private int getWeight() {
            return this.weight;
        }
    }
}
