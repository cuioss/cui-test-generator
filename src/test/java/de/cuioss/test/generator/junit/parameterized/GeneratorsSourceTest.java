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
package de.cuioss.test.generator.junit.parameterized;

import de.cuioss.test.generator.domain.Person;
import de.cuioss.test.generator.junit.EnableGeneratorController;
import de.cuioss.tools.logging.CuiLogger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;

import java.io.Serializable;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link GeneratorsSource} and {@link GeneratorsSourceArgumentsProvider}.
 * Organized into nested test classes by generator type categories.
 */
@EnableGeneratorController
class GeneratorsSourceTest {

    private static final CuiLogger LOGGER = new CuiLogger(GeneratorsSourceTest.class);

    @ParameterizedTest
    @DisplayName("Should generate values with specific seed")
    @GeneratorsSource(
            generator = GeneratorType.STRINGS,
            minSize = 3,
            maxSize = 10,
            seed = 42L,
            count = 3
    )
    void shouldGenerateValuesWithSpecificSeed(String value) {
        LOGGER.debug("Seed test: {}", value);
        assertNotNull(value);
    }

    /**
     * Tests for string generators.
     */
    @Nested
    @DisplayName("String Generators")
    class StringGeneratorsTest {

        @ParameterizedTest
        @DisplayName("Should generate string values with size parameters")
        @GeneratorsSource(
                generator = GeneratorType.STRINGS,
                minSize = 3,
                maxSize = 10,
                count = 3
        )
        void shouldGenerateStrings(String value) {
            LOGGER.debug("String value test: {}", value);
            assertNotNull(value);
            assertTrue(value.length() >= 3 && value.length() <= 10);
        }

        @ParameterizedTest
        @DisplayName("Should generate letter string values")
        @GeneratorsSource(
                generator = GeneratorType.LETTER_STRINGS,
                minSize = 3,
                maxSize = 10,
                count = 3
        )
        void shouldGenerateLetterStrings(String value) {
            LOGGER.debug("Letter string test: {}", value);
            assertNotNull(value);
            assertTrue(value.length() >= 3 && value.length() <= 10);
            assertTrue(value.chars().allMatch(Character::isLetter));
        }

        @ParameterizedTest
        @DisplayName("Should generate non-empty strings")
        @GeneratorsSource(
                generator = GeneratorType.NON_EMPTY_STRINGS,
                count = 3
        )
        void shouldGenerateNonEmptyStrings(String value) {
            LOGGER.debug("Non-empty string test: {}", value);
            assertNotNull(value);
            assertFalse(value.isEmpty());
        }

        @ParameterizedTest
        @DisplayName("Should generate non-blank strings")
        @GeneratorsSource(
                generator = GeneratorType.NON_BLANK_STRINGS,
                count = 3
        )
        void shouldGenerateNonBlankStrings(String value) {
            LOGGER.debug("Non-blank string test: {}", value);
            assertNotNull(value);
            assertFalse(value.isBlank());
        }
    }

    /**
     * Tests for boolean generators.
     */
    @Nested
    @DisplayName("Boolean Generators")
    class BooleanGeneratorsTest {

        @ParameterizedTest
        @DisplayName("Should generate boolean primitive values")
        @GeneratorsSource(
                generator = GeneratorType.BOOLEANS,
                count = 3
        )
        void shouldGenerateBooleanValues(Boolean value) {
            LOGGER.debug("Boolean value test: {}", value);
            assertNotNull(value);
        }

        @ParameterizedTest
        @DisplayName("Should generate Boolean object values")
        @GeneratorsSource(
                generator = GeneratorType.BOOLEAN_OBJECTS,
                count = 3
        )
        void shouldGenerateBooleanObjectValues(Boolean value) {
            LOGGER.debug("Boolean object test: {}", value);
            assertNotNull(value);
        }
    }

    /**
     * Tests for character generators.
     */
    @Nested
    @DisplayName("Character Generators")
    class CharacterGeneratorsTest {

        @ParameterizedTest
        @DisplayName("Should generate character primitive values")
        @GeneratorsSource(
                generator = GeneratorType.CHARACTERS,
                count = 3
        )
        void shouldGenerateCharacterValues(Character value) {
            LOGGER.debug("Character value test: {}", value);
            assertNotNull(value);
        }

