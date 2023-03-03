package de.cuioss.test.generator.domain;

import static de.cuioss.test.generator.Generators.longs;

import java.util.UUID;

import de.cuioss.test.generator.TypedGenerator;

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
