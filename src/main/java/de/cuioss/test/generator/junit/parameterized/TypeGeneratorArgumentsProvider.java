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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * Implementation of {@link ArgumentsProvider} that provides arguments from a
 * {@link TypedGenerator} for parameterized tests annotated with
 * {@link TypeGeneratorSource}.
 * 
 * <p>
 * This provider instantiates the specified {@link TypedGenerator} class and
 * generates the requested number of values to be used as test arguments.
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
 * @see TypeGeneratorSource
 * @see TypedGenerator
 */
public class TypeGeneratorArgumentsProvider implements ArgumentsProvider, AnnotationConsumer<TypeGeneratorSource> {

    private Class<? extends TypedGenerator<?>> generatorClass;
    private int count;
    private long seed;

    @Override
    public void accept(TypeGeneratorSource annotation) {
        generatorClass = annotation.value();
        count = Math.max(1, annotation.count());
        seed = annotation.seed();
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        // Handle seed management
        var previousSeed = RandomConfiguration.getLastSeed();
        var useSeed = determineSeed(context);
        
        if (useSeed != previousSeed) {
            RandomConfiguration.setSeed(useSeed);
        }
        
        try {
            // Create generator instance
            var generator = createGeneratorInstance();
            
            // Generate values
            List<Arguments> arguments = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                arguments.add(Arguments.of(generator.next()));
            }
            
            return arguments.stream();
        } finally {
            // Restore previous seed if we changed it
            if (useSeed != previousSeed) {
                RandomConfiguration.setSeed(previousSeed);
            }
        }
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
    @SuppressWarnings("java:S3655") // owolff: False positive, isPresent() is checked
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
     * @return a new instance of the generator
     * @throws JUnitException if the generator cannot be instantiated
     */
    private TypedGenerator<?> createGeneratorInstance() {
        requireNonNull(generatorClass, "Generator class must not be null");
        
        try {
            return ReflectionUtils.newInstance(generatorClass);
        } catch (Exception e) {
            throw new JUnitException(
                "Failed to create TypedGenerator instance for " + generatorClass.getName() + 
                ". Make sure it has a public no-args constructor.", e);
        }
    }
}
