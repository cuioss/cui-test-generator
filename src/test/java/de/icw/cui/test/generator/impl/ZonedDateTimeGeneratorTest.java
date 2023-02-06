package de.icw.cui.test.generator.impl;

import static de.icw.cui.test.generator.impl.ZonedDateTimeGenerator.lastMonthAgo;
import static de.icw.cui.test.generator.impl.ZonedDateTimeGenerator.lastTenYearsAgo;
import static de.icw.cui.test.generator.impl.ZonedDateTimeGenerator.someDaysAgo;
import static de.icw.cui.test.generator.impl.ZonedDateTimeGenerator.someHoursAgo;
import static de.icw.cui.test.generator.impl.ZonedDateTimeGenerator.someMinutesAgo;
import static de.icw.cui.test.generator.impl.ZonedDateTimeGenerator.someMonthsAgo;
import static de.icw.cui.test.generator.impl.ZonedDateTimeGenerator.someYearsAgo;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class ZonedDateTimeGeneratorTest {

    @Test
    void shouldProvideTimes() {
        assertNotNull(lastMonthAgo());
        assertNotNull(lastTenYearsAgo());
        assertNotNull(someDaysAgo());
        assertNotNull(someHoursAgo());
        assertNotNull(someMinutesAgo());
        assertNotNull(someMonthsAgo());
        assertNotNull(someYearsAgo());
    }
}
