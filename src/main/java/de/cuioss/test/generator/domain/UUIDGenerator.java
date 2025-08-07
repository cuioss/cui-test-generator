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

import de.cuioss.test.generator.TypedGenerator;

import java.util.UUID;

import static de.cuioss.test.generator.Generators.longs;

/**
 * Creates instances of UUIDs
 */
public class UUIDGenerator implements TypedGenerator<UUID> {

    private final TypedGenerator<Long> mostSignificantBits = longs();
    private final TypedGenerator<Long> leastSignificantBits = longs();

    @Override
    public UUID next() {
        return new UUID(mostSignificantBits.next(), leastSignificantBits.next());
    }

}
