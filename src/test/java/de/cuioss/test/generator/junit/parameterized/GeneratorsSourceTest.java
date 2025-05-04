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

import de.cuioss.test.generator.junit.EnableGeneratorController;
import de.cuioss.tools.logging.CuiLogger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link GeneratorsSource} and {@link GeneratorsSourceArgumentsProvider}.
 */
@EnableGeneratorController
class GeneratorsSourceTest {

    private static final CuiLogger LOGGER = new CuiLogger(GeneratorsSourceTest.class);

    @ParameterizedTest
    @DisplayName("Should generate multiple string values from Generators")
    @GeneratorsSource(
            generator = GeneratorType.STRINGS,
            minSize = 3,
            maxSize = 10,
            count = 5
    )
    void shouldGenerateMultipleStringValues(String value) {
        LOGGER.debug("String value test: {}", value);
        assertNotNull(value);
        assertTrue(value.length() >= 3 && value.length() <= 10);
    }

    @ParameterizedTest
    @DisplayName("Should generate integer values from Generators with parameters")
    @GeneratorsSource(
            generator = GeneratorType.INTEGERS,
            low = "1",
            high = "10",
            count = 5
    )
    void shouldGenerateIntegerValuesWithParameters(Integer value) {
        LOGGER.debug("Integer value test: {}", value);
        assertNotNull(value);
        assertTrue(value >= 1 && value <= 10);
    }

    @ParameterizedTest
    @DisplayName("Should generate values with specific seed")
    @GeneratorsSource(
            generator = GeneratorType.STRINGS,
            minSize = 3,
            maxSize = 10,
            seed = 42L,
            count = 3
    )
    void shouldGenerateValuesWithSpecificSeed(String value) {
        LOGGER.debug("Seed test: {}", value);
        assertNotNull(value);
    }

    @ParameterizedTest
    @DisplayName("Should generate non-empty strings")
    @GeneratorsSource(
            generator = GeneratorType.NON_EMPTY_STRINGS,
            count = 3
    )
    void shouldGenerateNonEmptyStrings(String value) {
        LOGGER.debug("Non-empty string test: {}", value);
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @ParameterizedTest
    @DisplayName("Should generate boolean values")
    @GeneratorsSource(
            generator = GeneratorType.BOOLEANS,
            count = 3
    )
    void shouldGenerateBooleanValues(Boolean value) {
        LOGGER.debug("Boolean value test: {}", value);
        assertNotNull(value);
    }
}