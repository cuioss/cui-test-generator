/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
