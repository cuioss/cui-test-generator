package de.cuioss.test.generator.domain;

import static de.cuioss.test.generator.Generators.integers;

import de.cuioss.test.generator.TypedGenerator;

/**
 * Simply generates random phone numbers.
 *
 * @author Oliver Wolff
 *
 */
public class PhoneNumberGenerator implements TypedGenerator<String> {

    private final TypedGenerator<Integer> prepend = integers(100, 999);
    private final TypedGenerator<Integer> number = integers(1000, 9999);

    @Override
    public String next() {
        return "0" + prepend.next() + "/" + number.next();
    }

    @Override
    public Class<String> getType() {
        return String.class;
    }
}
