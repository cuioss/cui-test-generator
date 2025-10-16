/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.test.generator.internal.net.java.quickcheck;

import de.cuioss.test.generator.internal.net.java.quickcheck.characteristic.AbstractCharacteristic;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import static de.cuioss.test.generator.internal.net.java.quickcheck.AssertThrows.assertThrows;
import static de.cuioss.test.generator.internal.net.java.quickcheck.QuickCheck.*;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.integers;
import static java.lang.System.clearProperty;
import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("ProhibitedExceptionDeclared")
class QuickCheckTest {

    private Characteristic<Object> characteristic;
    private Generator<Object> generator;

    @BeforeEach
    void setUp() {
        characteristic = MockFactory.createObjectCharacteristic();
        generator = MockFactory.createObjectGenerator();
    }

    @AfterEach
    void tearDown() {
        clearProperty(SYSTEM_PROPERTY_RUNS);
    }

    @Test
    void forAllCallCharacteristic() throws Throwable {
        expectRuns(QuickCheck.getDefaultNumberOfRuns());
        replayMocks();
        forAll(PrimitiveGenerators.fixedValues(new Object()), characteristic);
        verifyMocks();
    }

    @Test
    void forAllFailsException() throws Throwable {
        var exception = new IllegalStateException("test failure");
        var last = expectExceptionThrownAfterFirstGenerator(exception);
        replayMocks();
        try {
            forAll(generator, characteristic);
        } catch (CharacteristicException e) {
            assertSame(exception, e.getCause());
            assertSame(last, e.getInstance());
            assertNotNull(e.getMessage());
        }
        verifyMocks();
    }

    private Object expectExceptionThrownAfterFirstGenerator(Exception thrown) throws Throwable {
        var last = new Object();
        expect(generator.next()).andReturn(last);
        characteristic.setUp();
        characteristic.specify(last);
        expectLastCall().andThrow(thrown);
        expect(characteristic.name()).andReturn(PrimitiveGenerators.strings().next()).atLeastOnce();
        characteristic.tearDown();

        return last;
    }

    @Test
    void forNumberOfRuns() throws Throwable {
        var runs = 2;
        expectRuns(runs);
        replayMocks();
        QuickCheck.forAll(runs, PrimitiveGenerators.fixedValues(new Object()), characteristic);
        verifyMocks();
    }

    private void expectRuns(int runs) throws Throwable {
        for (var i = 0; i < runs; i++) {
            characteristic.setUp();
            characteristic.specify(anyObject());
            characteristic.tearDown();
        }
    }

    @Test
    void choose() {
        final var lo = 10;
        final var hi = 100;
        forAll(integers(lo, hi), new AbstractCharacteristic<>() {

            @Override
            public void doSpecify(Integer anyInt) {
                assertTrue(lo <= anyInt, () -> Integer.toString(anyInt));
                assertTrue(anyInt <= hi, () -> Integer.toString(anyInt));
            }
        });
    }

    @Test
    void forAllVerbose() throws Throwable {
        var genReturned = "returned";
        expect(generator.next()).andReturn(genReturned);
        setUpCallTearDown(genReturned);
        replayMocks();

        var writer = new StringWriter();

        var runner = new RunnerImpl<>(characteristic, 1, generator, new PrintWriter(writer));
        runner.forAll();
        var actual = writer.toString();
        var expected = "1:[returned]";
        assertEquals(expected, actual.trim());
        verifyMocks();
    }

    private void setUpCallTearDown(Object first) throws Throwable {
        characteristic.setUp();
        characteristic.specify(first);
        characteristic.tearDown();
    }

    @Test
    void forAllWithGuard() throws Throwable {
        var first = new Object();
        var second = new Object();
        expect(generator.next()).andReturn(first);
        expect(generator.next()).andReturn(second);
        characteristic.setUp();
        characteristic.specify(first);
        expectLastCall().andThrow(new GuardException());
        characteristic.specify(second);
        characteristic.tearDown();
        replayMocks();
        QuickCheck.forAllVerbose(1, generator, characteristic);
        verifyMocks();
    }

    @Test
    void forWithGuardAbortsAfterMaxTry() throws Throwable {
        var runs = 3;
        var maxGeneratorTries = RunnerImpl.getMaxGeneratorTries(runs);
        expect(generator.next()).andReturn(new Object()).times(maxGeneratorTries);
        characteristic.setUp();
        for (var i = 0; i < maxGeneratorTries; i++) {
            characteristic.specify(anyObject());
            expectLastCall().andThrow(new GuardException());
        }
        characteristic.tearDown();

        replayMocks();
        assertThrows(GeneratorException.class, () -> forAll(runs, generator, characteristic));
        verifyMocks();
    }

    @Test
    void testGuard() {
        assertDoesNotThrow(() -> guard(true));
    }

    @Test
    void guardThrowsGuardException() {
        assertThrows(GuardException.class, () -> guard(false));
    }

    @Test
    void getDefaultNumberOfRuns() {
        assertEquals(MAX_NUMBER_OF_RUNS, QuickCheck.getDefaultNumberOfRuns());
    }

    @Test
    void getDefaultNumberOfRunsOverwriteProperty() {
        var runs = integers(1, Integer.MAX_VALUE).next();
        System.setProperty(QuickCheck.SYSTEM_PROPERTY_RUNS, runs.toString());
        assertEquals(runs.intValue(), QuickCheck.getDefaultNumberOfRuns());
    }

    private void verifyMocks() {
        verify(characteristic, generator);
    }

    private void replayMocks() {
        replay(characteristic, generator);

    }
}
