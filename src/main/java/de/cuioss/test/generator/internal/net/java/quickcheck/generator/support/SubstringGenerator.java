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
import de.cuioss.tools.base.Preconditions;

import java.util.Objects;

public class SubstringGenerator implements Generator<String> {

    private final String superstring;
    private final Generator<Integer> sizes;

    public SubstringGenerator(String superstring, int minSize, int maxSize) {
        Objects.requireNonNull(superstring, "superstring");
        Preconditions.checkArgument(minSize >= 0);
        Preconditions.checkArgument(superstring.length() >= maxSize);
        this.superstring = superstring;
        this.sizes = new IntegerGenerator(minSize, maxSize);
    }

    @Override
    public String next() {
        int size = sizes.next();
        int space = superstring.length() - size;
        assert space >= 0;
        Integer offset = new IntegerGenerator(0, space).next();
        String substring = superstring.substring(offset, offset + size);
        assert substring.length() == size;
        return substring;
    }
}
