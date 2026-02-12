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

import java.lang.reflect.InaccessibleObjectException;

import static org.junit.jupiter.api.Assertions.*;

class JpmsReflectionHelperTest {

    @Test
    @DisplayName("Should instantiate a public generator class")
    void shouldInstantiatePublicGenerator() {
        var generator = JpmsReflectionHelper.newGeneratorInstance(PublicTestGenerator.class);
        assertNotNull(generator);
        assertEquals("test", generator.next());
    }

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
    @DisplayName("isJpmsAccessException should return true for InaccessibleObjectException")
    void shouldDetectInaccessibleObjectException() {
        var exception = new InaccessibleObjectException("Unable to make accessible");
        assertTrue(JpmsReflectionHelper.isJpmsAccessException(exception));
    }

    @Test
    @DisplayName("isJpmsAccessException should return true for IllegalAccessException with 'does not export'")
    void shouldDetectDoesNotExportMessage() {
        var exception = new IllegalAccessException("module foo does not export bar.baz to unnamed module");
        assertTrue(JpmsReflectionHelper.isJpmsAccessException(exception));
    }

    @Test
    @DisplayName("isJpmsAccessException should return true for IllegalAccessException with 'does not open'")
    void shouldDetectDoesNotOpenMessage() {
        var exception = new IllegalAccessException("module foo does not open bar.baz to unnamed module");
        assertTrue(JpmsReflectionHelper.isJpmsAccessException(exception));
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
        var exception = new IllegalAccessException("Class X cannot access a member of class Y with modifiers 'private'");
        assertFalse(JpmsReflectionHelper.isJpmsAccessException(exception));
    }

    @Test
    @DisplayName("buildJpmsErrorMessage should contain package, module info, and all remediation options")
    void shouldBuildComprehensiveErrorMessage() {
        var message = JpmsReflectionHelper.buildJpmsErrorMessage(PublicTestGenerator.class, "instantiate");

        // Should contain class and package info
        assertTrue(message.contains(PublicTestGenerator.class.getName()), "Should contain class name");
        assertTrue(message.contains(PublicTestGenerator.class.getPackageName()), "Should contain package name");

        // Should contain all 4 remediation options
        assertTrue(message.contains("useModulePath"), "Should mention useModulePath");
        assertTrue(message.contains("opens " + PublicTestGenerator.class.getPackageName() + ";"),
                "Should mention opens directive");
        assertTrue(message.contains("opens " + PublicTestGenerator.class.getPackageName() + " to org.junit.platform.commons;"),
                "Should mention targeted opens directive");
        assertTrue(message.contains("@TypeGeneratorMethodSource"), "Should mention factory method alternative");
    }

    @Test
    @DisplayName("Non-JPMS failure (no no-args constructor) should preserve standard error from ReflectionSupport")
    void shouldPreserveStandardErrorForNonJpmsFailure() {
        // GeneratorWithoutNoArgsConstructor has no no-arg constructor — this is NOT a JPMS issue
        assertThrows(Exception.class,
                () -> JpmsReflectionHelper.newGeneratorInstance(GeneratorWithoutNoArgsConstructor.class));
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

    @SuppressWarnings("unused")
    public static class TestFactory {
        public static TypedGenerator<String> createGenerator() {
            return Generators.strings(5, 10);
        }

        public TypedGenerator<Integer> createInstanceGenerator() {
            return Generators.integers(1, 100);
        }
    }
}
