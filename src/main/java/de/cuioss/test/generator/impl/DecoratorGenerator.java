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
 * A decorator pattern implementation for {@link TypedGenerator} that allows wrapping
 * and enhancing existing generators. This is particularly useful for testing corner cases
 * and special scenarios.
 * 
 * <p>Features:</p>
 * <ul>
 *   <li>Wraps any existing TypedGenerator</li>
 *   <li>Preserves type safety through generic type parameter</li>
 *   <li>Provides explicit type information via {@link #getType()}</li>
 *   <li>Thread-safe if the decorated generator is thread-safe</li>
 * </ul>
 * 
 * <p><em>Example usage:</em></p>
 * <pre>
 * {@code
 * // Create a decorator for integer generation
 * TypedGenerator&lt;Integer&gt; baseGenerator = Generators.integers(1, 100);
 * var decorator = new DecoratorGenerator&lt;>(Integer.class, baseGenerator);
 * 
 * // Use the decorated generator
 * Integer value = decorator.next();
 * }
 * </pre>
 * 
 * <p>Common use cases include:</p>
 * <ul>
 *   <li>Adding validation or transformation logic</li>
 *   <li>Logging or monitoring generator behavior</li>
 *   <li>Implementing special case handling</li>
 *   <li>Combining multiple generators</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @param <T> The type of elements to be generated
 * @see TypedGenerator
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
