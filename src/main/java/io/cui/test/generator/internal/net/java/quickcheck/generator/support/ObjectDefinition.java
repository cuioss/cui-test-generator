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
package io.cui.test.generator.internal.net.java.quickcheck.generator.support;

import static java.lang.String.format;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import io.cui.test.generator.internal.net.java.quickcheck.Generator;
import io.cui.test.generator.internal.net.java.quickcheck.ObjectGenerator.ReturnValue;

class ObjectDefinition<T> implements InvocationHandler {

    private final Map<Method, Generator<?>> methodsGeneratorMapping = new HashMap<>();

    private final T targetMethodRecorder;
    private Method calledTargetMethod;

    private final Class<T> type;

    public ObjectDefinition(Class<T> objectType) {
        checkIsInterface(objectType);
        this.type = objectType;
        this.targetMethodRecorder = createTargetMethodRecorder(objectType);
    }

    public T getRecorder() {
        return targetMethodRecorder;
    }

    public <R> ReturnValue<R> onMethod(R obj) {
        final Method currentCalled = currentCalledMethod();
        return generator -> defineMapping(currentCalled, generator);
    }

    @Override
    @SuppressWarnings("java:S4274") // owolff: Ok for test code
    public Object invoke(Object proxy, Method method, Object[] args) {
        assert args == null;
        calledTargetMethod = method;
        return null;
    }

    private Method currentCalledMethod() {
        if (calledTargetMethod == null) {
            throw new IllegalStateException(
                    "Has to be called with recorder instance.");
        }
        final Method currentCalled = calledTargetMethod;
        calledTargetMethod = null;
        return currentCalled;
    }

    @SuppressWarnings("unchecked")
    private T createTargetMethodRecorder(Class<T> type) {
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class[] { type }, this);

    }

    private void checkIsInterface(Class<T> objectType) {
        if (!objectType.isInterface()) {
            String message = format("Only interfaces supported (class: %s).",
                    objectType.getName());
            throw new IllegalArgumentException(message);
        }
    }

    public Class<T> getType() {
        return type;
    }

    public void defineMapping(Method method, Generator<?> generator) {
        methodsGeneratorMapping.put(method, generator);
    }

    @SuppressWarnings("java:S1452") // owolff: Ok for test code
    public Generator<?> retrieveMapping(Method method) {
        return methodsGeneratorMapping.get(method);
    }
}
