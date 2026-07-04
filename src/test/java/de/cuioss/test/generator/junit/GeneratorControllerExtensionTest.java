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
package de.cuioss.test.generator.junit;

import de.cuioss.test.generator.internal.RandomContext;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.opentest4j.TestAbortedException;

import static org.junit.jupiter.api.Assertions.*;

@EnableGeneratorController
@GeneratorSeed(5L)
class GeneratorControllerExtensionTest {

    private static final long DEFAULT_SEED = 4711L;

    @Test
    @GeneratorSeed(11L)
    void shouldReadFromMethod() {
        assertEquals(11L, RandomContext.getLastSeed());
    }

    @Test
    void shouldReadFromType() {
        assertEquals(5L, RandomContext.getLastSeed());
    }

    // cui-rewrite:disable InvalidExceptionUsageRecipe
    @Test
    void shouldRethrowTestAbortedException() {
        var exception = new TestAbortedException();
        var extension = new GeneratorControllerExtension();

        try {
            extension.handleTestExecutionException(null, exception);
            fail("Should have thrown exception");
        } catch (Throwable e) {
            assertSame(exception, e);
        }
    }

    // cui-rewrite:disable InvalidExceptionUsageRecipe
    @Test
    void shouldRethrowAssertionFailedError() {
        var exception = new AssertionFailedError();
        var extension = new GeneratorControllerExtension();
        RandomContext.setSeed(DEFAULT_SEED);
        try {
            extension.handleTestExecutionException(null, exception);
            fail("Should have thrown exception");
        } catch (Throwable e) {
            assertNotSame(exception, e);
            assertTrue(e.getMessage().contains(String.valueOf(DEFAULT_SEED)));
        }
    }

    // cui-rewrite:disable InvalidExceptionUsageRecipe
    @Test
    void shouldRethrowIllegalArgumentException() {
        var exception = new IllegalArgumentException();
        var extension = new GeneratorControllerExtension();
        RandomContext.setSeed(DEFAULT_SEED);
        try {
            extension.handleTestExecutionException(null, exception);
            fail("Should have thrown exception");
        } catch (Throwable e) {
            assertNotSame(exception, e);
            assertInstanceOf(AssertionFailedError.class, e);
            assertTrue(e.getMessage().contains(String.valueOf(DEFAULT_SEED)));
        }
    }

    // cui-rewrite:disable InvalidExceptionUsageRecipe
    @Test
    void shouldPreserveCauseExpectedAndActual() {
        var original = new AssertionFailedError("boom", "the-expected", "the-actual");
        var extension = new GeneratorControllerExtension();
        RandomContext.setSeed(DEFAULT_SEED);
        try {
            extension.handleTestExecutionException(null, original);
            fail("Should have thrown exception");
        } catch (Throwable e) {
            var afe = assertInstanceOf(AssertionFailedError.class, e);
            assertSame(original, afe.getCause(), "Original assertion must be preserved as cause");
            assertTrue(afe.isExpectedDefined());
            assertTrue(afe.isActualDefined());
            assertEquals("the-expected", afe.getExpected().getValue());
            assertEquals("the-actual", afe.getActual().getValue());
            assertTrue(afe.getMessage().contains(String.valueOf(DEFAULT_SEED)));
        }
    }

    // cui-rewrite:disable InvalidExceptionUsageRecipe
    @Test
    void shouldPreserveCauseForNonAssertionErrorWithoutClassPrefix() {
        var original = new IllegalStateException("bad state");
        var extension = new GeneratorControllerExtension();
        RandomContext.setSeed(DEFAULT_SEED);
        try {
            extension.handleTestExecutionException(null, original);
            fail("Should have thrown exception");
        } catch (Throwable e) {
            assertInstanceOf(AssertionFailedError.class, e);
            assertSame(original, e.getCause(), "Original throwable must be preserved as cause");
            assertTrue(e.getMessage().startsWith("java.lang.IllegalStateException:"),
                    "Message must use the qualified class name without a spurious 'class ' prefix");
            assertFalse(e.getMessage().startsWith("class "));
        }
    }

    /**
     * Verifies that a {@code @Nested} test without its own seed inherits the
     * enclosing class-level {@code @GeneratorSeed} (S-7).
     */
    @Nested
    class NestedWithoutOwnSeed {

        @Test
        void shouldInheritEnclosingClassSeed() {
            assertEquals(5L, RandomContext.getLastSeed());
        }
    }
}
