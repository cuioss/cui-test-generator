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
package de.cuioss.test.generator.internal.net.java.quickcheck.generator.distribution;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class RandomConfigurationTest {

    @AfterEach
    void systemProperty() {
        System.clearProperty(RandomConfiguration.SEED_SYSTEM_PROPERTY);
        assert System.getProperty(RandomConfiguration.SEED_SYSTEM_PROPERTY) == null;
    }

    @Test
    void initSeed() {
        var seed = RandomConfiguration.initSeed();
        assertSeed(seed);
    }

    @Test
    void setSeed() {
        var seed = 42L;
        RandomConfiguration.setSeed(seed);
        assertSeed(seed);
    }

    @Test
    void readSystemProperty() {
        var seed = 1L;
        System.setProperty(RandomConfiguration.SEED_SYSTEM_PROPERTY, Long.toString(seed));
        RandomConfiguration.readSystemProperty();
        assertSeed(seed);
    }

    @Test
    void readSystemPropertyInvalid() {
        System.setProperty(RandomConfiguration.SEED_SYSTEM_PROPERTY, "invalid");
        assertThrows(NumberFormatException.class, RandomConfiguration::readSystemProperty);
    }

    private void assertSeed(long seed) {
        var benchmark = new Random(seed);
        for (var i = 0; i < 100; i++) {
            assertEquals(benchmark.nextDouble(), RandomConfiguration.random.nextDouble(), 0.0);
        }
    }
}
