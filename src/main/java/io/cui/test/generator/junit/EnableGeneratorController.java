package io.cui.test.generator.junit;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ExtendWith;

/**
 * <h2>Purpose and Usage</h2>
 * This annotation is meant to be set on a junit 5 test-case. It controls the generator subsystem
 * and, in case of test-failures, provides information, that can be used for repeating the failed
 * tests with a fixed seed for the generators, see {@link GeneratorSeed} for details. This fixed
 * seed results in the generators reproducing the exact same test-data.
 * Sample output: 
 * <pre>
GeneratorController seed was 4711L. 
Use a fixed seed by applying @GeneratorSeed(4711L) for the method/class, 
or by using the system property '-Dio.cui.test.generator.seed=4711'
 * </pre>
 * <h2>Implementation</h2>
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
