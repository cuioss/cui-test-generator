/*
 *  Licensed to the author under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.cui.test.generator.internal.net.java.quickcheck.generator.support;

import io.cui.test.generator.internal.net.java.quickcheck.generator.distribution.Distribution;

public class ByteGenerator extends AbstractNumberGenerator<Byte> {

    public ByteGenerator() {
        this(Byte.MIN_VALUE, Byte.MAX_VALUE);
    }

    public ByteGenerator(byte lo, byte hi) {
        this(lo, hi, Distribution.UNIFORM);
    }

    public ByteGenerator(byte min, byte max, Distribution dist) {
        super(min, max, dist);
    }

    @Override
    public Byte next() {
        return nextByte();
    }

    private byte nextByte() {
        return (byte) nextLong();
    }

}
