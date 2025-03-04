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
 * {@code @CompositeTypeGeneratorSource} is an {@link ArgumentsSource} that provides
 * access to values from multiple {@link TypedGenerator} implementations for
 * parameterized tests, generating combinations of values.
 * 
 * <p>
 * This annotation allows you to combine multiple {@link TypedGenerator} implementations
 * to generate combinations of values for your parameterized tests. The generators
 * can be specified either by class or by method reference.
 * </p>
 * 
 * <h2>Example Usage</h2>
 * 
 * <pre>
 * {@code
 * @ParameterizedTest
 * @CompositeTypeGeneratorSource(
 *     generatorClasses = {
 *         NonBlankStringGenerator.class,
 *         IntegerGenerator.class
 *     },
 *     count = 3
 * )
 * void testWithMultipleGenerators(String text, Integer number) {
 *     assertNotNull(text);
 *     assertNotNull(number);
 *     // Test with combinations of text and number
 * }
 * 
 * @ParameterizedTest
 * @CompositeTypeGeneratorSource(
 *     generatorMethods = {
 *         "createStringGenerator",
 *         "createIntegerGenerator"
 *     },
 *     count = 3
 * )
 * void testWithMultipleMethodGenerators(String text, Integer number) {
 *     assertNotNull(text);
 *     assertNotNull(number);
 *     // Test with combinations of text and number
 * }
 * 
 * // Method generators
 * static TypedGenerator<String> createStringGenerator() {
 *     return Generators.strings(5, 10);
 * }
 * 
 * static TypedGenerator<Integer> createIntegerGenerator() {
 *     return Generators.integers(1, 100);
 * }
 * }
 * </pre>
 * 
 * @author Oliver Wolff
 * @since 2.0
 * @see TypedGenerator
 * @see CompositeTypeGeneratorArgumentsProvider
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ArgumentsSource(CompositeTypeGeneratorArgumentsProvider.class)
public @interface CompositeTypeGeneratorSource {

    /**
     * The TypedGenerator classes to use for generating test values.
     * Each class must have a no-args constructor.
     * 
     * @return the TypedGenerator classes
     */
    @SuppressWarnings("java:S1452") // This wildcard is because of the TypedGenerator interface. Ok for testing
    Class<? extends TypedGenerator<?>>[] generatorClasses() default {};

    /**
     * The method names to invoke to get TypedGenerator instances.
     * Methods can be in the test class or in external classes using the format
     * "fully.qualified.ClassName#methodName".
     * 
     * @return the method names
     */
    String[] generatorMethods() default {};

    /**
     * Number of combinations to generate.
     * For each generator, this many values will be generated and combined
     * in a cartesian product with values from other generators.
     * 
     * @return the number of values to generate per generator, defaults to 1
     */
    int count() default 1;

    /**
     * Whether to generate a cartesian product of all generator values.
     * If true, all possible combinations of values from the generators will be created.
     * If false, generators will be paired one-to-one (requires all generators to produce
     * the same number of values).
     * 
     * @return true to generate a cartesian product, false for one-to-one pairing
     */
    boolean cartesianProduct() default true;

    /**
     * Optional seed for reproducible tests.
     * <p>
     * If set to a value other than -1, this seed will be used for all generators
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
