package io.cui.test.generator.junit;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.opentest4j.AssertionFailedError;
import org.opentest4j.TestAbortedException;

import io.cui.test.generator.internal.net.java.quickcheck.generator.distribution.RandomConfiguration;

/**
 * If enabled, either by using {@link ExtendWith} or {@link EnableGeneratorController} this
 * {@link Extension} controls the seed initialization, by checking for {@link GeneratorSeed} and
 * intercepts Test-failures by printing information providing the seed to reproduce.
 *
 * @author Oliver Wolff
 *
 */
public class GeneratorControllerExtension implements BeforeEachCallback, TestExecutionExceptionHandler {

    private static final String MSG_TEMPLATE = "%s\nGeneratorController seed was %sL. "
            + "\nUse a fixed seed by applying @GeneratorSeed(%sL) for the method/class, "
            + "\nor by using the system property '-D" + RandomConfiguration.SEED_SYSTEM_PROPERTY
            + "=%s'\n";

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {

        if (throwable instanceof TestAbortedException) {
            throw throwable;
        } else if (throwable instanceof AssertionFailedError) {
            AssertionFailedError failure = new AssertionFailedError(createErrorMessage(throwable, RandomConfiguration.getLastSeed()));
            failure.setStackTrace(throwable.getStackTrace());
            throw failure;
        } else {
            AssertionFailedError failure =
                new AssertionFailedError(throwable.getClass() + ": " + createErrorMessage(throwable, RandomConfiguration.getLastSeed()));
            failure.setStackTrace(throwable.getStackTrace());
            throw failure;
        }

    }

    @Override
    @SuppressWarnings("java:S3655") // owolff: false positive: isPresent is checked
    public void beforeEach(ExtensionContext context) throws Exception {
        boolean seedSetByAnnotation = false;
        long initialSeed = -1;
        if (context.getElement().isPresent()) {
            AnnotatedElement annotatedElement = context.getElement().get();
            GeneratorSeed seedAnnotation = annotatedElement.getAnnotation(GeneratorSeed.class);
            if (null == seedAnnotation && (annotatedElement instanceof Method)) {
                seedAnnotation = ((Method) annotatedElement).getDeclaringClass()
                        .getAnnotation(GeneratorSeed.class);
            }
            if (null != seedAnnotation) {
                initialSeed = seedAnnotation.value();
                seedSetByAnnotation = true;
            }
        }
        if (seedSetByAnnotation) {
            RandomConfiguration.setSeed(initialSeed);
        } else {
            RandomConfiguration.initSeed();
        }
    }


    private String createErrorMessage(Throwable e, Long seed) {
        String causeMsg = e.getMessage() == null ? "" : e.getMessage();
        return String.format(MSG_TEMPLATE, causeMsg, seed, seed, seed);
    }

}
