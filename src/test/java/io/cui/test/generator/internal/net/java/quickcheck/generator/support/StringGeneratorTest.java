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

import static io.cui.test.generator.internal.net.java.quickcheck.QuickCheck.forAll;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.strings;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.iterable.Iterables.toIterable;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.support.CharacterGenerator.BASIC_LATIN;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.support.CharacterGenerator.LATIN_1_SUPPLEMENT;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.cui.test.generator.internal.net.java.quickcheck.Generator;
import io.cui.test.generator.internal.net.java.quickcheck.MockFactory;
import io.cui.test.generator.internal.net.java.quickcheck.characteristic.AbstractCharacteristic;
import io.cui.test.generator.internal.net.java.quickcheck.characteristic.Classification;
import io.cui.test.generator.internal.net.java.quickcheck.collection.Pair;
import io.cui.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators;

class StringGeneratorTest {

    @Test
    void testGenerateLetters() {
        for (String any : toIterable(PrimitiveGenerators.strings())) {
            assertTrue(any.length() <= StringGenerator.MAX_LENGTH);
            for (int i = 0; i < any.length(); i++) {
                char actualLetter = any.charAt(i);
                assertTrue(between(actualLetter, CharacterGenerator.BASIC_LATIN));
            }
        }
    }

    @Test
    void testGeneratePrintableLetters() {
        for (String any : toIterable(io.cui.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.printableStrings())) {
            assertTrue(any.length() <= StringGenerator.MAX_LENGTH);
            for (int i = 0; i < any.length(); i++) {
                char actualLetter = any.charAt(i);
                assertTrue(between(actualLetter, BASIC_LATIN) || between(actualLetter, LATIN_1_SUPPLEMENT));
            }
        }
    }

    @Test
    void testGenerateOnlyA() {
        final String allowedCharacters = "a";
        Generator<String> generator = strings('a', 'a');
        testOnlyAllowedCharacters(allowedCharacters, generator);
    }

    @Test
    void testGenerateOnlyAllowedStrings() {
        final String allowedCharacters = "abc";
        Generator<String> generator = strings(allowedCharacters);
        testOnlyAllowedCharacters(allowedCharacters, generator);
    }

    @Test
    void testGenerateOnlyAllowedStringsWithSize() {
        final String allowedCharacters = "abc";
        int max = 10;
        int min = 3;
        Generator<String> generator = strings(allowedCharacters, min, max);
        testOnlyAllowedCharacters(allowedCharacters, generator, min, max);
    }

    private void testOnlyAllowedCharacters(final String allowedCharacters,
            Generator<String> generator) {
        testOnlyAllowedCharacters(allowedCharacters, generator, 0,
                StringGenerator.MAX_LENGTH);
    }

    private void testOnlyAllowedCharacters(final String allowedCharacters,
            Generator<String> generator, final int min, final int max) {
        forAll(generator, new AbstractCharacteristic<>() {

            @Override
            protected void doSpecify(String any) {
                assertTrue(any.length() <= max, Integer.toString(any.length()));
                assertTrue(any.length() >= min, Integer.toString(any.length()));
                for (int i = 0; i < any.length(); i++) {

                    assertTrue(allowedCharacters.contains(Character
                            .toString(any.charAt(i))));
                }
            }
        });
    }

    private static final String CAPITAL_LETTER = "A-Z";
    private static final String SMALL_LETTER = "a-z";

    @Test
    void testLettersStrings() {
        Generator<String> largeStrings = PrimitiveGenerators.letterStrings(1000, 1000);
        for (String any : toIterable(largeStrings)) {
            Classification classification = new Classification();
            for (int i = 0; i < any.length(); i++) {
                char c = any.charAt(i);
                classification.doClassify(between(c, 'a', 'z'), SMALL_LETTER);
                classification.doClassify(between(c, 'A', 'Z'), CAPITAL_LETTER);
                // have to call callDone() here because there are any.length
                // calls
                // to classify with condition true
                classification.call();
            }
            assertAbout50Percent(classification, SMALL_LETTER, CAPITAL_LETTER);
        }
    }

    private void assertAbout50Percent(Classification classification, String... categories) {
        for (String category : categories) {
            double frequency = classification.getFrequency(category);
            assertTrue(frequency > 40 && frequency < 60);
        }
    }

    private boolean between(char actualLetter, Pair<Character, Character> characterRange) {
        return between(actualLetter, characterRange.getFirst(), characterRange.getSecond());
    }

    private boolean between(char c, char lo, char hi) {
        return c <= (hi) && c >= (lo);
    }

    @Test
    void testLetterStringsSize() {
        final int lo = 1;
        final int hi = 100;
        testStringLengthCharacteristic(lo, hi, PrimitiveGenerators
                .letterStrings(lo, hi));
    }

    @Test
    void testStringsGenerateWithMaxSize() {
        int max = 10;
        int min = 0;
        Generator<String> generator = strings(max);
        testStringLengthCharacteristic(min, max, generator);
    }

    private void testStringLengthCharacteristic(final int lo, final int hi,
            Generator<String> extendibleGenerator) {
        final String ltMiddle = "<1/2";
        final String gtMiddle = ">1/2";
        AbstractCharacteristic<String> characteristic = new AbstractCharacteristic<>() {

            @Override
            protected void doSpecify(String any) {

                assertTrue(any.length() >= lo && any.length() <= hi);
                int middle = (hi - lo) / 2 + lo;
                classify(middle > any.length(), ltMiddle);
                classify(middle < any.length(), gtMiddle);
            }
        };
        forAll(1000, extendibleGenerator, characteristic);
        assertAbout50Percent(characteristic.getClassification(), ltMiddle, gtMiddle);
    }

    @Test
    void testStringsSize() {
        final int lo = 1;
        final int hi = 100;
        testStringLengthCharacteristic(lo, hi, PrimitiveGenerators.strings(lo,
                hi));
    }

    @Test
    void testStringGeneratorWithLengthAndCharacterGenerators() {
        Generator<Character> characterGenerator = MockFactory.createCharacterGenerator();
        Generator<Integer> lengthGenerator = MockFactory.createIntegerGenerator();
        expect(lengthGenerator.next()).andReturn(2);
        expect(characterGenerator.next()).andReturn('a').times(2);

        replay(characterGenerator, lengthGenerator);
        Generator<String> strings = PrimitiveGenerators.strings(lengthGenerator, characterGenerator);
        assertEquals("aa", strings.next());

        verify(characterGenerator, lengthGenerator);
    }

    @Test
    void testGenerateNonEmptyStrings() {
        for (String any : toIterable(PrimitiveGenerators.nonEmptyStrings()))
            assertTrue(any.length() > 0 && any.length() <= StringGenerator.MAX_LENGTH);
    }
}
