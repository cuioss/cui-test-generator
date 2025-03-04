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

import de.cuioss.test.generator.Generators;
import de.cuioss.tools.property.PropertyUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings({"unchecked", "rawtypes"})
@DisplayName("CollectionGenerator should")
class CollectionGeneratorTest {

    private final CollectionGenerator<String> generator = new CollectionGenerator<>(Generators.letterStrings());
    private final ComplexBean complexBean = new ComplexBean();

    @Nested
    @DisplayName("handle collection type intersections")
    class CollectionTypeIntersectionTests {

        @Test
        @DisplayName("correctly handle List type")
        void shouldIntersectList() {
            // Arrange
            final var expectedType = (Class<Iterable<?>>) PropertyUtil
                    .resolvePropertyType(complexBean.getClass(), ComplexBean.ATTRIBUTE_STRING_LIST).get();

            // Act
            final Class<? extends Iterable> collectionClass = generator.nextCollection(expectedType).getClass();

            // Assert
            assertTrue(List.class.isAssignableFrom(collectionClass), "Should be assignable to List");
            assertFalse(Set.class.isAssignableFrom(collectionClass), "Should not be assignable to Set");
        }

        @Test
        @DisplayName("correctly handle Collection type")
        void shouldIntersectCollection() {
            // Arrange
            final var expectedType = (Class<Iterable<?>>) PropertyUtil
                    .resolvePropertyType(complexBean.getClass(), ComplexBean.ATTRIBUTE_STRING_COLLECTION).get();

            // Act
            final Class<? extends Iterable> collectionClass = generator.nextCollection(expectedType).getClass();

            // Assert
            assertTrue(Collection.class.isAssignableFrom(collectionClass), "Should be assignable to Collection");
            assertFalse(Set.class.isAssignableFrom(collectionClass), "Should not be assignable to Set");
        }

        @Test
        @DisplayName("correctly handle Set type")
        void shouldIntersectSet() {
            // Arrange
            final var expectedType = (Class<Iterable<?>>) PropertyUtil
                    .resolvePropertyType(complexBean.getClass(), ComplexBean.ATTRIBUTE_STRING_SET).get();

            // Act
            final Class<? extends Iterable> collectionClass = generator.nextCollection(expectedType).getClass();

            // Assert
            assertFalse(List.class.isAssignableFrom(collectionClass), "Should not be assignable to List");
            assertTrue(Set.class.isAssignableFrom(collectionClass), "Should be assignable to Set");
        }

        @Test
        @DisplayName("correctly handle SortedSet type")
        void shouldIntersectSortedSet() {
            // Arrange
            final var expectedType = (Class<Iterable<?>>) PropertyUtil
                    .resolvePropertyType(complexBean.getClass(), ComplexBean.ATTRIBUTE_STRING_SORTED_SET).get();

            // Act
            final Class<? extends Iterable> collectionClass = generator.nextCollection(expectedType).getClass();

            // Assert
            assertFalse(List.class.isAssignableFrom(collectionClass), "Should not be assignable to List");
            assertTrue(SortedSet.class.isAssignableFrom(collectionClass), "Should be assignable to SortedSet");
        }
    }

    @Nested
    @DisplayName("handle string collections")
    class StringCollectionTests {

        @Test
        @DisplayName("generate non-null collections with non-empty strings")
        void shouldWrapStrings() {
            // Arrange
            var localGenerator = new CollectionGenerator<>(Generators.nonEmptyStrings());

            // Act & Assert
            var list = localGenerator.list();
            assertNotNull(list, "Generated list should not be null");
            assertFalse(list.isEmpty(), "Generated list should not be empty");

            var set = localGenerator.set();
            assertNotNull(set, "Generated set should not be null");
            assertFalse(set.isEmpty(), "Generated set should not be empty");
        }

        @Test
        @DisplayName("throw exception for null generator")
        void shouldHandleNullGenerator() {
            // Act & Assert
            assertThrows(NullPointerException.class, () -> new CollectionGenerator<>(null),
                    "Should throw NPE for null generator");
        }
    }
}
