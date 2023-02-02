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
package io.cui.test.generator.internal.net.java.quickcheck.generator.support;

import static io.cui.test.generator.internal.net.java.quickcheck.characteristic.ClassificationTestHelper.assertFrequencyGreater;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.characters;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.support.CharacterGenerator.BASIC_LATIN;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.support.CharacterGenerator.LATIN_1_SUPPLEMENT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.cui.test.generator.internal.net.java.quickcheck.Generator;
import io.cui.test.generator.internal.net.java.quickcheck.characteristic.Classification;
import io.cui.test.generator.internal.net.java.quickcheck.collection.Pair;
import io.cui.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators;

class CharacterGeneratorTest {

    @Test
    void anyCharacters() {
        for (char c : toIterable(PrimitiveGenerators.characters())) {
            assertTrue(in(c, BASIC_LATIN) || in(c, LATIN_1_SUPPLEMENT));
        }
    }

    @Test
    void charactersForLowerAndUpperBounds() {
        char lower = 'a';
        char upper = 'f';

        List<Character> expected = new ArrayList<>();
        for (char c = lower; c <= upper; c++)
            expected.add(c);

        for (Character actual : toIterable(characters(lower, upper)))
            assertTrue(expected.contains(actual));
    }

    @Test
    void generatorFixedCharactersNotEmptyArgument() {
        Character[] empty = {};
        assertThrows(IllegalArgumentException.class, () -> PrimitiveGenerators.characters(empty));
    }

    @Test
    void generatorFixedCharacters() {
        final char character = 'a';
        for (char any : toIterable(characters(character)))
            assertEquals(any, character);
    }

    @Test
    void generatorMultipleFixedCharacters() {
        Character[] chars = { 'a', 'x', 'q' };
        StringBuilder input = new StringBuilder();
        for (char c : chars) {
            input.append(c);
        }

        Classification classification = new Classification();
        Generator<Character> generator = characters(input.toString());
        for (Character any : toIterable(generator))
            classification.classifyCall(any);

        assertEquals(chars.length, classification.getCategories().size());
        assertFrequencyGreater(classification, 20.0, chars);
    }

    private boolean in(char c, Pair<Character, Character> chars) {
        return chars.getFirst() <= c && c <= chars.getSecond();
    }

}
