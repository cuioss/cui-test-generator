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

import org.junit.platform.commons.JUnitException;
import org.junit.platform.commons.support.ReflectionSupport;

import java.lang.reflect.InaccessibleObjectException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Package-private utility for JPMS-aware reflection operations.
 * <p>
 * When the Java Platform Module System (JPMS) restricts access to generator classes
 * or factory methods, this helper provides:
 * <ul>
 *   <li>A fallback via {@code setAccessible(true)} (works when the package is {@code opens}-ed)</li>
 *   <li>Clear, actionable error messages pointing to concrete remediation steps</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @since 2.0
 */
final class JpmsReflectionHelper {

    private JpmsReflectionHelper() {
        // Utility class
    }

    /**
     * Creates a new instance of the given class, falling back to {@code setAccessible(true)}
     * if the initial attempt fails due to JPMS access restrictions.
     *
     * @param clazz the class to instantiate
     * @param <T>   the type of the instance
     * @return a new instance
     * @throws JUnitException if instantiation fails
     */
    // cui-rewrite:disable InvalidExceptionUsageRecipe
    static <T> T newGeneratorInstance(Class<T> clazz) {
        try {
            return ReflectionSupport.newInstance(clazz);
        } catch (Exception e) {
            if (isJpmsAccessException(e)) {
                // Fallback: try setAccessible
                try {
                    var constructor = clazz.getDeclaredConstructor();
                    constructor.setAccessible(true);
                    return constructor.newInstance();
                } catch (InaccessibleObjectException fallbackEx) {
                    throw new JUnitException(buildJpmsErrorMessage(clazz, "instantiate"), fallbackEx);
                } catch (Exception fallbackEx) {
                    throw new JUnitException(buildJpmsErrorMessage(clazz, "instantiate"), fallbackEx);
                }
            }
            // Not a JPMS issue — rethrow as-is to preserve original message
            throw e;
        }
    }

    /**
     * Invokes the given method, falling back to {@code setAccessible(true)}
     * if the initial attempt fails due to JPMS access restrictions.
     *
     * @param method the method to invoke
     * @param target the target object (null for static methods)
     * @param args   the method arguments
     * @return the method return value
     * @throws JUnitException if invocation fails due to JPMS restrictions
     * @throws InvocationTargetException if the underlying method throws an exception
     * @throws IllegalAccessException if access is denied for non-JPMS reasons
     */
    static Object invokeMethod(Method method, Object target, Object... args)
            throws InvocationTargetException, IllegalAccessException {
        try {
            return method.invoke(target, args);
        } catch (IllegalAccessException e) {
            if (isJpmsAccessException(e)) {
                // Fallback: try setAccessible
                try {
                    method.setAccessible(true);
                    return method.invoke(target, args);
                } catch (InaccessibleObjectException fallbackEx) {
                    throw new JUnitException(
                            buildJpmsErrorMessage(method.getDeclaringClass(), "invoke method '" + method.getName() + "'"),
                            fallbackEx);
                } catch (IllegalAccessException fallbackEx) {
                    throw new JUnitException(
                            buildJpmsErrorMessage(method.getDeclaringClass(), "invoke method '" + method.getName() + "'"),
                            fallbackEx);
                }
            }
            throw e;
        }
    }

    /**
     * Checks whether the given throwable (or any cause in its chain) indicates
     * a JPMS access restriction.
     *
     * @param throwable the throwable to inspect
     * @return true if the exception chain contains a JPMS-related access issue
     */
    static boolean isJpmsAccessException(Throwable throwable) {
        var current = throwable;
        while (current != null) {
            if (current instanceof InaccessibleObjectException) {
                return true;
            }
            if (current instanceof IllegalAccessException iae) {
                var message = iae.getMessage();
                if (message != null && (message.contains("does not export")
                        || message.contains("does not open")
                        || message.contains("module"))) {
                    return true;
                }
            }
            current = current.getCause();
        }
        return false;
    }

    /**
     * Builds a structured error message with JPMS-specific remediation guidance.
     *
     * @param targetClass the class that could not be accessed
     * @param operation   a description of the operation that failed (e.g., "instantiate")
     * @return the error message
     */
    static String buildJpmsErrorMessage(Class<?> targetClass, String operation) {
        var packageName = targetClass.getPackageName();
        var module = targetClass.getModule();
        var moduleName = module.isNamed() ? module.getName() : "<unnamed module>";

        return "Failed to " + operation + " class " + targetClass.getName()
                + " (package: " + packageName + ", module: " + moduleName + ")."
                + " This is likely a Java Platform Module System (JPMS) access restriction."
                + "\n\nPossible solutions:"
                + "\n  1. Configure Maven Surefire to use the classpath instead of the module path:"
                + "\n       <plugin>"
                + "\n         <artifactId>maven-surefire-plugin</artifactId>"
                + "\n         <configuration>"
                + "\n           <useModulePath>false</useModulePath>"
                + "\n         </configuration>"
                + "\n       </plugin>"
                + "\n  2. Add an 'opens' directive to your module-info.java:"
                + "\n       opens " + packageName + ";"
                + "\n  3. Use a targeted 'opens' directive:"
                + "\n       opens " + packageName + " to org.junit.platform.commons;"
                + "\n  4. Use @TypeGeneratorMethodSource with a factory method inside your test class instead.";
    }
}
