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

import java.util.List;
import java.util.stream.Stream;

/**
 * Shared test implementation of {@link AbstractTypedGeneratorArgumentsProvider} used by the
 * provider tests in this package. It exposes the protected {@code generateArguments} and
 * {@code createGeneratorInstance} hooks so tests can exercise them directly. Consolidates the
 * previously duplicated {@code TestProvider} definitions.
 */
class TestArgumentsProvider extends AbstractTypedGeneratorArgumentsProvider {

    private final long seed;
    private final int count;

    TestArgumentsProvider(long seed, int count) {
        this.seed = seed;
        this.count = count;
    }

    @Override
    protected Stream<? extends Arguments> provideArgumentsForGenerators(ExtensionContext context) {
        return Stream.empty();
    }

    @Override
    protected long getSeed() {
        return seed;
    }

    @Override
    protected int getCount() {
        return count;
    }

    List<Arguments> generateArgumentsPublic(TypedGenerator<?> generator) {
        return generateArguments(generator);
    }

    TypedGenerator<?> createGeneratorInstancePublic(Class<? extends TypedGenerator<?>> generatorClass) {
        return createGeneratorInstance(generatorClass);
    }
}
