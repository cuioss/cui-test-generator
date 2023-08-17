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

import lombok.Getter;

/**
 * Indicates that a characteristic specification was not true for a given
 * instance.
 */
@SuppressWarnings("java:S1948") // owolff: not a problem for test-code
public class CharacteristicException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final Object instance;

    @Getter
    private final Characteristic<?> charateristic;

    /**
     * @param message       error message
     * @param cause         causing exception thrown by characteristic
     * @param charateristic characteristic violated
     */
    public CharacteristicException(String message, Throwable cause, Characteristic<?> charateristic) {
        this(message, cause, charateristic, null);
    }

    /**
     * @param message       error message
     * @param cause         causing exception thrown by characteristic
     * @param instance      violating the specified characteristic
     * @param charateristic characteristic violated
     */
    public CharacteristicException(String message, Throwable cause, Characteristic<?> charateristic, Object instance) {
        super(message, cause);
        this.instance = instance;
        this.charateristic = charateristic;
    }

    /**
     * Instance causing the characteristic violation.
     *
     * @return the violating instance
     */
    public Object getInstance() {
        return instance;
    }
}
