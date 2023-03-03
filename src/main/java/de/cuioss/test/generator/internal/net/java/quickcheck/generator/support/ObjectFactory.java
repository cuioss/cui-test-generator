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

import static java.lang.String.format;
import static java.lang.reflect.Proxy.newProxyInstance;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class ObjectFactory<T> implements InvocationHandler {

    private final ObjectDefinition<T> definition;

    ObjectFactory(ObjectDefinition<T> definition) {
        this.definition = definition;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        return definition.retrieveMapping(method).next();
    }

    private void checkAllMethodsAreDefined() {
        for (Method method : definition.getType().getMethods()) {
            if (!method.getReturnType().equals(Void.TYPE)
                    && definition.retrieveMapping(method) == null) {
                String message = format("Definition for method %s missing.",
                        method.getName());
                throw new IllegalStateException(message);
            }
        }
    }

    @SuppressWarnings("unchecked")
    T newValue() {
        checkAllMethodsAreDefined();
        return (T) newProxyInstance(this.getClass().getClassLoader(),
                new Class[] { definition.getType() }, this);
    }
}
