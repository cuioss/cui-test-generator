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
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Generates {@link Float} objects within a configurable range.
 * 
 * <p>Features:</p>
 * <ul>
 *   <li>Configurable range through constructor parameters</li>
 *   <li>Default range: {@link Float#MIN_VALUE} to {@link Float#MAX_VALUE}</li>
 *   <li>Thread-safe implementation</li>
 *   <li>Uses double precision for internal generation to ensure even distribution</li>
 * </ul>
 * 
 * <p><em>Example usage:</em></p>
 * <pre>
 * // Generate floats using full range
 * var generator = new FloatObjectGenerator();
 * Float value = generator.next();
 * 
 * // Generate floats within specific range
 * var rangeGenerator = new FloatObjectGenerator(0.0f, 100.0f);
 * Float bounded = rangeGenerator.next(); // Value between 0 and 100
 * </pre>
 * 
 * @author Oliver Wolff
 * @see PrimitiveGenerators
 */
@AllArgsConstructor
@NoArgsConstructor
public class FloatObjectGenerator implements TypedGenerator<Float> {

    private float low = Float.MIN_VALUE;
    private float high = Float.MAX_VALUE;

    @Override
    public Float next() {
        return PrimitiveGenerators.doubles(low, high).next().floatValue();
    }

    @Override
    public Class<Float> getType() {
        return Float.class;
    }

}
