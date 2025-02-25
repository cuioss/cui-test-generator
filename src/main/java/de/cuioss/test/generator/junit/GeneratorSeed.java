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

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation for configuring fixed generator seeds in test cases.
 * This annotation works in conjunction with {@link EnableGeneratorController}
 * to provide reproducible test data generation.
 *
 * <h2>Features</h2>
 * <ul>
 *   <li>Can be applied at method or class level</li>
 *   <li>Takes precedence over system property configuration</li>
 *   <li>Ensures consistent test data across test runs</li>
 *   <li>Useful for debugging and reproducing test failures</li>
 * </ul>
 *
 * <h2>Usage Examples</h2>
 * <pre>
 * // Method-level configuration
 * &#64;EnableGeneratorController
 * class MyTest {
 *     &#64;Test
 *     &#64;GeneratorSeed(4711L)
 *     void shouldGenerateConsistentData() {
 *         var result = Generators.strings().next();
 *         // Will always generate the same string
 *     }
 * }
 *
 * // Class-level configuration
 * &#64;EnableGeneratorController
 * &#64;GeneratorSeed(8042L)
 * class MyReproducibleTest {
 *     &#64;Test
 *     void allTestsUseTheSameSeed() {
 *         // All tests in this class use seed 8042L
 *     }
 * }
 * </pre>
 *
 * <h2>Configuration Priority</h2>
 * <ol>
 *   <li>Method-level {@code @GeneratorSeed}</li>
 *   <li>Class-level {@code @GeneratorSeed}</li>
 *   <li>System property configuration</li>
 * </ol>
 *
 * @author Oliver Wolff
 * @see EnableGeneratorController
 * @see GeneratorControllerExtension
 */
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface GeneratorSeed {

    /**
     * @return the seed for the generators
     */
    long value();
}
