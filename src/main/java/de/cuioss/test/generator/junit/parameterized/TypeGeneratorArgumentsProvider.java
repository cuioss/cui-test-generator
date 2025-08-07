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

import java.util.stream.Stream;

/**
 * Implementation of {@link org.junit.jupiter.params.provider.ArgumentsProvider} that provides arguments from a
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
public class TypeGeneratorArgumentsProvider extends AbstractTypedGeneratorArgumentsProvider
        implements AnnotationConsumer<TypeGeneratorSource> {

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
    protected Stream<? extends Arguments> provideArgumentsForGenerators(ExtensionContext context) {
        // Create generator instance
        var generator = createGeneratorInstance(generatorClass);

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
}
