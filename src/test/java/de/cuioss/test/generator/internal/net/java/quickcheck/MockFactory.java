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
package de.cuioss.test.generator.internal.net.java.quickcheck;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createStrictMock;

public class MockFactory {

    public static Generator<Object> createObjectGenerator() {
        return createMock(Generator.class);
    }

    public static Characteristic<Object> createObjectCharacteristic() {
        return createStrictMock(Characteristic.class);
    }

    public static Characteristic<Integer> createIntegerCharacteristic() {
        return createMock(Characteristic.class);
    }

    public static Generator<Integer> createIntegerGenerator() {
        return createMock(Generator.class);
    }

    public static Generator<Character> createCharacterGenerator() {
        return createMock(Generator.class);
    }

    public static Generator<Boolean> createBooleanMock() {
        return createMock(Generator.class);
    }

    public static Runner<Object> createRunnerMock() {
        return createMock(Runner.class);
    }

}
