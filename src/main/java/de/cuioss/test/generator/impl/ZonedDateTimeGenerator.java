package de.cuioss.test.generator.impl;

import static de.cuioss.test.generator.Generators.integers;

import java.time.ZonedDateTime;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;

/**
 * Provide any value for {@linkplain ZonedDateTime}
 *
 * @author Eugen Fischer
 */
public class ZonedDateTimeGenerator implements TypedGenerator<ZonedDateTime> {

    private static final TypedGenerator<Integer> SOME_INT = integers(0, 10);

    @Override
    public ZonedDateTime next() {
        return ZonedDateTime.ofInstant(Generators.dates().next().toInstant(), Generators.zoneIds().next());
    }

    @Override
    public Class<ZonedDateTime> getType() {
        return ZonedDateTime.class;
    }

    /**
     * @return an arbitrary ZonedDateTime
     */
    public static ZonedDateTime any() {
        return new ZonedDateTimeGenerator().next();
    }

    /**
     * @return value of ZonedDateTime for now
     */
    public static ZonedDateTime now() {
        return ZonedDateTime.now();
    }

    /**
     * @return value of ZonedDateTime one hour ago
     */
    public static ZonedDateTime someMinutesAgo() {
        return now().minusMinutes(SOME_INT.next());
    }

    /**
     * @return value of ZonedDateTime one hour ago
     */
    public static ZonedDateTime someHoursAgo() {
        return now().minusHours(SOME_INT.next());
    }

    /**
     * @return value of ZonedDateTime one day ago
     */
    public static ZonedDateTime someDaysAgo() {
        return now().minusDays(SOME_INT.next());
    }

    /**
     * @return value of ZonedDateTime one week ago
     */
    public static ZonedDateTime someWeeksAgo() {
        return now().minusWeeks(SOME_INT.next());
    }

    /**
     * @return value of ZonedDateTime one month ago
     */
    public static ZonedDateTime someMonthsAgo() {
        return now().minusMonths(SOME_INT.next());
    }

    /**
     * @return value of ZonedDateTime one year ago
     */
    public static ZonedDateTime someYearsAgo() {
        return now().minusYears(SOME_INT.next());
    }

    /**
     * @return value of ZonedDateTime with date somewhere 10 years ago
     */
    public static ZonedDateTime lastTenYearsAgo() {
        return now()
                .minusYears(SOME_INT.next())
                .minusMonths(SOME_INT.next())
                .minusDays(SOME_INT.next());
    }

    /**
     * @return value of ZonedDateTime with date somewhere lastMonth
     */
    public static ZonedDateTime lastMonthAgo() {
        return now().minusMinutes(SOME_INT.next())
                .minusHours(SOME_INT.next())
                .minusDays(SOME_INT.next());
    }
}
