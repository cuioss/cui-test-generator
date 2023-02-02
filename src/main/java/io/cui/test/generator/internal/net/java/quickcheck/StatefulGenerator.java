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
 * Any generator keeping state information has to implemet this interface.
 *
 * @param <T>
 *            type of the generated instance
 */
public interface StatefulGenerator<T> extends Generator<T> {

    /**
     * Resets the state of the generator to the state it had immediately after
     * construction.
     * <p>
     * Makes a heavy weight generator reusable.
     */
    void reset();
}
