package de.icw.cui.test.generator.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Locale;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class DomainGeneratorTest {

    @Test
    void shouldProvideBlindtexts() {
        assertNotNull(new BlindTextGenerator().next());
    }

    @Test
    void shouldProvideEmails() {
        assertNotNull(new EmailGenerator().next());
    }

    @Test
    void shouldProvideFullnames() {
        assertNotNull(new FullNameGenerator(Locale.ENGLISH).next());
    }

    @Test
    void shouldProvideMailSubjects() {
        assertNotNull(new MailSubjectGenerator().next());
    }

    @Test
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
    void shouldProvideOrganisationNames() {
        assertNotNull(OrganizationNameGenerator.READABLE.generator().next());
        assertNotNull(OrganizationNameGenerator.UNIT_TESTS.generator().next());
    }

    @Test
    void shouldProvidePersons() {
        assertNotNull(new PersonGenerator().next());
    }

    @Test
    void shouldProvideTitle() {
        assertNotNull(TitleGenerator.READABLE.generator().next());
        assertNotNull(TitleGenerator.UNIT_TESTS.generator().next());
    }

    @Test
    void shouldProvideDns() {
        assertEquals(String.class, new DistinguishedNamesGenerator().getType());
        assertNotNull(new DistinguishedNamesGenerator().next());
    }

    @Test
    void shouldProvideCities() {
        assertEquals(String.class, new CityGenerator().getType());
        assertNotNull(new CityGenerator().next());
    }

    @Test
    void shouldProvideStreetNames() {
        assertEquals(String.class, new StreetNameGenerator().getType());
        assertNotNull(new StreetNameGenerator().next());
    }

    @Test
    void shouldProvideStreets() {
        assertEquals(String.class, new StreetGenerator().getType());
        assertNotNull(new StreetGenerator().next());
    }

    @Test
    void shouldProvideZipcodes() {
        assertEquals(Integer.class, new ZipCodeGenerator().getType());
        assertNotNull(new ZipCodeGenerator().next());
    }

    @Test
    void shouldProvidePhoneNumber() {
        assertEquals(String.class, new PhoneNumberGenerator().getType());
        assertNotNull(new PhoneNumberGenerator().next());
    }

    @Test
    void shouldProvideUUIDs() {
        assertEquals(UUID.class, new UUIDGenerator().getType());
        assertNotNull(new UUIDGenerator().next());
    }

    @Test
    void shouldProvideUUIDStrings() {
        assertEquals(String.class, new UUIDStringGenerator().getType());
        assertNotNull(new UUIDStringGenerator().next());
    }
}
