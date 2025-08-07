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
package de.cuioss.test.generator;

import de.cuioss.test.generator.impl.CollectionGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.GeneratorException;
import de.cuioss.tools.property.PropertyMemberInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.temporal.Temporal;
import java.util.ArrayList;

import static de.cuioss.test.generator.Generators.*;
import static de.cuioss.tools.string.MoreStrings.isEmpty;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Generators utility class provides")
class GeneratorsTest {

    @Nested
    @DisplayName("enum handling capabilities that")
    class EnumHandling {

        @Test
        @DisplayName("should handle enums if correct type is given")
        void shouldHandleEnumsIfCorrectTypeGiven() {
            assertFalse(enumValuesIfAvailable(Integer.class).isPresent());
            assertFalse(enumValuesIfAvailable(null).isPresent());
            assertTrue(enumValuesIfAvailable(PropertyMemberInfo.class).isPresent());
            assertNotNull(enumValuesIfAvailable(PropertyMemberInfo.class).get().next());
            assertEquals(PropertyMemberInfo.class, enumValuesIfAvailable(PropertyMemberInfo.class).get().getType());
        }

        @Test
        @DisplayName("should handle enum values")
        void shouldHandleEnumsValues() {
            assertNotNull(enumValues(PropertyMemberInfo.class).next());
            assertEquals(PropertyMemberInfo.class, enumValues(PropertyMemberInfo.class).getType());
        }
    }

    @Nested
    @DisplayName("string generation capabilities that")
    class StringGeneration {

        @Test
        @DisplayName("should handle non-empty strings")
        void shouldHandleNonEmptyStrings() {
            assertFalse(isEmpty(nonEmptyStrings().next()));
            assertEquals(String.class, nonEmptyStrings().getType());
        }

        @Test
        @DisplayName("should handle non-blank strings")
        void shouldHandleNonBlankStrings() {
            assertFalse(isEmpty(nonBlankStrings().next()));
            assertEquals(String.class, nonBlankStrings().getType());
        }

        @Test
        @DisplayName("should handle strings with limits")
        void shouldHandleStringsWithLimits() {
            assertFalse(isEmpty(strings(1, 2).next()));
            assertEquals(String.class, strings(1, 2).getType());
        }

        @Test
        @DisplayName("should handle strings")
        void shouldHandleStrings() {
            assertEquals(String.class, strings().getType());
        }

        @Test
        @DisplayName("should handle letter strings")
        void shouldHandleLetterStrings() {
            assertFalse(isEmpty(letterStrings().next()));
            assertEquals(String.class, letterStrings().getType());
        }

        @Test
        @DisplayName("should handle letter strings with limits")
        void shouldHandleLetterStringsWithLimits() {
            assertFalse(isEmpty(letterStrings(1, 2).next()));
            assertEquals(String.class, letterStrings(1, 2).getType());
        }

        @Test
        @DisplayName("should handle specific strings with size")
        void specificStringsWithSize() {
            final var result = strings("X", 2, 2).next();
            assertNotNull(result);
            assertEquals("XX", result);
        }
    }

    @Nested
    @DisplayName("fixed value generation capabilities that")
    class FixedValueGeneration {

        @Test
        @DisplayName("should fail on zero size array")
        void shouldFailOnZeroSizeArray() {
            assertThrows(IllegalArgumentException.class, () -> fixedValues(String.class, new String[0]));
        }

        @Test
        @DisplayName("should fail on empty iterable")
        void shouldFailOnEmptyIterable() {
            var values = new ArrayList<String>();
            assertThrows(IllegalArgumentException.class, () -> fixedValues(String.class, values));
        }

        @Test
        @DisplayName("should handle string var args")
        void shouldHandleStringVarArgs() {
            assertNotNull(fixedValues(String.class, "1", "2").next());
            assertNotNull(fixedValues("1", "2").next());
        }
    }

    @Nested
    @DisplayName("primitive generation capabilities that")
    class PrimitiveGeneration {

        @Test
        @DisplayName("should handle class types")
        void shouldHandleClassTypes() {
            assertNotNull(classTypes().next());
        }

        @Test
        @DisplayName("should handle locales")
        void shouldHandleLocales() {
            assertNotNull(locales().next());
        }

        @Test
        @DisplayName("should handle boolean primitives")
        void shouldHandleBooleanPrimitives() {
            assertNotNull(booleans().next());
        }

        @Test
        @DisplayName("should handle booleans")
        void shouldHandleBooleans() {
            assertNotNull(booleanObjects().next());
        }

        @Test
        @DisplayName("should handle byte primitives")
        void shouldHandleBytePrimitives() {
            assertNotNull(bytes().next());
        }

        @Test
        @DisplayName("should handle bytes")
        void shouldHandleBytes() {
            assertNotNull(byteObjects().next());
        }

        @Test
        @DisplayName("should handle char primitives")
        void shouldHandleCharPrimitives() {
            assertNotNull(characters().next());
        }

