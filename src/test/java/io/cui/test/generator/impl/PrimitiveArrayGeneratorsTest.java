package io.cui.test.generator.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PrimitiveArrayGeneratorsTest {

    @Test
    void shouldProvideBooleans() {
        var generator = PrimitiveArrayGenerators.resolveForType(boolean.class);
        assertEquals(boolean.class, generator.getType());
        var array = generator.next();
        assertNotNull(array);
        assertTrue(array.getClass().isArray());
        var castArray = (boolean[]) array;
        assertNotNull(castArray);
    }

    @Test
    void shouldProvideBytes() {
        var generator = PrimitiveArrayGenerators.resolveForType(byte.class);
        assertEquals(byte.class, generator.getType());
        var array = generator.next();
        assertNotNull(array);
        assertTrue(array.getClass().isArray());
        var castArray = (byte[]) array;
        assertNotNull(castArray);
    }

    @Test
    void shouldProvideChars() {
        var generator = PrimitiveArrayGenerators.resolveForType(char.class);
        assertEquals(char.class, generator.getType());
        var array = generator.next();
        assertNotNull(array);
        assertTrue(array.getClass().isArray());
        var castArray = (char[]) array;
        assertNotNull(castArray);
    }

    @Test
    void shouldProvideIntegers() {
        var generator = PrimitiveArrayGenerators.resolveForType(int.class);
        assertEquals(int.class, generator.getType());
        var array = generator.next();
        assertNotNull(array);
        assertTrue(array.getClass().isArray());
        var castArray = (int[]) array;
        assertNotNull(castArray);
    }

    @Test
    void shouldProvideLongs() {
        var generator = PrimitiveArrayGenerators.resolveForType(long.class);
        assertEquals(long.class, generator.getType());
        var array = generator.next();
        assertNotNull(array);
        assertTrue(array.getClass().isArray());
        var castArray = (long[]) array;
        assertNotNull(castArray);
    }

    @Test
    void shouldProvideFloats() {
        var generator = PrimitiveArrayGenerators.resolveForType(float.class);
        assertEquals(float.class, generator.getType());
        var array = generator.next();
        assertNotNull(array);
        assertTrue(array.getClass().isArray());
        var castArray = (float[]) array;
        assertNotNull(castArray);
    }

    @Test
    void shouldProvideDouble() {
        var generator = PrimitiveArrayGenerators.resolveForType(double.class);
        assertEquals(double.class, generator.getType());
        var array = generator.next();
        assertNotNull(array);
        assertTrue(array.getClass().isArray());
        var castArray = (double[]) array;
        assertNotNull(castArray);
    }

    @Test
    void shouldProvideShorts() {
        var generator = PrimitiveArrayGenerators.resolveForType(short.class);
        assertEquals(short.class, generator.getType());
        var array = generator.next();
        assertNotNull(array);
        assertTrue(array.getClass().isArray());
        var castArray = (short[]) array;
        assertNotNull(castArray);
    }
}
