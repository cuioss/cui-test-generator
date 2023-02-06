/**
 * The generators defined within this packages are the base for the cui-value-object test framework.
 * In essence is a variant of <a href="https://bitbucket.org/blob79/quickcheck/">QuickCheck</a>.
 * Central elements are:
 * <ul>
 * <li>{@link io.cui.test.generator.TypedGenerator}: Is the core Type. Instances of it are used
 * for generating arbitrary instances of any value-object. Compared to
 * {@link io.cui.test.generator.internal.net.java.quickcheck.Generator} it provides an additional
 * method providing runtime information on the type being generated:
 * {@link io.cui.test.generator.TypedGenerator#getType()}.</li>
 * <li>{@link io.cui.test.generator.Generators}: Factory methods for accessing
 * {@link io.cui.test.generator.TypedGenerator} for many java-lang types.
 * <li>{@link io.cui.test.generator.domain}: Provides some domain specific
 * {@link io.cui.test.generator.TypedGenerator}
 * <li>
 * </ul>
 */
package io.cui.test.generator;
