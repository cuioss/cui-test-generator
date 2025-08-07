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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PrimitiveArrayGenerators should")
class PrimitiveArrayGeneratorsTest {

    @Nested
    @DisplayName("handle boolean arrays")
    class BooleanArrayTests {
        @Test
        @DisplayName("provide boolean array generator")
        void shouldProvideBooleans() {
            // Arrange & Act
            var generator = PrimitiveArrayGenerators.resolveForType(boolean.class);

            // Assert type
            assertEquals(boolean.class, generator.getType(),
                    "Generator should return boolean.class as type");

            // Assert array generation
            var array = generator.next();
            assertNotNull(array, "Generated array should not be null");
            assertTrue(array.getClass().isArray(), "Generated object should be an array");

            var castArray = (boolean[]) array;
            assertNotNull(castArray, "Cast array should not be null");
            assertTrue(castArray.length > 0, "Generated array should not be empty");
        }
    }

    @Nested
    @DisplayName("handle byte arrays")
    class ByteArrayTests {
        @Test
        @DisplayName("provide byte array generator")
        void shouldProvideBytes() {
            // Arrange & Act
            var generator = PrimitiveArrayGenerators.resolveForType(byte.class);

            // Assert type
            assertEquals(byte.class, generator.getType(),
                    "Generator should return byte.class as type");

            // Assert array generation
            var array = generator.next();
            assertNotNull(array, "Generated array should not be null");
            assertTrue(array.getClass().isArray(), "Generated object should be an array");

            var castArray = (byte[]) array;
            assertNotNull(castArray, "Cast array should not be null");
            assertTrue(castArray.length > 0, "Generated array should not be empty");
        }
    }

    @Nested
    @DisplayName("handle char arrays")
    class CharArrayTests {
        @Test
        @DisplayName("provide char array generator")
        void shouldProvideChars() {
            // Arrange & Act
            var generator = PrimitiveArrayGenerators.resolveForType(char.class);

            // Assert type
            assertEquals(char.class, generator.getType(),
                    "Generator should return char.class as type");

            // Assert array generation
            var array = generator.next();
            assertNotNull(array, "Generated array should not be null");
            assertTrue(array.getClass().isArray(), "Generated object should be an array");

            var castArray = (char[]) array;
            assertNotNull(castArray, "Cast array should not be null");
            assertTrue(castArray.length > 0, "Generated array should not be empty");
        }
    }

    @Nested
    @DisplayName("handle integer arrays")
    class IntegerArrayTests {
        @Test
        @DisplayName("provide integer array generator")
        void shouldProvideIntegers() {
            // Arrange & Act
            var generator = PrimitiveArrayGenerators.resolveForType(int.class);

            // Assert type
            assertEquals(int.class, generator.getType(),
                    "Generator should return int.class as type");

            // Assert array generation
            var array = generator.next();
            assertNotNull(array, "Generated array should not be null");
            assertTrue(array.getClass().isArray(), "Generated object should be an array");

            var castArray = (int[]) array;
            assertNotNull(castArray, "Cast array should not be null");
            assertTrue(castArray.length > 0, "Generated array should not be empty");
        }
    }

    @Nested
    @DisplayName("handle long arrays")
    class LongArrayTests {
        @Test
        @DisplayName("provide long array generator")
        void shouldProvideLongs() {
            // Arrange & Act
            var generator = PrimitiveArrayGenerators.resolveForType(long.class);

            // Assert type
            assertEquals(long.class, generator.getType(),
                    "Generator should return long.class as type");

            // Assert array generation
            var array = generator.next();
            assertNotNull(array, "Generated array should not be null");
            assertTrue(array.getClass().isArray(), "Generated object should be an array");

            var castArray = (long[]) array;
            assertNotNull(castArray, "Cast array should not be null");
            assertTrue(castArray.length > 0, "Generated array should not be empty");
        }
    }

    @Nested
    @DisplayName("handle float arrays")
    class FloatArrayTests {
        @Test
        @DisplayName("provide float array generator")
        void shouldProvideFloats() {
            // Arrange & Act
            var generator = PrimitiveArrayGenerators.resolveForType(float.class);

            // Assert type
            assertEquals(float.class, generator.getType(),
                    "Generator should return float.class as type");

            // Assert array generation
            var array = generator.next();
            assertNotNull(array, "Generated array should not be null");
            assertTrue(array.getClass().isArray(), "Generated object should be an array");

            var castArray = (float[]) array;
            assertNotNull(castArray, "Cast array should not be null");
            assertTrue(castArray.length > 0, "Generated array should not be empty");
        }
    }

    @Nested
    @DisplayName("handle double arrays")
    class DoubleArrayTests {
        @Test
        @DisplayName("provide double array generator")
        void shouldProvideDoubles() {
            // Arrange & Act
            var generator = PrimitiveArrayGenerators.resolveForType(double.class);

            // Assert type
            assertEquals(double.class, generator.getType(),
                    "Generator should return double.class as type");

            // Assert array generation
            var array = generator.next();
            assertNotNull(array, "Generated array should not be null");
            assertTrue(array.getClass().isArray(), "Generated object should be an array");

            var castArray = (double[]) array;
            assertNotNull(castArray, "Cast array should not be null");
            assertTrue(castArray.length > 0, "Generated array should not be empty");
        }
    }

    @Nested
    @DisplayName("handle short arrays")
    class ShortArrayTests {
        @Test
        @DisplayName("provide short array generator")
        void shouldProvideShorts() {
            // Arrange & Act
            var generator = PrimitiveArrayGenerators.resolveForType(short.class);

            // Assert type
            assertEquals(short.class, generator.getType(),
                    "Generator should return short.class as type");

            // Assert array generation
            var array = generator.next();
            assertNotNull(array, "Generated array should not be null");
            assertTrue(array.getClass().isArray(), "Generated object should be an array");

            var castArray = (short[]) array;
            assertNotNull(castArray, "Cast array should not be null");
            assertTrue(castArray.length > 0, "Generated array should not be empty");
        }
    }

    @Nested
    @DisplayName("handle error cases")
    class ErrorHandlingTests {
        @Test
        @DisplayName("throw exception for null type")
        void shouldHandleNullType() {
            // Act & Assert
            assertThrows(NullPointerException.class,
                    () -> PrimitiveArrayGenerators.resolveForType(null),
                    "Should throw NPE for null type");
        }

        @Test
        @DisplayName("throw exception for non-primitive type")
        void shouldHandleNonPrimitiveType() {
            // Act & Assert
            assertThrows(IllegalArgumentException.class,
                    () -> PrimitiveArrayGenerators.resolveForType(String.class),
                    "Should throw IllegalArgumentException for non-primitive type");
        }
    }
}
