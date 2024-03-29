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

import de.cuioss.test.generator.internal.net.java.quickcheck.Generator;
import de.cuioss.test.generator.internal.net.java.quickcheck.GeneratorException;
import de.cuioss.tools.base.Preconditions;

/**
 * Base class for generators which can reject the values generated by their
 * wrapped generator. This will be tried until the maximum number of tries is
 * reached.
 */
public abstract class VetoableGenerator<T> implements Generator<T> {

    private final Generator<? extends T> generator;
    private final int maxTries;

    // TODO this could be a bit high
    // for runs = 200 this means 20000 tries for the worst case
    public static final int DEFAULT_MAX_TRIES = 100;

    public static final int MIN_TRIES = 1;

    protected VetoableGenerator(Generator<? extends T> generator) {
        this(generator, DEFAULT_MAX_TRIES);
    }

    protected VetoableGenerator(Generator<? extends T> generator, int maxTries) {
        Preconditions.checkArgument(MIN_TRIES <= maxTries, "maxTries");
        this.generator = generator;
        this.maxTries = maxTries;
    }

    @Override
    public T next() throws GeneratorException {
        for (int idx = 0; idx < maxTries; idx++) {
            T value = generator.next();
            if (tryValue(value))
                return value;
        }
        throw new GeneratorException(
                "Failed to generate another value after [%s] tries (generator: %s)".formatted(maxTries, generator),
                generator);
    }

    /**
     * @return true to accept the current value.
     */
    protected abstract boolean tryValue(T value);
}
