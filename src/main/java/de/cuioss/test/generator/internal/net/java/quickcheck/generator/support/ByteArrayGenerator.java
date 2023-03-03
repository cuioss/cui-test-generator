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

public class ByteArrayGenerator extends AbstractPrimitiveArrayGenerator<Byte[], byte[]> {

    public static final int MAX_SIZE = ArrayGenerator.MAX_SIZE;
    public static final int MIN_SIZE = ArrayGenerator.MIN_SIZE;

    public ByteArrayGenerator() {
        super(new ArrayGenerator<>(new ByteGenerator(), Byte.class),
                byte.class);
    }

    public ByteArrayGenerator(Generator<Integer> size) {
        this(new ByteGenerator(), size);
    }

    public ByteArrayGenerator(Generator<Byte> content, Generator<Integer> size) {
        super(new ArrayGenerator<>(content, size, Byte.class), byte.class);
    }

}
