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
package de.cuioss.test.generator.impl;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.junit.EnableGeneratorController;
import de.cuioss.test.generator.junit.GeneratorSeed;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@EnableGeneratorController
@GeneratorSeed(42L)
@DisplayName("DecoratorGenerator should")
class DecoratorGeneratorTest {

    @Test
    @DisplayName("return the declared type rather than the wrapped generator's type")
    void shouldReturnDeclaredType() {
        var decorator = new DecoratorGenerator<>(int.class, Generators.integers(1, 10));
        assertEquals(int.class, decorator.getType(),
                "getType() must return the type declared in the constructor");
    }

    @Test
    @DisplayName("delegate next() to the wrapped generator")
    void shouldDelegateNext() {
        var decorator = new DecoratorGenerator<>(int.class, Generators.integers(1, 10));
        for (int i = 0; i < 200; i++) {
            int value = decorator.next();
            assertTrue(value >= 1 && value <= 10, "Delegated value out of the wrapped range: " + value);
        }
    }

    @Test
    @DisplayName("pass through the exact value produced by the wrapped generator")
    void shouldPassThroughExactValue() {
        var decorator = new DecoratorGenerator<>(String.class, Generators.fixedValues("constant"));
        assertEquals("constant", decorator.next(),
                "next() must return exactly what the wrapped generator produces");
    }
}
