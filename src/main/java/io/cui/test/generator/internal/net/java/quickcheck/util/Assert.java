/*
 *  Licensed to the author under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.cui.test.generator.internal.net.java.quickcheck.util;

import static java.lang.String.format;

public final class Assert {

    private static final String MISSING = "MISSING";

    private Assert() { /* prevent any instances */
    }

    public static void notNull(Object arg, String argName) {
        if (arg == null) {
            throw new IllegalArgumentException("Argument " + caption(argName)
                    + " expected to be not null");
        }
    }

    public static void notEmpty(char[] arg, String argName) {
        if (arg.length == 0) {
            throw new IllegalArgumentException("Array " + caption(argName)
                    + " expected to be not empty");
        }
    }

    public static void notEmpty(Object[] arg, String argName) {
        if (arg.length == 0) {
            throw new IllegalArgumentException("Array " + caption(argName)
                    + " expected to be not empty");
        }
    }

    public static <T> void notEmpty(Iterable<T> arg, String argName) {
        if (! arg.iterator().hasNext()) {
            throw new IllegalArgumentException(caption(argName) + " expected to be not empty");
        }
    }

    public static void lessOrEqual(double threshold, double arg, String argName) {
        if (arg > threshold) {
            throw new IllegalArgumentException("Value " + value(arg)
                    + " of argument " + caption(argName)
                    + " expected to be not greater than " + value(threshold));
        }
    }

    public static void equal(double expected, double arg, String argName) {
        if (arg != expected) {
            throw new IllegalArgumentException(format(
                    "Value %s of argument %s expected to be not equal to %s",
                    value(arg), caption(argName), value(expected)));
        }

    }

    public static void greaterOrEqual(double threshold, double arg,
            String argName) {
        if (arg < threshold) {
            throw new IllegalArgumentException("Value " + value(arg)
                    + " of argument " + caption(argName)
                    + " expected to be not less than " + value(threshold));
        }
    }

    private static String caption(Object obj) {
        return "[" + (obj == null ? MISSING : obj) + "]";
    }

    private static String value(Object value) {
        return "<" + (value == null ? MISSING : value) + ">";
    }

}
