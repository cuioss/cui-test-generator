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
 * <h2>Purpose and Usage</h2> This annotation is meant to be set on a junit 5
 * test-case. It controls the generator subsystem and, in case of test-failures,
 * provides information, that can be used for repeating the failed tests with a
 * fixed seed for the generators, see {@link GeneratorSeed} for details. This
 * fixed seed results in the generators reproducing the exact same test-data.
 * Sample output:
 *
 * <pre>
GeneratorController seed was 4711L.
Use a fixed seed by applying @GeneratorSeed(4711L) for the method/class,
or by using the system property '-Dde.cuioss.test.generator.seed=4711'
 * </pre>
 *
 * <h2>Implementation</h2> Shorthand for enabling
 * {@link GeneratorControllerExtension} for a certain test-class. This type is
 * equivalent to {@link ExtendWith} {@link GeneratorControllerExtension}
 *
 * @author Oliver Wolff
 *
 */
@Retention(RUNTIME)
@Target(TYPE)
@ExtendWith(GeneratorControllerExtension.class)
public @interface EnableGeneratorController {

}
