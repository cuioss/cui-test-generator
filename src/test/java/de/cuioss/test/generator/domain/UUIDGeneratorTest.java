package de.cuioss.test.generator.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.distribution.RandomConfiguration;

class UUIDGeneratorTest {

    @Test
    void producesReproducibleData() {
        long seed = Generators.longs().next();

        RandomConfiguration.setSeed(seed);
        var result1 = new UUIDGenerator().next();

        RandomConfiguration.setSeed(seed);
        var result2 = new UUIDGenerator().next();

        assertEquals(result1, result2, "UUIDGenerator produces non reproducible data");
    }

}
