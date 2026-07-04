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

import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.generator.internal.RandomContext;
import de.cuioss.test.generator.junit.EnableGeneratorController;
import de.cuioss.test.generator.junit.GeneratorSeed;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@EnableGeneratorController
@GeneratorSeed(42L)
@DisplayName("NonBlankStringGenerator should")
class NonBlankStringGeneratorTest {

    private final TypedGenerator<String> underTest = new NonBlankStringGenerator();

    @Test
    @DisplayName("return String.class as type")
    void getTypeReturnsString() {
        assertEquals(String.class, underTest.getType(),
                "Generator should return String.class as type");
    }

    @RepeatedTest(5)
    @DisplayName("generate non-blank strings with at least one character")
    void shouldGenerateNonBlankStrings() {
        var result = underTest.next();

        assertFalse(result.isBlank(), "Generated string should not be blank");
        assertTrue(result.length() >= 1, "Generated string should contain at least one character");
        assertFalse(result.trim().isEmpty(), "Generated string should contain non-whitespace characters");
    }

    @Test
    @DisplayName("be reproducible with the same seed")
    void shouldBeReproducible() {
        RandomContext.setSeed(42L);
        var first = underTest.next();
        RandomContext.setSeed(42L);
        var second = underTest.next();
        assertEquals(first, second, "Same seed must yield the same value");
    }
}
