package io.cui.test.generator.domain;

import static io.cui.test.generator.Generators.integers;

import io.cui.test.generator.TypedGenerator;

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
