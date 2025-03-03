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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.params.provider.ArgumentsSource;

import de.cuioss.test.generator.TypedGenerator;

/**
 * {@code @TypeGeneratorMethodSource} is an {@link ArgumentsSource} that provides
 * access to values from a {@link TypedGenerator} returned by a factory method
 * for parameterized tests.
 * 
 * <p>
 * This annotation allows you to use a method that returns a configured
 * {@link TypedGenerator} instance for your parameterized tests. The method will
 * be invoked to obtain the generator, and the specified number of values will be
 * generated and passed to your test method.
 * </p>
 * 
 * <p>
 * The method referenced by this annotation must:
 * <ul>
 * <li>Be static if referenced from a different class</li>
 * <li>Return a {@link TypedGenerator} instance</li>
 * <li>Take no parameters</li>
 * </ul>
 * </p>
 * 
 * <h2>Example Usage</h2>
 * 
 * <pre>
 * {@code
 * @ParameterizedTest
 * @TypeGeneratorMethodSource("createStringGenerator")
 * void testWithCustomGenerator(String value) {
 *     assertNotNull(value);
 * }
 * 
 * static TypedGenerator<String> createStringGenerator() {
 *     return Generators.strings(5, 10);
 * }
 * 
 * @ParameterizedTest
 * @TypeGeneratorMethodSource(value = "createIntegerGenerator", count = 5)
 * void testWithMultipleIntegers(Integer value) {
 *     assertNotNull(value);
 * }
 * 
 * static TypedGenerator<Integer> createIntegerGenerator() {
 *     return Generators.integers(1, 100);
 * }
 * }
 * </pre>
 * 
 * <p>
 * You can also reference a method in another class:
 * </p>
 * 
 * <pre>
 * {@code
 * @ParameterizedTest
 * @TypeGeneratorMethodSource("de.cuioss.test.MyGeneratorFactory#createGenerator")
 * void testWithExternalGenerator(MyType value) {
 *     // test with value
 * }
 * }
 * </pre>
 * 
 * @author Oliver Wolff
 * @since 2.0
 * @see TypedGenerator
 * @see TypeGeneratorMethodArgumentsProvider
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ArgumentsSource(TypeGeneratorMethodArgumentsProvider.class)
public @interface TypeGeneratorMethodSource {

    /**
     * The name of the method to invoke to get the TypedGenerator.
     * <p>
     * This can be:
     * <ul>
     * <li>A simple method name in the test class (e.g., "createGenerator")</li>
     * <li>A fully qualified method name in another class (e.g.,
     * "de.cuioss.test.MyGeneratorFactory#createGenerator")</li>
     * </ul>
     * </p>
     * 
     * @return the method name
     */
    String value();
    
    /**
     * Number of instances to generate.
     * 
     * @return the number of instances to generate, defaults to 1
     */
    int count() default 1;
}
