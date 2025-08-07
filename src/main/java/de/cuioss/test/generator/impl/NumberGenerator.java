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

import de.cuioss.test.generator.TypedGenerator;

import static de.cuioss.test.generator.Generators.integers;

/**
 * Generates {@link Number} instances using integer values.
 * This generator provides a basic implementation that creates
 * integer numbers within the full range of possible values.
 * 
 * <p>Features:</p>
 * <ul>
 *   <li>Uses {@link de.cuioss.test.generator.Generators#integers()} internally</li>
 *   <li>Generates values across the full integer range</li>
 *   <li>Thread-safe implementation</li>
 *   <li>Suitable for general numeric testing</li>
 * </ul>
 * 
 * <p><em>Example usage:</em></p>
 * <pre>
 * {@code
 * // Create a generator
 * var generator = new NumberGenerator();
 * 
 * // Generate single values
 * Number value = generator.next();
 * 
 * // Generate collections
 * var collectionGen = new CollectionGenerator&lt;>(generator);
 * List&lt;Number&gt; numbers = collectionGen.list(5); // List of 5 numbers
 * }
 * </pre>
 * 
 * <p>This generator is particularly useful for testing:</p>
 * <ul>
 *   <li>Generic number handling</li>
 *   <li>Numeric type conversions</li>
 *   <li>Mathematical operations</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @see Number
 * @see de.cuioss.test.generator.Generators#integers()
 */
public class NumberGenerator implements TypedGenerator<Number> {

    @Override
    public Number next() {
        return integers().next();
    }

    @Override
    public Class<Number> getType() {
        return Number.class;
    }

}
