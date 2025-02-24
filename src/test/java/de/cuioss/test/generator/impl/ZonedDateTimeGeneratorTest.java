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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static de.cuioss.test.generator.impl.ZonedDateTimeGenerator.lastMonthAgo;
import static de.cuioss.test.generator.impl.ZonedDateTimeGenerator.lastTenYearsAgo;
import static de.cuioss.test.generator.impl.ZonedDateTimeGenerator.someDaysAgo;
import static de.cuioss.test.generator.impl.ZonedDateTimeGenerator.someHoursAgo;
import static de.cuioss.test.generator.impl.ZonedDateTimeGenerator.someMinutesAgo;
import static de.cuioss.test.generator.impl.ZonedDateTimeGenerator.someMonthsAgo;
import static de.cuioss.test.generator.impl.ZonedDateTimeGenerator.someYearsAgo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("ZonedDateTimeGenerator should")
class ZonedDateTimeGeneratorTest {

    @Nested
    @DisplayName("generate minute-based times")
    class MinuteBasedTests {
        @Test
        @DisplayName("provide times some minutes ago")
        void shouldProvideSomeMinutesAgo() {
            // Act
            var result = someMinutesAgo();

            // Assert
            assertNotNull(result, "Generated time should not be null");
            assertTimeIsInRange(result, ChronoUnit.MINUTES, 1, 60);
        }
    }

    @Nested
    @DisplayName("generate hour-based times")
    class HourBasedTests {
        @Test
        @DisplayName("provide times some hours ago")
        void shouldProvideSomeHoursAgo() {
            // Act
            var result = someHoursAgo();

            // Assert
            assertNotNull(result, "Generated time should not be null");
            assertTimeIsInRange(result, ChronoUnit.HOURS, 1, 24);
        }
    }

    @Nested
    @DisplayName("generate day-based times")
    class DayBasedTests {
        @Test
        @DisplayName("provide times some days ago")
        void shouldProvideSomeDaysAgo() {
            // Act
            var result = someDaysAgo();

            // Assert
            assertNotNull(result, "Generated time should not be null");
            assertTimeIsInRange(result, ChronoUnit.DAYS, 1, 30);
        }
    }

    @Nested
    @DisplayName("generate month-based times")
    class MonthBasedTests {
        @Test
        @DisplayName("provide times from last month")
        void shouldProvideLastMonthAgo() {
            // Act
            var result = lastMonthAgo();

            // Assert
            assertNotNull(result, "Generated time should not be null");
            assertTimeIsInRange(result, ChronoUnit.MONTHS, 1, 1);
        }

        @Test
        @DisplayName("provide times from some months ago")
        void shouldProvideSomeMonthsAgo() {
            // Act
            var result = someMonthsAgo();

            // Assert
            assertNotNull(result, "Generated time should not be null");
            assertTimeIsInRange(result, ChronoUnit.MONTHS, 1, 12);
        }
    }

    @Nested
    @DisplayName("generate year-based times")
    class YearBasedTests {
        @Test
        @DisplayName("provide times from some years ago")
        void shouldProvideSomeYearsAgo() {
            // Act
            var result = someYearsAgo();

            // Assert
            assertNotNull(result, "Generated time should not be null");
            assertTimeIsInRange(result, ChronoUnit.YEARS, 1, 10);
        }

        @Test
        @DisplayName("provide times from last ten years")
        void shouldProvideLastTenYearsAgo() {
            // Act
            var result = lastTenYearsAgo();

            // Assert
            assertNotNull(result, "Generated time should not be null");
            assertTimeIsInRange(result, ChronoUnit.YEARS, 9, 11);
        }
    }

    /**
     * Helper method to assert that a generated time falls within the expected range
     *
     * @param generatedTime The time to check
     * @param unit          The time unit to measure
     * @param minUnits      Minimum number of units ago
     * @param maxUnits      Maximum number of units ago
     */
    private void assertTimeIsInRange(ZonedDateTime generatedTime, ChronoUnit unit, long minUnits, long maxUnits) {
        var now = ZonedDateTime.now();
        var unitsBetween = unit.between(generatedTime, now);

        assertTrue(unitsBetween >= minUnits && unitsBetween <= maxUnits,
                String.format("Generated time should be between %d and %d %s ago, but was %d",
                        minUnits, maxUnits, unit.toString().toLowerCase(), unitsBetween));
    }

}
