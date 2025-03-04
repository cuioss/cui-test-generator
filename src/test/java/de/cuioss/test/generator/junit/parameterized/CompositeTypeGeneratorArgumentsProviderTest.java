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
package de.cuioss.test.generator.junit.parameterized;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.platform.commons.JUnitException;

import java.util.List;
import java.util.stream.Collectors;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CompositeTypeGeneratorArgumentsProviderTest {

    private CompositeTypeGeneratorArgumentsProvider provider;
    private ExtensionContext context;
    private CompositeTypeGeneratorSource annotation;

    @BeforeEach
    void setUp() {
        provider = new CompositeTypeGeneratorArgumentsProvider();
        context = createMock(ExtensionContext.class);
        annotation = createMock(CompositeTypeGeneratorSource.class);
    }

    @Test
    void shouldAcceptAnnotation() {
        // given
        @SuppressWarnings("unchecked")
        Class<? extends TypedGenerator<?>>[] generatorClasses = new Class[] { StringGenerator.class };
        String[] generatorMethods = new String[] { "createIntegerGenerator" };
        
        expect(annotation.generatorClasses()).andReturn(generatorClasses).anyTimes();
        expect(annotation.generatorMethods()).andReturn(generatorMethods).anyTimes();
        expect(annotation.count()).andReturn(5).anyTimes();
        expect(annotation.cartesianProduct()).andReturn(true).anyTimes();
        expect(annotation.seed()).andReturn(123L).anyTimes();
        replay(annotation);

        // when
        provider.accept(annotation);

        // then
        assertEquals(5, provider.getCount());
        assertEquals(123L, provider.getSeed());
        verify(annotation);
    }

    @Test
    void shouldUseDefaultValuesWhenNotSpecified() {
        // given
        @SuppressWarnings("unchecked")
        Class<? extends TypedGenerator<?>>[] generatorClasses = new Class[] { StringGenerator.class };
        String[] generatorMethods = new String[0];
        
        expect(annotation.generatorClasses()).andReturn(generatorClasses).anyTimes();
        expect(annotation.generatorMethods()).andReturn(generatorMethods).anyTimes();
        expect(annotation.count()).andReturn(0).anyTimes(); // Should use default of 1
        expect(annotation.cartesianProduct()).andReturn(false).anyTimes();
        expect(annotation.seed()).andReturn(0L).anyTimes(); // Should use system time
        replay(annotation);

        // when
        provider.accept(annotation);

        // then
        assertEquals(1, provider.getCount()); // Default is 1
        assertTrue(provider.getSeed() > 0); // Should be set to system time
        verify(annotation);
    }

    @Test
    void shouldThrowExceptionWhenNoGeneratorsSpecified() {
        // given
        @SuppressWarnings("unchecked")
        Class<? extends TypedGenerator<?>>[] generatorClasses = new Class[0];
        String[] generatorMethods = new String[0];
        
        expect(annotation.generatorClasses()).andReturn(generatorClasses).anyTimes();
        expect(annotation.generatorMethods()).andReturn(generatorMethods).anyTimes();
        expect(annotation.count()).andReturn(1).anyTimes();
        expect(annotation.cartesianProduct()).andReturn(false).anyTimes();
        expect(annotation.seed()).andReturn(0L).anyTimes();
        replay(annotation);
        provider.accept(annotation);

        replay(context);

        // when/then
        assertThrows(JUnitException.class, () -> provider.provideArguments(context));
        verify(context);
    }

    @Test
    void shouldProvideArgumentsFromGeneratorClasses() throws Exception {
        // given
        @SuppressWarnings("unchecked")
        Class<? extends TypedGenerator<?>>[] generatorClasses = new Class[] { StringGenerator.class };
        String[] generatorMethods = new String[0];
        
        expect(annotation.generatorClasses()).andReturn(generatorClasses).anyTimes();
        expect(annotation.generatorMethods()).andReturn(generatorMethods).anyTimes();
        expect(annotation.count()).andReturn(3).anyTimes();
        expect(annotation.cartesianProduct()).andReturn(false).anyTimes();
        expect(annotation.seed()).andReturn(42L).anyTimes();
        replay(annotation);
        provider.accept(annotation);

        replay(context);

        // when
        List<Arguments> arguments = provider.provideArguments(context)
                .collect(Collectors.toList());

        // then
        assertEquals(3, arguments.size());
        for (Arguments args : arguments) {
            assertEquals(1, args.get().length);
            assertTrue(args.get()[0] instanceof String);
        }
        verify(context);
    }

    @Test
    void shouldProvideArgumentsFromGeneratorMethods() throws Exception {
        // given
        @SuppressWarnings("unchecked")
        Class<? extends TypedGenerator<?>>[] generatorClasses = new Class[0];
        String[] generatorMethods = new String[] { 
            TestFactoryClass.class.getName() + "#createGenerator" 
        };
        
        expect(annotation.generatorClasses()).andReturn(generatorClasses).anyTimes();
        expect(annotation.generatorMethods()).andReturn(generatorMethods).anyTimes();
        expect(annotation.count()).andReturn(2).anyTimes();
        expect(annotation.cartesianProduct()).andReturn(false).anyTimes();
        expect(annotation.seed()).andReturn(0L).anyTimes();
        replay(annotation);
        provider.accept(annotation);

        replay(context);

        // when
        List<Arguments> arguments = provider.provideArguments(context)
                .collect(Collectors.toList());

        // then
        assertEquals(2, arguments.size());
        for (Arguments args : arguments) {
            assertEquals(1, args.get().length);
            assertTrue(args.get()[0] instanceof String);
        }
        verify(context);
    }

    @Test
    void shouldProvideArgumentsFromMultipleSources() throws Exception {
        // given
        @SuppressWarnings("unchecked")
        Class<? extends TypedGenerator<?>>[] generatorClasses = new Class[] { StringGenerator.class };
        String[] generatorMethods = new String[] { 
            TestFactoryClass.class.getName() + "#createIntegerGenerator" 
        };
        
        expect(annotation.generatorClasses()).andReturn(generatorClasses).anyTimes();
        expect(annotation.generatorMethods()).andReturn(generatorMethods).anyTimes();
        expect(annotation.count()).andReturn(2).anyTimes();
        expect(annotation.cartesianProduct()).andReturn(true).anyTimes();
        expect(annotation.seed()).andReturn(0L).anyTimes();
        replay(annotation);
        provider.accept(annotation);

        replay(context);

        // when
        List<Arguments> arguments = provider.provideArguments(context)
                .collect(Collectors.toList());

        // then
        assertEquals(4, arguments.size()); // 2x2 cartesian product
        for (Arguments args : arguments) {
            assertEquals(2, args.get().length);
            assertTrue(args.get()[0] instanceof String);
            assertTrue(args.get()[1] instanceof Integer);
        }
        verify(context);
    }

    @Test
    void shouldProvideOneToOnePairsWhenCartesianProductIsFalse() throws Exception {
        // given
        @SuppressWarnings("unchecked")
        Class<? extends TypedGenerator<?>>[] generatorClasses = new Class[] { StringGenerator.class };
        String[] generatorMethods = new String[] { 
            TestFactoryClass.class.getName() + "#createIntegerGenerator" 
        };
        
        expect(annotation.generatorClasses()).andReturn(generatorClasses).anyTimes();
        expect(annotation.generatorMethods()).andReturn(generatorMethods).anyTimes();
        expect(annotation.count()).andReturn(3).anyTimes();
        expect(annotation.cartesianProduct()).andReturn(false).anyTimes();
        expect(annotation.seed()).andReturn(0L).anyTimes();
        replay(annotation);
        provider.accept(annotation);

        replay(context);

        // when
        List<Arguments> arguments = provider.provideArguments(context)
                .collect(Collectors.toList());

        // then
        assertEquals(3, arguments.size()); // 3 pairs (not cartesian product)
        for (Arguments args : arguments) {
            assertEquals(2, args.get().length);
            assertTrue(args.get()[0] instanceof String);
            assertTrue(args.get()[1] instanceof Integer);
        }
        verify(context);
    }

    /**
     * Test generator class that implements TypedGenerator.
     */
    public static class StringGenerator implements TypedGenerator<String> {
        @Override
        public String next() {
            return "test-string";
        }

        @Override
        public Class<String> getType() {
            return String.class;
        }
    }

    /**
     * Test factory class with methods that return TypedGenerator instances.
     * Some methods are intentionally unused directly but are required for testing
     * method resolution logic.
     */
    @SuppressWarnings("unused")
    static class TestFactoryClass {
        
        /**
         * Creates a string generator for testing.
         * @return a TypedGenerator that generates strings
         */
        public static TypedGenerator<String> createGenerator() {
            return Generators.strings(5, 10);
        }
        
        /**
         * Creates an integer generator for testing.
         * @return a TypedGenerator that generates integers
         */
        public static TypedGenerator<Integer> createIntegerGenerator() {
            return Generators.integers(1, 100);
        }
    }
}
