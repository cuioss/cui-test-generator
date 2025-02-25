/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.test.generator.impl;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;

import java.time.ZonedDateTime;

import static de.cuioss.test.generator.Generators.integers;

/**
 * Generates {@link ZonedDateTime} instances with random dates, times, and time zones.
 * This generator combines date generation with zone ID generation to create complete
 * zoned date-time values.
 *
 * <p>Features:</p>
 * <ul>
 *   <li>Generates valid ZonedDateTime instances</li>
 *   <li>Uses {@link Generators#dates()} for the date-time component</li>
 *   <li>Uses {@link Generators#zoneIds()} for the time zone component</li>
 *   <li>Provides utility methods for common test scenarios</li>
 *   <li>Thread-safe implementation</li>
 * </ul>
 *
 * <p><em>Example usage:</em></p>
 * <pre>
 * // Using the generator directly
 * var generator = new ZonedDateTimeGenerator();
 * ZonedDateTime dateTime = generator.next();
 *
 * // Using convenience methods
 * ZonedDateTime any = ZonedDateTimeGenerator.any();
 * ZonedDateTime future = ZonedDateTimeGenerator.future();
 * ZonedDateTime past = ZonedDateTimeGenerator.past();
 * </pre>
 *
 * <p>This generator is particularly useful for testing:</p>
 * <ul>
 *   <li>Time zone conversions</li>
 *   <li>Date-time formatting and parsing</li>
 *   <li>Temporal calculations across time zones</li>
 * </ul>
 *
 * @author Eugen Fischer
 * @see ZonedDateTime
 * @see Generators#dates()
 * @see Generators#zoneIds()
 */
public class ZonedDateTimeGenerator implements TypedGenerator<ZonedDateTime> {

    private static final TypedGenerator<Integer> SOME_INT = integers(1, 10);

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
        return now().minusYears(10);
    }

    /**
     * @return value of ZonedDateTime with date somewhere lastMonth
     */
    public static ZonedDateTime lastMonthAgo() {
        return now().minusMonths(1);
    }
}
