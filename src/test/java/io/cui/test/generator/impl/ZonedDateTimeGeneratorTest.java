package io.cui.test.generator.impl;

import static io.cui.test.generator.impl.ZonedDateTimeGenerator.lastMonthAgo;
import static io.cui.test.generator.impl.ZonedDateTimeGenerator.lastTenYearsAgo;
import static io.cui.test.generator.impl.ZonedDateTimeGenerator.someDaysAgo;
import static io.cui.test.generator.impl.ZonedDateTimeGenerator.someHoursAgo;
import static io.cui.test.generator.impl.ZonedDateTimeGenerator.someMinutesAgo;
import static io.cui.test.generator.impl.ZonedDateTimeGenerator.someMonthsAgo;
import static io.cui.test.generator.impl.ZonedDateTimeGenerator.someYearsAgo;
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
