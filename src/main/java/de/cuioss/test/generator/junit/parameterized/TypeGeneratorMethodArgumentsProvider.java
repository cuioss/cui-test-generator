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
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.util.ArrayList;
import java.util.List;
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
 * @author Oliver Wolff
 * @see TypeGeneratorMethodSource
 * @see TypedGenerator
 * @since 2.0
 */
public class TypeGeneratorMethodArgumentsProvider implements ArgumentsProvider, AnnotationConsumer<TypeGeneratorMethodSource> {

    private String methodName;
    private int count;
    private long seed = System.currentTimeMillis();

    @Override
    public void accept(TypeGeneratorMethodSource annotation) {
        methodName = annotation.value();
        count = Math.max(1, annotation.count());
        if (annotation.seed() != 0) {
            seed = annotation.seed();
        }
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        // Get the TypedGenerator from the method
        var generator = GeneratorMethodResolver.getGenerator(methodName, context);

        // Generate values
        List<Arguments> arguments = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            arguments.add(Arguments.of(generator.next()));
        }

        return arguments.stream();
    }

    /**
     * @return the seed value used for this provider
     */
    public long getSeed() {
        return seed;
    }

    /**
     * @return the count of values to generate
     */
    public int getCount() {
        return count;
    }
}
