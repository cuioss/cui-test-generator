package io.cui.test.generator.domain;

import static io.cui.test.generator.Generators.integers;

import io.cui.test.generator.TypedGenerator;

/**
 * Generator for some zipCodes, number between 10000 - 99999
 *
 * @author Oliver Wolff
 *
 */
public class ZipCodeGenerator implements TypedGenerator<Integer> {

    private final TypedGenerator<Integer> zibCodes = integers(10000, 99999);

    @Override
    public Integer next() {
        return zibCodes.next();
    }

    @Override
    public Class<Integer> getType() {
        return Integer.class;
    }
}
