package de.cuioss.test.generator.junit;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This annotation is to be used in the context of {@link EnableGeneratorController}.
 * It explicitly sets the seed for the generators.
 *
 * @author Oliver Wolff
 *
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface GeneratorSeed {

    /**
     * @return the seed for the generators
     */
    long value();
}
