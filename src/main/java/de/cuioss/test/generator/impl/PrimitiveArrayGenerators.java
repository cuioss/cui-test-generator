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
package de.cuioss.test.generator.impl;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;

import static de.cuioss.tools.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Provides generators for arrays of all Java primitive types.
 * Each generator creates arrays of random length (1-128 elements) with random values
 * appropriate for the primitive type.
 * 
 * <p>Available generators:</p>
 * <ul>
 *   <li>{@link #BOOLEAN} - boolean[] arrays with random true/false values</li>
 *   <li>{@link #BYTE} - byte[] arrays with random byte values</li>
 *   <li>{@link #CHAR} - char[] arrays with random character values</li>
 *   <li>{@link #SHORT} - short[] arrays with random short values</li>
 *   <li>{@link #INTEGER} - int[] arrays with random integer values</li>
 *   <li>{@link #LONG} - long[] arrays with random long values</li>
 *   <li>{@link #FLOAT} - float[] arrays with random float values</li>
 *   <li>{@link #DOUBLE} - double[] arrays with random double values</li>
 * </ul>
 * 
 * <p><em>Example usage:</em></p>
 * <pre>
 * // Generate arrays of different primitive types
 * boolean[] booleans = (boolean[]) PrimitiveArrayGenerators.BOOLEAN.next();
 * int[] integers = (int[]) PrimitiveArrayGenerators.INTEGER.next();
 * 
 * // Get a generator for a specific primitive type
 * var generator = PrimitiveArrayGenerators.resolveForType(int.class);
 * int[] moreIntegers = (int[]) generator.next();
 * </pre>
 * 
 * <p>Implementation notes:</p>
 * <ul>
 *   <li>All generators are thread-safe</li>
 *   <li>Array size is randomly chosen between 1 and 128 elements</li>
 *   <li>Values are generated using the corresponding primitive generators from {@link Generators}</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @see Generators
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

    private static final TypedGenerator<Integer> sizeGenerator = Generators.integers(1, 128);

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
