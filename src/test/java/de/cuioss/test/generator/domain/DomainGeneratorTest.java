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

import de.cuioss.test.generator.junit.EnableGeneratorController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@EnableGeneratorController
@DisplayName("Domain Generator provides")
class DomainGeneratorTest {

    @Nested
    @DisplayName("text content generators that")
    class TextContent {

        @Test
        @DisplayName("should provide blind texts")
        void shouldProvideBlindTexts() {
            assertNotNull(new BlindTextGenerator().next());
        }

        @Test
        @DisplayName("should provide mail subjects")
        void shouldProvideMailSubjects() {
            assertNotNull(new MailSubjectGenerator().next());
        }

        @Test
        @DisplayName("should provide titles")
        void shouldProvideTitle() {
            assertNotNull(TitleGenerator.READABLE.generator().next());
            assertNotNull(TitleGenerator.UNIT_TESTS.generator().next());
        }
    }

    @Nested
    @DisplayName("person related generators that")
    class PersonRelated {

        @Test
        @DisplayName("should provide full names")
        void shouldProvideFullNames() {
            var fullName = new FullNameGenerator(Locale.ENGLISH).next();
            assertNotNull(fullName);
            assertTrue(fullName.contains(" "),
                    "A full name must contain a space separating first and family name: " + fullName);
        }

        @Test
        @DisplayName("should provide various names")
        void shouldProvideNames() {
            assertNotNull(NameGenerators.UNIT_TESTS.generator().next());
            assertNotNull(NameGenerators.FAMILY_NAMES_ENGLISH.generator().next());
            assertNotNull(NameGenerators.FAMILY_NAMES_GERMAN.generator().next());
            assertNotNull(NameGenerators.FIRSTNAMES_ANY_ENGLISH.generator().next());
            assertNotNull(NameGenerators.FIRSTNAMES_ANY_GERMAN.generator().next());
            assertNotNull(NameGenerators.FIRSTNAMES_FEMALE_ENGLISH.generator().next());
            assertNotNull(NameGenerators.FIRSTNAMES_FEMALE_GERMAN.generator().next());
            assertNotNull(NameGenerators.FIRSTNAMES_MALE_ENGLISH.generator().next());
            assertNotNull(NameGenerators.FIRSTNAMES_MALE_GERMAN.generator().next());
        }

        @Test
        @DisplayName("should provide persons")
        void shouldProvidePersons() {
            assertNotNull(new PersonGenerator().next());
        }

        @Test
        @DisplayName("should provide phone numbers")
        void shouldProvidePhoneNumber() {
            assertEquals(String.class, new PhoneNumberGenerator().getType());
            var phoneNumber = new PhoneNumberGenerator().next();
            assertNotNull(phoneNumber);
            // Produced as "0" + area(100-999) + "/" + number(1000-9999), e.g. "0123/4567".
            assertTrue(phoneNumber.matches("^0\\d{3}/\\d{4}$"),
                    "Unexpected phone number format: " + phoneNumber);
        }
    }

    @Nested
    @DisplayName("organization related generators that")
    class OrganizationRelated {

        @Test
        @DisplayName("should provide organization names")
        void shouldProvideOrganisationNames() {
            assertNotNull(OrganizationNameGenerator.READABLE.generator().next());
            assertNotNull(OrganizationNameGenerator.UNIT_TESTS.generator().next());
        }

        @Test
        @DisplayName("should provide distinguished names")
        void shouldProvideDns() {
            assertEquals(String.class, new DistinguishedNamesGenerator().getType());
            var dn = new DistinguishedNamesGenerator().next();
            assertNotNull(dn);
            assertTrue(dn.contains("="),
                    "A distinguished name must contain at least one '=': " + dn);
            for (var component : dn.split(",")) {
                assertTrue(component.matches("[^=,]+=[^=,]+"),
                        "Each component must be a 'prefix=value' pair: " + component + " in " + dn);
            }
        }
    }

    @Nested
    @DisplayName("address related generators that")
    class AddressRelated {

        @Test
        @DisplayName("should provide cities")
        void shouldProvideCities() {
            assertEquals(String.class, new CityGenerator().getType());
            assertNotNull(new CityGenerator().next());
        }

        @Test
        @DisplayName("should provide street names")
        void shouldProvideStreetNames() {
            assertEquals(String.class, new StreetNameGenerator().getType());
            assertNotNull(new StreetNameGenerator().next());
        }

        @Test
        @DisplayName("should provide complete streets")
        void shouldProvideStreets() {
            assertEquals(String.class, new StreetGenerator().getType());
            assertNotNull(new StreetGenerator().next());
        }

        @Test
        @DisplayName("should provide zip codes")
        void shouldProvideZipcodes() {
            assertEquals(Integer.class, new ZipCodeGenerator().getType());
            var generator = new ZipCodeGenerator();
            for (int i = 0; i < 200; i++) {
                int zip = generator.next();
                assertTrue(zip >= 1067 && zip <= 99999, "Zip code out of range: " + zip);
            }
        }
    }

    @Nested
    @DisplayName("identifier generators that")
    class IdentifierRelated {

        @Test
        @DisplayName("should provide UUIDs")
        void shouldProvideUUIDs() {
            assertEquals(UUID.class, new UUIDGenerator().getType());
            assertNotNull(new UUIDGenerator().next());
        }

        @Test
        @DisplayName("should provide UUID strings")
        void shouldProvideUUIDStrings() {
            assertEquals(String.class, new UUIDStringGenerator().getType());
            assertNotNull(new UUIDStringGenerator().next());
        }
    }
}
