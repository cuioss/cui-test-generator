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

import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.distribution.RandomConfiguration;
import de.cuioss.test.generator.junit.GeneratorSeed;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;
import org.junit.platform.commons.JUnitException;
import org.junit.platform.commons.util.ReflectionUtils;
import org.junit.platform.commons.util.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * Implementation of {@link ArgumentsProvider} that provides arguments from multiple
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
public class CompositeTypeGeneratorArgumentsProvider implements ArgumentsProvider, AnnotationConsumer<CompositeTypeGeneratorSource> {

    public static final String IN_CLASS = "] in class [";
    private Class<? extends TypedGenerator<?>>[] generatorClasses;
    private String[] generatorMethods;
    private int count;
    private boolean cartesianProduct;
    private long seed;

    @Override
    public void accept(CompositeTypeGeneratorSource annotation) {
        generatorClasses = annotation.generatorClasses();
        generatorMethods = annotation.generatorMethods();
        count = Math.max(1, annotation.count());
        cartesianProduct = annotation.cartesianProduct();
        seed = annotation.seed();
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        if (generatorClasses.length == 0 && generatorMethods.length == 0) {
            throw new JUnitException("At least one generator class or method must be specified");
        }
        
        // Handle seed management
        var previousSeed = RandomConfiguration.getLastSeed();
        var useSeed = determineSeed(context);
        
        if (useSeed != previousSeed) {
            RandomConfiguration.setSeed(useSeed);
        }
        
        try {
            // Create generator instances
            List<TypedGenerator<?>> generators = new ArrayList<>();
            
            // Add generators from classes
            for (Class<? extends TypedGenerator<?>> generatorClass : generatorClasses) {
                generators.add(createGeneratorInstance(generatorClass));
            }
            
            // Add generators from methods
            for (String methodName : generatorMethods) {
                generators.add(getGenerator(methodName, context));
            }
            
            // Generate values from each generator
            List<List<Object>> generatedValues = new ArrayList<>();
            for (TypedGenerator<?> generator : generators) {
                List<Object> values = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    values.add(generator.next());
                }
                generatedValues.add(values);
            }
            
            // Create combinations of values
            return createArgumentsCombinations(generatedValues);
        } finally {
            // Restore previous seed if we changed it
            if (useSeed != previousSeed) {
                RandomConfiguration.setSeed(previousSeed);
            }
        }
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
        int size = generatedValues.get(0).size();
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
    private long determineSeed(ExtensionContext context) {
        // If seed is explicitly set in the annotation, use it
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
    private TypedGenerator<?> createGeneratorInstance(Class<? extends TypedGenerator<?>> generatorClass) {
        requireNonNull(generatorClass, "Generator class must not be null");
        
        try {
            return ReflectionUtils.newInstance(generatorClass);
        } catch (Exception e) {
            throw new JUnitException(
                "Failed to create TypedGenerator instance for " + generatorClass.getName() + 
                ". Make sure it has a public no-args constructor.", e);
        }
    }
    
    /**
     * Gets a TypedGenerator by invoking the specified method.
     * 
     * @param methodName the method name to invoke
     * @param context the extension context
     * @return the TypedGenerator instance
     * @throws JUnitException if the method cannot be found or invoked
     */
    private TypedGenerator<?> getGenerator(String methodName, ExtensionContext context) {
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
    private TypedGenerator<?> getGeneratorFromExternalClass(String methodReference) {
        String[] parts = methodReference.split("#", 2);
        if (parts.length != 2) {
            throw new JUnitException("Method reference [" + methodReference + "] must be in format 'fully.qualified.ClassName#methodName'");
        }
        
        var className = parts[0];
        var methodName = parts[1];
        
        try {
            var clazz = Class.forName(className);
            var method = findMethod(clazz, methodName)
                    .orElseThrow(() -> new JUnitException("Could not find method [" + methodName + IN_CLASS + className + "]"));
            
            if (!ReflectionUtils.isStatic(method)) {
                throw new JUnitException("Method [" + methodName + "] in external class [" + className + "] must be static");
            }
            
            return (TypedGenerator<?>) method.invoke(null);
        } catch (ClassNotFoundException e) {
            throw new JUnitException("Could not find class [" + className + "]", e);
        } catch (Exception e) {
            throw new JUnitException("Failed to invoke method [" + methodName + IN_CLASS + className + "]", e);
        }
    }
    
    /**
     * Finds a method in the given class that returns a TypedGenerator and takes no parameters.
     * 
     * @param clazz the class to search in
     * @param methodName the method name to find
     * @return an Optional containing the method, or empty if not found
     */
    private Optional<Method> findMethod(Class<?> clazz, String methodName) {
        return Arrays.stream(clazz.getDeclaredMethods())
            .filter(m -> m.getName().equals(methodName))
            .filter(m -> m.getParameterCount() == 0)
            .filter(m -> TypedGenerator.class.isAssignableFrom(m.getReturnType()))
            .findFirst();
    }
}
