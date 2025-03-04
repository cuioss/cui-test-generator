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

import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators;

import java.time.LocalDate;

/**
 * Generates {@link LocalDate} instances within a reasonable range around the epoch.
 * The generator creates dates between approximately 63 years before and after the epoch
 * (1970-01-01), providing a good range for most testing scenarios.
 * 
 * <p>Features:</p>
 * <ul>
 *   <li>Generates dates from -23000 to +23000 days from epoch</li>
 *   <li>Covers dates from roughly 1907 to 2033</li>
 *   <li>Even distribution across the range</li>
 *   <li>Thread-safe implementation</li>
 * </ul>
 * 
 * <p><em>Example usage:</em></p>
 * <pre>
 * {@code
 * // Create a generator
 * var generator = new LocalDateGenerator();
 * 
 * // Generate single values
 * LocalDate date = generator.next();
 * 
 * // Generate collections
 * var collectionGen = new CollectionGenerator&lt;&gt;(generator);
 * List&lt;LocalDate&gt; dates = collectionGen.list(5); // List of 5 dates
 * }
 * </pre>
 * 
 * <p>This generator is particularly useful for testing:</p>
 * <ul>
 *   <li>Date formatting and parsing</li>
 *   <li>Date calculations and comparisons</li>
 *   <li>Business logic involving dates</li>
 * </ul>
 *
 * @author Eugen Fischer
 * @see LocalDate
 * @see PrimitiveGenerators#longs(long, long)
 */
public class LocalDateGenerator implements TypedGenerator<LocalDate> {

    @Override
    public LocalDate next() {
        return LocalDate.ofEpochDay(PrimitiveGenerators.longs(-23000, 23000).next());
    }

    @Override
    public Class<LocalDate> getType() {
        return LocalDate.class;
    }

}
