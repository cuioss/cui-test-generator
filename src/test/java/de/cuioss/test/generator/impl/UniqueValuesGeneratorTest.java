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
package de.cuioss.test.generator.impl;

import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.generator.junit.EnableGeneratorController;
import de.cuioss.test.generator.junit.GeneratorSeed;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@EnableGeneratorController
@GeneratorSeed(42L)
@DisplayName("UniqueValuesGenerator should")
class UniqueValuesGeneratorTest {

    @Test
    @DisplayName("generate unique values")
    void shouldGenerateUniqueValues() {
        var source = new IntegerGenerator(0, 1000);
        var generator = new UniqueValuesGenerator<>(source);
        var seen = new HashSet<Integer>();
        for (int i = 0; i < 50; i++) {
            assertTrue(seen.add(generator.next()), "Duplicate value generated");
        }
    }

    @Test
    @DisplayName("throw when uniqueness exhausted")
    void shouldThrowOnExhaustion() {
        TypedGenerator<String> source = new FixedValuesGenerator<>(String.class, List.of("A", "B"));
        var generator = new UniqueValuesGenerator<>(source);
        generator.next(); // "A" or "B"
        generator.next(); // the other one
        assertThrows(IllegalStateException.class, generator::next);
    }

    @Test
    @DisplayName("delegate getType to source")
    void shouldDelegateType() {
        var source = new IntegerGenerator();
        var generator = new UniqueValuesGenerator<>(source);
        assertEquals(Integer.class, generator.getType());
    }

    @Test
    @DisplayName("validate constructor arguments")
    void shouldValidateConstructorArguments() {
        var source = new IntegerGenerator();
        assertThrows(NullPointerException.class, () -> new UniqueValuesGenerator<>(null));
        assertThrows(NullPointerException.class, () -> new UniqueValuesGenerator<>(null, 5));
        assertThrows(IllegalArgumentException.class, () -> new UniqueValuesGenerator<>(source, 0));
        assertThrows(IllegalArgumentException.class, () -> new UniqueValuesGenerator<>(source, -1));
    }

    @Test
    @DisplayName("remain thread-safe under concurrent access")
    void shouldRemainThreadSafeUnderConcurrency() throws Exception {
        var source = new IntegerGenerator(0, 1_000_000);
        var generator = new UniqueValuesGenerator<>(source, 1_000);

        int threads = 8;
        int perThread = 200;
        Set<Integer> collected = ConcurrentHashMap.newKeySet();
        var duplicates = new AtomicInteger();
        var errors = new AtomicInteger();
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        var latch = new CountDownLatch(threads);

        for (int t = 0; t < threads; t++) {
            pool.submit(() -> {
                try {
                    for (int i = 0; i < perThread; i++) {
                        Integer value = generator.next();
                        if (!collected.add(value)) {
                            duplicates.incrementAndGet();
                        }
                    }
                } catch (RuntimeException e) {
                    errors.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        assertTrue(latch.await(30, TimeUnit.SECONDS), "Concurrent generation timed out");
        pool.shutdownNow();

        assertEquals(0, errors.get(), "Concurrent generation must not raise exceptions");
        assertEquals(0, duplicates.get(), "Concurrent generation must not yield duplicates");
        assertEquals(threads * perThread, collected.size());
    }
}
