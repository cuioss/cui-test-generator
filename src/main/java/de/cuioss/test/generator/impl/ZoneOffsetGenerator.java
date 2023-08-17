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
 * Provide any valid value for {@linkplain ZoneOffset}
 *
 * @author Eugen Fischer
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
