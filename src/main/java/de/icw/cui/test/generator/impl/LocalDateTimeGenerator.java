package de.icw.cui.test.generator.impl;

import java.time.LocalDateTime;

import de.icw.cui.test.generator.Generators;
import de.icw.cui.test.generator.TypedGenerator;

/**
 * Provide any valid value for {@linkplain LocalDateTime}
 *
 * @author i000576
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