        @ParameterizedTest
        @DisplayName("Should generate Character object values")
        @GeneratorsSource(
                generator = GeneratorType.CHARACTER_OBJECTS,
                count = 3
        )
        void shouldGenerateCharacterObjectValues(Character value) {
            LOGGER.debug("Character object test: {}", value);
            assertNotNull(value);
        }
    }

    /**
     * Tests for number generators.
     */
    @Nested
    @DisplayName("Number Generators")
    class NumberGeneratorsTest {

        @ParameterizedTest
        @DisplayName("Should generate Number values")
        @GeneratorsSource(
                generator = GeneratorType.NUMBERS,
                count = 3
        )
        void shouldGenerateNumberValues(Number value) {
            LOGGER.debug("Number value test: {}", value);
            assertNotNull(value);
        }

        /**
         * Tests for integer generators.
         */
        @Nested
        @DisplayName("Integer Generators")
        class IntegerGeneratorsTest {

            @ParameterizedTest
            @DisplayName("Should generate integer values with range parameters")
            @GeneratorsSource(
                    generator = GeneratorType.INTEGERS,
                    low = "1",
                    high = "10",
                    count = 3
            )
            void shouldGenerateIntegerValuesWithParameters(Integer value) {
                LOGGER.debug("Integer value test: {}", value);
                assertNotNull(value);
                assertTrue(value >= 1 && value <= 10);
            }

            @ParameterizedTest
            @DisplayName("Should generate Integer object values")
            @GeneratorsSource(
                    generator = GeneratorType.INTEGER_OBJECTS,
                    count = 3
            )
            void shouldGenerateIntegerObjectValues(Integer value) {
                LOGGER.debug("Integer object test: {}", value);
                assertNotNull(value);
            }
        }

        /**
         * Tests for long generators.
         */
        @Nested
        @DisplayName("Long Generators")
        class LongGeneratorsTest {

            @ParameterizedTest
            @DisplayName("Should generate long values with range parameters")
            @GeneratorsSource(
                    generator = GeneratorType.LONGS,
                    low = "1",
                    high = "1000",
                    count = 3
            )
            void shouldGenerateLongValuesWithParameters(Long value) {
                LOGGER.debug("Long value test: {}", value);
                assertNotNull(value);
                assertTrue(value >= 1L && value <= 1000L);
            }

            @ParameterizedTest
            @DisplayName("Should generate Long object values")
            @GeneratorsSource(
                    generator = GeneratorType.LONG_OBJECTS,
                    count = 3
            )
            void shouldGenerateLongObjectValues(Long value) {
                LOGGER.debug("Long object test: {}", value);
                assertNotNull(value);
            }
        }

        /**
         * Tests for double generators.
         */
        @Nested
        @DisplayName("Double Generators")
        class DoubleGeneratorsTest {

            @ParameterizedTest
            @DisplayName("Should generate double values with range parameters")
            @GeneratorsSource(
                    generator = GeneratorType.DOUBLES,
                    low = "1.0",
                    high = "10.0",
                    count = 3
            )
            void shouldGenerateDoubleValuesWithParameters(Double value) {
                LOGGER.debug("Double value test: {}", value);
                assertNotNull(value);
                assertTrue(value >= 1.0 && value <= 10.0);
            }

            @ParameterizedTest
            @DisplayName("Should generate Double object values")
            @GeneratorsSource(
                    generator = GeneratorType.DOUBLE_OBJECTS,
                    count = 3
            )
            void shouldGenerateDoubleObjectValues(Double value) {
                LOGGER.debug("Double object test: {}", value);
                assertNotNull(value);
            }
        }

        /**
         * Tests for float generators.
         */
        @Nested
        @DisplayName("Float Generators")
        class FloatGeneratorsTest {

            @ParameterizedTest
            @DisplayName("Should generate float values with range parameters")
            @GeneratorsSource(
                    generator = GeneratorType.FLOATS,
                    low = "1.0",
                    high = "10.0",
                    count = 3
            )
            void shouldGenerateFloatValuesWithParameters(Float value) {
                LOGGER.debug("Float value test: {}", value);
                assertNotNull(value);
                assertTrue(value >= 1.0f && value <= 10.0f);
            }

