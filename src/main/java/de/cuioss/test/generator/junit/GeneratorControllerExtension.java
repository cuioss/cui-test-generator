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
package de.cuioss.test.generator.junit;

import de.cuioss.test.generator.internal.RandomContext;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.junit.platform.commons.support.AnnotationSupport;
import org.opentest4j.AssertionFailedError;
import org.opentest4j.TestAbortedException;
import org.opentest4j.ValueWrapper;

import java.util.Optional;

/**
 * JUnit 5 extension that manages test data generation by controlling generator seeds
 * and providing detailed failure information for test reproduction.
 *
 * <h2>Features</h2>
 * <ul>
 *   <li>Initializes generator seeds before each test</li>
 *   <li>Supports seed configuration via {@link GeneratorSeed} annotation</li>
 *   <li>Provides detailed failure information for test reproduction</li>
 *   <li>Handles both method and class-level seed configuration</li>
 * </ul>
 *
 * <h2>Usage</h2>
 * Can be enabled in two ways:
 * <pre>
 * // Option 1: Direct extension usage
 * &#64;ExtendWith(GeneratorControllerExtension.class)
 * class MyTest {
 *     &#64;Test
 *     void shouldGenerateData() { ... }
 * }
 *
 * // Option 2: Via meta-annotation (preferred)
 * &#64;EnableGeneratorController
 * class MyTest {
 *     &#64;Test
 *     void shouldGenerateData() { ... }
 * }
 * </pre>
 *
 * <h2>Seed Configuration</h2>
 * Seeds can be configured in order of precedence:
 * <ol>
 *   <li>Method-level {@code @GeneratorSeed}</li>
 *   <li>Class-level {@code @GeneratorSeed}</li>
 *   <li>System property {@code de.cuioss.test.generator.seed}</li>
 *   <li>Random seed (if no configuration present)</li>
 * </ol>
 *
 * <h2>Failure Handling</h2>
 * On test failure, provides a detailed message with:
 * <ul>
 *   <li>The original test failure message</li>
 *   <li>The seed used for test data generation</li>
 *   <li>Instructions for test reproduction</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @see EnableGeneratorController
 * @see GeneratorSeed
 * @see RandomContext#SEED_SYSTEM_PROPERTY
 */
public class GeneratorControllerExtension implements BeforeEachCallback, TestExecutionExceptionHandler {

    private static final String MSG_TEMPLATE = """
            %s
            GeneratorController seed was %sL.\s
            Use a fixed seed by applying @GeneratorSeed(%sL) for the method/class,\s
            or by using the system property '-D\
            """ + RandomContext.SEED_SYSTEM_PROPERTY + "=%s'\n";

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {

        if (throwable instanceof TestAbortedException) {
            throw throwable;
        }
        var seed = RandomContext.getLastSeed();
        if (throwable instanceof AssertionFailedError afe) {
            var message = createErrorMessage(afe, seed);
            AssertionFailedError failure;
            if (afe.isExpectedDefined() || afe.isActualDefined()) {
                failure = new AssertionFailedError(message, unwrap(afe.getExpected()), unwrap(afe.getActual()), afe);
            } else {
                failure = new AssertionFailedError(message, afe);
            }
            copyTrace(afe, failure);
            throw failure;
        }
        var failure = new AssertionFailedError(
                throwable.getClass().getName() + ": " + createErrorMessage(throwable, seed), throwable);
        copyTrace(throwable, failure);
        throw failure;
    }

    private static Object unwrap(ValueWrapper wrapper) {
        return wrapper == null ? null : wrapper.getValue();
    }

    private static void copyTrace(Throwable source, Throwable target) {
        target.setStackTrace(source.getStackTrace());
        for (var suppressed : source.getSuppressed()) {
            target.addSuppressed(suppressed);
        }
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        var seedAnnotation = findSeedAnnotation(context);
        if (seedAnnotation.isPresent()) {
            RandomContext.setSeed(seedAnnotation.get().value());
        } else {
            // Honor the replay system property as the per-test seed so that re-running
            // with -D<property>=<seed> reproduces a single failing test; otherwise draw
            // a fresh random seed.
            var configuredSeed = RandomContext.getConfiguredSeed();
            if (configuredSeed.isPresent()) {
                RandomContext.setSeed(configuredSeed.get());
            } else {
                RandomContext.initSeed();
            }
        }
    }

    /**
     * Resolves the applicable {@link GeneratorSeed} by walking outward from the current
     * context: the test method first, then each enclosing class (covering {@code @Nested}
     * tests), honoring annotations inherited on the concrete test class.
     *
     * @param context the extension context
     * @return the closest {@link GeneratorSeed}, or empty if none is present
     */
    private static Optional<GeneratorSeed> findSeedAnnotation(ExtensionContext context) {
        for (Optional<ExtensionContext> current = Optional.ofNullable(context);
             current.isPresent();
             current = current.get().getParent()) {
            var element = current.get().getElement();
            if (element.isPresent()) {
                var found = AnnotationSupport.findAnnotation(element.get(), GeneratorSeed.class);
                if (found.isPresent()) {
                    return found;
                }
            }
        }
        return Optional.empty();
    }

    private String createErrorMessage(Throwable e, Long seed) {
        var causeMsg = e.getMessage() == null ? "" : e.getMessage();
        return MSG_TEMPLATE.formatted(causeMsg, seed, seed, seed);
    }

}
