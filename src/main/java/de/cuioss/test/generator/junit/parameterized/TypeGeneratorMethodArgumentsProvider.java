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
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.util.stream.Stream;

/**
 * Implementation of {@link ArgumentsProvider} that provides arguments from a
 * {@link TypedGenerator} returned by a method for parameterized tests annotated
 * with {@link TypeGeneratorMethodSource}.
 *
 * <p>
 * This provider invokes the specified method to obtain a {@link TypedGenerator}
 * instance and generates the requested number of values to be used as test
 * arguments.
 * </p>
 *
 * <p>
 * The method can be:
 * <ul>
 * <li>A method in the test class</li>
 * <li>A static method in another class</li>
 * </ul>
 *
 * <p>
 * Seed management (including {@link de.cuioss.test.generator.junit.GeneratorSeed})
 * is inherited from {@link AbstractTypedGeneratorArgumentsProvider}.
 * </p>
 *
 * @author Oliver Wolff
 * @see TypeGeneratorMethodSource
 * @see TypedGenerator
 * @since 2.0
 */
public class TypeGeneratorMethodArgumentsProvider extends AbstractTypedGeneratorArgumentsProvider
        implements AnnotationConsumer<TypeGeneratorMethodSource> {

    private String methodName;

    private int count;

    @Override
    public void accept(TypeGeneratorMethodSource annotation) {
        methodName = annotation.value();
        count = Math.max(1, annotation.count());
    }

    @Override
    protected Stream<? extends Arguments> provideArgumentsForGenerators(ExtensionContext context) {
        var generator = GeneratorMethodResolver.getGenerator(methodName, context);
        return generateArguments(generator).stream();
    }

    /**
     * {@code @TypeGeneratorMethodSource} carries no seed attribute; reproducibility is
     * driven exclusively by {@code @GeneratorSeed}, handled by the base class.
     *
     * @return {@code -1L}, signalling no source-level seed
     */
    @Override
    protected long getSeed() {
        return -1L;
    }

    @Override
    protected int getCount() {
        return count;
    }

}
