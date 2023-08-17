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
package de.cuioss.test.generator;

import static de.cuioss.test.generator.Generators.asCollectionGenerator;
import static de.cuioss.test.generator.Generators.booleanObjects;
import static de.cuioss.test.generator.Generators.booleans;
import static de.cuioss.test.generator.Generators.byteObjects;
import static de.cuioss.test.generator.Generators.bytes;
import static de.cuioss.test.generator.Generators.characterObjects;
import static de.cuioss.test.generator.Generators.characters;
import static de.cuioss.test.generator.Generators.classTypes;
import static de.cuioss.test.generator.Generators.dates;
import static de.cuioss.test.generator.Generators.doubleObjects;
import static de.cuioss.test.generator.Generators.doubles;
import static de.cuioss.test.generator.Generators.enumValues;
import static de.cuioss.test.generator.Generators.enumValuesIfAvailable;
import static de.cuioss.test.generator.Generators.fixedValues;
import static de.cuioss.test.generator.Generators.floatObjects;
import static de.cuioss.test.generator.Generators.floats;
import static de.cuioss.test.generator.Generators.integerObjects;
import static de.cuioss.test.generator.Generators.integers;
import static de.cuioss.test.generator.Generators.letterStrings;
import static de.cuioss.test.generator.Generators.localDateTimes;
import static de.cuioss.test.generator.Generators.localDates;
import static de.cuioss.test.generator.Generators.localTimes;
import static de.cuioss.test.generator.Generators.locales;
import static de.cuioss.test.generator.Generators.longObjects;
import static de.cuioss.test.generator.Generators.longs;
import static de.cuioss.test.generator.Generators.nonBlankStrings;
import static de.cuioss.test.generator.Generators.nonEmptyStrings;
import static de.cuioss.test.generator.Generators.numbers;
import static de.cuioss.test.generator.Generators.runtimeExceptions;
import static de.cuioss.test.generator.Generators.serializables;
import static de.cuioss.test.generator.Generators.shortObjects;
import static de.cuioss.test.generator.Generators.shorts;
import static de.cuioss.test.generator.Generators.strings;
import static de.cuioss.test.generator.Generators.temporals;
import static de.cuioss.test.generator.Generators.throwables;
import static de.cuioss.test.generator.Generators.timeZones;
import static de.cuioss.test.generator.Generators.uniqueValues;
import static de.cuioss.test.generator.Generators.urls;
import static de.cuioss.test.generator.Generators.zoneIds;
import static de.cuioss.test.generator.Generators.zoneOffsets;
import static de.cuioss.test.generator.Generators.zonedDateTimes;
import static de.cuioss.tools.string.MoreStrings.isEmpty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.temporal.Temporal;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.impl.CollectionGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.GeneratorException;
import de.cuioss.tools.property.PropertyMemberInfo;

class GeneratorsTest {

    @Test
    void shouldHandleEnumsIfCorrectTypeGiven() {
        assertFalse(enumValuesIfAvailable(Integer.class).isPresent());
        assertFalse(enumValuesIfAvailable(null).isPresent());
        assertTrue(enumValuesIfAvailable(PropertyMemberInfo.class).isPresent());
        assertNotNull(enumValuesIfAvailable(PropertyMemberInfo.class).get().next());
        assertEquals(PropertyMemberInfo.class, enumValuesIfAvailable(PropertyMemberInfo.class).get().getType());
    }

    @Test
    void shouldHandleEnumsValues() {
        assertNotNull(enumValues(PropertyMemberInfo.class).next());
        assertEquals(PropertyMemberInfo.class, enumValues(PropertyMemberInfo.class).getType());
    }

    @Test
    void shouldHandleNonEmptyStrings() {
        assertFalse(isEmpty(nonEmptyStrings().next()));
        assertEquals(String.class, nonEmptyStrings().getType());
    }

    @Test
    void shouldHandleNonBlankStrings() {
        assertFalse(isEmpty(nonBlankStrings().next()));
        assertEquals(String.class, nonBlankStrings().getType());
    }

    @Test
    void shouldHandleStringsWithLimits() {
        assertFalse(isEmpty(strings(1, 2).next()));
        assertEquals(String.class, strings(1, 2).getType());
    }

    @Test
    void shouldHandleStrings() {
        assertEquals(String.class, strings().getType());
    }

    @Test
    void shouldHandleLetterStrings() {
        assertFalse(isEmpty(letterStrings().next()));
        assertEquals(String.class, letterStrings().getType());
    }

    @Test
    void shouldHandleLetterStringsWithLimits() {
        assertFalse(isEmpty(letterStrings(1, 2).next()));
        assertEquals(String.class, letterStrings(1, 2).getType());
    }

    @Test
    void shouldFailOnZeroSizeArray() {
        assertThrows(IllegalArgumentException.class, () -> fixedValues(String.class, new String[0]));
    }

