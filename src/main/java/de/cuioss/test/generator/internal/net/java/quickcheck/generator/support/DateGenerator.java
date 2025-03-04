/*
 * Licensed to the author under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.test.generator.internal.net.java.quickcheck.generator.support;

import de.cuioss.test.generator.internal.net.java.quickcheck.Generator;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.signum;

public class DateGenerator implements Generator<Date> {

    private final VetoableGenerator<Long> generator;

    public DateGenerator(TimeUnit precision, long low, long high, int tries) {
        generator = new VetoableGenerator<>(new MillisGenerator(precision, low, high), tries) {

            @Override
            protected boolean tryValue(Long value) {
                return value != null;
            }
        };
    }

    @Override
    public Date next() {
        return new Date(generator.next());
    }

    private static class MillisGenerator implements Generator<Long> {

        private final TimeUnit precision;
        private final LongGenerator times;
        private final long low;
        private final long high;

        public MillisGenerator(TimeUnit precision, long low, long high) {
            this.precision = precision;
            this.times = new LongGenerator(low, high);
            this.low = low;
            this.high = high;
        }

        @Override
        public Long next() {
            Long millis = times.next();
            Calendar time = Calendar.getInstance();
            time.setTimeInMillis(millis);
            switch (precision) {
                case DAYS:
                    time.set(Calendar.HOUR, 0); //$FALL-THROUGH$
                case HOURS:
                    time.set(Calendar.MINUTE, 0); //$FALL-THROUGH$
                case MINUTES:
                    time.set(Calendar.SECOND, 0); //$FALL-THROUGH$
                case SECONDS:
                    time.set(Calendar.MILLISECOND, 0); //$FALL-THROUGH$
                default:
            }
            long correctedMillis = time.getTimeInMillis();
            return isOutOffBounds(correctedMillis) || isOverflow(millis, correctedMillis) ? null : correctedMillis;
        }

        private boolean isOutOffBounds(long correctedMillis) {
            return correctedMillis < low || correctedMillis > high;
        }

        // TODO define this differently (signum changes near 0 without overflow)
        private boolean isOverflow(long millis, long correctedMillis) {
            return signum(correctedMillis) != signum(millis);
        }

        @Override
        public String toString() {
            return "%s[low=%s, high=%s, precision=%s".formatted(getClass().getSimpleName(), low, high, precision);
        }

    }
}
