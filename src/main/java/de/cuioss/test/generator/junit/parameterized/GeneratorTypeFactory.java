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
import org.junit.platform.commons.JUnitException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static java.util.Objects.requireNonNull;

/**
 * Shared creation of {@link TypedGenerator} instances from {@link GeneratorType} constants.
 * <p>
 * Used by both {@link GeneratorsSourceArgumentsProvider} (which supplies bounds/range from the
 * annotation) and {@link CompositeTypeGeneratorArgumentsProvider} (which has no parameters and
 * therefore relies on the default factory methods). Centralizing the dispatch keeps the two
 * providers consistent — in particular, domain generators (whose {@code methodName} is
 * {@code null}) are instantiated from their factory class in both cases.
 *
 * @author Oliver Wolff
 */
final class GeneratorTypeFactory {

    private GeneratorTypeFactory() {
        // utility class
    }

    /**
     * Creates a generator honoring the parameters supplied via {@code @GeneratorsSource}: bounds
     * ({@code minSize}/{@code maxSize}) for {@code NEEDS_BOUNDS} types and a range
     * ({@code low}/{@code high}) for {@code NEEDS_RANGE} types.
     *
     * @param type    the generator type, must not be {@code null}
     * @param minSize lower bound for size-parameterized generators
     * @param maxSize upper bound for size-parameterized generators
     * @param low     lower bound (parsed) for range-parameterized generators
     * @param high    upper bound (parsed) for range-parameterized generators
     * @return a new {@link TypedGenerator}
     * @throws JUnitException if the generator cannot be created
     */
    static TypedGenerator<?> createGenerator(GeneratorType type, int minSize, int maxSize, String low, String high) {
        requireNonNull(type, "Generator type must not be null");
        if (isDomainSpecific(type)) {
            return createDomainGenerator(type);
        }
        var methodName = requireMethodName(type);
        return switch (type.getParameterType()) {
            case NEEDS_BOUNDS -> invoke(findMethod(methodName, int.class, int.class), minSize, maxSize);
            case NEEDS_RANGE -> createRangeGenerator(type, methodName, low, high);
            case PARAMETERLESS -> invoke(findMethod(methodName));
        };
    }

    /**
     * Creates a generator using default parameters, for callers that cannot supply bounds or a
     * range (i.e. {@code @CompositeTypeGeneratorSource}). Standard types are created via their
     * parameterless factory method; domain types via their factory class.
     *
     * @param type the generator type, must not be {@code null}
     * @return a new {@link TypedGenerator}
     * @throws JUnitException if the generator cannot be created
     */
    static TypedGenerator<?> createGenerator(GeneratorType type) {
        requireNonNull(type, "Generator type must not be null");
        if (isDomainSpecific(type)) {
            return createDomainGenerator(type);
        }
        return invoke(findMethod(requireMethodName(type)));
    }

    private static boolean isDomainSpecific(GeneratorType type) {
        return type.getMethodName() == null
                && type.getFactoryClass() != null
                && TypedGenerator.class.isAssignableFrom(type.getFactoryClass());
    }

    @SuppressWarnings("unchecked")
    private static TypedGenerator<?> createDomainGenerator(GeneratorType type) {
        return JpmsReflectionHelper.newGeneratorInstance(
                (Class<? extends TypedGenerator<?>>) type.getFactoryClass());
    }

    private static String requireMethodName(GeneratorType type) {
        var methodName = type.getMethodName();
        if (methodName == null) {
            throw new JUnitException(
                    "Generator type '" + type + "' has no factory method and its factory class is not a TypedGenerator");
        }
        return methodName;
    }

    private static TypedGenerator<?> createRangeGenerator(GeneratorType type, String methodName, String low, String high) {
        var returnType = type.getReturnType();
        try {
            if (Integer.class.equals(returnType)) {
                return invoke(findMethod(methodName, int.class, int.class), Integer.parseInt(low), Integer.parseInt(high));
            }
            if (Long.class.equals(returnType)) {
                return invoke(findMethod(methodName, long.class, long.class), Long.parseLong(low), Long.parseLong(high));
            }
            if (Double.class.equals(returnType)) {
                return invoke(findMethod(methodName, double.class, double.class), Double.parseDouble(low), Double.parseDouble(high));
            }
            if (Float.class.equals(returnType)) {
                return invoke(findMethod(methodName, float.class, float.class), Float.parseFloat(low), Float.parseFloat(high));
            }
        } catch (NumberFormatException e) {
            throw new JUnitException("Invalid range for generator '" + methodName + "' (return type "
                    + returnType.getSimpleName() + "): low='" + low + "', high='" + high + "'", e);
        }
        throw new JUnitException("Range generator for return type '" + returnType.getSimpleName() + "' is not supported");
    }

    private static Method findMethod(String methodName, Class<?>... parameterTypes) {
        try {
            return Generators.class.getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new JUnitException("Could not find static generator method '" + methodName
                    + "' in " + Generators.class.getName() + " with the required parameter types", e);
        }
    }

    // cui-rewrite:disable InvalidExceptionUsageRecipe
    private static TypedGenerator<?> invoke(Method method, Object... args) {
        try {
            return (TypedGenerator<?>) method.invoke(null, args);
        } catch (InvocationTargetException e) {
            var cause = e.getTargetException();
            throw new JUnitException("Generator method '" + method.getName() + "' failed: " + cause.getMessage(), cause);
        } catch (IllegalAccessException e) {
            throw new JUnitException("Cannot access generator method '" + method.getName() + "'", e);
        }
    }
}
