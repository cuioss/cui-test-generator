package io.cui.test.generator.junit;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import io.cui.test.generator.internal.net.java.quickcheck.generator.distribution.RandomConfiguration;

@EnableGeneratorController
class GeneratorControllerExtensionWOSeedInfoTest {

    @Test
    void test() {
        assertNotEquals(0, RandomConfiguration.getLastSeed());
    }

}
