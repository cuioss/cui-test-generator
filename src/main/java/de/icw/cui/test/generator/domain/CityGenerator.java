package de.icw.cui.test.generator.domain;

import static de.icw.cui.test.generator.Generators.fixedValues;

import de.icw.cui.test.generator.TypedGenerator;

/**
 * Generator for some German cities.
 *
 * @author Oliver Wolff
 *
 */
public class CityGenerator implements TypedGenerator<String> {

    private final TypedGenerator<String> cities =
        fixedValues("Heidelberg", "Walldorf", "Mannheim", "Stuttgart", "Karlsruhe", "Berlin", "Freiburg", "Hamburg",
                "München");

    @Override
    public String next() {
        return cities.next();
    }

    @Override
    public Class<String> getType() {
        return String.class;
    }
}
