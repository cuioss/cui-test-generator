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
package de.cuioss.test.generator.junit;

import de.cuioss.test.generator.internal.net.java.quickcheck.generator.distribution.RandomConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@EnableGeneratorController
@DisplayName("GeneratorController without seed info should")
class GeneratorControllerExtensionWOSeedInfoTest {

    @RepeatedTest(5)
    @DisplayName("generate non-null seeds across test invocations")
    void shouldGenerateSeeds() {
        // Act
        var seed = RandomConfiguration.getLastSeed();

        // Assert
        assertNotEquals(0, seed, "Generated seed should not be zero");
    }
}
