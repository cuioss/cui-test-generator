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
package de.cuioss.test.generator.impl;

import de.cuioss.test.generator.TypedGenerator;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Wrapper for decorating an already existing {@link TypedGenerator}. This is
 * usually used for modeling corner cases.
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
