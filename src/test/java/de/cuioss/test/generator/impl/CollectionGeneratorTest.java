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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.Generators;
import de.cuioss.tools.property.PropertyUtil;

@SuppressWarnings({"unchecked", "rawtypes"})
class CollectionGeneratorTest {

    private final CollectionGenerator<String> generator = new CollectionGenerator<>(Generators.letterStrings());

    private final ComplexBean complexBean = new ComplexBean();

    @Test
    void shouldIntersectList() {
        final var expectedType = (Class<Iterable<?>>) PropertyUtil
                .resolvePropertyType(complexBean.getClass(), ComplexBean.ATTRIBUTE_STRING_LIST).get();
        final Class<? extends Iterable> collectionClass = generator.nextCollection(expectedType).getClass();
        assertTrue(List.class.isAssignableFrom(collectionClass));
        assertFalse(Set.class.isAssignableFrom(collectionClass));
    }

    @Test
    void shouldIntersectCollection() {
        final var expectedType = (Class<Iterable<?>>) PropertyUtil
                .resolvePropertyType(complexBean.getClass(), ComplexBean.ATTRIBUTE_STRING_COLLECTION).get();
        final Class<? extends Iterable> collectionClass = generator.nextCollection(expectedType).getClass();
        assertTrue(Collection.class.isAssignableFrom(collectionClass));
        assertFalse(Set.class.isAssignableFrom(collectionClass));
    }

    @Test
    void shouldIntersectSet() {
        final var expectedType = (Class<Iterable<?>>) PropertyUtil
                .resolvePropertyType(complexBean.getClass(), ComplexBean.ATTRIBUTE_STRING_SET).get();
        final Class<? extends Iterable> collectionClass = generator.nextCollection(expectedType).getClass();
        assertFalse(List.class.isAssignableFrom(collectionClass));
        assertTrue(Set.class.isAssignableFrom(collectionClass));
    }

    @Test
    void shouldIntersectSortedSet() {
        final var expectedType = (Class<Iterable<?>>) PropertyUtil
                .resolvePropertyType(complexBean.getClass(), ComplexBean.ATTRIBUTE_STRING_SORTED_SET).get();
        final Class<? extends Iterable> collectionClass = generator.nextCollection(expectedType).getClass();
        assertFalse(List.class.isAssignableFrom(collectionClass));
        assertTrue(SortedSet.class.isAssignableFrom(collectionClass));
    }

    @Test
    void shouldWrapStrings() {
        var localGenerator = new CollectionGenerator<>(Generators.nonEmptyStrings());
        assertNotNull(localGenerator.list());
        assertNotNull(localGenerator.set());
    }

}
