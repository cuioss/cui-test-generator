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

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.domain.BlindTextGenerator;
import de.cuioss.test.generator.domain.CityGenerator;
import de.cuioss.test.generator.domain.DistinguishedNamesGenerator;
import de.cuioss.test.generator.domain.EmailGenerator;
import de.cuioss.test.generator.domain.FullNameGenerator;
import de.cuioss.test.generator.domain.MailSubjectGenerator;
import de.cuioss.test.generator.domain.Person;
import de.cuioss.test.generator.domain.PersonGenerator;
import de.cuioss.test.generator.domain.PhoneNumberGenerator;
import de.cuioss.test.generator.domain.StreetGenerator;
import de.cuioss.test.generator.domain.StreetNameGenerator;
import de.cuioss.test.generator.domain.UUIDGenerator;
import de.cuioss.test.generator.domain.UUIDStringGenerator;
import de.cuioss.test.generator.domain.ZipCodeGenerator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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

/**
 * Enum representing all available generator types from the {@link de.cuioss.test.generator.Generators} class.
 * Used with {@link GeneratorsSource} to specify which generator method to use.
 *
 * @author Oliver Wolff
 * @since 2.3
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum GeneratorType {

    // String generators
    /** Generates non-empty String values that may contain whitespace or special characters. */
    NON_EMPTY_STRINGS("nonEmptyStrings", Generators.class, String.class, GeneratorParameterType.PARAMETERLESS),

    /** Generates non-blank String values that contain at least one non-whitespace character. */
    NON_BLANK_STRINGS("nonBlankStrings", Generators.class, String.class, GeneratorParameterType.PARAMETERLESS),

    /** Generates String values with length between minSize and maxSize, containing various characters. */
    STRINGS("strings", Generators.class, String.class, GeneratorParameterType.NEEDS_BOUNDS),

    /** Generates String values with length between minSize and maxSize, containing only letters. */
    LETTER_STRINGS("letterStrings", Generators.class, String.class, GeneratorParameterType.NEEDS_BOUNDS),

    // Boolean generators
    /** Generates random boolean primitive values (true or false). */
    BOOLEANS("booleans", Generators.class, Boolean.class, GeneratorParameterType.PARAMETERLESS),

    /** Generates random Boolean object values (True or False). */
    BOOLEAN_OBJECTS("booleanObjects", Generators.class, Boolean.class, GeneratorParameterType.PARAMETERLESS),

    // Byte generators
    /** Generates random byte primitive values within the full byte range. */
    BYTES("bytes", Generators.class, Byte.class, GeneratorParameterType.PARAMETERLESS),

    /** Generates random Byte object values within the full byte range. */
    BYTE_OBJECTS("byteObjects", Generators.class, Byte.class, GeneratorParameterType.PARAMETERLESS),

    // Character generators
    /** Generates random character primitive values. */
    CHARACTERS("characters", Generators.class, Character.class, GeneratorParameterType.PARAMETERLESS),

    /** Generates random Character object values. */
    CHARACTER_OBJECTS("characterObjects", Generators.class, Character.class, GeneratorParameterType.PARAMETERLESS),

    // Double generators
    /** Generates double primitive values within the specified range (low to high). */
    DOUBLES("doubles", Generators.class, Double.class, GeneratorParameterType.NEEDS_RANGE),

    /** Generates random Double object values across the full double range. */
    DOUBLE_OBJECTS("doubleObjects", Generators.class, Double.class, GeneratorParameterType.PARAMETERLESS),

    // Float generators
    /** Generates float primitive values within the specified range (low to high). */
    FLOATS("floats", Generators.class, Float.class, GeneratorParameterType.NEEDS_RANGE),

    /** Generates random Float object values across the full float range. */
    FLOAT_OBJECTS("floatObjects", Generators.class, Float.class, GeneratorParameterType.PARAMETERLESS),

    // Integer generators
    /** Generates integer primitive values within the specified range (low to high). */
    INTEGERS("integers", Generators.class, Integer.class, GeneratorParameterType.NEEDS_RANGE),

    /** Generates random Integer object values across the full integer range. */
    INTEGER_OBJECTS("integerObjects", Generators.class, Integer.class, GeneratorParameterType.PARAMETERLESS),

    // Number generator
    /** Generates random Number objects of various numeric types. */
    NUMBERS("numbers", Generators.class, Number.class, GeneratorParameterType.PARAMETERLESS),

    // Short generators
    /** Generates random short primitive values within the full short range. */
    SHORTS("shorts", Generators.class, Short.class, GeneratorParameterType.PARAMETERLESS),

    /** Generates random Short object values within the full short range. */
    SHORT_OBJECTS("shortObjects", Generators.class, Short.class, GeneratorParameterType.PARAMETERLESS),

    // Long generators
    /** Generates long primitive values within the specified range (low to high). */
    LONGS("longs", Generators.class, Long.class, GeneratorParameterType.NEEDS_RANGE),

    /** Generates random Long object values across the full long range. */
    LONG_OBJECTS("longObjects", Generators.class, Long.class, GeneratorParameterType.PARAMETERLESS),

    // Date and time generators
    /** Generates random java.util.Date objects with varying dates and times. */
    DATES("dates", Generators.class, Date.class, GeneratorParameterType.PARAMETERLESS),

    /** Generates random LocalDate objects representing dates without time components. */
    LOCAL_DATES("localDates", Generators.class, LocalDate.class, GeneratorParameterType.PARAMETERLESS),

    /** Generates random LocalTime objects representing times without date components. */
    LOCAL_TIMES("localTimes", Generators.class, LocalTime.class, GeneratorParameterType.PARAMETERLESS),

    /** Generates random LocalDateTime objects representing date-time combinations without time zones. */
    LOCAL_DATE_TIMES("localDateTimes", Generators.class, LocalDateTime.class, GeneratorParameterType.PARAMETERLESS),

    /** Generates random ZonedDateTime objects representing date-time combinations with time zones. */
    ZONED_DATE_TIMES("zonedDateTimes", Generators.class, ZonedDateTime.class, GeneratorParameterType.PARAMETERLESS),

    /** Generates random TimeZone objects representing various time zones. */
    TIME_ZONES("timeZones", Generators.class, TimeZone.class, GeneratorParameterType.PARAMETERLESS),

    /** Generates random ZoneId objects representing time zone identifiers. */
    ZONE_IDS("zoneIds", Generators.class, ZoneId.class, GeneratorParameterType.PARAMETERLESS),

    /** Generates random ZoneOffset objects representing time zone offsets from UTC. */
    ZONE_OFFSETS("zoneOffsets", Generators.class, ZoneOffset.class, GeneratorParameterType.PARAMETERLESS),

    /** Generates random Temporal objects of various temporal types (dates, times, etc.). */
    TEMPORALS("temporals", Generators.class, Temporal.class, GeneratorParameterType.PARAMETERLESS),

    // Other generators
    /** Generates random Class objects representing various Java types. */
    CLASS_TYPES("classTypes", Generators.class, Class.class, GeneratorParameterType.PARAMETERLESS),

    /** Generates random Locale objects representing different language and country combinations. */
    LOCALES("locales", Generators.class, Locale.class, GeneratorParameterType.PARAMETERLESS),

    /** Generates random Serializable objects of various types. */
    SERIALIZABLES("serializables", Generators.class, Serializable.class, GeneratorParameterType.PARAMETERLESS),

    /** Generates random RuntimeException instances with various exception types. */
    RUNTIME_EXCEPTIONS("runtimeExceptions", Generators.class, RuntimeException.class, GeneratorParameterType.PARAMETERLESS),

    /** Generates random Throwable instances with various exception and error types. */
    THROWABLES("throwables", Generators.class, Throwable.class, GeneratorParameterType.PARAMETERLESS),

    /** Generates random URL objects with various protocols, hosts, and paths. */
    URLS("urls", Generators.class, URL.class, GeneratorParameterType.PARAMETERLESS),

    /** Generates values from a fixed set of predefined values. */
    FIXED_VALUES("fixedValues", Generators.class, Object.class, GeneratorParameterType.PARAMETERLESS),

    // Domain-specific generators
    /** Generates random placeholder text (lorem ipsum style) for testing text-heavy components. */
    DOMAIN_BLIND_TEXT(null, BlindTextGenerator.class, String.class, GeneratorParameterType.PARAMETERLESS),

    /** Generates random city names for address-related testing. */
    DOMAIN_CITY(null, CityGenerator.class, String.class, GeneratorParameterType.PARAMETERLESS),

    /** Generates random distinguished names (DNs) for LDAP/directory testing. */
    DOMAIN_DISTINGUISHED_NAMES(null, DistinguishedNamesGenerator.class, String.class, GeneratorParameterType.PARAMETERLESS),

    /** Generates random email addresses in the format firstname.lastname@domain.tld. */
    DOMAIN_EMAIL(null, EmailGenerator.class, String.class, GeneratorParameterType.PARAMETERLESS),

    /** Generates random full names (first and last name combinations). */
    DOMAIN_FULL_NAME(null, FullNameGenerator.class, String.class, GeneratorParameterType.PARAMETERLESS),

    /** Generates random email subject lines for testing email functionality. */
    DOMAIN_MAIL_SUBJECT(null, MailSubjectGenerator.class, String.class, GeneratorParameterType.PARAMETERLESS),

    /** Generates random Person objects with first name, last name, and other personal details. */
    DOMAIN_PERSON(null, PersonGenerator.class, Person.class, GeneratorParameterType.PARAMETERLESS),

    /** Generates random phone numbers in various formats. */
    DOMAIN_PHONE_NUMBER(null, PhoneNumberGenerator.class, String.class, GeneratorParameterType.PARAMETERLESS),

    /** Generates random street addresses including street name and house number. */
    DOMAIN_STREET(null, StreetGenerator.class, String.class, GeneratorParameterType.PARAMETERLESS),

    /** Generates random street names without house numbers. */
    DOMAIN_STREET_NAME(null, StreetNameGenerator.class, String.class, GeneratorParameterType.PARAMETERLESS),

    /** Generates random UUID objects for unique identifier testing. */
    DOMAIN_UUID(null, UUIDGenerator.class, UUID.class, GeneratorParameterType.PARAMETERLESS),

    /** Generates random UUID strings in standard UUID format. */
    DOMAIN_UUID_STRING(null, UUIDStringGenerator.class, String.class, GeneratorParameterType.PARAMETERLESS),

    /** Generates random zip/postal codes as integers. */
    DOMAIN_ZIP_CODE(null, ZipCodeGenerator.class, Integer.class, GeneratorParameterType.PARAMETERLESS);

    private final String methodName;
    private final Class<?> factoryClass;
    private final Class<?> returnType;
    private final GeneratorParameterType parameterType;


}
