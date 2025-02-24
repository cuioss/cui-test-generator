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
package de.cuioss.test.generator.junit;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ExtendWith;

/**
 * JUnit 5 annotation that enables and controls the generator subsystem for test cases.
 * This annotation provides reproducible test data generation by managing generator seeds
 * and providing detailed failure information for test reproduction.
 *
 * <h2>Features</h2>
 * <ul>
 *   <li>Controls generator seed initialization</li>
 *   <li>Provides detailed failure information for test reproduction</li>
 *   <li>Supports fixed seeds via {@link GeneratorSeed} annotation</li>
 *   <li>Enables system property configuration for seeds</li>
 * </ul>
 *
 * <h2>Usage</h2>
 * <pre>
 * &#64;EnableGeneratorController
 * class MyGeneratorTest {
 *     &#64;Test
 *     void shouldGenerateData() {
 *         var generator = new CollectionGenerator<>(Generators.strings());
 *         var result = generator.list(5);
 *         assertThat(result).hasSize(5);
 *     }
 * }
 * </pre>
 *
 * <h2>Test Reproduction</h2>
 * On test failure, the extension provides detailed information about the generator seed:
 * <pre>
 * GeneratorController seed was 4711L.
 * Use a fixed seed by applying @GeneratorSeed(4711L) for the method/class,
 * or by using the system property '-Dde.cuioss.test.generator.seed=4711'
 * </pre>
 *
 * <h2>Seed Configuration Options</h2>
 * <ol>
 *   <li>Method-level: {@code @GeneratorSeed(4711L)}</li>
 *   <li>Class-level: {@code @GeneratorSeed(4711L)}</li>
 *   <li>System Property: {@code -Dde.cuioss.test.generator.seed=4711}</li>
 * </ol>
 *
 * @author Oliver Wolff
 * @see GeneratorControllerExtension
 * @see GeneratorSeed
 */
@Retention(RUNTIME)
@Target(TYPE)
@ExtendWith(GeneratorControllerExtension.class)
public @interface EnableGeneratorController {

}
