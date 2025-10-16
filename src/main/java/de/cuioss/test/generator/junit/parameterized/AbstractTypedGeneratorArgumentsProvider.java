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
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.distribution.RandomConfiguration;
import de.cuioss.test.generator.junit.GeneratorSeed;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.ParameterDeclarations;
import org.junit.platform.commons.JUnitException;
import org.junit.platform.commons.support.ReflectionSupport;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * Abstract base class for TypedGenerator-based ArgumentsProviders.
 * Contains common functionality for seed management and generator handling.
 *
 * @author Oliver Wolff
 * @since 2.0
 */
public abstract class AbstractTypedGeneratorArgumentsProvider implements ArgumentsProvider {

    /**
     * Common error message part for method not found exceptions.
     */
    protected static final String IN_CLASS = "] in class [";

    /**
     * Provides arguments for the parameterized test.
     *
     * @param parameters the parameter declarations (currently unused, reserved for future use)
     * @param context the extension context
     * @return a stream of arguments
     * @throws Exception if an error occurs
     */
    @Override
    public Stream<? extends Arguments> provideArguments(
            ParameterDeclarations parameters,
            ExtensionContext context) throws Exception {
        // Handle seed management
        var previousSeed = RandomConfiguration.getLastSeed();
        var useSeed = determineSeed(context);

        if (useSeed != previousSeed) {
            RandomConfiguration.setSeed(useSeed);
        }

        try {
            return provideArgumentsForGenerators(context);
        } finally {
            // Restore previous seed if we changed it
            if (useSeed != previousSeed) {
                RandomConfiguration.setSeed(previousSeed);
            }
        }
    }

    /**
     * Template method to be implemented by subclasses to provide arguments.
     *
     * @param context the extension context
     * @return a stream of arguments
     */
    @SuppressWarnings("java:S1452") // This wildcard is because of the TypedGenerator interface. Ok for testing
    protected abstract Stream<? extends Arguments> provideArgumentsForGenerators(ExtensionContext context);

    /**
     * Gets the seed value specified for this provider.
     *
     * @return the seed value, or -1 if not specified
     */
    protected abstract long getSeed();

    /**
     * Gets the count of values to generate.
     *
     * @return the count
     */
    protected abstract int getCount();

    /**
     * Generates arguments from a TypedGenerator.
     *
     * @param generator the generator to use
     * @return a list of Arguments
     */
    protected List<Arguments> generateArguments(TypedGenerator<?> generator) {
        List<Arguments> arguments = new ArrayList<>();
        for (int i = 0; i < getCount(); i++) {
            arguments.add(Arguments.of(generator.next()));
        }
        return arguments;
    }

    /**
     * Determines the seed to use for the generator based on the following priority:
     * 1. Seed specified in the annotation (if not -1)
     * 2. Seed from @GeneratorSeed on the test method
     * 3. Seed from @GeneratorSeed on the test class
     * 4. Current global seed from RandomConfiguration
     *
     * @param context the extension context
     * @return the seed to use
     */
    @SuppressWarnings("java:S3655") // False positive, isPresent() is checked
    protected long determineSeed(ExtensionContext context) {
        // If seed is explicitly set in the annotation, use it
        long seed = getSeed();
        if (seed != -1L) {
            return seed;
        }

        // Check for @GeneratorSeed on method or class
        if (context.getElement().isPresent()) {
            var element = context.getElement().get();

            // Check method first
            if (element instanceof Method method) {
                var seedAnnotation = method.getAnnotation(GeneratorSeed.class);
                if (seedAnnotation != null) {
                    return seedAnnotation.value();
                }

                // Then check class
                var classAnnotation = method.getDeclaringClass().getAnnotation(GeneratorSeed.class);
                if (classAnnotation != null) {
                    return classAnnotation.value();
                }
            }
        }

        // Fall back to current global seed
        return RandomConfiguration.getLastSeed();
    }

    /**
     * Creates an instance of the specified generator class using its no-args constructor.
     *
     * @param generatorClass the generator class to instantiate
     * @return a new instance of the generator
     * @throws JUnitException if the generator cannot be instantiated
     */
    // cui-rewrite:disable InvalidExceptionUsageRecipe
    @SuppressWarnings("java:S1452") // This wildcard is because of the TypedGenerator interface. Ok for testing
    protected TypedGenerator<?> createGeneratorInstance(Class<? extends TypedGenerator<?>> generatorClass) {
        requireNonNull(generatorClass, "Generator class must not be null");

        try {
            return ReflectionSupport.newInstance(generatorClass);
        } catch (Exception e) {
            throw new JUnitException(
                    "Failed to create TypedGenerator instance for " + generatorClass.getName() +
                            ". Make sure it has a public no-args constructor.", e);
        }
    }

    /**
     * Finds a method in the given class that returns a TypedGenerator and takes no parameters.
     *
     * @param clazz      the class to search in
     * @param methodName the method name to find
     * @return an Optional containing the method, or empty if not found
     */
    protected Optional<Method> findMethod(Class<?> clazz, String methodName) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(m -> m.getName().equals(methodName))
                .filter(m -> m.getParameterCount() == 0)
                .filter(m -> TypedGenerator.class.isAssignableFrom(m.getReturnType()))
                .findFirst();
    }
}
