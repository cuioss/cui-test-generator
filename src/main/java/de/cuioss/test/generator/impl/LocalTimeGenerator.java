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
package de.cuioss.test.generator.impl;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;

import java.time.LocalTime;

/**
 * Generates {@link LocalTime} instances covering all possible times within a day.
 * The generator creates times with second precision, evenly distributed across
 * the full 24-hour period.
 * 
 * <p>Features:</p>
 * <ul>
 *   <li>Generates times from 00:00:00 to 23:59:59</li>
 *   <li>Uses second-level precision</li>
 *   <li>Even distribution across all possible seconds of the day</li>
 *   <li>Thread-safe implementation</li>
 * </ul>
 * 
 * <p><em>Example usage:</em></p>
 * <pre>
 * {@code
 * // Create a generator
 * var generator = new LocalTimeGenerator();
 * 
 * // Generate single values
 * LocalTime time = generator.next();
 * 
 * // Generate collections
 * var collectionGen = new CollectionGenerator&lt;>(generator);
 * List&lt;LocalTime&gt; times = collectionGen.list(5); // List of 5 times
 * }
 * </pre>
 * 
 * <p>This generator is particularly useful for testing:</p>
 * <ul>
 *   <li>Time formatting and parsing</li>
 *   <li>Time-based calculations</li>
 *   <li>Time range validations</li>
 *   <li>24-hour format handling</li>
 * </ul>
 *
 * @author Eugen Fischer
 * @see LocalTime
 * @see Generators#integers(int, int)
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
