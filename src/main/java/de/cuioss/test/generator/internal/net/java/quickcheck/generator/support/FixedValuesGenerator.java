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
package de.cuioss.test.generator.internal.net.java.quickcheck.generator.support;

import de.cuioss.test.generator.internal.net.java.quickcheck.Generator;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static de.cuioss.tools.collect.CollectionLiterals.mutableList;
import static de.cuioss.tools.collect.MoreCollections.requireNotEmpty;

public class FixedValuesGenerator<T> implements Generator<T> {

    private final List<T> values;
    private final IntegerGenerator index;

    public FixedValuesGenerator() {
        this(Collections.singleton(null));
    }

    public FixedValuesGenerator(T value) {
        this(Set.of(value));
    }

    public FixedValuesGenerator(Iterable<T> values) {
        requireNotEmpty(values, "values");

        this.values = mutableList(values);
        this.index = new IntegerGenerator(0, this.values.size() - 1);
    }

    @Override
    public T next() {
        return this.values.get(this.index.nextInt());
    }
}
