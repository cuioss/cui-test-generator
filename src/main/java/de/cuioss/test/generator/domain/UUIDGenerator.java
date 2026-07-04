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
 * Creates random RFC 4122 version-4 (variant 2) {@link UUID} instances, so that
 * {@link UUID#version()} returns {@code 4} and {@link UUID#variant()} returns {@code 2}.
 */
public class UUIDGenerator implements TypedGenerator<UUID> {

    private final TypedGenerator<Long> mostSignificantBits = longs();
    private final TypedGenerator<Long> leastSignificantBits = longs();

    @Override
    public UUID next() {
        long most = mostSignificantBits.next();
        long least = leastSignificantBits.next();
        most &= 0xFFFF_FFFF_FFFF_0FFFL;
        most |= 0x0000_0000_0000_4000L; // version 4
        least &= 0x3FFF_FFFF_FFFF_FFFFL;
        least |= 0x8000_0000_0000_0000L; // IETF variant (variant 2)
        return new UUID(most, least);
    }

    @Override
    public Class<UUID> getType() {
        return UUID.class;
    }

}
