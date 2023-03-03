package de.cuioss.test.generator.junit;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.internal.net.java.quickcheck.generator.distribution.RandomConfiguration;

@EnableGeneratorController
class GeneratorControllerExtensionWOSeedInfoTest {

    @Test
    void test() {
        assertNotEquals(0, RandomConfiguration.getLastSeed());
    }

}
