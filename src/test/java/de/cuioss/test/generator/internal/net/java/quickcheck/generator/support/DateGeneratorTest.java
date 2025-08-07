/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.test.generator.internal.net.java.quickcheck.generator.support;

import de.cuioss.test.generator.internal.net.java.quickcheck.Generator;
import de.cuioss.test.generator.internal.net.java.quickcheck.QuickCheck;
import de.cuioss.test.generator.internal.net.java.quickcheck.characteristic.Classification;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators.*;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.dates;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.longs;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGeneratorsIterables.someDates;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.distribution.Distribution.POSITIV_NORMAL;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable;
import static java.lang.Long.MAX_VALUE;
import static java.lang.Long.MIN_VALUE;
import static java.util.concurrent.TimeUnit.DAYS;
import static org.junit.jupiter.api.Assertions.*;

class DateGeneratorTest {

    @Test
    void generateDateWithSecsPrecission() {
        var classification = new Classification();
        for (Date date : toIterable(PrimitiveGenerators.dates(TimeUnit.SECONDS))) {
            classify(classification, date);
        }
        assertZero(classification, Calendar.MILLISECOND);
        assertNotZero(classification, Calendar.SECOND);
        assertNotZero(classification, Calendar.MINUTE);
        assertNotZero(classification, Calendar.HOUR);
        assertNotZero(classification, Calendar.DAY_OF_WEEK);
        assertNotZero(classification, Calendar.MONTH);
        assertNotZero(classification, Calendar.YEAR);
    }

    @Test
    void generateDateWithMillisPrecission() {
        var classification = new Classification();
        for (Date date : toIterable(PrimitiveGenerators.dates(TimeUnit.MILLISECONDS))) {
            classify(classification, date);
        }
        assertNotZero(classification, Calendar.MILLISECOND);
        assertNotZero(classification, Calendar.SECOND);
        assertNotZero(classification, Calendar.MINUTE);
        assertNotZero(classification, Calendar.HOUR);
        assertNotZero(classification, Calendar.DAY_OF_WEEK);
        assertNotZero(classification, Calendar.MONTH);
        assertNotZero(classification, Calendar.YEAR);
    }

    @Test
    void generateDateWithDaysPrecission() {
        var classification = new Classification();
        for (Date date : toIterable(PrimitiveGenerators.dates(TimeUnit.DAYS))) {
            classify(classification, date);
        }
        assertZero(classification, Calendar.MILLISECOND);
        assertZero(classification, Calendar.SECOND);
        assertZero(classification, Calendar.MINUTE);
        assertZero(classification, Calendar.HOUR);
        assertNotZero(classification, Calendar.DAY_OF_WEEK);
        assertNotZero(classification, Calendar.MONTH);
        assertNotZero(classification, Calendar.YEAR);
    }

    @Test
    void generateDateWithHoursPrecission() {
        var classification = new Classification();
        for (Date date : toIterable(PrimitiveGenerators.dates(TimeUnit.HOURS))) {
            classify(classification, date);
        }
        assertZero(classification, Calendar.MILLISECOND);
        assertZero(classification, Calendar.SECOND);
        assertZero(classification, Calendar.MINUTE);
        assertNotZero(classification, Calendar.HOUR);
        assertNotZero(classification, Calendar.DAY_OF_WEEK);
        assertNotZero(classification, Calendar.MONTH);
        assertNotZero(classification, Calendar.YEAR);
    }

    Generator<List<Long>> boundGenerator() {
        var edgeTreshold = TimeUnit.DAYS.toMillis(10);

        var nearMin = longs(MIN_VALUE, MIN_VALUE + edgeTreshold, POSITIV_NORMAL);
        var nearZero = longs(-1 * edgeTreshold, edgeTreshold, POSITIV_NORMAL);
        var nearMax = longs(MAX_VALUE - edgeTreshold, MAX_VALUE, POSITIV_NORMAL);
        Generator<Long> longs = oneOf(nearMin).add(nearZero).add(nearMax).add(longs());
        return new VetoableGenerator<>(sortedLists(longs, 2, 2), DEFAULT_MAX_TRIES) {

            @Override
            protected boolean tryValue(List<Long> value) {
                return value.get(1) - value.getFirst() > DAYS.toMillis(QuickCheck.MAX_NUMBER_OF_RUNS);
            }
        };
    }

    @Test
    void boundedGenerator() {
        for (List<Long> bounds : toIterable(boundGenerator())) {
            var lo = bounds.getFirst();
            var hi = bounds.get(1);

            assertBounds(lo, hi, dates(lo, hi).next());
            assertBounds(lo, hi, dates(new Date(lo), new Date(hi)).next());
        }
    }

    @Test
    void boundedDayPrecisionGenerator() {
        var next = boundGenerator().next();
        long low = next.getFirst();
        long high = next.get(1);

        for (Date date : toIterable(dates(low, high, DAYS))) {
            assertBounds(low, high, date);
        }
    }

    private void assertBounds(Long lo, Long hi, Date date) {
        var next = date.getTime();
        assertTrue(lo <= next, "lo <= next, %s, %s".formatted(lo, next));
        assertTrue(hi >= next, "hi >= next, %s, %s".formatted(hi, next));
    }

    private void classify(Classification classification, Date date) {
        var instance = Calendar.getInstance();
        instance.setTime(date);
        classify(classification, instance, Calendar.MILLISECOND);
        classify(classification, instance, Calendar.SECOND);
        classify(classification, instance, Calendar.MINUTE);
        classify(classification, instance, Calendar.HOUR);
        classify(classification, instance, Calendar.DAY_OF_WEEK);
        classify(classification, instance, Calendar.MONTH);
        classify(classification, instance, Calendar.YEAR);
    }

    private void classify(Classification classification, Calendar instance, int value) {
        classification.doClassify(0 != instance.get(value), value);
    }

    private void assertNotZero(Classification classification, int value) {
        assertTrue(classification.getFrequency(value) > 80.0);
    }

    private void assertZero(Classification classification, int value) {
        assertEquals(0.0, classification.getFrequency(value));
    }

    @Test
    void dateGenerator() {
        for (Date any : someDates()) {
            assertNotNull(any);
        }
    }
}
