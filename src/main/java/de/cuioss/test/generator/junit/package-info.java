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
/**
 * Provides JUnit 5 integration for the CUI test generator framework.
 * This package contains annotations and extensions that enable reproducible
 * test data generation in JUnit tests.
 *
 * <h2>Core Components</h2>
 * <ul>
 *   <li>{@link de.cuioss.test.generator.junit.EnableGeneratorController} - Primary annotation for enabling generator support</li>
 *   <li>{@link de.cuioss.test.generator.junit.GeneratorSeed} - Configures fixed seeds for reproducible tests</li>
 *   <li>{@link de.cuioss.test.generator.junit.GeneratorControllerExtension} - JUnit extension implementing the core functionality</li>
 * </ul>
 *
 * <h2>Subpackages</h2>
 * <ul>
 *   <li>{@link de.cuioss.test.generator.junit.parameterized} - Provides integration with JUnit 5's parameterized testing framework</li>
 * </ul>
 *
 * <h2>Basic Usage</h2>
 * <pre>
 * {@code
 * @EnableGeneratorController
 * class MyGeneratorTest {
 *     @Test
 *     void shouldGenerateTestData() {
 *         var generator = new CollectionGenerator&lt;?&gt;(Generators.strings());
 *         var result = generator.list(5);
 *         assertThat(result).hasSize(5);
 *     }
 * }
 * }
 * </pre>
 *
 * <h2>Test Reproduction</h2>
 * When a test fails, the framework provides seed information for reproduction:
 * <pre>
 * GeneratorController seed was 4711L.
 * Use a fixed seed by applying @GeneratorSeed(4711L) for the method/class,
 * or by using the system property '-Dde.cuioss.test.generator.seed=4711'
 * </pre>
 *
 * <h2>Configuration Options</h2>
 * <ol>
 *   <li>Method-level seed: {@code @GeneratorSeed(4711L)}</li>
 *   <li>Class-level seed: {@code @GeneratorSeed(4711L)}</li>
 *   <li>System property: {@code -Dde.cuioss.test.generator.seed=4711}</li>
 * </ol>
 *
 * <h2>Best Practices</h2>
 * <ul>
 *   <li>Always use {@code @EnableGeneratorController} at the class level</li>
 *   <li>Use {@code @GeneratorSeed} for reproducing specific test failures</li>
 *   <li>Document seeds used for specific test scenarios</li>
 *   <li>Consider using class-level seeds for related test cases</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @see de.cuioss.test.generator.TypedGenerator
 * @see de.cuioss.test.generator.Generators
 */
package de.cuioss.test.generator.junit;
