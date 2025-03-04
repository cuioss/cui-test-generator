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

import de.cuioss.test.generator.TypedGenerator;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@code @TypeGeneratorFactorySource} is an {@link ArgumentsSource} that provides
 * access to values from a {@link TypedGenerator} created by a factory class
 * for parameterized tests.
 * 
 * <p>
 * This annotation allows you to use a factory class to create a {@link TypedGenerator}
 * for your parameterized tests. The factory class must have a static method that
 * returns a {@link TypedGenerator} instance. The method can optionally take parameters
 * specified in the annotation.
 * </p>
 * 
 * <h2>Example Usage</h2>
 * 
 * <pre>
 * {@code
 * @ParameterizedTest
 * @TypeGeneratorFactorySource(
 *     factoryClass = StringGeneratorFactory.class,
 *     factoryMethod = "createNonEmptyStringGenerator",
 *     count = 5
 * )
 * void testWithFactoryGeneratedStrings(String value) {
 *     assertNotNull(value);
 *     assertFalse(value.isEmpty());
 * }
 * 
 * // With parameters
 * @ParameterizedTest
 * @TypeGeneratorFactorySource(
 *     factoryClass = IntegerGeneratorFactory.class,
 *     factoryMethod = "createRangeGenerator",
 *     methodParameters = {"1", "100"},
 *     count = 10
 * )
 * void testWithParameterizedFactory(Integer value) {
 *     assertNotNull(value);
 *     assertTrue(value >= 1 && value <= 100);
 * }
 * }
 * </pre>
 * 
 * <p>
 * Factory class example:
 * </p>
 * 
 * <pre>
 * {@code
 * public class IntegerGeneratorFactory {
 *     public static TypedGenerator<Integer> createRangeGenerator(String min, String max) {
 *         return Generators.integers(Integer.parseInt(min), Integer.parseInt(max));
 *     }
 * }
 * }
 * </pre>
 * 
 * @author Oliver Wolff
 * @since 2.0
 * @see TypedGenerator
 * @see TypeGeneratorFactoryArgumentsProvider
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ArgumentsSource(TypeGeneratorFactoryArgumentsProvider.class)
public @interface TypeGeneratorFactorySource {

    /**
     * The factory class that will create the TypedGenerator.
     * 
     * @return the factory class
     */
    @SuppressWarnings("java:S1452") // This wildcard is because of the TypedGenerator interface. Ok for testing
    Class<?> factoryClass();

    /**
     * The name of the factory method to invoke.
     * The method must be static and return a TypedGenerator.
     * 
     * @return the factory method name
     */
    String factoryMethod();

    /**
     * Optional parameters to pass to the factory method.
     * All parameters are passed as strings, and the factory method
     * is responsible for parsing them to the appropriate types.
     * 
     * @return the method parameters as strings
     */
    String[] methodParameters() default {};

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
