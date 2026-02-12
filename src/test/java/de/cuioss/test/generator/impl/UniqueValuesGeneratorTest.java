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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UniqueValuesGenerator should")
class UniqueValuesGeneratorTest {

    @Test
    @DisplayName("generate unique values")
    void shouldGenerateUniqueValues() {
        var source = new IntegerGenerator(0, 1000);
        var generator = new UniqueValuesGenerator<>(source);
        var seen = new HashSet<Integer>();
        for (int i = 0; i < 50; i++) {
            assertTrue(seen.add(generator.next()), "Duplicate value generated");
        }
    }

    @Test
    @DisplayName("throw when uniqueness exhausted")
    void shouldThrowOnExhaustion() {
        TypedGenerator<String> source = new FixedValuesGenerator<>(String.class, List.of("A", "B"));
        var generator = new UniqueValuesGenerator<>(source);
        generator.next(); // "A" or "B"
        generator.next(); // the other one
        assertThrows(IllegalStateException.class, generator::next);
    }

    @Test
    @DisplayName("delegate getType to source")
    void shouldDelegateType() {
        var source = new IntegerGenerator();
        var generator = new UniqueValuesGenerator<>(source);
        assertEquals(Integer.class, generator.getType());
    }
}