    @Test
    void shouldFailOnEmptyIterable() {
        var values = new ArrayList<String>();
        assertThrows(IllegalArgumentException.class, () -> fixedValues(String.class, values));
    }

    @Test
    void shouldHandleStringVarArgs() {
        assertNotNull(fixedValues(String.class, "1", "2").next());
        assertNotNull(fixedValues("1", "2").next());
    }

    @Test
    void shouldHandleClassTypes() {
        assertNotNull(classTypes().next());
    }

    @Test
    void shouldHandleLocales() {
        assertNotNull(locales().next());
    }

    @Test
    void shouldHandleBooleanPrimitives() {
        assertNotNull(booleans().next());
    }

    @Test
    void shouldHandleBooleans() {
        assertNotNull(booleanObjects().next());
    }

    @Test
    void shouldHandleBytePrimitives() {
        assertNotNull(bytes().next());
    }

    @Test
    void shouldHandleBytes() {
        assertNotNull(byteObjects().next());
    }

    @Test
    void shouldHandleCharPrimitives() {
        assertNotNull(characters().next());
    }

    @Test
    void shouldHandleCharacter() {
        assertNotNull(characterObjects().next());
    }

    @Test
    void shouldHandleFloatPrimitives() {
        assertNotNull(floats().next());
        assertNotNull(floats(1, 5).next());
    }

    @Test
    void shouldHandleFloats() {
        assertNotNull(floatObjects().next());
    }

    @Test
    void shouldHandleDoublePrimitives() {
        assertNotNull(doubles().next());
    }

    @Test
    void shouldHandleDoubles() {
        assertNotNull(doubleObjects().next());
        assertNotNull(doubles(1, 4).next());
    }

    @Test
    void shouldHandleShortPrimitives() {
        assertNotNull(shorts().next());
    }

    @Test
    void shouldHandleShorts() {
        assertNotNull(shortObjects().next());
    }

    @Test
    void shouldHandleLongPrimitives() {
        assertNotNull(longs().next());
        assertNotNull(longs(1, 11).next());
    }

    @Test
    void shouldHandleLongs() {
        assertNotNull(longObjects().next());
    }

    @Test
    void shouldHandleDates() {
        assertNotNull(dates().next());
    }

    @Test
    void shouldHandleLocalDates() {
        assertNotNull(localDates().next());
    }

    @Test
    void shouldHandleLocalTimes() {
        assertNotNull(localTimes().next());
    }

    @Test
    void shouldHandleLocalDateTimes() {
        assertNotNull(localDateTimes().next());
    }

    @Test
    void shouldHandleTimeZones() {
        assertNotNull(timeZones().next());
    }

    @Test
    void shouldHandleZoneIds() {
        assertNotNull(zoneIds().next());
    }

    @Test
    void shouldHandleZoneOffset() {
        assertNotNull(zoneOffsets().next());
    }

    @Test
    void shouldHandleZonedDateTimes() {
        assertNotNull(zonedDateTimes().next());
    }

    @Test
    void shouldHandleIntegerPrimitives() {
        assertNotNull(integers().next());
    }

    @Test
    void shouldHandleNumber() {
        assertNotNull(numbers().next());
    }

    @Test
    void shouldHandleInteger() {
        assertNotNull(integerObjects().next());
        assertNotNull(integers(1, 31).next());
        assertNotNull(integers().next());
    }

    @Test
    void shouldHandleRuntimeExceptions() {
        assertNotNull(runtimeExceptions().next());
    }

    @Test
    void shouldHandleThrowable() {
        assertNotNull(throwables());
    }

    @Test
    void shouldHandleSerializables() {
        assertNotNull(serializables().next());
    }

    @Test
    void shouldHandleUrls() {
        assertNotNull(urls().next());
    }

    @Test
    void shouldHandleUniqueValues() {
        final TypedGenerator<String> generator = uniqueValues(nonEmptyStrings());
        assertNotNull(generator.next());
        assertNotEquals(generator.next(), generator.next());
    }

    @Test
    void shouldFailWithBooleanUniqueValues() {
        final TypedGenerator<Boolean> generator = uniqueValues(booleans());
        assertNotNull(generator.next());
        assertNotNull(generator.next());
        // Next should fail
        assertThrows(GeneratorException.class, generator::next);
    }

    @Test
    void shouldHandleCollections() {
        final CollectionGenerator<String> generator = asCollectionGenerator(nonEmptyStrings());
        assertNotNull(generator.list());
        assertNotNull(generator.set());
    }

    @Test
    void shouldHandleTemporals() {
        assertNotNull(temporals().next());
        assertEquals(Temporal.class, temporals().getType());
    }

    @Test
    void specificStringsWithSize() {
        final var result = strings("X", 2, 2).next();
        assertNotNull(result);
        assertEquals("XX", result);
    }
}
