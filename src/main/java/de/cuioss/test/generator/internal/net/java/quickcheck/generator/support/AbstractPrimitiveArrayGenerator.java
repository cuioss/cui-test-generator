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

import static java.lang.reflect.Array.*;

abstract class AbstractPrimitiveArrayGenerator<I, P> extends AbstractTransformerGenerator<I, P> {

    private final Class<?> componentType;

    AbstractPrimitiveArrayGenerator(Generator<I> inputGenerator, Class<?> componentType) {
        super(inputGenerator);
        this.componentType = componentType;
    }

    @Override
    protected P transform(Generator<I> inputGenerator) {
        I objectArray = inputGenerator.next();
        P primitive = createPrimitiveArray(objectArray);
        for (int i = 0; i < getLength(objectArray); i++) {
            set(primitive, i, get(objectArray, i));

        }
        return primitive;
    }

    @SuppressWarnings("unchecked")
    private P createPrimitiveArray(I objectArray) {
        return (P) newInstance(componentType, getLength(objectArray));
    }

}
