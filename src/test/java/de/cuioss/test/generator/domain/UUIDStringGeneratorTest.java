package de.cuioss.test.generator.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.distribution.RandomConfiguration;

class UUIDStringGeneratorTest {

    @Test
    void producesReproducibleData() {
        long seed = Generators.longs().next();

        RandomConfiguration.setSeed(seed);
        var result1 = new UUIDStringGenerator().next();

        RandomConfiguration.setSeed(seed);
        var result2 = new UUIDStringGenerator().next();

        assertEquals(result1, result2, "UUIDStringGenerator produces non reproducible data");
    }

}
