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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class SubsetGenerator<T> implements Generator<Set<T>> {

    private final List<T> superset;
    private final Generator<Integer> sizes;

    public SubsetGenerator(Iterable<T> superset, Generator<Integer> size) {
        requireNonNull(superset, "superset");
        requireNonNull(size, "size");
        var list = new ArrayList<T>();
        superset.forEach(list::add);
        this.superset = list;
        this.sizes = size;
    }

    public SubsetGenerator(Iterable<T> superset) {
        requireNonNull(superset, "superset");
        var list = new ArrayList<T>();
        superset.forEach(list::add);
        this.superset = list;
        this.sizes = new IntegerGenerator(0, list.size());
    }

    @Override
    public Set<T> next() {
        Collections.shuffle(superset);
        int size = sizes.next();
        if (size < 0) {
            throw new IllegalArgumentException("Size must be non-negative");
        }
        if (maxSize(superset) < size) {
            throw new IllegalArgumentException("Size must not exceed superset size");
        }
        return new HashSet<>(superset.subList(0, size));
    }

    private static <T> int maxSize(Collection<T> superset) {
        return superset.size();
    }

}
