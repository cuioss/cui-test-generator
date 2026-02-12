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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FixedValuesGenerator should")
class FixedValuesGeneratorTest {

    @Test
    @DisplayName("return only values from the provided list")
    void shouldReturnOnlyProvidedValues() {
        var values = List.of("A", "B", "C");
        var generator = new FixedValuesGenerator<>(String.class, values);
        for (int i = 0; i < 100; i++) {
            assertTrue(values.contains(generator.next()));
        }
    }

    @Test
    @DisplayName("throw on empty list")
    void shouldThrowOnEmptyList() {
        assertThrows(IllegalArgumentException.class,
                () -> new FixedValuesGenerator<>(String.class, new ArrayList<>()));
    }

    @Test
    @DisplayName("return correct type")
    void shouldReturnCorrectType() {
        var generator = new FixedValuesGenerator<>(String.class, List.of("A"));
        assertEquals(String.class, generator.getType());
    }

    @Test
    @DisplayName("work with enum values")
    void shouldWorkWithEnums() {
        var generator = new FixedValuesGenerator<>(TimeUnit.class,
                List.of(TimeUnit.SECONDS, TimeUnit.MINUTES));
        for (int i = 0; i < 50; i++) {
            var value = generator.next();
            assertTrue(value == TimeUnit.SECONDS || value == TimeUnit.MINUTES);
        }
    }
}
