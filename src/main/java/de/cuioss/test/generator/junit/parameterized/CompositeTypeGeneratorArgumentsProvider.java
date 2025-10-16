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
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.support.AnnotationConsumer;
import org.junit.platform.commons.JUnitException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Implementation of {@link org.junit.jupiter.params.provider.ArgumentsProvider} that provides arguments from multiple
 * {@link TypedGenerator} instances for parameterized tests annotated with
 * {@link CompositeTypeGeneratorSource}.
 * 
 * <p>
 * This provider creates multiple {@link TypedGenerator} instances from the specified
 * classes and/or methods, and generates combinations of values to be used as test arguments.
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
 * @see CompositeTypeGeneratorSource
 * @see TypedGenerator
 */
public class CompositeTypeGeneratorArgumentsProvider extends AbstractTypedGeneratorArgumentsProvider
        implements AnnotationConsumer<CompositeTypeGeneratorSource> {

    private Class<? extends TypedGenerator<?>>[] generatorClasses;
    private String[] generatorMethods;
    private GeneratorType[] generators;
    private int count;
    private boolean cartesianProduct;

    @Override
    public void accept(CompositeTypeGeneratorSource annotation) {
        generatorClasses = annotation.generatorClasses();
        generatorMethods = annotation.generatorMethods();
        generators = annotation.generators();
        count = Math.max(1, annotation.count());
        cartesianProduct = annotation.cartesianProduct();
    }

    @Override
    protected Stream<? extends Arguments> provideArgumentsForGenerators(ExtensionContext context) {
        if (generatorClasses.length == 0 && generatorMethods.length == 0 && generators.length == 0) {
            throw new JUnitException("At least one generator class, method, or type must be specified");
        }

        // Create generator instances
        List<TypedGenerator<?>> generatorInstances = new ArrayList<>();

        // Add generators from classes
        for (Class<? extends TypedGenerator<?>> generatorClass : generatorClasses) {
            generatorInstances.add(createGeneratorInstance(generatorClass));
        }

        // Add generators from methods
        for (String methodName : generatorMethods) {
            generatorInstances.add(GeneratorMethodResolver.getGenerator(methodName, context));
        }

        // Add generators from GeneratorType enum values
        for (GeneratorType generatorType : generators) {
            generatorInstances.add(createGeneratorFromType(generatorType));
        }

        // Generate values from each generator
        List<List<Object>> generatedValues = new ArrayList<>();
        for (TypedGenerator<?> generator : generatorInstances) {
            List<Object> values = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                values.add(generator.next());
            }
            generatedValues.add(values);
        }

        // Create combinations of values
        return createArgumentsCombinations(generatedValues);
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
     * Creates combinations of values from the generated values.
     * 
     * @param generatedValues lists of values from each generator
     * @return a stream of Arguments with combinations of values
     */
    private Stream<Arguments> createArgumentsCombinations(List<List<Object>> generatedValues) {
        if (generatedValues.isEmpty()) {
            return Stream.empty();
        }

        if (cartesianProduct) {
            return createCartesianProduct(generatedValues);
        } else {
            return createOneToOnePairs(generatedValues);
        }
    }

    /**
     * Creates a cartesian product of all generated values.
     * 
     * @param generatedValues lists of values from each generator
     * @return a stream of Arguments with all possible combinations
     */
    private Stream<Arguments> createCartesianProduct(List<List<Object>> generatedValues) {
        // Start with a single empty list
        List<List<Object>> combinations = new ArrayList<>();
        combinations.add(new ArrayList<>());

        // For each list of generated values
        for (List<Object> values : generatedValues) {
            List<List<Object>> newCombinations = new ArrayList<>();

            // For each existing combination
            for (List<Object> combination : combinations) {
                // For each value in the current list
                for (Object value : values) {
                    // Create a new combination by adding the value to the existing combination
                    List<Object> newCombination = new ArrayList<>(combination);
                    newCombination.add(value);
                    newCombinations.add(newCombination);
                }
            }

            combinations = newCombinations;
        }

        // Convert combinations to Arguments
        return combinations.stream()
                .map(combination -> Arguments.of(combination.toArray()));
    }

    /**
     * Creates one-to-one pairs of generated values.
     * Requires all generators to produce the same number of values.
     * 
     * @param generatedValues lists of values from each generator
     * @return a stream of Arguments with one-to-one pairs
     * @throws JUnitException if the lists have different sizes
     */
    private Stream<Arguments> createOneToOnePairs(List<List<Object>> generatedValues) {
        // Check that all lists have the same size
        int size = generatedValues.getFirst().size();
        for (List<Object> values : generatedValues) {
            if (values.size() != size) {
                throw new JUnitException(
                        "When cartesianProduct is false, all generators must produce the same number of values");
            }
        }

        // Create one-to-one pairs
        List<Arguments> arguments = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Object[] args = new Object[generatedValues.size()];
            for (int j = 0; j < generatedValues.size(); j++) {
                args[j] = generatedValues.get(j).get(i);
            }
            arguments.add(Arguments.of(args));
        }

        return arguments.stream();
    }

    /**
     * Creates a TypedGenerator from a GeneratorType enum value.
     *
     * @param generatorType the generator type
     * @return a TypedGenerator instance
     * @throws JUnitException if the generator cannot be created
     */
    // cui-rewrite:disable InvalidExceptionUsageRecipe
    @SuppressWarnings("java:S1452") // This wildcard is because of the TypedGenerator interface. Ok for testing
    private TypedGenerator<?> createGeneratorFromType(GeneratorType generatorType) {
        try {
            // Use reflection to invoke the method on Generators class
            var methodName = generatorType.getMethodName();
            var method = Generators.class.getMethod(methodName);
            return (TypedGenerator<?>) method.invoke(null);
        } catch (Exception e) {
            throw new JUnitException("Failed to create generator from type: " + generatorType, e);
        }
    }
}
