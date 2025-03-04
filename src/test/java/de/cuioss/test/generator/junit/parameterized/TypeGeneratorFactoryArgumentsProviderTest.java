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
import de.cuioss.test.generator.junit.EnableGeneratorController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.platform.commons.JUnitException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link TypeGeneratorFactoryArgumentsProvider} focusing on error paths
 * and edge cases not covered by {@link TypeGeneratorFactorySourceTest}.
 * Some methods in the test factory class are intentionally not directly invoked
 * in tests but are required to test method resolution logic in TypeGeneratorFactoryArgumentsProvider.
 */
@EnableGeneratorController
class TypeGeneratorFactoryArgumentsProviderTest {

    @Test
    @DisplayName("Should throw exception when factory class is null")
    void shouldThrowExceptionWhenFactoryClassIsNull() throws Exception {
        var provider = new TestProvider();
        setPrivateField(provider, "factoryClass", null);
        setPrivateField(provider, "factoryMethod", "someMethod");

        Exception exception = assertThrows(Exception.class, () -> {
            try {
                invokeCreateGeneratorFromFactory(provider);
            } catch (InvocationTargetException e) {
                if (e.getTargetException() instanceof Exception) {
                    throw (Exception) e.getTargetException();
                }
                throw new RuntimeException(e.getTargetException());
            }
        });

        assertInstanceOf(NullPointerException.class, exception, "Expected NullPointerException but got: " + exception.getClass().getName());
    }

    @Test
    @DisplayName("Should throw exception when factory method is null")
    void shouldThrowExceptionWhenFactoryMethodIsNull() throws Exception {
        var provider = new TestProvider();
        setPrivateField(provider, "factoryClass", TestFactoryClass.class);
        setPrivateField(provider, "factoryMethod", null);

        Exception exception = assertThrows(Exception.class, () -> {
            try {
                invokeCreateGeneratorFromFactory(provider);
            } catch (InvocationTargetException e) {
                if (e.getTargetException() instanceof Exception) {
                    throw (Exception) e.getTargetException();
                }
                throw new RuntimeException(e.getTargetException());
            }
        });

        assertInstanceOf(NullPointerException.class, exception, "Expected NullPointerException but got: " + exception.getClass().getName());
    }

    @Test
    @DisplayName("Should throw exception when factory method doesn't exist")
    void shouldThrowExceptionWhenFactoryMethodDoesntExist() throws Exception {
        var provider = new TestProvider();
        setPrivateField(provider, "factoryClass", TestFactoryClass.class);
        setPrivateField(provider, "factoryMethod", "nonExistentMethod");
        setPrivateField(provider, "methodParameters", new String[0]);

        Exception exception = assertThrows(Exception.class, () -> {
            try {
                invokeCreateGeneratorFromFactory(provider);
            } catch (InvocationTargetException e) {
                if (e.getTargetException() instanceof Exception) {
                    throw (Exception) e.getTargetException();
                }
                throw new RuntimeException(e.getTargetException());
            }
        });

        assertInstanceOf(JUnitException.class, exception, "Expected JUnitException but got: " + exception.getClass().getName());

        // The actual error message might be different, just check that it contains the method name
        String errorMessage = exception.getMessage();
        assertTrue(errorMessage.contains("nonExistentMethod"),
                "Error message should contain the method name: " + errorMessage);
    }

    @Test
    @DisplayName("Should throw exception when factory method throws exception")
    void shouldThrowExceptionWhenFactoryMethodThrowsException() throws Exception {
        var provider = new TestProvider();
        setPrivateField(provider, "factoryClass", TestFactoryClass.class);
        setPrivateField(provider, "factoryMethod", "throwingGenerator");
        setPrivateField(provider, "methodParameters", new String[0]);

        Exception exception = assertThrows(Exception.class, () -> {
            try {
                invokeCreateGeneratorFromFactory(provider);
            } catch (InvocationTargetException e) {
                if (e.getTargetException() instanceof Exception) {
                    throw (Exception) e.getTargetException();
                }
                throw new RuntimeException(e.getTargetException());
            }
        });

        assertInstanceOf(JUnitException.class, exception, "Expected JUnitException but got: " + exception.getClass().getName());

        // The actual error message might be different, just check that it contains the method name
        String errorMessage = exception.getMessage();
        assertTrue(errorMessage.contains("throwingGenerator"),
                "Error message should contain the method name: " + errorMessage);
    }

