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
package de.cuioss.test.generator.domain;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.distribution.RandomConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UUIDStringGenerator should")
class UUIDStringGeneratorTest {

    private static final String UUID_PATTERN =
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

    @Test
    @DisplayName("produce reproducible UUID strings with same seed")
    void producesReproducibleData() {
        // Arrange
        long seed = Generators.longs().next();
        var generator = new UUIDStringGenerator();

        // Act & Assert - First run
        RandomConfiguration.setSeed(seed);
        var result1 = generator.next();
        assertNotNull(result1, "Generated UUID string should not be null");

        // Act & Assert - Second run with same seed
        RandomConfiguration.setSeed(seed);
        var result2 = generator.next();
        assertNotNull(result2, "Generated UUID string should not be null");

        assertEquals(result1, result2, "UUIDStringGenerator should produce reproducible data with same seed");
    }

    @Test
    @DisplayName("provide correct type information")
    void shouldProvideCorrectType() {
        // Arrange
        var generator = new UUIDStringGenerator();

        // Act & Assert
        assertEquals(String.class, generator.getType(), "Generator should return String.class as type");
    }

    @Test
    @DisplayName("generate valid UUID strings")
    void shouldGenerateValidUUIDStrings() {
        // Arrange
        var generator = new UUIDStringGenerator();

        // Act
        var uuidString = generator.next();

        // Assert
        assertNotNull(uuidString, "Generated UUID string should not be null");
        assertTrue(uuidString.matches(UUID_PATTERN),
                "Generated string should match UUID pattern: " + uuidString);

        // Verify it can be parsed as UUID
        var uuid = UUID.fromString(uuidString);
        assertNotNull(uuid, "UUID string should be parseable to UUID object");
    }
}
