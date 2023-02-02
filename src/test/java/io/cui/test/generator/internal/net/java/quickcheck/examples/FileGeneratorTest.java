package io.cui.test.generator.internal.net.java.quickcheck.examples;

import static io.cui.test.generator.internal.net.java.quickcheck.QuickCheck.forAll;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.CombinedGenerators.nonEmptyLists;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.fixedValues;
import static io.cui.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.letterStrings;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.cui.test.generator.internal.net.java.quickcheck.Generator;
import io.cui.test.generator.internal.net.java.quickcheck.characteristic.AbstractCharacteristic;

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
            File f = roots.next();
            for (String p : paths.next()) {
                f = new File(f, p);
            }
            return f;
        }
    }
}
