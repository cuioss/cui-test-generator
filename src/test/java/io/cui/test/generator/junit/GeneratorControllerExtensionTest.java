package io.cui.test.generator.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.opentest4j.TestAbortedException;

import io.cui.test.generator.internal.net.java.quickcheck.generator.distribution.RandomConfiguration;

@EnableGeneratorController
@GeneratorSeed(5L)
class GeneratorControllerExtensionTest {

    private static final long DEFAULT_SEED = 4711L;

    @Test
    @GeneratorSeed(11L)
    void shouldReadFromMethod() {
        assertEquals(11L, RandomConfiguration.getLastSeed());
    }

    @Test
    void shouldReadFromType() {
        assertEquals(5L, RandomConfiguration.getLastSeed());
    }

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

    @Test
    void shouldRethrowAssertionFailedError() {
        var exception = new AssertionFailedError();
        var extension = new GeneratorControllerExtension();
        RandomConfiguration.setSeed(DEFAULT_SEED);
        try {
            extension.handleTestExecutionException(null, exception);
            fail("Should have thrown exception");
        } catch (Throwable e) {
            assertNotSame(exception, e);
            assertTrue(e.getMessage().contains(String.valueOf(DEFAULT_SEED)));
        }
    }

    @Test
    void shouldRethrowIllegalArgumentException() {
        var exception = new IllegalArgumentException();
        var extension = new GeneratorControllerExtension();
        RandomConfiguration.setSeed(DEFAULT_SEED);
        try {
            extension.handleTestExecutionException(null, exception);
            fail("Should have thrown exception");
        } catch (Throwable e) {
            assertNotSame(exception, e);
            assertInstanceOf(AssertionFailedError.class, e);
            assertTrue(e.getMessage().contains(String.valueOf(DEFAULT_SEED)));
        }
    }
}
