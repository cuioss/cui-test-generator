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
package de.cuioss.test.generator.domain;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.distribution.RandomConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("UUIDGenerator should")
class UUIDGeneratorTest {

    @Test
    @DisplayName("produce reproducible UUIDs with same seed")
    void producesReproducibleData() {
        // Arrange
        long seed = Generators.longs().next();
        var generator = new UUIDGenerator();

        // Act & Assert - First run
        RandomConfiguration.setSeed(seed);
        var result1 = generator.next();
        assertNotNull(result1, "Generated UUID should not be null");

        // Act & Assert - Second run with same seed
        RandomConfiguration.setSeed(seed);
        var result2 = generator.next();
        assertNotNull(result2, "Generated UUID should not be null");

        assertEquals(result1, result2, "UUIDGenerator should produce reproducible data with same seed");
    }

    @Test
    @DisplayName("provide correct type information")
    void shouldProvideCorrectType() {
        // Arrange
        var generator = new UUIDGenerator();

        // Act & Assert
        assertEquals(UUID.class, generator.getType(), "Generator should return UUID.class as type");
    }

    @Test
    @DisplayName("generate valid UUIDs")
    void shouldGenerateValidUUIDs() {
        // Arrange
        var generator = new UUIDGenerator();

        // Act
        var uuid = generator.next();

        // Assert
        assertNotNull(uuid, "Generated UUID should not be null");
        assertNotNull(uuid.toString(), "UUID string representation should not be null");
    }
}
