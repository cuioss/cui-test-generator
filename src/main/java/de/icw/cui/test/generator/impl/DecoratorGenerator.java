package de.icw.cui.test.generator.impl;

import de.icw.cui.test.generator.TypedGenerator;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Wrapper for decorating an already existing {@link TypedGenerator}. This is usually used for
 * modeling corner cases.
 *
 * @author Oliver Wolff
 * @param <T> identifying the type of elements to be generated
 *
 */
@RequiredArgsConstructor
@ToString(of = "type")
public class DecoratorGenerator<T> implements TypedGenerator<T> {

    @Getter
    @NonNull
    private final Class<T> type;

    @Getter
    @NonNull
    private final TypedGenerator<T> decorator;

    @Override
    public T next() {
        return decorator.next();
    }

}