            @ParameterizedTest
            @DisplayName("Should generate Float object values")
            @GeneratorsSource(
                    generator = GeneratorType.FLOAT_OBJECTS,
                    count = 3
            )
            void shouldGenerateFloatObjectValues(Float value) {
                LOGGER.debug("Float object test: {}", value);
                assertNotNull(value);
            }
        }

        /**
         * Tests for short generators.
         */
        @Nested
        @DisplayName("Short Generators")
        class ShortGeneratorsTest {

            @ParameterizedTest
            @DisplayName("Should generate short values with range parameters")
            @GeneratorsSource(
                    generator = GeneratorType.SHORTS,
                    count = 3
            )
            void shouldGenerateShortValuesWithParameters(Short value) {
                LOGGER.debug("Short value test: {}", value);
                assertNotNull(value);
            }

            @ParameterizedTest
            @DisplayName("Should generate Short object values")
            @GeneratorsSource(
                    generator = GeneratorType.SHORT_OBJECTS,
                    count = 3
            )
            void shouldGenerateShortObjectValues(Short value) {
                LOGGER.debug("Short object test: {}", value);
                assertNotNull(value);
            }
        }

        /**
         * Tests for byte generators.
         */
        @Nested
        @DisplayName("Byte Generators")
        class ByteGeneratorsTest {

            @ParameterizedTest
            @DisplayName("Should generate byte values with range parameters")
            @GeneratorsSource(
                    generator = GeneratorType.BYTES,
                    count = 3
            )
            void shouldGenerateByteValuesWithParameters(Byte value) {
                LOGGER.debug("Byte value test: {}", value);
                assertNotNull(value);
            }

            @ParameterizedTest
            @DisplayName("Should generate Byte object values")
            @GeneratorsSource(
                    generator = GeneratorType.BYTE_OBJECTS,
                    count = 3
            )
            void shouldGenerateByteObjectValues(Byte value) {
                LOGGER.debug("Byte object test: {}", value);
                assertNotNull(value);
            }
        }
    }

    /**
     * Tests for date and time generators.
     */
    @Nested
    @DisplayName("Date and Time Generators")
    class DateTimeGeneratorsTest {

        @ParameterizedTest
        @DisplayName("Should generate Date values")
        @GeneratorsSource(
                generator = GeneratorType.DATES,
                count = 3
        )
        void shouldGenerateDateValues(Date value) {
            LOGGER.debug("Date value test: {}", value);
            assertNotNull(value);
        }

        @ParameterizedTest
        @DisplayName("Should generate LocalDate values")
        @GeneratorsSource(
                generator = GeneratorType.LOCAL_DATES,
                count = 3
        )
        void shouldGenerateLocalDateValues(LocalDate value) {
            LOGGER.debug("LocalDate test: {}", value);
            assertNotNull(value);
        }

        @ParameterizedTest
        @DisplayName("Should generate LocalTime values")
        @GeneratorsSource(
                generator = GeneratorType.LOCAL_TIMES,
                count = 3
        )
        void shouldGenerateLocalTimeValues(LocalTime value) {
            LOGGER.debug("LocalTime test: {}", value);
            assertNotNull(value);
        }

        @ParameterizedTest
        @DisplayName("Should generate LocalDateTime values")
        @GeneratorsSource(
                generator = GeneratorType.LOCAL_DATE_TIMES,
                count = 3
        )
        void shouldGenerateLocalDateTimeValues(LocalDateTime value) {
            LOGGER.debug("LocalDateTime test: {}", value);
            assertNotNull(value);
        }

        @ParameterizedTest
        @DisplayName("Should generate ZonedDateTime values")
        @GeneratorsSource(
                generator = GeneratorType.ZONED_DATE_TIMES,
                count = 3
        )
        void shouldGenerateZonedDateTimeValues(ZonedDateTime value) {
            LOGGER.debug("ZonedDateTime test: {}", value);
            assertNotNull(value);
        }

        @ParameterizedTest
        @DisplayName("Should generate TimeZone values")
        @GeneratorsSource(
                generator = GeneratorType.TIME_ZONES,
                count = 3
        )
        void shouldGenerateTimeZoneValues(TimeZone value) {
            LOGGER.debug("TimeZone test: {}", value);
            assertNotNull(value);
        }

