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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * A cloning generator which uses object serialization to create clones of the
 * prototype object. For each call of {@link CloningGenerator#next()} a new copy
 * of the prototype will be generated.
 *
 * @param <T> Type of the prototype object
 *
 */
public class CloningGenerator<T> implements Generator<T> {

    private final T prototype;

    public CloningGenerator(T prototype) {
        this.prototype = prototype;
    }

    /**
     * Generate a new instance of the prototype object.
     */
    @Override
    public T next() {
        try {
            return cloneObject();
        } catch (IOException e) {
            throw new IllegalArgumentException("prototype " + prototype + " not serializable.", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("this should not happen " + e.getMessage(), e);
        }
    }

    private T cloneObject() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bytesStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(bytesStream);
        objectOutputStream.writeObject(prototype);
        objectOutputStream.flush();
        objectOutputStream.close();
        ObjectInputStream objectInputStream = new ObjectInputStream(
                new ByteArrayInputStream(bytesStream.toByteArray()));
        return castObjectToT(objectInputStream);
    }

    @SuppressWarnings("unchecked")
    private T castObjectToT(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        return (T) objectInputStream.readObject();
    }

}
