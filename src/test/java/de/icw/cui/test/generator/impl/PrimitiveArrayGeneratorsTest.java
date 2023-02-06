package de.icw.cui.test.generator.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PrimitiveArrayGeneratorsTest {

    @Test
    void shouldProvideBooleans() {
        PrimitiveArrayGenerators generator = PrimitiveArrayGenerators.resolveForType(boolean.class);
        assertEquals(boolean.class, generator.getType());
        Object array = generator.next();
        assertNotNull(array);
        assertTrue(array.getClass().isArray());
        boolean[] castArray = (boolean[]) array;
        assertNotNull(castArray);
    }

    @Test
    void shouldProvideBytes() {
        PrimitiveArrayGenerators generator = PrimitiveArrayGenerators.resolveForType(byte.class);
        assertEquals(byte.class, generator.getType());
        Object array = generator.next();
        assertNotNull(array);
        assertTrue(array.getClass().isArray());
        byte[] castArray = (byte[]) array;
        assertNotNull(castArray);
    }

    @Test
    void shouldProvideChars() {
        PrimitiveArrayGenerators generator = PrimitiveArrayGenerators.resolveForType(char.class);
        assertEquals(char.class, generator.getType());
        Object array = generator.next();
        assertNotNull(array);
        assertTrue(array.getClass().isArray());
        char[] castArray = (char[]) array;
        assertNotNull(castArray);
    }

    @Test
    void shouldProvideIntegers() {
        PrimitiveArrayGenerators generator = PrimitiveArrayGenerators.resolveForType(int.class);
        assertEquals(int.class, generator.getType());
        Object array = generator.next();
        assertNotNull(array);
        assertTrue(array.getClass().isArray());
        int[] castArray = (int[]) array;
        assertNotNull(castArray);
    }

    @Test
    void shouldProvideLongs() {
        PrimitiveArrayGenerators generator = PrimitiveArrayGenerators.resolveForType(long.class);
        assertEquals(long.class, generator.getType());
        Object array = generator.next();
        assertNotNull(array);
        assertTrue(array.getClass().isArray());
        long[] castArray = (long[]) array;
        assertNotNull(castArray);
    }

    @Test
    void shouldProvideFloats() {
        PrimitiveArrayGenerators generator = PrimitiveArrayGenerators.resolveForType(float.class);
        assertEquals(float.class, generator.getType());
        Object array = generator.next();
        assertNotNull(array);
        assertTrue(array.getClass().isArray());
        float[] castArray = (float[]) array;
        assertNotNull(castArray);
    }

    @Test
    void shouldProvideDouble() {
        PrimitiveArrayGenerators generator = PrimitiveArrayGenerators.resolveForType(double.class);
        assertEquals(double.class, generator.getType());
        Object array = generator.next();
        assertNotNull(array);
        assertTrue(array.getClass().isArray());
        double[] castArray = (double[]) array;
        assertNotNull(castArray);
    }

    @Test
    void shouldProvideShorts() {
        PrimitiveArrayGenerators generator = PrimitiveArrayGenerators.resolveForType(short.class);
        assertEquals(short.class, generator.getType());
        Object array = generator.next();
        assertNotNull(array);
        assertTrue(array.getClass().isArray());
        short[] castArray = (short[]) array;
        assertNotNull(castArray);
    }
}
