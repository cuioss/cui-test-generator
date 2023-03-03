package de.cuioss.test.generator.domain;

import static de.cuioss.test.generator.Generators.fixedValues;

import de.cuioss.test.generator.TypedGenerator;

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
