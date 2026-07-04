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
import de.cuioss.test.generator.internal.RandomContext;
import de.cuioss.test.generator.junit.GeneratorSeed;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.ParameterDeclarations;
import org.junit.platform.commons.JUnitException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
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
        // Apply an explicitly requested seed (annotation attribute or @GeneratorSeed)
        // unconditionally, so that reproducibility never depends on what earlier tests
        // happened to consume from the shared RNG.
        var explicitSeed = resolveExplicitSeed(context);
        explicitSeed.ifPresent(RandomContext::setSeed);

        try {
            return provideArgumentsForGenerators(context);
        } finally {
            if (explicitSeed.isPresent()) {
                // The position of java.util.Random cannot be restored; re-seeding with the
                // previous seed would replay its stream. Advance to a fresh random seed so
                // later consumers do not observe a rewound RNG.
                RandomContext.initSeed();
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
     * Resolves an explicitly requested seed, based on the following priority:
     * <ol>
     *   <li>Seed specified in the source annotation (if not {@code -1})</li>
     *   <li>Seed from {@code @GeneratorSeed} on the test method</li>
     *   <li>Seed from {@code @GeneratorSeed} on the test class</li>
     * </ol>
     * When none of these is present, no explicit seed is requested and the shared RNG is
     * left untouched (an empty result), so generation continues from the seed established
     * by the enclosing {@code @EnableGeneratorController} for the current test.
     *
     * @param context the extension context
     * @return the explicitly requested seed, or empty if none was requested
     */
    @SuppressWarnings("java:S3655") // False positive, isPresent() is checked
    protected OptionalLong resolveExplicitSeed(ExtensionContext context) {
        long seed = getSeed();
        if (seed != -1L) {
            return OptionalLong.of(seed);
        }

        if (context != null && context.getElement().isPresent()
                && context.getElement().get() instanceof Method method) {
            var methodAnnotation = method.getAnnotation(GeneratorSeed.class);
            if (methodAnnotation != null) {
                return OptionalLong.of(methodAnnotation.value());
            }
            var classAnnotation = method.getDeclaringClass().getAnnotation(GeneratorSeed.class);
            if (classAnnotation != null) {
                return OptionalLong.of(classAnnotation.value());
            }
        }

        return OptionalLong.empty();
    }

    /**
     * Determines the seed to use for the generator, falling back to the current global
     * seed from {@link RandomContext} when no explicit seed was requested.
     *
     * @param context the extension context
     * @return the seed to use
     */
    protected long determineSeed(ExtensionContext context) {
        return resolveExplicitSeed(context).orElseGet(RandomContext::getLastSeed);
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
            return JpmsReflectionHelper.newGeneratorInstance(generatorClass);
        } catch (JUnitException e) {
            // JPMS-specific error from the helper — propagate as-is
            throw e;
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
