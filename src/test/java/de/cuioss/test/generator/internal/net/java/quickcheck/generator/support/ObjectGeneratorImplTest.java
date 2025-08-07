/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.test.generator.internal.net.java.quickcheck.generator.support;

import de.cuioss.test.generator.internal.net.java.quickcheck.Generator;
import de.cuioss.test.generator.internal.net.java.quickcheck.MockFactory;
import de.cuioss.test.generator.internal.net.java.quickcheck.ObjectGenerator;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators;
import org.junit.jupiter.api.Test;

import static de.cuioss.test.generator.internal.net.java.quickcheck.generator.PrimitiveGenerators.integers;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.jupiter.api.Assertions.*;

class ObjectGeneratorImplTest {

    @Test
    void objects() {
        ObjectGenerator<Simple> generator = PrimitiveGenerators.objects(Simple.class);
        var expected = defineGeneratorValueForGetValue(generator);
        assertEquals(expected, generator.next().getValue());
    }

    private Integer defineGeneratorValueForGetValue(ObjectGenerator<? extends Simple> generator) {
        var expected = integers().next();
        Simple recorder = generator.getRecorder();
        generator.on(recorder.getValue()).returns(integerGenerator(expected));
        return expected;
    }

    @Test
    void compoundObjects() {
        ObjectGenerator<Simple> simpleGenerator = PrimitiveGenerators.objects(Simple.class);
        simpleGenerator.on(simpleGenerator.getRecorder().getValue()).returns(integers());
        ObjectGenerator<Compound> compoundGenerator = PrimitiveGenerators.objects(Compound.class);
        compoundGenerator.on(compoundGenerator.getRecorder().simple()).returns(simpleGenerator);

        var simple = compoundGenerator.next().simple();
        var value = simple.getValue();
        assertNotNull(value);
    }

    @Test
    void defaultObjects() {
        Generator<AllSupportedPrimitives> objects = PrimitiveGenerators.defaultObjects(AllSupportedPrimitives.class);
        var next = objects.next();
        assertInstanceOf(Byte.class, next.aByte());
        assertInstanceOf(Double.class, next.aDouble());
        assertInstanceOf(Integer.class, next.anInt());
        assertInstanceOf(Long.class, next.aLong());
        assertNotNull(next.aByteWrapper());
        assertNotNull(next.aDoubleWrapper());
        assertNotNull(next.anIntWrapper());
        assertNotNull(next.aLongWrapper());
        assertNotNull(next.aString());
        assertNotNull(next.aNumber());
    }

    @Test
    void defaultCompoundObjects() {
        Generator<Compound> defaultObjects = PrimitiveGenerators.defaultObjects(Compound.class);
        assertNotNull(defaultObjects.next().simple().getValue());
    }

    @Test
    void defaultObjectsMixedMode() {
        ObjectGenerator<Mixed> defaultObjects = PrimitiveGenerators.defaultObjects(Mixed.class);
        var expectedValue = defineGeneratorValueForGetValue(defaultObjects);
        var next = defaultObjects.next();
        assertNotNull(next.simple().getValue());
        assertEquals(expectedValue, next.getValue());
    }

    @Test
    void defaultCompoundsObjectsIgnoresClasses() {
        Generator<CompoundWithInvalidClassMember> defaultObjects = PrimitiveGenerators
                .defaultObjects(CompoundWithInvalidClassMember.class);
        assertThrows(IllegalStateException.class, defaultObjects::next);
    }

    @Test
    void objectsReturnTypesAreCovariant() {
        ObjectGenerator<Covariant> generator = PrimitiveGenerators.objects(Covariant.class);
        var expected = integers().next();
        var recorder = generator.getRecorder();
        generator.on(recorder.aNumber()).returns(integerGenerator(expected));
        assertEquals(expected, generator.next().aNumber());
    }

    @Test
    void objectsDefinitionMissing() {
        ObjectGenerator<Simple> generator = PrimitiveGenerators.objects(Simple.class);
        assertThrows(IllegalStateException.class, generator::next);
    }

    @Test
    void objectsClassNotSupported() {
        assertThrows(IllegalArgumentException.class, () -> PrimitiveGenerators.objects(Object.class));
    }

    @Test
    void objectsClassNull() {
        assertThrows(NullPointerException.class, () -> PrimitiveGenerators.objects(null));
    }

    @Test
    @SuppressWarnings("java:S5778")
    void objectsGenerateCallWithoutGenerator() {
        Simple notARecorder = new Simple() {

            @Override
            public Integer getValue() {
                return null;
            }

            @Override
            public void setValue(Integer v) {
            }
        };
        ObjectGenerator<Simple> generator = PrimitiveGenerators.objects(Simple.class);
        assertThrows(IllegalStateException.class, () -> generator.on(notARecorder.getValue()));
    }

    private Generator<Integer> integerGenerator(Integer expected) {
        var generator = MockFactory.createIntegerGenerator();
        expect(generator.next()).andReturn(expected);
        replay(generator);
        return generator;
    }

    private interface Simple {

        Integer getValue();

        void setValue(Integer v);
    }

    private interface Compound {

        Simple simple();

        void setSimple(Simple s);
    }

    private interface Mixed extends Simple, Compound {
    }

    private interface Covariant {

        Number aNumber();
    }

    private interface CompoundWithInvalidClassMember {

        Object classesNotValid();
    }

    private interface AllSupportedPrimitives {

        int anInt();

        long aLong();

        byte aByte();

        double aDouble();

        Integer anIntWrapper();

        Long aLongWrapper();

        Byte aByteWrapper();

        Double aDoubleWrapper();

        Number aNumber();

        String aString();
    }

}
