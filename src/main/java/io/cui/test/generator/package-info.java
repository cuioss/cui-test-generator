/**
 * <h2>Generators</h2>
 * Using the generators help creating variations of test-data. On the one hand it produces a large
 * number of different values. On the other hand it makes error cases reproducible see
 * {@link io.cui.test.generator.junit.EnableGeneratorController} and
 * {@link io.cui.test.generator.junit.GeneratorSeed} for details.
 * <h2>Provided Features</h2>
 * The generators defined within this packages are the base for the cui-value-object test framework.
 * In essence, it is a variant of <a href="https://bitbucket.org/blob79/quickcheck/">QuickCheck</a>.
 * In the current implementation it actually uses code derived from QuickCheck, thanks guys, but
 * mostly the
 * generator part. These code is isolated {@link io.cui.test.generator.internal.net.java.quickcheck}
 * and
 * will be replaced in the future. Therefore: Do not use any of that code at all!
 * Central elements are:
 * <ul>
 * <li>{@link io.cui.test.generator.TypedGenerator}: Is the core Type. Instances of it are used
 * for generating arbitrary instances of any value-object. Compared to
 * {@link io.cui.test.generator.internal.net.java.quickcheck.Generator} it provides an additional
 * method providing runtime information on the type being generated:
 * {@link io.cui.test.generator.TypedGenerator#getType()}.</li>
 * <li>{@link io.cui.test.generator.Generators}: Factory methods for accessing
 * {@link io.cui.test.generator.TypedGenerator} for many java-lang types.</li>
 * <li>{@link io.cui.test.generator.domain}: Provides some domain specific
 * {@link io.cui.test.generator.TypedGenerator}
 * </li>
 * </ul>
 */
package io.cui.test.generator;
