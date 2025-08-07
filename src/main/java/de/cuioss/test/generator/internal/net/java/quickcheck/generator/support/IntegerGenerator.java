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

import de.cuioss.test.generator.internal.net.java.quickcheck.generator.distribution.Distribution;

public class IntegerGenerator extends AbstractNumberGenerator<Integer> {

    public IntegerGenerator() {
        this(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public IntegerGenerator(int min, int max) {
        this(min, max, Distribution.UNIFORM);
    }

    public IntegerGenerator(int min, int max, Distribution dist) {
        super(min, max, dist);
    }

    @Override
    public Integer next() {
        return nextInt();
    }

    public int nextInt() {
        return (int) nextLong();
    }
}
