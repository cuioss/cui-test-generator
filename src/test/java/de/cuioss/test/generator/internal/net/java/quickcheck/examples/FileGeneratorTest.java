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
package de.cuioss.test.generator.internal.net.java.quickcheck.examples;

import de.cuioss.test.generator.internal.net.java.quickcheck.Generator;
import de.cuioss.test.generator.internal.net.java.quickcheck.characteristic.AbstractCharacteristic;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static de.cuioss.test.generator.internal.net.java.quickcheck.QuickCheck.forAll;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators.nonEmptyLists;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.fixedValues;
import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.letterStrings;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileGeneratorTest {

    // TODO which string are supported

    @Test
    void file() {
        forAll(new FileGenerator(), new AbstractCharacteristic<>() {

            @Override
            protected void doSpecify(File any) {
                assertTrue(any.isAbsolute());
            }
        });
    }

    static class FileGenerator implements Generator<File> {

        final Generator<File> roots = fixedValues(File.listRoots());
        final Generator<List<String>> paths = nonEmptyLists(letterStrings());

        @Override
        public File next() {
            var f = roots.next();
            for (String p : paths.next()) {
                f = new File(f, p);
            }
            return f;
        }
    }
}
