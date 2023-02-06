package io.cui.test.generator.domain;

import static io.cui.test.generator.Generators.longs;

import java.util.UUID;

import io.cui.test.generator.TypedGenerator;

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
