package io.cui.test.generator.impl;

import static java.time.ZoneId.getAvailableZoneIds;
import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import io.cui.test.generator.Generators;
import io.cui.test.generator.TypedGenerator;

/**
 * Provide any valid value for {@linkplain ZoneOffset}
 *
 * @author Eugen Fischer
 */
public class ZoneOffsetGenerator implements TypedGenerator<ZoneOffset> {

    private static final TypedGenerator<ZoneId> ZONE_IDS_GEN =
        Generators.fixedValues(ZoneId.class, getAvailableZoneIds().stream().map(ZoneId::of).collect(toList()));

    @Override
    public java.time.ZoneOffset next() {
        return LocalDateTime.now().atZone(ZONE_IDS_GEN.next()).getOffset();
    }

    @Override
    public Class<ZoneOffset> getType() {
        return ZoneOffset.class;
    }

}
