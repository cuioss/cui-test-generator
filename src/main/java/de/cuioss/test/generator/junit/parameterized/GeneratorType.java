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
import de.cuioss.test.generator.domain.OrganizationNameGenerator;
import de.cuioss.test.generator.domain.PersonGenerator;
import de.cuioss.test.generator.domain.PhoneNumberGenerator;
import de.cuioss.test.generator.domain.StreetGenerator;
import de.cuioss.test.generator.domain.StreetNameGenerator;
import de.cuioss.test.generator.domain.TitleGenerator;
import de.cuioss.test.generator.domain.UUIDGenerator;
import de.cuioss.test.generator.domain.UUIDStringGenerator;
import de.cuioss.test.generator.domain.ZipCodeGenerator;

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

/**
 * Enum representing all available generator types from the {@link de.cuioss.test.generator.Generators} class.
 * Used with {@link GeneratorsSource} to specify which generator method to use.
 * 
 * @author Oliver Wolff
 * @since 2.3
 */
public enum GeneratorType {

    // String generators
    NON_EMPTY_STRINGS("nonEmptyStrings", Generators.class, String.class),
    NON_BLANK_STRINGS("nonBlankStrings", Generators.class, String.class),
    STRINGS("strings", Generators.class, String.class),
    LETTER_STRINGS("letterStrings", Generators.class, String.class),

    // Boolean generators
    BOOLEANS("booleans", Generators.class, Boolean.class),
    BOOLEAN_OBJECTS("booleanObjects", Generators.class, Boolean.class),

    // Byte generators
    BYTES("bytes", Generators.class, Byte.class),
    BYTE_OBJECTS("byteObjects", Generators.class, Byte.class),

    // Character generators
    CHARACTERS("characters", Generators.class, Character.class),
    CHARACTER_OBJECTS("characterObjects", Generators.class, Character.class),

    // Double generators
    DOUBLES("doubles", Generators.class, Double.class),
    DOUBLE_OBJECTS("doubleObjects", Generators.class, Double.class),

    // Float generators
    FLOATS("floats", Generators.class, Float.class),
    FLOAT_OBJECTS("floatObjects", Generators.class, Float.class),

    // Integer generators
    INTEGERS("integers", Generators.class, Integer.class),
    INTEGER_OBJECTS("integerObjects", Generators.class, Integer.class),

    // Number generator
    NUMBERS("numbers", Generators.class, Number.class),

    // Short generators
    SHORTS("shorts", Generators.class, Short.class),
    SHORT_OBJECTS("shortObjects", Generators.class, Short.class),

    // Long generators
    LONGS("longs", Generators.class, Long.class),
    LONG_OBJECTS("longObjects", Generators.class, Long.class),

    // Date and time generators
    DATES("dates", Generators.class, Date.class),
    LOCAL_DATES("localDates", Generators.class, LocalDate.class),
    LOCAL_TIMES("localTimes", Generators.class, LocalTime.class),
    LOCAL_DATE_TIMES("localDateTimes", Generators.class, LocalDateTime.class),
    ZONED_DATE_TIMES("zonedDateTimes", Generators.class, ZonedDateTime.class),
    TIME_ZONES("timeZones", Generators.class, TimeZone.class),
    ZONE_IDS("zoneIds", Generators.class, ZoneId.class),
    ZONE_OFFSETS("zoneOffsets", Generators.class, ZoneOffset.class),
    TEMPORALS("temporals", Generators.class, Temporal.class),

    // Other generators
    CLASS_TYPES("classTypes", Generators.class, Class.class),
    LOCALES("locales", Generators.class, Locale.class),
    SERIALIZABLES("serializables", Generators.class, Serializable.class),
    RUNTIME_EXCEPTIONS("runtimeExceptions", Generators.class, RuntimeException.class),
    THROWABLES("throwables", Generators.class, Throwable.class),
    URLS("urls", Generators.class, URL.class),
    FIXED_VALUES("fixedValues", Generators.class, Object.class),

    // Domain-specific generators
    DOMAIN_BLIND_TEXT(null, BlindTextGenerator.class, String.class),
    DOMAIN_CITY(null, CityGenerator.class, String.class),
    DOMAIN_DISTINGUISHED_NAMES(null, DistinguishedNamesGenerator.class, String.class),
    DOMAIN_EMAIL(null, EmailGenerator.class, String.class),
    DOMAIN_FULL_NAME(null, FullNameGenerator.class, String.class),
    DOMAIN_MAIL_SUBJECT(null, MailSubjectGenerator.class, String.class),
    DOMAIN_ORGANIZATION_NAME(null, OrganizationNameGenerator.class, String.class),
    DOMAIN_PERSON(null, PersonGenerator.class, String.class),
    DOMAIN_PHONE_NUMBER(null, PhoneNumberGenerator.class, String.class),
    DOMAIN_STREET(null, StreetGenerator.class, String.class),
    DOMAIN_STREET_NAME(null, StreetNameGenerator.class, String.class),
    DOMAIN_TITLE(null, TitleGenerator.class, String.class),
    DOMAIN_UUID(null, UUIDGenerator.class, String.class),
    DOMAIN_UUID_STRING(null, UUIDStringGenerator.class, String.class),
    DOMAIN_ZIP_CODE(null, ZipCodeGenerator.class, String.class);

    private final String methodName;
    private final Class<?> factoryClass;
    private final Class<?> returnType;

    GeneratorType(String methodName, Class<?> factoryClass, Class<?> returnType) {
        this.methodName = methodName;
        this.factoryClass = factoryClass;
        this.returnType = returnType;
    }

    /**
     * Gets the method name in the factory class that corresponds to this generator type.
     * Will be null for domain-specific generators that use a constructor instead of a factory method.
     * 
     * @return the method name or null for domain-specific generators
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Gets the factory class that provides this generator type.
     * For standard generators, this will be {@link Generators}.
     * For domain-specific generators, this will be the generator class itself.
     * 
     * @return the factory class
     */
    public Class<?> getFactoryClass() {
        return factoryClass;
    }

    /**
     * Gets the return type of this generator.
     * 
     * @return the class representing the type of values this generator produces
     */
    public Class<?> getReturnType() {
        return returnType;
    }

}
