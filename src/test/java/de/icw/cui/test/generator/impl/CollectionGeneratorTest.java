package de.icw.cui.test.generator.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.junit.jupiter.api.Test;

import de.icw.cui.test.generator.Generators;
import io.cui.tools.property.PropertyUtil;

@SuppressWarnings({ "unchecked", "rawtypes" })
class CollectionGeneratorTest {

    private final CollectionGenerator<String> generator = new CollectionGenerator<>(Generators.letterStrings());

    private final ComplexBean complexBean = new ComplexBean();

    @Test
    void shouldIntersectList() {
        final Class<Iterable<?>> expectedType = (Class<Iterable<?>>) PropertyUtil
                .resolvePropertyType(complexBean.getClass(), ComplexBean.ATTRIBUTE_STRING_LIST).get();
        final Class<? extends Iterable> collectionClass = generator.nextCollection(expectedType).getClass();
        assertTrue(List.class.isAssignableFrom(collectionClass));
        assertFalse(Set.class.isAssignableFrom(collectionClass));
    }

    @Test
    void shouldIntersectCollection() {
        final Class<Iterable<?>> expectedType = (Class<Iterable<?>>) PropertyUtil
                .resolvePropertyType(complexBean.getClass(), ComplexBean.ATTRIBUTE_STRING_COLLECTION).get();
        final Class<? extends Iterable> collectionClass = generator.nextCollection(expectedType).getClass();
        assertTrue(Collection.class.isAssignableFrom(collectionClass));
        assertFalse(Set.class.isAssignableFrom(collectionClass));
    }

    @Test
    void shouldIntersectSet() {
        final Class<Iterable<?>> expectedType = (Class<Iterable<?>>) PropertyUtil
                .resolvePropertyType(complexBean.getClass(), ComplexBean.ATTRIBUTE_STRING_SET).get();
        final Class<? extends Iterable> collectionClass = generator.nextCollection(expectedType).getClass();
        assertFalse(List.class.isAssignableFrom(collectionClass));
        assertTrue(Set.class.isAssignableFrom(collectionClass));
    }

    @Test
    void shouldIntersectSortedSet() {
        final Class<Iterable<?>> expectedType = (Class<Iterable<?>>) PropertyUtil
                .resolvePropertyType(complexBean.getClass(), ComplexBean.ATTRIBUTE_STRING_SORTED_SET).get();
        final Class<? extends Iterable> collectionClass = generator.nextCollection(expectedType).getClass();
        assertFalse(List.class.isAssignableFrom(collectionClass));
        assertTrue(SortedSet.class.isAssignableFrom(collectionClass));
    }

    @Test
    void shouldWrapStrings() {
        CollectionGenerator<String> generator = new CollectionGenerator<>(Generators.nonEmptyStrings());
        assertNotNull(generator.list());
        assertNotNull(generator.set());
    }

}
