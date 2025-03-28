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

import java.util.Collection;
import java.util.Objects;

import static de.cuioss.tools.collect.CollectionLiterals.mutableList;

public class ExcludingGenerator<T> extends VetoableGenerator<T> {

    private final Collection<T> excluded;

    public ExcludingGenerator(Generator<T> generator, Iterable<T> excluded, int tries) {
        super(generator, tries);
        Objects.requireNonNull(excluded, "excluded");
        this.excluded = mutableList(excluded);
    }

    @Override
    protected boolean tryValue(T value) {
        return !excluded.contains(value);
    }

}
