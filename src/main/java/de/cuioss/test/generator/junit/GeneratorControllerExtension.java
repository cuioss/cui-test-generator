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
package de.cuioss.test.generator.junit;

import java.lang.reflect.Method;
import org.opentest4j.AssertionFailedError;
import org.opentest4j.TestAbortedException;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

import de.cuioss.test.generator.internal.net.java.quickcheck.generator.distribution.RandomConfiguration;

/**
 * If enabled, either by using {@link ExtendWith} or
 * {@link EnableGeneratorController} this {@link Extension} controls the seed
 * initialization, by checking for {@link GeneratorSeed} and intercepts
 * Test-failures by printing information providing the seed to reproduce.
 *
 * @author Oliver Wolff
 *
 */
public class GeneratorControllerExtension implements BeforeEachCallback, TestExecutionExceptionHandler {

    private static final String MSG_TEMPLATE = """
            %s
            GeneratorController seed was %sL.\s
            Use a fixed seed by applying @GeneratorSeed(%sL) for the method/class,\s
            or by using the system property '-D\
            """ + RandomConfiguration.SEED_SYSTEM_PROPERTY + "=%s'\n";

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {

        if (throwable instanceof TestAbortedException) {
            throw throwable;
        }
        if (throwable instanceof AssertionFailedError) {
            var failure = new AssertionFailedError(createErrorMessage(throwable, RandomConfiguration.getLastSeed()));
            failure.setStackTrace(throwable.getStackTrace());
            throw failure;
        }
        var failure = new AssertionFailedError(
                throwable.getClass() + ": " + createErrorMessage(throwable, RandomConfiguration.getLastSeed()));
        failure.setStackTrace(throwable.getStackTrace());
        throw failure;

    }

    @Override
    @SuppressWarnings("java:S3655") // owolff: false positive: isPresent is checked
    public void beforeEach(ExtensionContext context) throws Exception {
        var seedSetByAnnotation = false;
        long initialSeed = -1;
        if (context.getElement().isPresent()) {
            var annotatedElement = context.getElement().get();
            var seedAnnotation = annotatedElement.getAnnotation(GeneratorSeed.class);
            if (null == seedAnnotation && annotatedElement instanceof Method method) {
                seedAnnotation = method.getDeclaringClass().getAnnotation(GeneratorSeed.class);
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
        var causeMsg = e.getMessage() == null ? "" : e.getMessage();
        return MSG_TEMPLATE.formatted(causeMsg, seed, seed, seed);
    }

}
