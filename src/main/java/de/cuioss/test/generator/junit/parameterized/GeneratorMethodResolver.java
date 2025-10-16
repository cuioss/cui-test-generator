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

import de.cuioss.test.generator.TypedGenerator;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.JUnitException;
import org.junit.platform.commons.util.ReflectionUtils;
import org.junit.platform.commons.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * Utility class for resolving methods that return TypedGenerator instances.
 * Used by both TypeGeneratorMethodArgumentsProvider and CompositeTypeGeneratorArgumentsProvider.
 *
 * @author Oliver Wolff
 * @since 2.0
 */
final class GeneratorMethodResolver {

    static final String IN_CLASS = "] in class [";

    private GeneratorMethodResolver() {
        // Utility class constructor
    }

    /**
     * Gets a TypedGenerator by invoking the specified method.
     *
     * @param methodName the method name to invoke
     * @param context    the extension context
     * @return the TypedGenerator instance
     * @throws JUnitException if the method cannot be found or invoked
     */
    // cui-rewrite:disable InvalidExceptionUsageRecipe
    @SuppressWarnings("java:S1452") // owolff: This wildcard is because of the TypedGenerator interface. Ok for testing
    static TypedGenerator<?> getGenerator(String methodName, ExtensionContext context) {
        requireNonNull(methodName, "Method name must not be null");

        if (StringUtils.isBlank(methodName)) {
            throw new JUnitException("Method name must not be blank");
        }

        // Check if method name contains a class reference
        if (methodName.contains("#")) {
            return getGeneratorFromExternalClass(methodName);
        }

        // Get method from test class
        var testClass = context.getRequiredTestClass();
        var testInstance = context.getTestInstance().orElse(null);

        var method = findMethod(testClass, methodName)
                .orElseThrow(() -> new JUnitException("Could not find method [" + methodName + IN_CLASS + testClass.getName() + "]"));

        try {
            if (ReflectionUtils.isStatic(method)) {
                return (TypedGenerator<?>) method.invoke(null);
            } else if (testInstance != null) {
                return (TypedGenerator<?>) method.invoke(testInstance);
            } else {
                throw new JUnitException("Cannot invoke instance method [" + methodName + "] without a test instance");
            }
        } catch (Exception e) {
            throw new JUnitException("Failed to invoke method [" + methodName + "]", e);
        }
    }

    /**
     * Gets a TypedGenerator by invoking a static method in an external class.
     *
     * @param methodReference the method reference in format "fully.qualified.ClassName#methodName"
     * @return the TypedGenerator instance
     * @throws JUnitException if the method cannot be found or invoked
     */
    // cui-rewrite:disable InvalidExceptionUsageRecipe
    @SuppressWarnings("java:S1452") // owolff: This wildcard is because of the TypedGenerator interface. Ok for testing
    static TypedGenerator<?> getGeneratorFromExternalClass(String methodReference) {
        var parts = methodReference.split("#", 2);
        if (parts.length != 2) {
            throw new JUnitException("Method reference [" + methodReference + "] must be in format 'fully.qualified.ClassName#methodName'");
        }

        var className = parts[0];
        var localMethodName = parts[1];

        try {
            var clazz = Class.forName(className);
            var method = findMethod(clazz, localMethodName)
                    .orElseThrow(() -> new JUnitException("Could not find method [" + localMethodName + IN_CLASS + className + "]"));

            if (!ReflectionUtils.isStatic(method)) {
                throw new JUnitException("Method [" + localMethodName + "] in external class [" + className + "] must be static");
            }

            return (TypedGenerator<?>) method.invoke(null);
        } catch (ClassNotFoundException e) {
            throw new JUnitException("Could not find class [" + className + "]", e);
        } catch (Exception e) {
            throw new JUnitException("Failed to invoke method [" + localMethodName + IN_CLASS + className + "]", e);
        }
    }

    /**
     * Finds a method in the given class that returns a TypedGenerator and takes no parameters.
     *
     * @param clazz      the class to search in
     * @param methodName the method name to find
     * @return an Optional containing the method, or empty if not found
     */
    static Optional<Method> findMethod(Class<?> clazz, String methodName) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.getName().equals(methodName))
                .filter(method -> method.getParameterCount() == 0)
                .filter(method -> TypedGenerator.class.isAssignableFrom(method.getReturnType()))
                .findFirst();
    }
}
