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

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.support.AnnotationConsumer;
import org.junit.platform.commons.JUnitException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * Implementation of {@link org.junit.jupiter.params.provider.ArgumentsProvider} that provides arguments from a
 * {@link TypedGenerator} created by the {@link Generators} utility class for parameterized tests
 * annotated with {@link GeneratorsSource}.
 *
 * <p>
 * This provider invokes the specified generator method from the {@link Generators} class to obtain a {@link TypedGenerator}
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
 * @see GeneratorsSource
 * @see TypedGenerator
 * @see Generators
 * @since 2.0
 */
public class GeneratorsSourceArgumentsProvider extends AbstractTypedGeneratorArgumentsProvider
        implements AnnotationConsumer<GeneratorsSource> {

    private GeneratorType generatorType;
    private int minSize;
    private int maxSize;
    private String low;
    private String high;
    private int count;
    private long seed;

    @Override
    public void accept(GeneratorsSource annotation) {
        generatorType = annotation.generator();

        minSize = annotation.minSize();
        maxSize = annotation.maxSize();
        low = annotation.low();
        high = annotation.high();
        count = Math.max(1, annotation.count());
        seed = annotation.seed();
    }

    @Override
    protected Stream<? extends Arguments> provideArgumentsForGenerators(ExtensionContext context) {
        // Create generator instance using factory
        TypedGenerator<?> generator = null;
        try {
            generator = createGeneratorFromFactory();
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException |
                IllegalAccessException e) {
            throw new IllegalStateException(e);
        }

        // Generate values
        return generateArguments(generator).stream();
    }

    @Override
    protected long getSeed() {
        return seed;
    }

    @Override
    protected int getCount() {
        return count;
    }

    /**
     * Creates a TypedGenerator instance based on the generator type.
     *
     * @return a TypedGenerator instance
     * @throws JUnitException if the generator cannot be created
     */
    private TypedGenerator<?> createGeneratorFromFactory() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        requireNonNull(generatorType, "Generator type must not be null");

        // Check if this is a domain-specific generator (factory class is the generator class itself)
        if (isDomainSpecificGenerator()) {
            return createDomainSpecificGenerator();
        }

        // Otherwise, it's a standard generator from the Generators class
        String methodName = generatorType.getMethodName();
        requireNonNull(methodName, "Generator method name must not be null");

        return switch (generatorType.getParameterType()) {
            case NEEDS_BOUNDS -> createStringGenerator();
            case NEEDS_RANGE -> createNumberGenerator();
            case PARAMETERLESS -> createParameterlessGenerator();
            default -> throw new UnsupportedOperationException(
                    "Generator method '" + methodName + "' has an unsupported parameter type: " + generatorType.getParameterType() + ". " +
                            "Only PARAMETERLESS, NEEDS_BOUNDS, and NEEDS_RANGE parameter types are supported.");
        };
    }

    /**
     * Checks if the generator type is a domain-specific generator.
     *
     * @return true if it's a domain-specific generator
     */
    private boolean isDomainSpecificGenerator() {
        return generatorType.getMethodName() == null &&
                generatorType.getFactoryClass() != null &&
                TypedGenerator.class.isAssignableFrom(generatorType.getFactoryClass());
    }

    /**
     * Creates a domain-specific generator by instantiating the generator class.
     *
     * @return a TypedGenerator instance
     */
    private TypedGenerator<?> createDomainSpecificGenerator() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> generatorClass = generatorType.getFactoryClass();
        Constructor<?> constructor = generatorClass.getDeclaredConstructor();
        return (TypedGenerator<?>) constructor.newInstance();
    }

    /**
     * Creates a string-based generator using the minSize and maxSize parameters.
     *
     * @return a TypedGenerator for strings
     */
    private TypedGenerator<?> createStringGenerator() throws InvocationTargetException, IllegalAccessException {
        Method method = findMethodWithParameters(generatorType.getMethodName(), int.class, int.class);
        return (TypedGenerator<?>) method.invoke(null, minSize, maxSize);
    }

    /**
     * Creates a number-based generator using the low and high parameters.
     *
     * @return a TypedGenerator for numbers
     */
    private TypedGenerator<?> createNumberGenerator() throws InvocationTargetException, IllegalAccessException {
        // Determine the parameter types based on the generator return type
        Class<?>[] paramTypes;
        Object[] params;

        Class<?> returnType = generatorType.getReturnType();

        if (Integer.class.equals(returnType)) {
            paramTypes = new Class<?>[]{int.class, int.class};
            params = new Object[]{Integer.parseInt(low), Integer.parseInt(high)};
        } else if (Long.class.equals(returnType)) {
            paramTypes = new Class<?>[]{long.class, long.class};
            params = new Object[]{Long.parseLong(low), Long.parseLong(high)};
        } else if (Double.class.equals(returnType)) {
            paramTypes = new Class<?>[]{double.class, double.class};
            params = new Object[]{Double.parseDouble(low), Double.parseDouble(high)};
        } else if (Float.class.equals(returnType)) {
            paramTypes = new Class<?>[]{float.class, float.class};
            params = new Object[]{Float.parseFloat(low), Float.parseFloat(high)};
        } else if (Short.class.equals(returnType)) {
            paramTypes = new Class<?>[]{short.class, short.class};
            params = new Object[]{Short.parseShort(low), Short.parseShort(high)};
        } else if (Byte.class.equals(returnType)) {
            paramTypes = new Class<?>[]{byte.class, byte.class};
            params = new Object[]{Byte.parseByte(low), Byte.parseByte(high)};
        } else {
            throw new UnsupportedOperationException(
                    "Number generator for type '" + returnType.getSimpleName() + "' is not supported.");
        }

        Method method = findMethodWithParameters(generatorType.getMethodName(), paramTypes);
        return (TypedGenerator<?>) method.invoke(null, params);
    }

    /**
     * Creates a generator that doesn't require parameters.
     *
     * @return a TypedGenerator
     */
    private TypedGenerator<?> createParameterlessGenerator() throws InvocationTargetException, IllegalAccessException {
        Method method = findMethodWithParameters(generatorType.getMethodName());
        return (TypedGenerator<?>) method.invoke(null);
    }


    /**
     * Finds a generator method in the Generators class with the specified parameter types.
     *
     * @param methodName     the name of the method to find
     * @param parameterTypes the parameter types the method should accept
     * @return the generator method
     * @throws JUnitException if the method cannot be found
     */
    private Method findMethodWithParameters(String methodName, Class<?>... parameterTypes) {
        try {
            return Generators.class.getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new JUnitException(
                    "Could not find static generator method '" + methodName +
                            "' in Generators class with the specified parameter types", e);
        }
    }
}
