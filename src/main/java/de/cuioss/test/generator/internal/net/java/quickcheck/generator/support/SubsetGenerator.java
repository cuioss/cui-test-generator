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

import static de.cuioss.tools.base.Preconditions.checkArgument;
import static de.cuioss.tools.collect.CollectionLiterals.mutableList;
import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.cuioss.test.generator.internal.net.java.quickcheck.Generator;

public class SubsetGenerator<T> implements Generator<Set<T>> {

    private final List<T> superset;
    private final Generator<Integer> sizes;

    public SubsetGenerator(Iterable<T> superset, Generator<Integer> size) {
        requireNonNull(superset, "superset");
        requireNonNull(size, "size");
        this.superset = mutableList(superset);
        this.sizes = size;
    }

    public SubsetGenerator(Iterable<T> superset) {
        this(superset, new IntegerGenerator(0, mutableList(superset).size()));
    }

    @Override
    public Set<T> next() {
        Collections.shuffle(superset);
        int size = sizes.next();
        checkArgument(0 <= size);
        checkArgument(maxSize(superset) >= size);
        return new HashSet<>(superset.subList(0, size));
    }

    private static <T> int maxSize(Collection<T> superset) {
        return superset.size();
    }

}
