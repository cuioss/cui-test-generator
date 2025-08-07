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

import java.util.List;

import static de.cuioss.tools.collect.MoreCollections.requireNotEmpty;

public class RoundRobinGenerator<T> implements Generator<T> {

    private final List<Generator<T>> generators;
    private int index;

    public RoundRobinGenerator(List<Generator<T>> generators) {
        requireNotEmpty(generators, "generators");
        this.generators = generators;
        index = 0;
    }

    @Override
    public T next() {
        Generator<T> generator = generators.get(index++ % generators.size());
        return generator.next();
    }
}
