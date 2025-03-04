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

import java.io.Serial;

/**
 * GeneratorException is thrown if the creation of a new instance failed.
 *
 */
public class GeneratorException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    private final transient Generator<?> generator;

    /**
     * @param message   error message
     * @param generator offending generator
     */
    public GeneratorException(String message, Generator<?> generator) {
        super(message);
        this.generator = generator;
    }
}
