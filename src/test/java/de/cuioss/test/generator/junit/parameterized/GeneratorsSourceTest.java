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
package de.cuioss.test.generator.junit.parameterized;

import de.cuioss.test.generator.domain.Person;
import de.cuioss.test.generator.junit.EnableGeneratorController;
import de.cuioss.test.generator.junit.GeneratorSeed;
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


    @ParameterizedTest
    @DisplayName("Should generate values with specific seed")
    @GeneratorSeed(42L)
    @GeneratorsSource(
            generator = GeneratorType.STRINGS,
            minSize = 3,
            maxSize = 10,
            count = 3
    )
    void shouldGenerateValuesWithSpecificSeed(String value) {

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

            assertNotNull(value);
        }

        @ParameterizedTest
        @DisplayName("Should generate Boolean object values")
        @GeneratorsSource(
                generator = GeneratorType.BOOLEAN_OBJECTS,
                count = 3
        )
        void shouldGenerateBooleanObjectValues(Boolean value) {

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

            assertNotNull(value);
        }

        @ParameterizedTest
        @DisplayName("Should generate Character object values")
        @GeneratorsSource(
                generator = GeneratorType.CHARACTER_OBJECTS,
                count = 3
        )
        void shouldGenerateCharacterObjectValues(Character value) {

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

                assertNotNull(value);
            }

            @ParameterizedTest
            @DisplayName("Should generate Short object values")
            @GeneratorsSource(
                    generator = GeneratorType.SHORT_OBJECTS,
                    count = 3
            )
            void shouldGenerateShortObjectValues(Short value) {

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

                assertNotNull(value);
            }

            @ParameterizedTest
            @DisplayName("Should generate Byte object values")
            @GeneratorsSource(
                    generator = GeneratorType.BYTE_OBJECTS,
                    count = 3
            )
            void shouldGenerateByteObjectValues(Byte value) {

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

            assertNotNull(value);
        }

        @ParameterizedTest
        @DisplayName("Should generate LocalDate values")
        @GeneratorsSource(
                generator = GeneratorType.LOCAL_DATES,
                count = 3
        )
        void shouldGenerateLocalDateValues(LocalDate value) {

            assertNotNull(value);
        }

        @ParameterizedTest
        @DisplayName("Should generate LocalTime values")
        @GeneratorsSource(
                generator = GeneratorType.LOCAL_TIMES,
                count = 3
        )
        void shouldGenerateLocalTimeValues(LocalTime value) {

            assertNotNull(value);
        }

        @ParameterizedTest
        @DisplayName("Should generate LocalDateTime values")
        @GeneratorsSource(
                generator = GeneratorType.LOCAL_DATE_TIMES,
                count = 3
        )
        void shouldGenerateLocalDateTimeValues(LocalDateTime value) {

            assertNotNull(value);
        }

        @ParameterizedTest
        @DisplayName("Should generate ZonedDateTime values")
        @GeneratorsSource(
                generator = GeneratorType.ZONED_DATE_TIMES,
                count = 3
        )
        void shouldGenerateZonedDateTimeValues(ZonedDateTime value) {

            assertNotNull(value);
        }

        @ParameterizedTest
        @DisplayName("Should generate TimeZone values")
        @GeneratorsSource(
                generator = GeneratorType.TIME_ZONES,
                count = 3
        )
        void shouldGenerateTimeZoneValues(TimeZone value) {

            assertNotNull(value);
        }

        @ParameterizedTest
        @DisplayName("Should generate ZoneId values")
        @GeneratorsSource(
                generator = GeneratorType.ZONE_IDS,
                count = 3
        )
        void shouldGenerateZoneIdValues(ZoneId value) {

            assertNotNull(value);
        }

        @ParameterizedTest
        @DisplayName("Should generate ZoneOffset values")
        @GeneratorsSource(
                generator = GeneratorType.ZONE_OFFSETS,
                count = 3
        )
        void shouldGenerateZoneOffsetValues(ZoneOffset value) {

            assertNotNull(value);
        }

        @ParameterizedTest
        @DisplayName("Should generate Temporal values")
        @GeneratorsSource(
                generator = GeneratorType.TEMPORALS,
                count = 3
        )
        void shouldGenerateTemporalValues(Temporal value) {

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

            assertNotNull(value);
        }

        @ParameterizedTest
        @DisplayName("Should generate Locale values")
        @GeneratorsSource(
                generator = GeneratorType.LOCALES,
                count = 3
        )
        void shouldGenerateLocaleValues(Locale value) {

            assertNotNull(value);
        }

        @ParameterizedTest
        @DisplayName("Should generate Serializable values")
        @GeneratorsSource(
                generator = GeneratorType.SERIALIZABLES,
                count = 3
        )
        void shouldGenerateSerializableValues(Serializable value) {

            assertNotNull(value);
        }

        @ParameterizedTest
        @DisplayName("Should generate RuntimeException values")
        @GeneratorsSource(
                generator = GeneratorType.RUNTIME_EXCEPTIONS,
                count = 3
        )
        void shouldGenerateRuntimeExceptionValues(RuntimeException value) {

            assertNotNull(value);
        }

        @ParameterizedTest
        @DisplayName("Should generate Throwable values")
        @GeneratorsSource(
                generator = GeneratorType.THROWABLES,
                count = 3
        )
        void shouldGenerateThrowableValues(Throwable value) {

            assertNotNull(value);
        }

        @ParameterizedTest
        @DisplayName("Should generate URL values")
        @GeneratorsSource(
                generator = GeneratorType.URLS,
                count = 3
        )
        void shouldGenerateUrlValues(URL value) {

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

            assertNotNull(value);
        }

        @ParameterizedTest
        @DisplayName("Should generate zip codes")
        @GeneratorsSource(
                generator = GeneratorType.DOMAIN_ZIP_CODE,
                count = 3
        )
        void shouldGenerateZipCodes(Integer value) {

            assertNotNull(value);
            assertTrue(value > 0);
        }
    }
}
