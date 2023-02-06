package io.cui.test.generator.domain;

import lombok.Builder;
import lombok.Value;

/**
 * Simple person object containing a title, firstname, lastname, organization and a corresponding
 * email-address
 *
 * @author Oliver Wolff
 *
 */
@Value
@Builder
public class Person {

    private String title;
    private String firstname;
    private String lastname;
    private String email;
    private String organisation;
}
