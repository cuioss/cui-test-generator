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
 * the need to specify the factory class. You only need to provide the factory method name
 * and any required parameters specific to the generator type.
 * </p>
 * 
 * <h2>Example Usage</h2>
 * 
 * <pre>
 * {@code
 * @ParameterizedTest
 * @GeneratorsSource(
 *     generator = "nonEmptyStrings",
 *     count = 5
 * )
 * void testWithGeneratedStrings(String value) {
 *     assertNotNull(value);
 *     assertFalse(value.isEmpty());
 * }
 * 
 * // With string parameters
 * @ParameterizedTest
 * @GeneratorsSource(
 *     generator = "strings",
 *     minSize = 3,
 *     maxSize = 10,
 *     count = 10
 * )
 * void testWithStringParameters(String value) {
 *     assertNotNull(value);
 *     assertTrue(value.length() >= 3 && value.length() <= 10);
 * }
 * 
 * // With number parameters
 * @ParameterizedTest
 * @GeneratorsSource(
 *     generator = "integers",
 *     low = "1",
 *     high = "100",
 *     count = 10
 * )
 * void testWithNumberParameters(Integer value) {
 *     assertNotNull(value);
 *     assertTrue(value >= 1 && value <= 100);
 * }
 * }
 * </pre>
 * 
 * @author Oliver Wolff
 * @since 2.0
 * @see TypedGenerator
 * @see Generators
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
     * @return the generator type
     */
    GeneratorType generator();


    /**
     * Minimum size for String-based generators.
     * Only applicable for String generators that accept size parameters.
     * 
     * @return the minimum size for strings
     */
    int minSize() default 0;

    /**
     * Maximum size for String-based generators.
     * Only applicable for String generators that accept size parameters.
     * 
     * @return the maximum size for strings
     */
    int maxSize() default 10;

    /**
     * Lower bound for number-based generators.
     * Only applicable for number generators that accept range parameters.
     * The value is parsed according to the generator's number type.
     * 
     * @return the lower bound as a string
     */
    String low() default "0";

    /**
     * Upper bound for number-based generators.
     * Only applicable for number generators that accept range parameters.
     * The value is parsed according to the generator's number type.
     * 
     * @return the upper bound as a string
     */
    String high() default "100";

    /**
     * Number of instances to generate.
     * 
     * @return the number of instances to generate, defaults to 1
     */
    int count() default 1;

    /**
     * Optional seed for reproducible tests.
     * <p>
     * If set to a value other than -1, this seed will be used for the generator
     * instead of the seed managed by {@link de.cuioss.test.generator.junit.GeneratorControllerExtension}.
     * </p>
     * <p>
     * This is useful for tests that need specific generated values regardless of
     * the global seed configuration.
     * </p>
     * 
     * @return the seed to use, or -1 to use the globally configured seed
     */
    long seed() default -1L;
}
