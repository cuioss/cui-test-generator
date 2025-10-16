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
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.support.AnnotationConsumer;
import org.junit.platform.commons.JUnitException;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * Implementation of {@link org.junit.jupiter.params.provider.ArgumentsProvider} that provides arguments from a
 * {@link TypedGenerator} created by a factory class for parameterized tests
 * annotated with {@link TypeGeneratorFactorySource}.
 * 
 * <p>
 * This provider invokes the specified factory method to obtain a {@link TypedGenerator}
 * instance and generates the requested number of values to be used as test arguments.
 * </p>
 * 
 * <p>
 * Seed management is integrated with the existing
 * {@link de.cuioss.test.generator.junit.GeneratorControllerExtension} to ensure
 * consistent and reproducible test data generation.
 * </p>
 * 
 * @author Oliver Wolff
 * @since 2.0
 * @see TypeGeneratorFactorySource
 * @see TypedGenerator
 */
public class TypeGeneratorFactoryArgumentsProvider extends AbstractTypedGeneratorArgumentsProvider
        implements AnnotationConsumer<TypeGeneratorFactorySource> {

    private Class<?> factoryClass;
    private String factoryMethod;
    private String[] methodParameters;
    private int count;

    @Override
    public void accept(TypeGeneratorFactorySource annotation) {
        factoryClass = annotation.factoryClass();
        factoryMethod = annotation.factoryMethod();
        methodParameters = annotation.methodParameters();
        count = Math.max(1, annotation.count());
    }

    @Override
    protected Stream<? extends Arguments> provideArgumentsForGenerators(ExtensionContext context) {
        // Create generator instance using factory
        var generator = createGeneratorFromFactory();

        // Generate values
        return generateArguments(generator).stream();
    }

    @Override
    protected long getSeed() {
        return -1L;
    }

    @Override
    protected int getCount() {
        return count;
    }

    /**
     * Creates a TypedGenerator instance by invoking the specified factory method.
     *
     * @return a TypedGenerator instance
     * @throws JUnitException if the factory method cannot be found or invoked
     */
    // cui-rewrite:disable InvalidExceptionUsageRecipe
    private TypedGenerator<?> createGeneratorFromFactory() {
        requireNonNull(factoryClass, "Factory class must not be null");
        requireNonNull(factoryMethod, "Factory method must not be null");

        try {
            // Find the factory method
            Method method = findFactoryMethod();

            // Invoke the factory method with the provided parameters
            return (TypedGenerator<?>) method.invoke(null, (Object[]) methodParameters);
        } catch (Exception e) {
            throw new JUnitException(
                    "Failed to create TypedGenerator using factory method '" + factoryMethod +
                            "' in class '" + factoryClass.getName() + "'", e);
        }
    }

    /**
     * Finds the factory method in the factory class.
     * 
     * @return the factory method
     * @throws JUnitException if the method cannot be found
     */
    private Method findFactoryMethod() {
        // Look for a method with the exact parameter count
        List<Method> candidateMethods = Arrays.stream(factoryClass.getMethods())
                .filter(m -> m.getName().equals(factoryMethod))
                .filter(m -> Modifier.isStatic(m.getModifiers()))
                .filter(m -> m.getParameterCount() == methodParameters.length)
                .filter(m -> TypedGenerator.class.isAssignableFrom(m.getReturnType()))
                .toList();

        if (candidateMethods.isEmpty()) {
            throw new JUnitException(
                    "Could not find static factory method '" + factoryMethod +
                            "' in class '" + factoryClass.getName() +
                            "' with " + methodParameters.length + " parameters that returns TypedGenerator");
        }

        if (candidateMethods.size() > 1) {
            // If multiple methods match, try to find one that accepts String parameters
            var stringParamMethods = candidateMethods.stream()
                    .filter(m -> Arrays.stream(m.getParameterTypes())
                            .allMatch(p -> p == String.class))
                    .toList();

            if (stringParamMethods.size() == 1) {
                return stringParamMethods.getFirst();
            }
        }

        // If we have exactly one candidate or couldn't narrow down further, use the first one
        return candidateMethods.getFirst();
    }
}
