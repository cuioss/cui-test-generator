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

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import io.cui.test.generator.internal.net.java.quickcheck.Generator;
import io.cui.test.generator.internal.net.java.quickcheck.ObjectGenerator;

/**
 * {@link ObjectGenerator} implementation that can generate object graphs with
 * default primitive value generators.
 */
public class ObjectDefaultMappingGenerator<T> extends ObjectGeneratorImpl<T> {

    private static final Map<Class<?>, Generator<?>> RETURN_TYPE_TO_GENERATOR = new HashMap<>();

    static {
        map(new IntegerGenerator(), Integer.class, int.class);
        map(new StringGenerator(), String.class);
        map(new LongGenerator(), Long.class, long.class);
        map(new DoubleGenerator(), Double.class, double.class, Number.class);
        map(new ByteGenerator(), byte.class, Byte.class);
    }

    public ObjectDefaultMappingGenerator(Class<T> objectType) {
        super(objectType);
        mapMethodsToGenerators();
    }

    private static void map(Generator<?> generator, Class<?>... classes) {
        for (Class<?> c : classes) {
            RETURN_TYPE_TO_GENERATOR.put(c, generator);
        }
    }

    private void mapMethodsToGenerators() {
        for (Method method : definition.getType().getMethods()) {
            Class<?> returnType = method.getReturnType();
            Generator<?> generator = RETURN_TYPE_TO_GENERATOR.get(returnType);
            if (generator == null && returnType.isInterface()) {
                generator = buildGenerator(returnType);
            }
            definition.defineMapping(method, generator);
        }
    }

    private <R> ObjectDefaultMappingGenerator<R> buildGenerator(
            Class<R> returnType) {
        return new ObjectDefaultMappingGenerator<>(returnType);
    }
}
