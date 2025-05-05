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

/**
 * Enum that tracks the parameter requirements for generators.
 * Used by {@link GeneratorType} to specify what kind of parameters a generator needs.
 * 
 * @author Oliver Wolff
 * @since 2.3
 */
enum GeneratorParameterType {

    /**
     * For generators that don't require any parameters.
     * These generators are invoked without arguments.
     */
    PARAMETERLESS,

    /**
     * For string generators that need minSize and maxSize parameters.
     * These generators are invoked with two integer arguments that specify
     * the minimum and maximum size of the generated strings.
     */
    NEEDS_BOUNDS,

    /**
     * For number generators that need low and high parameters.
     * These generators are invoked with two arguments that specify
     * the minimum and maximum values of the generated numbers.
     */
    NEEDS_RANGE
}