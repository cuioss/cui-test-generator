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
package de.cuioss.test.generator.impl;

import static java.time.ZoneId.getAvailableZoneIds;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;

/**
 * Generates {@link ZoneOffset} instances based on the available system time zones.
 * This generator creates offsets by sampling from all available zone IDs and
 * extracting their current offsets.
 * 
 * <p>Features:</p>
 * <ul>
 *   <li>Uses all system-available zone IDs ({@link ZoneId#getAvailableZoneIds()})</li>
 *   <li>Generates valid offsets based on current time</li>
 *   <li>Covers both positive and negative offsets</li>
 *   <li>Thread-safe implementation</li>
 * </ul>
 * 
 * <p><em>Example usage:</em></p>
 * <pre>
 * var generator = new ZoneOffsetGenerator();
 * ZoneOffset offset = generator.next();
 * 
 * // Use with collection generator
 * var collectionGen = new CollectionGenerator<>(generator);
 * List<ZoneOffset> offsets = collectionGen.list(5); // List of 5 offsets
 * </pre>
 * 
 * <p>This generator is particularly useful for testing:</p>
 * <ul>
 *   <li>Time zone offset calculations</li>
 *   <li>UTC conversions</li>
 *   <li>Date-time formatting with offsets</li>
 *   <li>International time handling</li>
 * </ul>
 *
 * @author Eugen Fischer
 * @see ZoneOffset
 * @see ZoneId
 * @see LocalDateTime
 */
public class ZoneOffsetGenerator implements TypedGenerator<ZoneOffset> {

    private static final TypedGenerator<ZoneId> ZONE_IDS_GEN = Generators.fixedValues(ZoneId.class,
            getAvailableZoneIds().stream().map(ZoneId::of).toList());

    @Override
    public java.time.ZoneOffset next() {
        return LocalDateTime.now().atZone(ZONE_IDS_GEN.next()).getOffset();
    }

    @Override
    public Class<ZoneOffset> getType() {
        return ZoneOffset.class;
    }

}
