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

import static io.cui.test.generator.internal.net.java.quickcheck.generator.support.ListGenerator.MAX_SIZE;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.support.ListGenerator.MIN_SIZE;

import java.util.List;

import io.cui.test.generator.internal.net.java.quickcheck.Generator;

/**
 * Generator for the default collection size.
 */
public class SizeGenerator implements Generator<Integer> {

    private final Generator<Integer> sizes = new IntegerGenerator(MIN_SIZE, MAX_SIZE);

    @Override public Integer next() { return sizes.next(); }

    /**
     * A minimum and maximum size value in the {@link ListGenerator#MIN_SIZE}
     * and {@link ListGenerator#MAX_SIZE} range.
     */
    public static List<Integer> anyMinMax(){
        FixedValuesGenerator<Integer> sized = new FixedValuesGenerator<>(2);
        return new SortedListGenerator<>(new SizeGenerator(), sized).next();
    }
}
