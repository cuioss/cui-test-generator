package io.cui.test.generator.junit;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Shorthand for enabling {@link GeneratorControllerExtension} for a certain test-class. This type
 * is equivalent to {@link ExtendWith} {@link GeneratorControllerExtension}
 * 
 * @author Oliver Wolff
 *
 */
@Retention(RUNTIME)
@Target(TYPE)
@ExtendWith(GeneratorControllerExtension.class)
public @interface EnableGeneratorController {

}
