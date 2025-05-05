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
import de.cuioss.test.generator.TypedGenerator;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@code @GeneratorsSource} is a variant of {@link TypeGeneratorFactorySource} that always uses
 * {@link Generators} as the factory class for creating {@link TypedGenerator} instances
 * for parameterized tests.
 * 
 * <p>
 * This annotation simplifies the use of the {@link Generators} utility class by eliminating
 * the need to specify the factory class. You only need to provide the generator type from the
 * {@link GeneratorType} enum and any required parameters specific to the generator type.
 * </p>
 * 
 * <h2>Generator Types</h2>
 * <p>
 * The {@link GeneratorType} enum provides access to all available generators, categorized as:
 * <ul>
 *   <li>String generators (e.g., NON_EMPTY_STRINGS, STRINGS, LETTER_STRINGS)</li>
 *   <li>Boolean generators (e.g., BOOLEANS, BOOLEAN_OBJECTS)</li>
 *   <li>Number generators (e.g., INTEGERS, DOUBLES, LONGS)</li>
 *   <li>Date and time generators (e.g., DATES, LOCAL_DATES, ZONED_DATE_TIMES)</li>
 *   <li>Other generators (e.g., CLASS_TYPES, LOCALES, URLS)</li>
 *   <li>Domain-specific generators (e.g., DOMAIN_EMAIL, DOMAIN_PERSON, DOMAIN_UUID)</li>
 * </ul>
 * 
 * 
 * <h2>Parameter Types</h2>
 * <p>
 * Generators have different parameter requirements:
 * <ul>
 *   <li>PARAMETERLESS: No additional parameters needed</li>
 *   <li>NEEDS_BOUNDS: Requires minSize and maxSize for string length</li>
 *   <li>NEEDS_RANGE: Requires low and high for numeric ranges</li>
 * </ul>
 * 
 * 
 * <h2>Example Usage</h2>
 * 
 * <pre>
 * {@code
 * // Parameterless generator
 * @ParameterizedTest
 * @GeneratorsSource(
 *     generator = GeneratorType.NON_EMPTY_STRINGS,
 *     count = 5
 * )
 * void testWithGeneratedStrings(String value) {
 *     assertNotNull(value);
 *     assertFalse(value.isEmpty());
 * }
 * 
 * // String generator with bounds
 * @ParameterizedTest
 * @GeneratorsSource(
 *     generator = GeneratorType.STRINGS,
 *     minSize = 3,
 *     maxSize = 10,
 *     count = 10
 * )
 * void testWithStringParameters(String value) {
 *     assertNotNull(value);
 *     assertTrue(value.length() >= 3 && value.length() <= 10);
 * }
 * 
 * // Number generator with range
 * @ParameterizedTest
 * @GeneratorsSource(
 *     generator = GeneratorType.INTEGERS,
 *     low = "1",
 *     high = "100",
 *     count = 10
 * )
 * void testWithNumberParameters(Integer value) {
 *     assertNotNull(value);
 *     assertTrue(value >= 1 && value <= 100);
 * }
 * 
 * // Domain-specific generator
 * @ParameterizedTest
 * @GeneratorsSource(
 *     generator = GeneratorType.DOMAIN_EMAIL,
 *     count = 3
 * )
 * void testWithEmailAddresses(String value) {
 *     assertNotNull(value);
 *     assertTrue(value.contains("@"));
 * }
 * 
 * // With specific seed for reproducible tests
 * @ParameterizedTest
 * @GeneratorsSource(
 *     generator = GeneratorType.STRINGS,
 *     minSize = 3,
 *     maxSize = 10,
 *     seed = 42L,
 *     count = 3
 * )
 * void testWithSpecificSeed(String value) {
 *     // This test will always generate the same values
 *     assertNotNull(value);
 * }
 * }
 * </pre>
 * 
 * @author Oliver Wolff
 * @since 2.0
 * @see TypedGenerator
 * @see Generators
 * @see GeneratorType
 * @see GeneratorsSourceArgumentsProvider
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ArgumentsSource(GeneratorsSourceArgumentsProvider.class)
public @interface GeneratorsSource {

    /**
     * The generator type to use from {@link Generators}.
     * 
     * <p>
     * Select a generator from the {@link GeneratorType} enum, which includes:
     * <ul>
     *   <li>String generators: NON_EMPTY_STRINGS, STRINGS, LETTER_STRINGS, etc.</li>
     *   <li>Number generators: INTEGERS, DOUBLES, LONGS, etc.</li>
     *   <li>Date/time generators: LOCAL_DATES, ZONED_DATE_TIMES, etc.</li>
     *   <li>Domain-specific generators: DOMAIN_EMAIL, DOMAIN_PERSON, etc.</li>
     *   <li>Other generators: BOOLEANS, URLS, LOCALES, etc.</li>
     * </ul>
     * 
     * @return the generator type
     */
    GeneratorType generator();


    /**
     * Minimum size for String-based generators.
     * Only applicable for String generators with parameter type NEEDS_BOUNDS
     * (e.g., STRINGS, LETTER_STRINGS).
     * 
     * @return the minimum size for strings
     */
    int minSize() default 0;

    /**
     * Maximum size for String-based generators.
     * Only applicable for String generators with parameter type NEEDS_BOUNDS
     * (e.g., STRINGS, LETTER_STRINGS).
     * 
     * @return the maximum size for strings
     */
    int maxSize() default 10;

    /**
     * Lower bound for number-based generators.
     * Only applicable for number generators with parameter type NEEDS_RANGE
     * (e.g., INTEGERS, DOUBLES, LONGS, FLOATS).
     * The value is parsed according to the generator's number type.
     * 
     * @return the lower bound as a string
     */
    String low() default "0";

    /**
     * Upper bound for number-based generators.
     * Only applicable for number generators with parameter type NEEDS_RANGE
     * (e.g., INTEGERS, DOUBLES, LONGS, FLOATS).
     * The value is parsed according to the generator's number type.
     * 
     * @return the upper bound as a string
     */
    String high() default "100";

    /**
     * Number of instances to generate.
     * This controls how many test invocations will occur with different generated values.
     * 
     * @return the number of instances to generate, defaults to 1
     */
    int count() default 1;

}
