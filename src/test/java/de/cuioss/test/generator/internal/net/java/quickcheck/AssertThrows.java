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
package de.cuioss.test.generator.internal.net.java.quickcheck;

class AssertThrows {

    /**
     * Assert that an exception (the type of which is given as first argument) is
     * thrown when the code given as second argument is executed. An
     * {@link AssertionError} is thrown when no exception is thrown in the code, or
     * the wrong type of exception is thrown.
     *
     * @return The thrown exception for further inspection.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Throwable> T assertThrows(Class<T> expectedType, ExceptionThrowingExecutor executor) {
        try {
            executor.execute();
        } catch (Throwable t) {
            if (expectedType.isInstance(t)) {
                return (T) t;
            }
            throw new AssertionError(
                    "Expected an exception of type " + expectedType.getName() + ", but got: " + t.getClass().getName(),
                    t);
        }
        throw new AssertionError(
                "No exception was thrown, but expected an exception of type: " + expectedType.getName());
    }

    public interface ExceptionThrowingExecutor {

        @SuppressWarnings({"checkstyle:illegalthrows", "ProhibitedExceptionDeclared"})
        void execute() throws Throwable;
    }
}
