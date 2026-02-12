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

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("DateGenerator should")
class DateGeneratorTest {

    @Test
    @DisplayName("generate non-null Date instances")
    void shouldGenerateDates() {
        var generator = new DateGenerator();
        for (int i = 0; i < 100; i++) {
            assertNotNull(generator.next());
        }
    }

    @Test
    @DisplayName("return Date.class as type")
    void shouldReturnCorrectType() {
        assertEquals(Date.class, new DateGenerator().getType());
    }
}
