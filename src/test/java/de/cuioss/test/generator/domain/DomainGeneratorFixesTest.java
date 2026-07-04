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
package de.cuioss.test.generator.domain;

import de.cuioss.test.generator.internal.RandomContext;
import de.cuioss.test.generator.junit.EnableGeneratorController;
import de.cuioss.test.generator.junit.GeneratorSeed;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@EnableGeneratorController
@GeneratorSeed(42L)
@DisplayName("Domain generator fixes")
class DomainGeneratorFixesTest {

    @Test
    @DisplayName("expose a static getType() that consumes no RNG draw (D-5)")
    void getTypeShouldReturnStaticTypeWithoutConsumingDraws() {
        assertEquals(String.class, new BlindTextGenerator().getType());
        assertEquals(String.class, new EmailGenerator().getType());
        assertEquals(String.class, new FullNameGenerator().getType());
        assertEquals(String.class, new MailSubjectGenerator().getType());
        assertEquals(Person.class, new PersonGenerator().getType());
        assertEquals(UUID.class, new UUIDGenerator().getType());

        RandomContext.setSeed(7L);
        var first = new EmailGenerator().next();
        var second = new EmailGenerator().next();

        RandomContext.setSeed(7L);
        var generator = new EmailGenerator();
        var firstAgain = generator.next();
        generator.getType();
        var secondAgain = generator.next();

        assertEquals(first, firstAgain);
        assertEquals(second, secondAgain, "getType() must not consume RNG draws");
    }

    @Test
    @DisplayName("build locale-independent email addresses (D-2)")
    void createEmailShouldBeLocaleIndependent() {
        var previous = Locale.getDefault();
        try {
            Locale.setDefault(Locale.forLanguageTag("tr-TR"));
            var email = EmailGenerator.createEmail("Isabella", "Ingram");
            assertTrue(email.startsWith("isabella.ingram@"), email);
            assertFalse(email.contains("ı"), "Must not contain a locale-dependent dotless i: " + email);
        } finally {
            Locale.setDefault(previous);
        }
    }

    @Test
    @DisplayName("keep the mail-subject prefix count within its documented 0-3 bound (D-1)")
    void mailSubjectShouldStayWithinDocumentedBounds() {
        var prefixes = Set.of("Re:", "Fw:", "Answ:", "Yep:");
        var generator = new MailSubjectGenerator();
        for (int i = 0; i < 2_000; i++) {
            var subject = generator.next();
            // Prefixes are single tokens drawn first; content words (some containing spaces) follow.
            var tokens = subject.isBlank() ? new String[0] : subject.split(" ");
            int leadingPrefixes = 0;
            for (var token : tokens) {
                if (!prefixes.contains(token)) {
                    break;
                }
                leadingPrefixes++;
            }
            assertTrue(leadingPrefixes <= 3, "At most 3 prefixes expected, got: " + subject);
        }
    }

    @Test
    @DisplayName("generate reproducible mail subjects for a fixed seed (D-1)")
    void mailSubjectShouldBeReproducible() {
        RandomContext.setSeed(5L);
        var first = new MailSubjectGenerator().next();
        RandomContext.setSeed(5L);
        var second = new MailSubjectGenerator().next();
        assertEquals(first, second);
    }

    @Test
    @DisplayName("generate zip codes covering the leading-zero range (D-7)")
    void zipCodesShouldCoverLeadingZeroRange() {
        var generator = new ZipCodeGenerator();
        boolean sawLeadingZeroCode = false;
        for (int i = 0; i < 5_000; i++) {
            int zip = generator.next();
            assertTrue(zip >= 1067 && zip <= 99999, "Out of range: " + zip);
            if (zip < 10000) {
                sawLeadingZeroCode = true;
            }
        }
        assertTrue(sawLeadingZeroCode, "Must be able to produce codes below 10000 (leading-zero codes)");
    }

    @Test
    @DisplayName("never use the attribute prefix 'dc' as a DN value (D-8)")
    void distinguishedNamesShouldNotUseDcAsValue() {
        var generator = new DistinguishedNamesGenerator();
        for (int i = 0; i < 500; i++) {
            var dn = generator.next();
            for (var component : dn.split(",")) {
                var value = component.substring(component.indexOf('=') + 1);
                assertNotEquals("dc", value, "A DN value must not be the attribute prefix 'dc': " + dn);
            }
        }
    }

    @Test
    @DisplayName("use German names for German-language locale variants (D-9)")
    void germanLocaleVariantsShouldUseGermanNames() {
        RandomContext.setSeed(3L);
        var fromGerman = new FullNameGenerator(Locale.GERMAN).next();
        RandomContext.setSeed(3L);
        var fromGermany = new FullNameGenerator(Locale.GERMANY).next();
        assertEquals(fromGerman, fromGermany, "Locale.GERMANY must use the same German name set as Locale.GERMAN");
    }

    @Test
    @DisplayName("produce RFC 4122 version-4 UUIDs (D-10)")
    void uuidsShouldBeRfc4122Version4() {
        var generator = new UUIDGenerator();
        for (int i = 0; i < 1_000; i++) {
            var uuid = generator.next();
            assertEquals(4, uuid.version(), "Expected a version-4 UUID");
            assertEquals(2, uuid.variant(), "Expected the IETF variant");
        }
    }
}
