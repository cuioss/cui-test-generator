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
package de.cuioss.test.generator.internal.net.java.quickcheck.generator.support;

import de.cuioss.test.generator.internal.net.java.quickcheck.Generator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.Prototype;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CloningGeneratorTest {

    private Generator<Prototype> cloningGenerator;
    private Prototype prototype;

    @BeforeEach
    void setUp() {
        prototype = new Prototype("");
        cloningGenerator = PrimitiveGenerators.clonedValues(prototype);
    }

    @Test
    void cloningGeneratorPrototypeNotSameObjectAsGeneratedValue() {

        var next = cloningGenerator.next();
        assertNotNull(next);
        assertNotSame(prototype, next);
    }

    @Test
    void cloningGeneratorGeneratedValuesAreNotTheSame() {
        Prototype last = null;
        for (var i = 0; i < 10; i++) {
            var next = cloningGenerator.next();
            assertNotSame(next, last);
            last = next;
        }
    }

    @Test
    void throwsExceptionIfNotSerializable() {
        final var generator = new CloningGenerator<>(new Object());
        assertThrows(IllegalArgumentException.class, generator::next);
    }

}
