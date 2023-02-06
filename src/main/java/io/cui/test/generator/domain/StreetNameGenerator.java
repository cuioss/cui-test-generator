package io.cui.test.generator.domain;

import static io.cui.test.generator.Generators.fixedValues;

import io.cui.test.generator.TypedGenerator;

/**
 * Generator for some German Street-names.
 *
 * @author Oliver Wolff
 *
 */
public class StreetNameGenerator implements TypedGenerator<String> {

    private final TypedGenerator<String> streets =
        fixedValues("Hauptstraße", "Bahnhofstraße", "Brunnenweg", "Schlossallee", "Altrottstrasse", "Parkweg",
                "Paradeplatz");

    @Override
    public String next() {
        return streets.next();
    }

    @Override
    public Class<String> getType() {
        return String.class;
    }
}
