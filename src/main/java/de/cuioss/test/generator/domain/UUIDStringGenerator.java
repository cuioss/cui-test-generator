package de.cuioss.test.generator.domain;

import java.util.UUID;

import de.cuioss.test.generator.TypedGenerator;

/**
 * Creates instrances of random uid-strings
 *
 * @author Oliver Wolff
 *
 */
public class UUIDStringGenerator implements TypedGenerator<String> {

    private final TypedGenerator<UUID> uuids = new UUIDGenerator();

    @Override
    public String next() {
        return uuids.next().toString();
    }

    @Override
    public Class<String> getType() {
        return String.class;
    }

}
