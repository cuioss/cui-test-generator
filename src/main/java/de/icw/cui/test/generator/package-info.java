/**
 * The generators defined within this packages are the base for the cui-value-object test framework.
 * In essence is a variant of <a href="https://bitbucket.org/blob79/quickcheck/">QuickCheck</a>.
 * Central elements are:
 * <ul>
 * <li>{@link de.icw.cui.test.generator.TypedGenerator}: Is the core Type. Instances of it are used
 * for generating arbitrary instances of any value-object. Compared to
 * {@link net.java.quickcheck.Generator} it provides an additional method providing runtime
 * information on the type being generated:
 * {@link de.icw.cui.test.generator.TypedGenerator#getType()}.</li>
 * <li>{@link de.icw.cui.test.generator.Generators}: Factory method for accessing
 * {@link de.icw.cui.test.generator.TypedGenerator} for many java-lang types. In addition it
 * provides the method
 * {@link de.icw.cui.test.generator.Generators#wrap(Class, net.java.quickcheck.Generator)} to reuse
 * existing {@link net.java.quickcheck.Generator} implementations</li>
 * <li>{@link de.icw.cui.test.generator.domain}: Provides some domain specific
 * {@link de.icw.cui.test.generator.TypedGenerator}
 * <li>
 * </ul>
 */
package de.icw.cui.test.generator;
