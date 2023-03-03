package de.cuioss.test.generator.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.TypedGenerator;

class NonBlankStringGeneratorTest {

    private final TypedGenerator<String> underTest = new NonBlankStringGenerator();

    @Test
    void getTypeReturnsString() {
        assertEquals(String.class, underTest.getType());
    }

    @Test
    void nextReturnsNonBlankString() {
        var result = underTest.next();
        assertFalse(result.trim().isEmpty());
    }

}
