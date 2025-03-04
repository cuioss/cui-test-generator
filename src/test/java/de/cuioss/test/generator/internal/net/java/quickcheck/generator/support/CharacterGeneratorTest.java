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

import de.cuioss.test.generator.internal.net.java.quickcheck.characteristic.Classification;
import de.cuioss.test.generator.internal.net.java.quickcheck.collection.Pair;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static de.cuioss.test.generator.internal.net.java.quickcheck.characteristic.ClassificationTestHelper.assertFrequencyGreater;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.characters;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.CharacterGenerator.BASIC_LATIN;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.CharacterGenerator.LATIN_1_SUPPLEMENT;
import static org.junit.jupiter.api.Assertions.*;

class CharacterGeneratorTest {

    @Test
    void anyCharacters() {
        for (char c : toIterable(PrimitiveGenerators.characters())) {
            assertTrue(in(c, BASIC_LATIN) || in(c, LATIN_1_SUPPLEMENT));
        }
    }

    @Test
    void charactersForLowerAndUpperBounds() {
        var lower = 'a';
        var upper = 'f';

        List<Character> expected = new ArrayList<>();
        for (var c = lower; c <= upper; c++) {
            expected.add(c);
        }

        for (Character actual : toIterable(characters(lower, upper))) {
            assertTrue(expected.contains(actual));
        }
    }

    @Test
    void generatorFixedCharactersNotEmptyArgument() {
        Character[] empty = {};
        assertThrows(IllegalArgumentException.class, () -> PrimitiveGenerators.characters(empty));
    }

    @Test
    void generatorFixedCharacters() {
        final var character = 'a';
        for (char any : toIterable(characters(character))) {
            assertEquals(any, character);
        }
    }

    @Test
    void generatorMultipleFixedCharacters() {
        Character[] chars = {'a', 'x', 'q'};
        var input = new StringBuilder();
        for (char c : chars) {
            input.append(c);
        }

        var classification = new Classification();
        var generator = characters(input.toString());
        for (Character any : toIterable(generator)) {
            classification.classifyCall(any);
        }

        assertEquals(chars.length, classification.getCategories().size());
        assertFrequencyGreater(classification, 20.0, chars);
    }

    private boolean in(char c, Pair<Character, Character> chars) {
        return chars.getFirst() <= c && c <= chars.getSecond();
    }

}
