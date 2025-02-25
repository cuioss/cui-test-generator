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

import java.util.UUID;

import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.tools.logging.CuiLogger;

/**
 * Generates random UUID strings in the standard 8-4-4-4-12 format (e.g. "550e8400-e29b-41d4-a716-446655440000").
 * The generator is thread-safe and produces reproducible results when using the same seed.
 * 
 * <p><em>Example usage from tests:</em></p>
 * <pre>
 * var generator = new UUIDStringGenerator();
 * String uuid = generator.next(); // Returns a valid UUID string
 * UUID.fromString(uuid); // Can be parsed back to UUID
 * </pre>
 * 
 * @author Oliver Wolff
 */
public class UUIDStringGenerator implements TypedGenerator<String> {

    private static final CuiLogger LOGGER = new CuiLogger(UUIDStringGenerator.class);
    private final TypedGenerator<UUID> uuids = new UUIDGenerator();

    @Override
    public String next() {
        var result = uuids.next().toString();
        LOGGER.debug("Generated UUID: %s", result);
        return result;
    }

    @Override
    public Class<String> getType() {
        return String.class;
    }

}
