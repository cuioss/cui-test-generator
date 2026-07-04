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

import de.cuioss.test.generator.junit.EnableGeneratorController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.platform.commons.JUnitException;

import static org.junit.jupiter.api.Assertions.*;

@EnableGeneratorController
@DisplayName("GeneratorTypeFactory should")
class GeneratorTypeFactoryTest {

    @ParameterizedTest
    @EnumSource(GeneratorType.class)
    @DisplayName("create a working generator for every GeneratorType via the default path")
    void shouldCreateEveryGeneratorTypeWithDefaults(GeneratorType type) {
        // Regression for J-1 (domain types) and J-2 (no unusable FIXED_VALUES constant):
        // every constant must resolve to a usable generator through the shared default path.
        var generator = GeneratorTypeFactory.createGenerator(type);
        assertNotNull(generator, () -> "No generator created for " + type);
        assertNotNull(generator.next(), () -> "Null value produced for " + type);
    }

    @Test
    @DisplayName("instantiate domain generators from their factory class")
    void shouldCreateDomainGeneratorFromFactoryClass() {
        var generator = GeneratorTypeFactory.createGenerator(GeneratorType.DOMAIN_CITY);
        assertNotNull(generator.next());
        assertEquals(String.class, generator.getType());
    }

    @Test
    @DisplayName("honor explicit bounds for NEEDS_BOUNDS types")
    void shouldHonorExplicitBounds() {
        var generator = GeneratorTypeFactory.createGenerator(GeneratorType.STRINGS, 5, 5, "0", "0");
        var value = (String) generator.next();
        assertEquals(5, value.length());
    }

    @Test
    @DisplayName("honor explicit range for NEEDS_RANGE types")
    void shouldHonorExplicitRange() {
        var generator = GeneratorTypeFactory.createGenerator(GeneratorType.INTEGERS, 0, 0, "7", "7");
        assertEquals(7, generator.next());
    }

    @Test
    @DisplayName("wrap malformed range bounds in a descriptive JUnitException")
    void shouldRejectMalformedRange() {
        var exception = assertThrows(JUnitException.class,
                () -> GeneratorTypeFactory.createGenerator(GeneratorType.INTEGERS, 0, 0, "not-a-number", "10"));
        assertTrue(exception.getMessage().contains("Invalid range"), exception.getMessage());
    }
}
