/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.test.generator.impl;

import static de.cuioss.tools.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;

/**
 * Generates arrays of primitive types.
 *
 * @author Oliver Wolff
 */
public enum PrimitiveArrayGenerators {

    /** Provides booleans */
    BOOLEAN {

        @Override
        public Object next() {
            final int size = sizeGenerator.next();
            final var generator = Generators.booleans();
            final var array = new boolean[size];
            for (var index = 0; index < size; index++) {
                array[index] = generator.next();
            }
            return array;
        }

        @Override
        public Class<?> getType() {
            return boolean.class;
        }
    },
    /** Provides bytes */
    BYTE {

        @Override
        public Object next() {
            final int size = sizeGenerator.next();
            final var generator = Generators.bytes();
            final var array = new byte[size];
            for (var index = 0; index < size; index++) {
                array[index] = generator.next();
            }
            return array;
        }

        @Override
        public Class<?> getType() {
            return byte.class;
        }
    },
    /** Provides chars */
    CHAR {

        @Override
        public Object next() {
            final int size = sizeGenerator.next();
            final var generator = Generators.characters();
            final var array = new char[size];
            for (var index = 0; index < size; index++) {
                array[index] = generator.next();
            }
            return array;
        }

        @Override
        public Class<?> getType() {
            return char.class;
        }
    },
    /** Provides shorts */
    SHORT {

        @Override
        public Object next() {
            final int size = sizeGenerator.next();
            final TypedGenerator<Short> generator = new ShortObjectGenerator();
            final var array = new short[size];
            for (var index = 0; index < size; index++) {
                array[index] = generator.next();
            }
            return array;
        }

        @Override
        public Class<?> getType() {
            return short.class;
        }
    },
    /** Provides integers */
    INTEGER {

        @Override
        public Object next() {
            final int size = sizeGenerator.next();
            final var generator = Generators.integers();
            final var array = new int[size];
            for (var index = 0; index < size; index++) {
                array[index] = generator.next();
            }
            return array;
        }

        @Override
        public Class<?> getType() {
            return int.class;
        }
    },
    /** Provides longs */
    LONG {

        @Override
        public Object next() {
            final int size = sizeGenerator.next();
            final var generator = Generators.longs();
            final var array = new long[size];
            for (var index = 0; index < size; index++) {
                array[index] = generator.next();
            }
            return array;
        }

        @Override
        public Class<?> getType() {
            return long.class;
        }
    },
    /** Provides floats */
    FLOAT {

        @Override
        public Object next() {
            final int size = sizeGenerator.next();
            final TypedGenerator<Float> generator = new FloatObjectGenerator();
            final var array = new float[size];
            for (var index = 0; index < size; index++) {
                array[index] = generator.next();
            }
            return array;
        }

        @Override
        public Class<?> getType() {
            return float.class;
        }
    },
    /** Provides doubles */
    DOUBLE {

        @Override
        public Object next() {
            final int size = sizeGenerator.next();
            final var generator = Generators.doubles();
            final var array = new double[size];
            for (var index = 0; index < size; index++) {
                array[index] = generator.next();
            }
            return array;
        }

        @Override
        public Class<?> getType() {
            return double.class;
        }
    };

    private static final TypedGenerator<Integer> sizeGenerator = Generators.integers(0, 128);

    /**
     * @return an primitive array of the configured type, with the sizes 1-128
     */
    public abstract Object next();

    /**
     * @return the type of the primitive
     */
    public abstract Class<?> getType();

    /**
     * Returns a {@link PrimitiveArrayGenerators} for the given primitive type.
     *
     * @param primitiveType must not be null and a primitive type.
     * @return the found {@link PrimitiveArrayGenerators} or throws an
     *         {@link IllegalStateException} if none could be found
     */
    public static PrimitiveArrayGenerators resolveForType(final Class<?> primitiveType) {
        requireNonNull(primitiveType);
        checkArgument(primitiveType.isPrimitive(), "You must provide a primitive type, given: " + primitiveType);
        for (final PrimitiveArrayGenerators generator : PrimitiveArrayGenerators.values()) {
            if (primitiveType.equals(generator.getType())) {
                return generator;
            }
        }
        throw new IllegalStateException("No generator registered for type " + primitiveType);
    }

}
