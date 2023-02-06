package de.icw.cui.test.generator.qc;

import de.icw.cui.test.generator.TypedGenerator;
import io.cui.test.generator.internal.net.java.quickcheck.Generator;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Wrapper for {@link Generator} provided by QuickCheck
 *
 * @author Oliver Wolff
 * @param <T> identifying the type of elements to be generated
 *
 */
@RequiredArgsConstructor
@ToString(of = "type")
public class QuickCheckGeneratorAdapter<T> implements TypedGenerator<T> {

    @Getter
    @NonNull
    private final Class<T> type;

    @Getter
    @NonNull
    private final Generator<T> generator;

    @Override
    public T next() {
        return generator.next();
    }

}
