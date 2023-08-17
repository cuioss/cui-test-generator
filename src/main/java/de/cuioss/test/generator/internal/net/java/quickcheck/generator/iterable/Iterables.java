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
package de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

import de.cuioss.test.generator.internal.net.java.quickcheck.Generator;
import de.cuioss.test.generator.internal.net.java.quickcheck.QuickCheck;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.Generators;

public final class Iterables {

    private Iterables() {
    }

    /**
     * Convert a generator into a {@link Iterable iterable}.<br>
     *
     * The generator will be run {@link QuickCheck#MAX_NUMBER_OF_RUNS} times.
     */
    public static <T> Iterable<T> toIterable(final Generator<T> generator) {
        return Generators.toIterable(generator);
    }

    /**
     * Convert a generator into an {@link Iterable iterable}.
     *
     * @param numberOfRuns to execute the runner
     */
    public static <T> Iterable<T> toIterable(final Generator<T> generator, final int numberOfRuns) {
        return Generators.toIterable(generator, numberOfRuns);
    }

    /**
     * Calculate the size of an {@link Iterable}.
     * <p>
     * The size of an {@link Iterable} is the number of {@link Iterator#next()}
     * calls not throwing a {@link NoSuchElementException}.
     * </p>
     *
     * @param iterable to calculate the size of
     */
    public static <T> int sizeOf(Iterable<T> iterable) {
        requireNonNull(iterable, "iterable");
        int size = 0;
        // noinspection StatementWithEmptyBody
        for (Iterator<T> iter = iterable.iterator(); iter.hasNext(); iter.next(), size++)
            ;
        return size;
    }
}
