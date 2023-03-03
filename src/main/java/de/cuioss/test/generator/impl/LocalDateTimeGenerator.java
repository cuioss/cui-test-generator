package de.cuioss.test.generator.impl;

import java.time.LocalDateTime;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;

/**
 * Provide any valid value for {@linkplain LocalDateTime}
 *
 * @author Eugen Fischer
 */
public class LocalDateTimeGenerator implements TypedGenerator<LocalDateTime> {

    @Override
    public LocalDateTime next() {
        return LocalDateTime.of(Generators.localDates().next(), Generators.localTimes().next());
    }

    @Override
    public Class<LocalDateTime> getType() {
        return LocalDateTime.class;
    }

}
