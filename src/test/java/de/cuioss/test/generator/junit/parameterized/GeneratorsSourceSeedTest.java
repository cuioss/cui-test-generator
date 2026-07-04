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
package de.cuioss.test.generator.junit.parameterized;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.internal.RandomContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("@GeneratorsSource seed attribute")
class GeneratorsSourceSeedTest {

    @Test
    @DisplayName("applies the configured seed so generation is reproducible")
    void shouldHonorSeedAttribute() throws Exception {
        GeneratorsSource annotation = createMock(GeneratorsSource.class);
        expect(annotation.generator()).andReturn(GeneratorType.INTEGERS).anyTimes();
        expect(annotation.minSize()).andReturn(0).anyTimes();
        expect(annotation.maxSize()).andReturn(10).anyTimes();
        expect(annotation.low()).andReturn("1").anyTimes();
        expect(annotation.high()).andReturn("100").anyTimes();
        expect(annotation.count()).andReturn(3).anyTimes();
        expect(annotation.seed()).andReturn(42L).anyTimes();
        replay(annotation);

        var provider = new GeneratorsSourceArgumentsProvider();
        provider.accept(annotation);
        assertEquals(42L, provider.getSeed());

        List<Object> produced = provider.provideArguments(null, null)
                .map(args -> args.get()[0]).toList();

        // Baseline generated directly from the same seed, in the same order.
        RandomContext.setSeed(42L);
        var generator = Generators.integers(1, 100);
        List<Object> baseline = List.of(generator.next(), generator.next(), generator.next());

        assertEquals(baseline, produced, "The seed attribute must drive reproducible generation");
        verify(annotation);
    }
}
