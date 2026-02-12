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

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.JUnitException;

import java.lang.reflect.InaccessibleObjectException;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class JpmsReflectionHelperTest {

    // --- newGeneratorInstance tests ---

    @Test
    @DisplayName("Should instantiate a public generator class")
    void shouldInstantiatePublicGenerator() {
        var generator = JpmsReflectionHelper.newGeneratorInstance(PublicTestGenerator.class);
        assertNotNull(generator);
        assertEquals("test", generator.next());
    }

    @Test
    @DisplayName("Non-JPMS failure (no no-args constructor) should preserve standard error")
    void shouldPreserveStandardErrorForNonJpmsFailure() {
        // ReflectionSupport.newInstance() throws JUnitException on Java 21 but
        // NoSuchMethodException on Java 25 — both are non-JPMS, so verify the
        // exception is NOT treated as a JPMS issue regardless of type
        var exception = assertThrows(Exception.class,
                () -> JpmsReflectionHelper.newGeneratorInstance(GeneratorWithoutNoArgsConstructor.class));
        assertFalse(JpmsReflectionHelper.isJpmsAccessException(exception),
                "Non-JPMS failure should not be classified as JPMS access exception");
    }

    @Test
    @DisplayName("newGeneratorInstance should succeed for package-private class with private constructor")
    void shouldInstantiatePrivateConstructorClass() {
        // ReflectionSupport.newInstance() handles private constructors within the same package
        var generator = JpmsReflectionHelper.newGeneratorInstance(PrivateConstructorGenerator.class);
        assertNotNull(generator);
        assertEquals("private", generator.next());
    }

    // --- invokeMethod tests ---

    @Test
    @DisplayName("Should invoke a static method returning a generator")
    void shouldInvokeStaticMethod() throws Exception {
        var method = TestFactory.class.getDeclaredMethod("createGenerator");
        var result = JpmsReflectionHelper.invokeMethod(method, null);
        assertNotNull(result);
        assertInstanceOf(TypedGenerator.class, result);
    }

    @Test
    @DisplayName("Should invoke an instance method")
    void shouldInvokeInstanceMethod() throws Exception {
        var method = TestFactory.class.getDeclaredMethod("createInstanceGenerator");
        var instance = new TestFactory();
        var result = JpmsReflectionHelper.invokeMethod(method, instance);
        assertNotNull(result);
        assertInstanceOf(TypedGenerator.class, result);
    }

    @Test
    @DisplayName("invokeMethod should propagate InvocationTargetException when method throws")
    void shouldPropagateInvocationTargetException() throws Exception {
        var method = TestFactory.class.getDeclaredMethod("throwingMethod");
        assertThrows(InvocationTargetException.class,
                () -> JpmsReflectionHelper.invokeMethod(method, null));
    }

    @Test
    @DisplayName("invokeMethod should rethrow non-JPMS IllegalAccessException")
    void shouldRethrowNonJpmsIllegalAccessException() throws Exception {
        // Get a private method — invoke() will throw IllegalAccessException
        var method = TestFactory.class.getDeclaredMethod("privateMethod");
        // The IllegalAccessException from invoke() won't contain JPMS keywords,
        // so it should be rethrown as-is
        assertThrows(IllegalAccessException.class,
                () -> JpmsReflectionHelper.invokeMethod(method, null));
    }

    // --- isJpmsAccessException tests ---

    @Test
    @DisplayName("isJpmsAccessException should return true for InaccessibleObjectException")
    void shouldDetectInaccessibleObjectException() {
        assertTrue(JpmsReflectionHelper.isJpmsAccessException(
                new InaccessibleObjectException("Unable to make accessible")));
    }

    @Test
    @DisplayName("isJpmsAccessException should return true for IllegalAccessException with 'does not export'")
    void shouldDetectDoesNotExportMessage() {
        assertTrue(JpmsReflectionHelper.isJpmsAccessException(
                new IllegalAccessException("module foo does not export bar.baz to unnamed module")));
    }

    @Test
    @DisplayName("isJpmsAccessException should return true for IllegalAccessException with 'does not open'")
    void shouldDetectDoesNotOpenMessage() {
        assertTrue(JpmsReflectionHelper.isJpmsAccessException(
                new IllegalAccessException("module foo does not open bar.baz to unnamed module")));
    }

    @Test
    @DisplayName("isJpmsAccessException should return true for nested JPMS cause")
    void shouldDetectNestedJpmsCause() {
        var cause = new InaccessibleObjectException("Unable to make accessible");
        var wrapper = new IllegalStateException("wrapper", cause);
        assertTrue(JpmsReflectionHelper.isJpmsAccessException(wrapper));
    }

    @Test
    @DisplayName("isJpmsAccessException should return false for unrelated exceptions")
    void shouldReturnFalseForUnrelatedException() {
        assertFalse(JpmsReflectionHelper.isJpmsAccessException(new IllegalStateException("something else")));
        assertFalse(JpmsReflectionHelper.isJpmsAccessException(new IllegalArgumentException("bad argument")));
        assertFalse(JpmsReflectionHelper.isJpmsAccessException(new NullPointerException()));
    }

    @Test
    @DisplayName("isJpmsAccessException should return false for IllegalAccessException without JPMS message")
    void shouldReturnFalseForNonJpmsIllegalAccess() {
        assertFalse(JpmsReflectionHelper.isJpmsAccessException(
                new IllegalAccessException("Class X cannot access a member of class Y with modifiers 'private'")));
    }

    @Test
    @DisplayName("isJpmsAccessException should return false for IllegalAccessException with null message")
    void shouldReturnFalseForNullMessage() {
        assertFalse(JpmsReflectionHelper.isJpmsAccessException(new IllegalAccessException(null)));
    }

    @Test
    @DisplayName("isJpmsAccessException should detect JPMS cause wrapped in JUnitException")
    void shouldDetectJpmsCauseInJUnitException() {
        var jpmsEx = new InaccessibleObjectException("module access denied");
        var junitEx = new JUnitException("wrapped", jpmsEx);
        assertTrue(JpmsReflectionHelper.isJpmsAccessException(junitEx));
    }

    // --- buildJpmsErrorMessage tests ---

    @Test
    @DisplayName("buildJpmsErrorMessage should contain package, module info, and all remediation options")
    void shouldBuildComprehensiveErrorMessage() {
        var message = JpmsReflectionHelper.buildJpmsErrorMessage(PublicTestGenerator.class, "instantiate");

        assertTrue(message.contains(PublicTestGenerator.class.getName()), "Should contain class name");
        assertTrue(message.contains(PublicTestGenerator.class.getPackageName()), "Should contain package name");
        assertTrue(message.contains("<unnamed module>"), "Should contain unnamed module for classpath class");
        assertTrue(message.contains("useModulePath"), "Should mention useModulePath");
        assertTrue(message.contains("opens " + PublicTestGenerator.class.getPackageName() + ";"),
                "Should mention opens directive");
        assertTrue(message.contains("opens " + PublicTestGenerator.class.getPackageName() + " to org.junit.platform.commons;"),
                "Should mention targeted opens directive");
        assertTrue(message.contains("@TypeGeneratorMethodSource"), "Should mention factory method alternative");
    }

    @Test
    @DisplayName("buildJpmsErrorMessage should include the operation description")
    void shouldIncludeOperationInErrorMessage() {
        var message = JpmsReflectionHelper.buildJpmsErrorMessage(PublicTestGenerator.class, "invoke method 'foo'");
        assertTrue(message.contains("invoke method 'foo'"), "Should contain the operation");
        assertTrue(message.contains("JPMS"), "Should mention JPMS");
    }

    // --- Test helpers ---

    public static class PublicTestGenerator implements TypedGenerator<String> {
        @Override
        public String next() {
            return "test";
        }

        @Override
        public Class<String> getType() {
            return String.class;
        }
    }

    public static class GeneratorWithoutNoArgsConstructor implements TypedGenerator<String> {
        private final String value;

        public GeneratorWithoutNoArgsConstructor(String value) {
            this.value = value;
        }

        @Override
        public String next() {
            return value;
        }

        @Override
        public Class<String> getType() {
            return String.class;
        }
    }

    /**
     * Generator with a private no-args constructor, used to test reflective instantiation
     * of classes with non-public constructors.
     */
    static class PrivateConstructorGenerator implements TypedGenerator<String> {
        private PrivateConstructorGenerator() {
            // private constructor
        }

        @Override
        public String next() {
            return "private";
        }

        @Override
        public Class<String> getType() {
            return String.class;
        }
    }

    @SuppressWarnings("unused")
    public static class TestFactory {
        public static TypedGenerator<String> createGenerator() {
            return Generators.strings(5, 10);
        }

        public TypedGenerator<Integer> createInstanceGenerator() {
            return Generators.integers(1, 100);
        }

        public static String throwingMethod() {
            throw new IllegalArgumentException("test exception from method");
        }

        private static String privateMethod() {
            return "private";
        }
    }
}
