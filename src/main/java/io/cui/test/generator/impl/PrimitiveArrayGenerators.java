package io.cui.test.generator.impl;

import static io.cui.tools.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

import io.cui.test.generator.Generators;
import io.cui.test.generator.TypedGenerator;

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
            final TypedGenerator<Boolean> generator = Generators.booleans();
            final boolean[] array = new boolean[size];
            for (int index = 0; index < size; index++) {
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
            final TypedGenerator<Byte> generator = Generators.bytes();
            final byte[] array = new byte[size];
            for (int index = 0; index < size; index++) {
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
            final TypedGenerator<Character> generator = Generators.characters();
            final char[] array = new char[size];
            for (int index = 0; index < size; index++) {
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
            final short[] array = new short[size];
            for (int index = 0; index < size; index++) {
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
            final TypedGenerator<Integer> generator = Generators.integers();
            final int[] array = new int[size];
            for (int index = 0; index < size; index++) {
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
            final TypedGenerator<Long> generator = Generators.longs();
            final long[] array = new long[size];
            for (int index = 0; index < size; index++) {
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
            final float[] array = new float[size];
            for (int index = 0; index < size; index++) {
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
            final TypedGenerator<Double> generator = Generators.doubles();
            final double[] array = new double[size];
            for (int index = 0; index < size; index++) {
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
     * @return the found {@link PrimitiveArrayGenerators} or throws an {@link IllegalStateException}
     *         if none could be found
     */
    public static final PrimitiveArrayGenerators resolveForType(final Class<?> primitiveType) {
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
