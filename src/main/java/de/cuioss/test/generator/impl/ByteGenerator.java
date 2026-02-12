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

/**
 * Generates random {@link Byte} values within the full byte range.
 *
 * @author Oliver Wolff
 */
public class ByteGenerator implements TypedGenerator<Byte> {

    private final IntegerGenerator delegate;

    /**
     * Creates a generator for the full byte range.
     */
    public ByteGenerator() {
        this.delegate = new IntegerGenerator(Byte.MIN_VALUE, Byte.MAX_VALUE);
    }

    @Override
    public Byte next() {
        return delegate.next().byteValue();
    }

    @Override
    public Class<Byte> getType() {
        return Byte.class;
    }
}
