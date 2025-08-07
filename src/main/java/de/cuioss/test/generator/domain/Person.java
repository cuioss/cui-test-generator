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

import lombok.Builder;
import lombok.Value;

/**
 * Immutable value object representing a person with basic identifying information.
 * Uses Lombok's {@code @Value} for immutability and {@code @Builder} for convenient object creation.
 * 
 * <p><em>Example usage:</em></p>
 * <pre>
 * Person person = Person.builder()
 *     .title("Dr.")
 *     .firstname("John")
 *     .lastname("Doe")
 *     .email("john.doe@example.com")
 *     .organisation("ACME Corp")
 *     .build();
 * </pre>
 * 
 * @author Oliver Wolff
 */
@Value
@Builder
public class Person {

    String title;
    String firstname;
    String lastname;
    String email;
    String organisation;
}
