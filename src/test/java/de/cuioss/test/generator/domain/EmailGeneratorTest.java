/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.test.generator.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("EmailGenerator")
class EmailGeneratorTest {

    private final EmailGenerator underTest = new EmailGenerator();

    @Test
    @DisplayName("should provide type information")
    void shouldProvideTypeInformation() {
        assertEquals(String.class, underTest.getType());
    }

    @Test
    @DisplayName("should generate valid email addresses")
    void shouldGenerateValidEmails() {
        var result = underTest.next();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.matches("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$"), "Email should match standard email format");
    }

    @Nested
    @DisplayName("createEmail factory method")
    class CreateEmailMethod {

        @ParameterizedTest(name = "should create valid email from {0} {1}")
        @CsvSource({
            "john,doe",
            "JOHN,DOE",
            "Jane,Smith",
            "JANE,SMITH"
        })
        void shouldCreateValidEmail(String firstname, String lastname) {
            var result = EmailGenerator.createEmail(firstname, lastname);
            var expectedStart = firstname.toLowerCase() + "." + lastname.toLowerCase() + "@";
            assertTrue(result.startsWith(expectedStart), "Email should start with lowercase name");
            assertTrue(result.matches("^[a-z]+\\.[a-z]+@[a-z]+\\.[a-z]+$"), "Email should have valid format");
            assertTrue(result.matches(".*@(email|mail|cuioss|message|example|hospital)\\.(de|org|com|net)$"),
                "Email should use one of the predefined domains and TLDs");
        }

        @ParameterizedTest(name = "should handle invalid firstname: {0}")
        @ValueSource(strings = {"", " ", "  "})
        void shouldHandleInvalidFirstname(String firstname) {
            assertEquals("invalid.email@example.com", EmailGenerator.createEmail(firstname, "doe"));
        }

        @ParameterizedTest(name = "should handle invalid lastname: {0}")
        @ValueSource(strings = {"", " ", "  "})
        void shouldHandleInvalidLastname(String lastname) {
            assertEquals("invalid.email@example.com", EmailGenerator.createEmail("john", lastname));
        }

        @Test
        @DisplayName("should handle null inputs")
        void shouldHandleNullInputs() {
            assertEquals("invalid.email@example.com", EmailGenerator.createEmail(null, "doe"));
            assertEquals("invalid.email@example.com", EmailGenerator.createEmail("john", null));
            assertEquals("invalid.email@example.com", EmailGenerator.createEmail(null, null));
        }
    }
}
