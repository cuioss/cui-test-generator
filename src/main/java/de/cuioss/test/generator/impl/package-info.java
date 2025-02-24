/**
 * Core implementation classes for the CUI test generator framework.
 * This package provides concrete implementations of various test data generators.
 * 
 * <h2>Generator Categories</h2>
 * 
 * <h3>Collection and Array Generators</h3>
 * <ul>
 *   <li>{@link de.cuioss.test.generator.impl.CollectionGenerator} - Creates Lists, Sets, and other collections</li>
 *   <li>{@link de.cuioss.test.generator.impl.PrimitiveArrayGenerators} - Generates arrays of primitive types</li>
 * </ul>
 * 
 * <h3>Date and Time Generators</h3>
 * <ul>
 *   <li>{@link de.cuioss.test.generator.impl.LocalDateGenerator} - Generates dates within a reasonable epoch range</li>
 *   <li>{@link de.cuioss.test.generator.impl.LocalTimeGenerator} - Creates times with second precision</li>
 *   <li>{@link de.cuioss.test.generator.impl.LocalDateTimeGenerator} - Combines date and time generation</li>
 *   <li>{@link de.cuioss.test.generator.impl.ZonedDateTimeGenerator} - Adds time zone support</li>
 *   <li>{@link de.cuioss.test.generator.impl.ZoneOffsetGenerator} - Generates time zone offsets</li>
 * </ul>
 * 
 * <h3>Numeric Generators</h3>
 * <ul>
 *   <li>{@link de.cuioss.test.generator.impl.NumberGenerator} - Generic number generation</li>
 *   <li>{@link de.cuioss.test.generator.impl.FloatObjectGenerator} - Float values with configurable ranges</li>
 *   <li>{@link de.cuioss.test.generator.impl.ShortObjectGenerator} - Short values across the full range</li>
 * </ul>
 * 
 * <h3>String and URL Generators</h3>
 * <ul>
 *   <li>{@link de.cuioss.test.generator.impl.NonBlankStringGenerator} - Non-empty string generation</li>
 *   <li>{@link de.cuioss.test.generator.impl.URLGenerator} - Valid URL generation from predefined sets</li>
 * </ul>
 * 
 * <h3>Utility Generators</h3>
 * <ul>
 *   <li>{@link de.cuioss.test.generator.impl.DecoratorGenerator} - Wraps and enhances other generators</li>
 * </ul>
 * 
 * <h2>Common Usage Patterns</h2>
 * <pre>
 * // Basic generator usage
 * var stringGen = new NonBlankStringGenerator();
 * String value = stringGen.next();
 * 
 * // Collection generation
 * var intGen = Generators.integers(1, 100);
 * var collectionGen = new CollectionGenerator<>(intGen);
 * List<Integer> list = collectionGen.list(5);
 * 
 * // Date/Time generation with zones
 * var dateTimeGen = new ZonedDateTimeGenerator();
 * ZonedDateTime future = dateTimeGen.future();
 * 
 * // Numeric generation with ranges
 * var floatGen = new FloatObjectGenerator(0.0f, 100.0f);
 * Float number = floatGen.next();
 * </pre>
 * 
 * <h2>Implementation Notes</h2>
 * <ul>
 *   <li>All generators are thread-safe unless explicitly noted otherwise</li>
 *   <li>Generators follow the fail-fast principle for invalid inputs</li>
 *   <li>Each generator provides specific configuration options where appropriate</li>
 *   <li>Documentation includes usage examples from actual tests</li>
 *   <li>Generators can be combined using the CollectionGenerator or DecoratorGenerator</li>
 * </ul>
 * 
 * @author Oliver Wolff
 * @author Eugen Fischer
 * @see de.cuioss.test.generator.TypedGenerator
 * @see de.cuioss.test.generator.Generators
 */
package de.cuioss.test.generator.impl;