        @ParameterizedTest
        @DisplayName("Should generate ZoneId values")
        @GeneratorsSource(
                generator = GeneratorType.ZONE_IDS,
                count = 3
        )
        void shouldGenerateZoneIdValues(ZoneId value) {
            LOGGER.debug("ZoneId test: {}", value);
            assertNotNull(value);
        }

        @ParameterizedTest
        @DisplayName("Should generate ZoneOffset values")
        @GeneratorsSource(
                generator = GeneratorType.ZONE_OFFSETS,
                count = 3
        )
        void shouldGenerateZoneOffsetValues(ZoneOffset value) {
            LOGGER.debug("ZoneOffset test: {}", value);
            assertNotNull(value);
        }

        @ParameterizedTest
        @DisplayName("Should generate Temporal values")
        @GeneratorsSource(
                generator = GeneratorType.TEMPORALS,
                count = 3
        )
        void shouldGenerateTemporalValues(Temporal value) {
            LOGGER.debug("Temporal test: {}", value);
            assertNotNull(value);
        }
    }

    /**
     * Tests for other miscellaneous generators.
     */
    @Nested
    @DisplayName("Other Generators")
    class OtherGeneratorsTest {

        @ParameterizedTest
        @DisplayName("Should generate Class values")
        @GeneratorsSource(
                generator = GeneratorType.CLASS_TYPES,
                count = 3
        )
        void shouldGenerateClassValues(Class<?> value) {
            LOGGER.debug("Class test: {}", value);
            assertNotNull(value);
        }

        @ParameterizedTest
        @DisplayName("Should generate Locale values")
        @GeneratorsSource(
                generator = GeneratorType.LOCALES,
                count = 3
        )
        void shouldGenerateLocaleValues(Locale value) {
            LOGGER.debug("Locale test: {}", value);
            assertNotNull(value);
        }

        @ParameterizedTest
        @DisplayName("Should generate Serializable values")
        @GeneratorsSource(
                generator = GeneratorType.SERIALIZABLES,
                count = 3
        )
        void shouldGenerateSerializableValues(Serializable value) {
            LOGGER.debug("Serializable test: {}", value);
            assertNotNull(value);
        }

        @ParameterizedTest
        @DisplayName("Should generate RuntimeException values")
        @GeneratorsSource(
                generator = GeneratorType.RUNTIME_EXCEPTIONS,
                count = 3
        )
        void shouldGenerateRuntimeExceptionValues(RuntimeException value) {
            LOGGER.debug("RuntimeException test: {}", value);
            assertNotNull(value);
        }

        @ParameterizedTest
        @DisplayName("Should generate Throwable values")
        @GeneratorsSource(
                generator = GeneratorType.THROWABLES,
                count = 3
        )
        void shouldGenerateThrowableValues(Throwable value) {
            LOGGER.debug("Throwable test: {}", value);
            assertNotNull(value);
        }

        @ParameterizedTest
        @DisplayName("Should generate URL values")
        @GeneratorsSource(
                generator = GeneratorType.URLS,
                count = 3
        )
        void shouldGenerateUrlValues(URL value) {
            LOGGER.debug("URL test: {}", value);
            assertNotNull(value);
        }
    }

    /**
     * Tests for domain-specific generators.
     */
    @Nested
    @DisplayName("Domain-Specific Generators")
    class DomainGeneratorsTest {

        @ParameterizedTest
        @DisplayName("Should generate blind text")
        @GeneratorsSource(
                generator = GeneratorType.DOMAIN_BLIND_TEXT,
                count = 3
        )
        void shouldGenerateBlindText(String value) {
            LOGGER.debug("Blind text test: {}", value);
            assertNotNull(value);
            assertFalse(value.isEmpty());
        }

        @ParameterizedTest
        @DisplayName("Should generate city names")
        @GeneratorsSource(
                generator = GeneratorType.DOMAIN_CITY,
                count = 3
        )
        void shouldGenerateCityNames(String value) {
            LOGGER.debug("City name test: {}", value);
            assertNotNull(value);
            assertFalse(value.isEmpty());
        }

