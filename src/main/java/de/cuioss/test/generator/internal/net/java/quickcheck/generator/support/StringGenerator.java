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

import de.cuioss.test.generator.internal.net.java.quickcheck.ExtendibleGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.FrequencyGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.Generator;

import java.util.Objects;

public class StringGenerator implements ExtendibleGenerator<Character, String> {

    public static final int MIN_LENGTH = 0;

    public static final int MAX_LENGTH = 30;

    private final FrequencyGenerator<Character> characters;
    private final Generator<Integer> length;

    public StringGenerator() {
        this(new CharacterGenerator());
    }

    public StringGenerator(char first, char last) {
        this(new CharacterGenerator(first, last));
    }

    public StringGenerator(Generator<Character> characters) {
        this(new IntegerGenerator(MIN_LENGTH, MAX_LENGTH), characters);
    }

    public StringGenerator(Generator<Integer> length, Generator<Character> characters) {
        Objects.requireNonNull(length, "length");

        this.length = length;
        this.characters = new DefaultFrequencyGenerator<>(characters);
    }

    @Override
    public String next() {
        int size = length.next();
        StringBuilder builder = new StringBuilder();
        for (int count = 0; count < size; count++) {
            builder.append(characters.next());
        }
        return builder.toString();
    }

    @Override
    public ExtendibleGenerator<Character, String> add(Generator<Character> characterGenerator) {
        characters.add(characterGenerator);
        return this;
    }
}
