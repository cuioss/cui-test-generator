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

/**
 * <h2>JUnit 5 Parameterized Test Support for TypedGenerator</h2>
 * 
 * <p>
 * This package provides JUnit 5 extensions that enable seamless integration of
 * {@link de.cuioss.test.generator.TypedGenerator} instances with JUnit 5's
 * parameterized testing framework. These extensions allow developers to use
 * TypedGenerator implementations to generate test data for parameterized tests.
 * </p>
 * 
 * <p>
 * This package extends the functionality provided in the parent package
 * {@link de.cuioss.test.generator.junit} by adding specific support for JUnit 5's
 * parameterized testing features.
 * </p>
 * 
 * <h3>Common Parameters</h3>
 * 
 * <p>
 * All annotations in this package support the following common parameters:
 * </p>
 * 
 * <ul>
 *   <li><strong>count</strong> - Specifies the number of test instances to generate.
 *       This controls how many test invocations will occur with different generated values.
 *       Defaults to 1 in most annotations.</li>
 *   <li><strong>seed</strong> - An optional seed value for reproducible tests.
 *       When specified (not -1), this seed will be used for the generator instead of the
 *       seed managed by {@link de.cuioss.test.generator.junit.GeneratorControllerExtension}.
 *       This is useful for tests that need specific generated values regardless of
 *       the global seed configuration.</li>
 * </ul>
 * 
 * <h3>Available Annotations</h3>
 * 
 * <p>
 * The package provides the following annotations for different use cases:
 * </p>
 * 
 * <ul>
 *   <li>{@link de.cuioss.test.generator.junit.parameterized.TypeGeneratorSource} - 
 *       Uses a TypedGenerator implementation directly via its class.</li>
 *   <li>{@link de.cuioss.test.generator.junit.parameterized.TypeGeneratorMethodSource} - 
 *       Uses a method that returns a configured TypedGenerator instance.</li>
 *   <li>{@link de.cuioss.test.generator.junit.parameterized.TypeGeneratorFactorySource} - 
 *       Uses a factory class with a static method to create a TypedGenerator instance.</li>
 *   <li>{@link de.cuioss.test.generator.junit.parameterized.CompositeTypeGeneratorSource} - 
 *       Combines multiple TypedGenerator implementations to generate combinations of values.</li>
 * </ul>
 * 
 * <h3>Integration with JUnit 5</h3>
 * 
 * <p>
 * These annotations are designed to be used with JUnit 5's {@code @ParameterizedTest}
 * annotation. They provide {@link org.junit.jupiter.params.provider.ArgumentsProvider}
 * implementations that generate test arguments from TypedGenerator instances.
 * </p>
 * 
 * <p>
 * For detailed usage examples, refer to the documentation of each annotation.
 * </p>
 * 
 * @author Oliver Wolff
 * @since 2.0
 * @see de.cuioss.test.generator.TypedGenerator
 * @see org.junit.jupiter.params.ParameterizedTest
 * @see de.cuioss.test.generator.junit.EnableGeneratorController
 * @see de.cuioss.test.generator.junit.GeneratorSeed
 * @see de.cuioss.test.generator.junit.GeneratorControllerExtension
 */
package de.cuioss.test.generator.junit.parameterized;