        @ParameterizedTest
        @DisplayName("Should generate distinguished names")
        @GeneratorsSource(
                generator = GeneratorType.DOMAIN_DISTINGUISHED_NAMES,
                count = 3
        )
        void shouldGenerateDistinguishedNames(String value) {
            LOGGER.debug("Distinguished name test: {}", value);
            assertNotNull(value);
            assertFalse(value.isEmpty());
        }

        @ParameterizedTest
        @DisplayName("Should generate email addresses")
        @GeneratorsSource(
                generator = GeneratorType.DOMAIN_EMAIL,
                count = 3
        )
        void shouldGenerateEmailAddresses(String value) {
            LOGGER.debug("Email test: {}", value);
            assertNotNull(value);
            assertTrue(value.contains("@"));
        }

        @ParameterizedTest
        @DisplayName("Should generate full names")
        @GeneratorsSource(
                generator = GeneratorType.DOMAIN_FULL_NAME,
                count = 3
        )
        void shouldGenerateFullNames(String value) {
            LOGGER.debug("Full name test: {}", value);
            assertNotNull(value);
            assertTrue(value.contains(" "));
        }

        @ParameterizedTest
        @DisplayName("Should generate mail subjects")
        @GeneratorsSource(
                generator = GeneratorType.DOMAIN_MAIL_SUBJECT,
                count = 3
        )
        void shouldGenerateMailSubjects(String value) {
            LOGGER.debug("Mail subject test: {}", value);
            assertNotNull(value);
            assertFalse(value.isEmpty());
        }

        @ParameterizedTest
        @DisplayName("Should generate person objects")
        @GeneratorsSource(
                generator = GeneratorType.DOMAIN_PERSON,
                count = 3
        )
        void shouldGeneratePersonObjects(Person value) {
            LOGGER.debug("Person object test: {}", value);
            assertNotNull(value);
            assertNotNull(value.getFirstname());
            assertNotNull(value.getLastname());
        }

        @ParameterizedTest
        @DisplayName("Should generate phone numbers")
        @GeneratorsSource(
                generator = GeneratorType.DOMAIN_PHONE_NUMBER,
                count = 3
        )
        void shouldGeneratePhoneNumbers(String value) {
            LOGGER.debug("Phone number test: {}", value);
            assertNotNull(value);
            assertFalse(value.isEmpty());
        }

        @ParameterizedTest
        @DisplayName("Should generate street names")
        @GeneratorsSource(
                generator = GeneratorType.DOMAIN_STREET_NAME,
                count = 3
        )
        void shouldGenerateStreetNames(String value) {
            LOGGER.debug("Street name test: {}", value);
            assertNotNull(value);
            assertFalse(value.isEmpty());
        }

        @ParameterizedTest
        @DisplayName("Should generate streets")
        @GeneratorsSource(
                generator = GeneratorType.DOMAIN_STREET,
                count = 3
        )
        void shouldGenerateStreets(String value) {
            LOGGER.debug("Street test: {}", value);
            assertNotNull(value);
            assertFalse(value.isEmpty());
        }

        @ParameterizedTest
        @DisplayName("Should generate UUIDs")
        @GeneratorsSource(
                generator = GeneratorType.DOMAIN_UUID_STRING,
                count = 3
        )
        void shouldGenerateUUIDs(String value) {
            LOGGER.debug("UUID test: {}", value);
            assertNotNull(value);
            assertEquals(36, value.length());
            assertEquals(4, value.split("-").length - 1);
        }

        @ParameterizedTest
        @DisplayName("Should generate UUID objects")
        @GeneratorsSource(
                generator = GeneratorType.DOMAIN_UUID,
                count = 3
        )
        void shouldGenerateUUIDObjects(UUID value) {
            LOGGER.debug("UUID object test: {}", value);
            assertNotNull(value);
        }

        @ParameterizedTest
        @DisplayName("Should generate zip codes")
        @GeneratorsSource(
                generator = GeneratorType.DOMAIN_ZIP_CODE,
                count = 3
        )
        void shouldGenerateZipCodes(Integer value) {
            LOGGER.debug("Zip code test: {}", value);
            assertNotNull(value);
            assertTrue(value > 0);
        }
    }
}
