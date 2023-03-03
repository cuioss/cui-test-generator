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
import java.util.Set;

import de.cuioss.test.generator.internal.net.java.quickcheck.Generator;
import de.cuioss.test.generator.internal.net.java.quickcheck.StatefulGenerator;

abstract class AbstractUniqueValuesGenerator<T> extends VetoableGenerator<T> implements StatefulGenerator<T> {

    private final Set<T> values;

    public AbstractUniqueValuesGenerator(Set<T> values, Generator<? extends T> generator, int maxTries) {
        super(generator, maxTries);
        Objects.requireNonNull(values, "values");
        this.values = values;
        reset();
    }

    @Override
    protected boolean tryValue(T value) {
        return values.add(value);
    }

    @Override
    public void reset() {
        values.clear();
    }
}
