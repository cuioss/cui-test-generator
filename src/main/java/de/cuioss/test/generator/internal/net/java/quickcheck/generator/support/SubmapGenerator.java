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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

public class SubmapGenerator<K, V> implements Generator<Map<K, V>> {

    private final SubsetGenerator<Entry<K, V>> subsets;

    public SubmapGenerator(Map<K, V> supermap) {
        Objects.requireNonNull(supermap, "supermap");
        this.subsets = new SubsetGenerator<>(supermap.entrySet());
    }

    public SubmapGenerator(Map<K, V> supermap, Generator<Integer> sizes) {
        Objects.requireNonNull(supermap, "supermap");
        Objects.requireNonNull(sizes, "sizes");
        this.subsets = new SubsetGenerator<>(supermap.entrySet(), sizes);
    }

    @Override
    public Map<K, V> next() {
        Set<Entry<K, V>> entries = subsets.next();
        Map<K, V> submap = HashMap.newHashMap(entries.size());
        for (Entry<K, V> e : entries)
            submap.put(e.getKey(), e.getValue());
        return submap;
    }
}
