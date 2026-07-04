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
            return (TypedGenerator<?>) JpmsReflectionHelper.invokeMethod(method, null, (Object[]) methodParameters);
        } catch (JUnitException e) {
            // JPMS-specific error from the helper — propagate as-is
            throw e;
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
        // The provided parameters are always Strings, so only all-String-parameter overloads are
        // viable candidates. Filtering up front removes the nondeterministic getFirst() fallback.
        List<Method> candidateMethods = Arrays.stream(factoryClass.getMethods())
                .filter(m -> m.getName().equals(factoryMethod))
                .filter(m -> Modifier.isStatic(m.getModifiers()))
                .filter(m -> m.getParameterCount() == methodParameters.length)
                .filter(m -> TypedGenerator.class.isAssignableFrom(m.getReturnType()))
                .filter(m -> Arrays.stream(m.getParameterTypes()).allMatch(p -> p == String.class))
                .toList();

        if (candidateMethods.isEmpty()) {
            throw new JUnitException(
                    "Could not find static factory method '" + factoryMethod +
                            "' in class '" + factoryClass.getName() +
                            "' with " + methodParameters.length + " String parameter(s) that returns TypedGenerator");
        }

        // Two same-arity, all-String overloads are impossible in Java, so this is unambiguous.
        return candidateMethods.getFirst();
    }
}
