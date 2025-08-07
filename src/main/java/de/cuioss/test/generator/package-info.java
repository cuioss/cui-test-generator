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
/**
 * <h2>Generators</h2> Using the generators help creating variations of
 * test-data. On the one hand it produces a large number of different values. On
 * the other hand it makes error cases reproducible see
 * {@link de.cuioss.test.generator.junit.EnableGeneratorController} and
 * {@link de.cuioss.test.generator.junit.GeneratorSeed} for details.
 * <h2>Provided Features</h2> The generators defined within this packages are
 * the base for the cui-value-object test framework. In essence, it is a variant
 * of <a href="https://bitbucket.org/blob79/quickcheck/">QuickCheck</a>. In the
 * current implementation it actually uses code derived from QuickCheck, thanks
 * guys, but mostly the generator part. These code is isolated
 * {@link de.cuioss.test.generator.internal.net.java.quickcheck} and will be
 * replaced in the future. Therefore: Do not use any of that code at all!
 * Central elements are:
 * <ul>
 * <li>{@link de.cuioss.test.generator.TypedGenerator}: Is the core Type.
 * Instances of it are used for generating arbitrary instances of any
 * value-object. Compared to
 * {@link de.cuioss.test.generator.internal.net.java.quickcheck.Generator} it
 * provides an additional method providing runtime information on the type being
 * generated: {@link de.cuioss.test.generator.TypedGenerator#getType()}.</li>
 * <li>{@link de.cuioss.test.generator.Generators}: Factory methods for
 * accessing {@link de.cuioss.test.generator.TypedGenerator} for many java-lang
 * types.</li>
 * <li>{@link de.cuioss.test.generator.domain}: Provides some domain specific
 * {@link de.cuioss.test.generator.TypedGenerator}</li>
 * </ul>
 */
package de.cuioss.test.generator;
