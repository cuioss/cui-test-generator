package io.cui.test.generator.impl;

import java.time.LocalTime;

import io.cui.test.generator.Generators;
import io.cui.test.generator.TypedGenerator;

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
