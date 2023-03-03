package de.cuioss.test.generator.impl;

import java.time.LocalDate;

import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators;

/**
 * Provide any valid value for {@linkplain LocalDate}
 *
 * @author Eugen Fischer
 */
public class LocalDateGenerator implements TypedGenerator<LocalDate> {

    @Override
    public LocalDate next() {
        return LocalDate.ofEpochDay(PrimitiveGenerators.longs(-23000, 23000).next());
    }

    @Override
    public Class<LocalDate> getType() {
        return LocalDate.class;
    }

}
