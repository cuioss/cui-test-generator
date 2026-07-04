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
package de.cuioss.test.generator;

import de.cuioss.test.generator.junit.EnableGeneratorController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@EnableGeneratorController
@DisplayName("TypedGenerator default getType() should")
class TypedGeneratorDefaultTypeTest {

    @Test
    @DisplayName("infer the type from the value returned by next()")
    void shouldInferTypeFromNext() {
        TypedGenerator<String> generator = () -> "x";
        assertEquals(String.class, generator.getType(),
                "Default getType() must infer the concrete class from next()");
    }

    @Test
    @DisplayName("throw NullPointerException when next() returns null")
    void shouldThrowWhenNextReturnsNull() {
        TypedGenerator<String> generator = () -> null;
        assertThrows(NullPointerException.class, generator::getType,
                "Default getType() dereferences next() and must fail on a null value");
    }
}