    @Test
    @DisplayName("Should select correct method when multiple methods match parameter count")
    void shouldSelectCorrectMethodWhenMultipleMethodsMatchParameterCount() throws Exception {
        var provider = new TestProvider();
        setPrivateField(provider, "factoryClass", TestFactoryClass.class);
        setPrivateField(provider, "factoryMethod", "overloadedMethod");
        setPrivateField(provider, "methodParameters", new String[]{"test"});

        TypedGenerator<?> generator;
        try {
            generator = invokeCreateGeneratorFromFactory(provider);
        } catch (InvocationTargetException e) {
            if (e.getTargetException() instanceof Exception) {
                throw (Exception) e.getTargetException();
            }
            throw new RuntimeException(e.getTargetException());
        }

        assertNotNull(generator);
        assertEquals("string-variant", generator.next());
    }

    @Test
    @DisplayName("Should select Integer variant of overloaded method")
    void shouldSelectIntegerVariantOfOverloadedMethod() {
        TypedGenerator<Integer> generator = TestFactoryClass.overloadedMethod(42);
        assertNotNull(generator);
        assertEquals(42, generator.next());
    }

    @Test
    @DisplayName("Should handle multiple matching methods without string parameters")
    void shouldHandleMultipleMatchingMethodsWithoutStringParameters() throws Exception {
        var provider = new TestProvider();
        setPrivateField(provider, "factoryClass", TestFactoryClass.class);
        setPrivateField(provider, "factoryMethod", "ambiguousMethod");
        setPrivateField(provider, "methodParameters", new String[]{"test"});

        TypedGenerator<?> generator;
        try {
            generator = invokeCreateGeneratorFromFactory(provider);
        } catch (InvocationTargetException e) {
            if (e.getTargetException() instanceof Exception) {
                throw (Exception) e.getTargetException();
            }
            throw new RuntimeException(e.getTargetException());
        }

        assertNotNull(generator);
        // Verify that a valid generator is returned
        assertInstanceOf(TypedGenerator.class, generator);
    }

    /**
     * Helper method to set a private field on an object.
     */
    private void setPrivateField(Object object, String fieldName, Object value) throws Exception {
        Field field = TypeGeneratorFactoryArgumentsProvider.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }

    /**
     * Helper method to invoke the private createGeneratorFromFactory method.
     */
    private TypedGenerator<?> invokeCreateGeneratorFromFactory(TypeGeneratorFactoryArgumentsProvider provider) throws Exception {
        Method method = TypeGeneratorFactoryArgumentsProvider.class.getDeclaredMethod("createGeneratorFromFactory");
        method.setAccessible(true);
        return (TypedGenerator<?>) method.invoke(provider);
    }

    /**
     * Test factory class with various methods for testing.
     * Some methods are intentionally not directly invoked in tests but are required
     * to test method resolution logic in TypeGeneratorFactoryArgumentsProvider.
     */
    @SuppressWarnings("java:S1172") // Intentionally unused but required for testing
    public static class TestFactoryClass {

        /**
         * Method that throws an exception.
         */
        public static TypedGenerator<String> throwingGenerator() {
            throw new RuntimeException("Test exception");
        }

        /**
         * Overloaded method with String parameter.
         */
        public static TypedGenerator<String> overloadedMethod(String param) {
            return Generators.fixedValues("string-variant");
        }

        /**
         * Overloaded method with Integer parameter.
         * This method is used to test method overloading resolution.
         */
        public static TypedGenerator<Integer> overloadedMethod(Integer param) {
            return Generators.fixedValues(42);
        }

        /**
         * First ambiguous method that takes an Object parameter.
         * When multiple methods match and none have String parameters,
         * TypeGeneratorFactoryArgumentsProvider will choose the first one.
         */
        public static TypedGenerator<String> ambiguousMethod(Object param) {
            return Generators.fixedValues("object-variant");
        }

        /**
         * Second ambiguous method that takes a Comparable parameter.
         * This method is intentionally not directly invoked but is required
         * to test ambiguous method resolution.
         */
        @SuppressWarnings("unused") // Intentionally unused but required for testing
        public static TypedGenerator<String> ambiguousMethod(Comparable<?> param) {
            return Generators.fixedValues("comparable-variant");
        }
    }

    /**
     * Test provider implementation for testing.
     */
    private static class TestProvider extends TypeGeneratorFactoryArgumentsProvider {
        @Override
        protected Stream<? extends Arguments> provideArgumentsForGenerators(ExtensionContext context) {
            return Stream.empty();
        }
    }
}