        @Test
        @DisplayName("should handle characters")
        void shouldHandleCharacters() {
            assertNotNull(characterObjects().next());
        }

        @Test
        @DisplayName("should handle float primitives")
        void shouldHandleFloatPrimitives() {
            assertNotNull(floats().next());
            assertNotNull(floats(1, 5).next());
        }

        @Test
        @DisplayName("should handle floats")
        void shouldHandleFloats() {
            assertNotNull(floatObjects().next());
        }

        @Test
        @DisplayName("should handle double primitives")
        void shouldHandleDoublePrimitives() {
            assertNotNull(doubles().next());
        }

        @Test
        @DisplayName("should handle doubles")
        void shouldHandleDoubles() {
            assertNotNull(doubleObjects().next());
            assertNotNull(doubles(1, 4).next());
        }

        @Test
        @DisplayName("should handle short primitives")
        void shouldHandleShortPrimitives() {
            assertNotNull(shorts().next());
        }

        @Test
        @DisplayName("should handle shorts")
        void shouldHandleShorts() {
            assertNotNull(shortObjects().next());
        }

        @Test
        @DisplayName("should handle long primitives")
        void shouldHandleLongPrimitives() {
            assertNotNull(longs().next());
            assertNotNull(longs(1, 11).next());
        }

        @Test
        @DisplayName("should handle longs")
        void shouldHandleLongs() {
            assertNotNull(longObjects().next());
        }

        @Test
        @DisplayName("should handle dates")
        void shouldHandleDates() {
            assertNotNull(dates().next());
        }

        @Test
        @DisplayName("should handle local dates")
        void shouldHandleLocalDates() {
            assertNotNull(localDates().next());
        }

        @Test
        @DisplayName("should handle local times")
        void shouldHandleLocalTimes() {
            assertNotNull(localTimes().next());
        }

        @Test
        @DisplayName("should handle local date times")
        void shouldHandleLocalDateTimes() {
            assertNotNull(localDateTimes().next());
        }

        @Test
        @DisplayName("should handle time zones")
        void shouldHandleTimeZones() {
            assertNotNull(timeZones().next());
        }

        @Test
        @DisplayName("should handle zone ids")
        void shouldHandleZoneIds() {
            assertNotNull(zoneIds().next());
        }

        @Test
        @DisplayName("should handle zone offset")
        void shouldHandleZoneOffset() {
            assertNotNull(zoneOffsets().next());
        }

        @Test
        @DisplayName("should handle zoned date times")
        void shouldHandleZonedDateTimes() {
            assertNotNull(zonedDateTimes().next());
        }

        @Test
        @DisplayName("should handle integer primitives")
        void shouldHandleIntegerPrimitives() {
            assertNotNull(integers().next());
        }

        @Test
        @DisplayName("should handle numbers")
        void shouldHandleNumbers() {
            assertNotNull(numbers().next());
        }

        @Test
        @DisplayName("should handle integers")
        void shouldHandleIntegers() {
            assertNotNull(integerObjects().next());
            assertNotNull(integers(1, 31).next());
            assertNotNull(integers().next());
        }

        @Test
        @DisplayName("should handle runtime exceptions")
        void shouldHandleRuntimeExceptions() {
            assertNotNull(runtimeExceptions().next());
        }

        @Test
        @DisplayName("should handle throwables")
        void shouldHandleThrowables() {
            assertNotNull(throwables());
        }

        @Test
        @DisplayName("should handle serializables")
        void shouldHandleSerializables() {
            assertNotNull(serializables().next());
        }

        @Test
        @DisplayName("should handle urls")
        void shouldHandleUrls() {
            assertNotNull(urls().next());
        }

        @Test
        @DisplayName("should handle unique values")
        void shouldHandleUniqueValues() {
            final TypedGenerator<String> generator = uniqueValues(nonEmptyStrings());
            assertNotNull(generator.next());
            assertNotEquals(generator.next(), generator.next());
        }

        @Test
        @DisplayName("should fail with boolean unique values")
        void shouldFailWithBooleanUniqueValues() {
            final TypedGenerator<Boolean> generator = uniqueValues(booleans());
            assertNotNull(generator.next());
            assertNotNull(generator.next());
            // Next should fail
            assertThrows(GeneratorException.class, generator::next);
        }
    }

    @Nested
    @DisplayName("collection generation capabilities that")
    class CollectionGeneration {

        @Test
        @DisplayName("should handle collections")
        void shouldHandleCollections() {
            final CollectionGenerator<String> generator = asCollectionGenerator(nonEmptyStrings());
            assertNotNull(generator.list());
            assertNotNull(generator.set());
        }
    }

    @Nested
    @DisplayName("temporal generation capabilities that")
    class TemporalGeneration {

        @Test
        @DisplayName("should handle temporals")
        void shouldHandleTemporals() {
            assertNotNull(temporals().next());
            assertEquals(Temporal.class, temporals().getType());
        }
    }
}
