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

import de.cuioss.test.generator.internal.RandomContext;
import de.cuioss.test.generator.junit.EnableGeneratorController;
import de.cuioss.test.generator.junit.GeneratorSeed;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

@EnableGeneratorController
@GeneratorSeed(42L)
@DisplayName("URLGenerator should")
class URLGeneratorTest {

    @Test
    @DisplayName("return URL.class as type")
    void shouldReturnCorrectType() {
        assertEquals(URL.class, new URLGenerator().getType());
    }

    @Test
    @DisplayName("generate well-formed http or https URLs")
    void shouldGenerateWellFormedUrls() {
        var generator = new URLGenerator();
        for (int i = 0; i < 100; i++) {
            URL url = generator.next();
            String protocol = url.getProtocol();
            assertTrue("http".equals(protocol) || "https".equals(protocol),
                    "Unexpected protocol: " + protocol);
            assertFalse(url.getHost().isEmpty(), "URL must have a host: " + url);
            // A well-formed URL round-trips through URI parsing without error.
            assertDoesNotThrow(url::toURI, "URL must be well-formed: " + url);
        }
    }

    @Test
    @DisplayName("be reproducible with the same seed")
    void shouldBeReproducible() {
        var generator = new URLGenerator();
        RandomContext.setSeed(42L);
        URL first = generator.next();
        RandomContext.setSeed(42L);
        URL second = generator.next();
        assertEquals(first.toString(), second.toString(), "Same seed must yield the same URL");
    }
}
