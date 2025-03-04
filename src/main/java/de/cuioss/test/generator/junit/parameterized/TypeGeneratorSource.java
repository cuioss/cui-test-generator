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
 * {@code @TypeGeneratorSource} is an {@link ArgumentsSource} that provides
 * access to values from a {@link TypedGenerator} implementation for
 * parameterized tests.
 * 
 * <p>
 * This annotation allows you to use any {@link TypedGenerator} implementation
 * directly in your parameterized tests. The generator will be instantiated using
 * its default constructor, and the specified number of values will be generated
 * and passed to your test method.
 * </p>
 * 
 * <h2>Example Usage</h2>
 * 
 * <pre>
 * {@code
 * @ParameterizedTest
 * @TypeGeneratorSource(NonBlankStringGenerator.class)
 * void testWithGeneratedStrings(String value) {
 *     assertNotNull(value);
 *     assertFalse(value.isBlank());
 * }
 * 
 * @ParameterizedTest
 * @TypeGeneratorSource(value = IntegerGenerator.class, count = 5)
 * void testWithMultipleIntegers(Integer value) {
 *     assertNotNull(value);
 * }
 * }
 * </pre>
 * 
 * <p>
 * If you need to use a custom generator with specific configuration, consider
 * using {@code @TypeGeneratorMethodSource} instead.
 * </p>
 * 
 * @author Oliver Wolff
 * @since 2.0
 * @see TypedGenerator
 * @see TypeGeneratorArgumentsProvider
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ArgumentsSource(TypeGeneratorArgumentsProvider.class)
public @interface TypeGeneratorSource {

    /**
     * The TypedGenerator class to use for generating test values.
     * Must have a no-args constructor.
     * 
     * @return the TypedGenerator class
     */
    @SuppressWarnings("java:S1452") // owolff: This wildcard is because of the TypedGenerator interface. Ok for testing
    Class<? extends TypedGenerator<?>> value();

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
