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
package de.cuioss.test.generator.junit.parameterized;

import de.cuioss.test.generator.TypedGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.JUnitException;

import java.util.Optional;
import java.util.stream.Stream;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class GeneratorMethodResolverTest {

    @Test
    void shouldGetGeneratorFromExternalClass() {
        // given
        String methodReference = SharedTestFactory.class.getName() + "#createGenerator";

        // when
        TypedGenerator<?> generator = GeneratorMethodResolver.getGeneratorFromExternalClass(methodReference);

        // then
        assertNotNull(generator);
        assertEquals(String.class, generator.getType());
    }

    static Stream<Arguments> invalidExternalMethodReferences() {
        return Stream.of(
                arguments("invalid#format#method", "Could not find class [invalid]"),
                arguments("com.nonexistent.Class#method", "Could not find class [com.nonexistent.Class]"),
                arguments(SharedTestFactory.class.getName() + "#nonExistentMethod", "Could not find method [nonExistentMethod] in class [" + SharedTestFactory.class.getName() + "]"),
                arguments(SharedTestFactory.class.getName() + "#createDoubleGenerator", "Method [createDoubleGenerator] in external class [" + SharedTestFactory.class.getName() + "] must be static")
        );
    }

    @ParameterizedTest
    @MethodSource("invalidExternalMethodReferences")
    void shouldThrowExceptionForInvalidExternalMethodReference(String methodReference, String expectedErrorMessage) {
        // when/then
        JUnitException exception = assertThrows(JUnitException.class, () ->
                GeneratorMethodResolver.getGeneratorFromExternalClass(methodReference));
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    @SuppressWarnings("unchecked") // Safe: SharedTestFactory.class is statically known; EasyMock limitation requires raw Class
    void shouldGetGeneratorFromTestClass() {
        // given
        String methodName = "createGenerator";
        ExtensionContext context = createMock(ExtensionContext.class);
        expect(context.getRequiredTestClass()).andReturn((Class) SharedTestFactory.class).anyTimes();
        expect(context.getTestInstance()).andReturn(Optional.empty()).anyTimes();
        replay(context);

        // when
        TypedGenerator<?> generator = GeneratorMethodResolver.getGenerator(methodName, context);

        // then
        assertNotNull(generator);
        assertEquals(String.class, generator.getType());
        verify(context);
    }

    @Test
    @SuppressWarnings("unchecked") // Safe: SharedTestFactory.class is statically known; EasyMock limitation requires raw Class
    void shouldGetGeneratorFromTestInstance() {
        // given
        String methodName = "createDoubleGenerator";
        ExtensionContext context = createMock(ExtensionContext.class);
        SharedTestFactory testInstance = new SharedTestFactory();

        expect(context.getRequiredTestClass()).andReturn((Class) SharedTestFactory.class).anyTimes();
        expect(context.getTestInstance()).andReturn(Optional.of(testInstance)).anyTimes();
        replay(context);

        // when
        TypedGenerator<?> generator = GeneratorMethodResolver.getGenerator(methodName, context);

        // then
        assertNotNull(generator);
        assertEquals(Double.class, generator.getType());
        verify(context);
    }

    static Stream<Arguments> invalidMethodScenarios() {
        return Stream.of(
                arguments("createDoubleGenerator", false, "Cannot invoke instance method [createDoubleGenerator] without a test instance"),
                arguments(null, true, null), // Should throw NullPointerException
                arguments("", false, "Method name must not be blank")
        );
    }

    @ParameterizedTest
    @MethodSource("invalidMethodScenarios")
    @SuppressWarnings("unchecked") // Safe: SharedTestFactory.class is statically known; EasyMock limitation requires raw Class
    void shouldThrowExceptionForInvalidMethodScenarios(String methodName, boolean isNullPointerExpected, String expectedErrorMessage) {
        // given
        ExtensionContext context = createMock(ExtensionContext.class);

        if (methodName != null && !methodName.isEmpty()) {
            expect(context.getRequiredTestClass()).andReturn((Class) SharedTestFactory.class).anyTimes();
            expect(context.getTestInstance()).andReturn(Optional.empty()).anyTimes();
        }
        replay(context);

        // when/then
        if (isNullPointerExpected) {
            assertThrows(NullPointerException.class, () ->
                    GeneratorMethodResolver.getGenerator(methodName, context));
        } else {
            JUnitException exception = assertThrows(JUnitException.class, () ->
                    GeneratorMethodResolver.getGenerator(methodName, context));
            assertEquals(expectedErrorMessage, exception.getMessage());
        }
        verify(context);
    }

    static Stream<Arguments> methodFindingScenarios() {
        return Stream.of(
                arguments("createGenerator", true),
                arguments("nonExistentMethod", false),
                arguments("methodWithWrongReturnType", false),
                arguments("methodWithParameters", false)
        );
    }

    @ParameterizedTest
    @MethodSource("methodFindingScenarios")
    void shouldFindOrNotFindMethod(String methodName, boolean shouldBeFound) {
        // given
        Class<?> clazz = SharedTestFactory.class;

        // when
        var method = GeneratorMethodResolver.findMethod(clazz, methodName);

        // then
        if (shouldBeFound) {
            assertTrue(method.isPresent());
            assertEquals(methodName, method.get().getName());
        } else {
            assertFalse(method.isPresent());
        }
    }

}
