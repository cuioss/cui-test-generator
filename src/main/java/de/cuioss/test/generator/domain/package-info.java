/**
 * Provides domain-specific test data generators implementing {@link de.cuioss.test.generator.TypedGenerator}.
 * These generators create realistic test data for various business domains, particularly focused on
 * personal information, location data, and organizational structures.
 * 
 * <h2>Generator Categories</h2>
 * 
 * <h3>Personal Information</h3>
 * <ul>
 *   <li>{@link de.cuioss.test.generator.domain.PersonGenerator} - Complete person objects with configurable attributes</li>
 *   <li>{@link de.cuioss.test.generator.domain.FullNameGenerator} - Locale-aware name generation (German/English)</li>
 *   <li>{@link de.cuioss.test.generator.domain.EmailGenerator} - Valid email addresses with realistic patterns</li>
 *   <li>{@link de.cuioss.test.generator.domain.PhoneNumberGenerator} - German-format phone numbers</li>
 * </ul>
 * 
 * <h3>Location Data</h3>
 * <ul>
 *   <li>{@link de.cuioss.test.generator.domain.CityGenerator} - Major German city names</li>
 *   <li>{@link de.cuioss.test.generator.domain.StreetGenerator} - Complete street addresses with numbers</li>
 *   <li>{@link de.cuioss.test.generator.domain.StreetNameGenerator} - Common German street names</li>
 *   <li>{@link de.cuioss.test.generator.domain.ZipCodeGenerator} - Valid German postal codes</li>
 * </ul>
 * 
 * <h3>Organization and Professional Data</h3>
 * <ul>
 *   <li>{@link de.cuioss.test.generator.domain.OrganizationNameGenerator} - Famous fictional company names</li>
 *   <li>{@link de.cuioss.test.generator.domain.TitleGenerator} - Academic and professional titles</li>
 *   <li>{@link de.cuioss.test.generator.domain.DistinguishedNamesGenerator} - LDAP DN strings</li>
 * </ul>
 * 
 * <h3>Content Generation</h3>
 * <ul>
 *   <li>{@link de.cuioss.test.generator.domain.BlindTextGenerator} - Multi-language placeholder texts</li>
 *   <li>{@link de.cuioss.test.generator.domain.MailSubjectGenerator} - Healthcare-focused email subjects</li>
 * </ul>
 * 
 * <h3>Technical Identifiers</h3>
 * <ul>
 *   <li>{@link de.cuioss.test.generator.domain.UUIDGenerator} - UUID objects</li>
 *   <li>{@link de.cuioss.test.generator.domain.UUIDStringGenerator} - Formatted UUID strings</li>
 * </ul>
 * 
 * <h2>Usage Patterns</h2>
 * <p>Most generators provide:</p>
 * <ul>
 *   <li>Realistic data for visual/integration testing</li>
 *   <li>Technical variants for unit testing</li>
 *   <li>Thread-safe operation</li>
 *   <li>Deterministic output when seeded</li>
 * </ul>
 * 
 * <p><em>Example usage:</em></p>
 * <pre>
 * // Create a complete person with realistic data
 * var person = new PersonGenerator().next();
 * 
 * // Generate a German address
 * var address = new StreetGenerator().next() + "\n" +
 *               new ZipCodeGenerator().next() + " " +
 *               new CityGenerator().next();
 * </pre>
 * 
 * @see de.cuioss.test.generator.TypedGenerator
 */
package de.cuioss.test.generator.domain;
