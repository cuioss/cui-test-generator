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
