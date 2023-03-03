package de.cuioss.test.generator.impl;

import java.time.LocalTime;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;

/**
 * Provide any valid value for {@linkplain LocalTime}
 *
 * @author Eugen Fischer
 */
public class LocalTimeGenerator implements TypedGenerator<LocalTime> {

    private static final Integer SECONDS_PER_DAY = 24 * 60 * 60;

    @Override
    public LocalTime next() {
        return LocalTime.ofSecondOfDay(Generators.integers(0, SECONDS_PER_DAY - 1).next().longValue());
    }

    @Override
    public Class<LocalTime> getType() {
        return LocalTime.class;
    }

}
