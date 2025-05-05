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
package de.cuioss.test.generator.junit.parameterized;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.platform.commons.JUnitException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;

class TypeGeneratorMethodArgumentsProviderTest {

    private TypeGeneratorMethodArgumentsProvider provider;
    private ExtensionContext context;
    private TypeGeneratorMethodSource annotation;

    @BeforeEach
    void setUp() {
        provider = new TypeGeneratorMethodArgumentsProvider();
        context = createMock(ExtensionContext.class);
        annotation = createMock(TypeGeneratorMethodSource.class);
    }

    @Test
    void shouldAcceptAnnotation() {
        // given
        expect(annotation.value()).andReturn("testMethod").anyTimes();
        expect(annotation.count()).andReturn(5).anyTimes();
        replay(annotation);

        // when
        provider.accept(annotation);

        // then
        assertEquals(5, provider.getCount());
        assertEquals(-1L, provider.getSeed()); // Default seed is -1L
        verify(annotation);
    }

    @Test
    void shouldUseDefaultValuesWhenNotSpecified() {
        // given
        expect(annotation.value()).andReturn("testMethod").anyTimes();
        expect(annotation.count()).andReturn(0).anyTimes(); // Should use default of 1
        replay(annotation);

        // when
        provider.accept(annotation);

        // then
        assertEquals(1, provider.getCount()); // Default is 1
        assertEquals(-1L, provider.getSeed()); // Default seed is -1L
        verify(annotation);
    }

    @Test
    void shouldProvideArgumentsFromTestClassMethod() {
        // given
        expect(annotation.value()).andReturn("createGenerator").anyTimes();
        expect(annotation.count()).andReturn(3).anyTimes();
        replay(annotation);
        provider.accept(annotation);

        // Mock the context to return our test class
        expect(context.getRequiredTestClass()).andReturn((Class) TestFactoryClass.class).anyTimes();
        expect(context.getTestInstance()).andReturn(Optional.empty()).anyTimes();
        replay(context);

        // when
        List<Arguments> arguments = provider.provideArguments(context)
                .collect(Collectors.toList());

        // then
        assertEquals(3, arguments.size());
        for (Arguments args : arguments) {
            assertEquals(1, args.get().length);
            assertInstanceOf(String.class, args.get()[0]);
        }
        verify(context);
    }

    @Test
    void shouldProvideArgumentsFromExternalClassMethod() {
        // given
        String methodReference = TestFactoryClass.class.getName() + "#createGenerator";
        expect(annotation.value()).andReturn(methodReference).anyTimes();
        expect(annotation.count()).andReturn(2).anyTimes();
        replay(annotation);
        provider.accept(annotation);

        replay(context); // Context shouldn't be needed for external class methods

        // when
        List<Arguments> arguments = provider.provideArguments(context)
                .collect(Collectors.toList());

        // then
        assertEquals(2, arguments.size());
        for (Arguments args : arguments) {
            assertEquals(1, args.get().length);
            assertInstanceOf(String.class, args.get()[0]);
        }
        verify(context);
    }

    @Test
    void shouldThrowExceptionForInvalidMethodReference() {
        // given
        expect(annotation.value()).andReturn("invalid#format#method").anyTimes();
        expect(annotation.count()).andReturn(1).anyTimes();
        replay(annotation);
        provider.accept(annotation);

        replay(context);

        // when/then
        assertThrows(JUnitException.class, () -> provider.provideArguments(context));
        verify(context);
    }

    @Test
    void shouldThrowExceptionForNonExistentMethod() {
        // given
        expect(annotation.value()).andReturn("nonExistentMethod").anyTimes();
        expect(annotation.count()).andReturn(1).anyTimes();
        replay(annotation);
        provider.accept(annotation);

        expect(context.getRequiredTestClass()).andReturn((Class) TestFactoryClass.class).anyTimes();
        expect(context.getTestInstance()).andReturn(Optional.empty()).anyTimes();
        replay(context);

        // when/then
        assertThrows(JUnitException.class, () -> provider.provideArguments(context));
        verify(context);
    }

    @Test
    void shouldThrowExceptionForNonExistentClass() {
        // given
        expect(annotation.value()).andReturn("com.nonexistent.Class#method").anyTimes();
        expect(annotation.count()).andReturn(1).anyTimes();
        replay(annotation);
        provider.accept(annotation);

        replay(context);

        // when/then
        assertThrows(JUnitException.class, () -> provider.provideArguments(context));
        verify(context);
    }

    /**
     * Test factory class with methods that return TypedGenerator instances.
     * Some methods are intentionally unused directly but are required for testing
     * method resolution logic.
     */
    @SuppressWarnings("unused")
    static class TestFactoryClass {

        /**
         * Creates a string generator for testing.
         * @return a TypedGenerator that generates strings
         */
        public static TypedGenerator<String> createGenerator() {
            return Generators.strings(5, 10);
        }

        /**
         * Creates an integer generator for testing.
         * This method is intentionally not directly called in tests but is needed
         * to verify method resolution logic.
         * @return a TypedGenerator that generates integers
         */
        public static TypedGenerator<Integer> createIntegerGenerator() {
            return Generators.integers(1, 100);
        }

        /**
         * Non-static method that should not be callable without an instance.
         * This method is intentionally not directly called in tests but is needed
         * to verify method resolution logic.
         * @return a TypedGenerator that generates doubles
         */
        public TypedGenerator<Double> createDoubleGenerator() {
            return Generators.doubles(0.0, 1.0);
        }
    }
}
