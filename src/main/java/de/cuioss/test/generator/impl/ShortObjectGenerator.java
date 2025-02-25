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

/**
 * Generates {@link Short} objects across the full range of possible short values.
 * 
 * <p>Features:</p>
 * <ul>
 *   <li>Generates values from -32,768 to 32,767 ({@link Short#MIN_VALUE} to {@link Short#MAX_VALUE})</li>
 *   <li>Even distribution across the entire short range</li>
 *   <li>Thread-safe implementation</li>
 *   <li>Uses integer generation internally for precision</li>
 * </ul>
 * 
 * <p><em>Example usage:</em></p>
 * <pre>
 * var generator = new ShortObjectGenerator();
 * Short value = generator.next(); // Returns a random short value
 * </pre>
 * 
 * <p>This generator is particularly useful for testing:</p>
 * <ul>
 *   <li>Boundary conditions at Short.MIN_VALUE and Short.MAX_VALUE</li>
 *   <li>Type conversion scenarios</li>
 *   <li>Numeric overflow cases</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @see PrimitiveGenerators
 */
public class ShortObjectGenerator implements TypedGenerator<Short> {

    @Override
    public Short next() {
        return PrimitiveGenerators.integers(-32768, 32767).next().shortValue();
    }

    @Override
    public Class<Short> getType() {
        return Short.class;
    }

}
