/*
 *  Licensed to the author under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.cui.test.generator.internal.net.java.quickcheck.generator.support;

import static io.cui.test.generator.internal.net.java.quickcheck.generator.support.SetGenerator.MAX_SIZE;
import static io.cui.test.generator.internal.net.java.quickcheck.util.Assert.notNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import io.cui.test.generator.internal.net.java.quickcheck.Generator;

public class MapGenerator<K,V> implements Generator<Map<K,V>> {

    private final Generator<Set<K>> keys;
    private final Generator<V> values;

    public MapGenerator(Generator<K> keys, Generator<V> values) {
        notNull(keys, "keys");
        notNull(values, "values");
        this.keys = new SetGenerator<>(keys);
        this.values = values;
    }

    public MapGenerator(Generator<K> keys, Generator<V> values, Generator<Integer> size) {
        notNull(keys, "keys");
        notNull(values, "values");
        notNull(size, "size");
        this.keys = new SetGenerator<>(keys, size, MAX_SIZE);
        this.values = values;
    }

    @Override public Map<K, V> next() {
        Map<K, V> next = new HashMap<>();
        for(K key : keys.next()) next.put(key, values.next());
        return next;
    }
}
