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
package de.cuioss.test.generator.junit.parameterized;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;

/**
 * Shared test fixture providing factory methods that return {@link TypedGenerator} instances.
 * Consolidates the previously copy-pasted {@code TestFactoryClass} definitions used by the
 * method-resolution tests in this package. Some methods are intentionally unused directly but are
 * required for exercising the method-resolution logic (static vs. non-static, wrong return type,
 * parameters, ...).
 */
@SuppressWarnings("unused")
class SharedTestFactory {

    /**
     * Creates a string generator for testing.
     *
     * @return a TypedGenerator that generates strings
     */
    public static TypedGenerator<String> createGenerator() {
        return Generators.strings(5, 10);
    }

    /**
     * Creates an integer generator for testing.
     *
     * @return a TypedGenerator that generates integers
     */
    public static TypedGenerator<Integer> createIntegerGenerator() {
        return Generators.integers(1, 100);
    }

    /**
     * Non-static method that must not be callable without an instance.
     *
     * @return a TypedGenerator that generates doubles
     */
    public TypedGenerator<Double> createDoubleGenerator() {
        return Generators.doubles(0.0, 1.0);
    }

    /**
     * Method with a wrong return type for testing method resolution.
     *
     * @return a string, not a TypedGenerator
     */
    public static String methodWithWrongReturnType() {
        return "not a generator";
    }

    /**
     * Method with parameters for testing method resolution.
     *
     * @param min minimum value
     * @param max maximum value
     * @return a TypedGenerator that generates integers
     */
    public static TypedGenerator<Integer> methodWithParameters(int min, int max) {
        return Generators.integers(min, max);
    }
}
