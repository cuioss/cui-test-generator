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
package io.cui.test.generator.internal.net.java.quickcheck;

/**
 * <p>
 * Create a frequency generator. The frequency of {@link Generator} usage
 * depends on the generator weight.
 * </p>
 *
 */
public interface FrequencyGenerator<T> extends ExtendibleGenerator<T, T> {

    /**
     * Add a new input generator.
     *
     * @param gen
     *            input generator
     * @param weight
     *            weight of this generator
     * @return this generator
     */
    FrequencyGenerator<T> add(Generator<T> gen, int weight);
}
