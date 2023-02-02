/*
 * Licensed to the author under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.cui.test.generator.internal.net.java.quickcheck.generator.support;

import static org.easymock.EasyMock.createMockBuilder;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.easymock.IMockBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import io.cui.test.generator.internal.net.java.quickcheck.Generator;
import io.cui.test.generator.internal.net.java.quickcheck.MockFactory;

@Disabled
class AbstractTransformerGeneratorTest {

    private Generator<Object> inputGenerator;
    private AbstractTransformerGenerator<Object, Object> transformerGenerator;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @BeforeEach
    void setUp() throws NoSuchMethodException, SecurityException {
        inputGenerator = MockFactory.createObjectGenerator();
        IMockBuilder<AbstractTransformerGenerator> mockBuilder =
            createMockBuilder(AbstractTransformerGenerator.class);
        mockBuilder.addMockedMethod(
                AbstractTransformerGenerator.class.getDeclaredMethod("transform", Generator.class));
        transformerGenerator = mockBuilder.createMock();
    }

    @Test
    void testBuildTransformer() {
        transformerGenerator.setInputGenerator(inputGenerator);

        Object returnedObject = new Object();
        expect(transformerGenerator.transform(inputGenerator))
                .andReturn(returnedObject);
        replay(transformerGenerator);
        assertSame(returnedObject, transformerGenerator.next());
        verify(transformerGenerator);
    }
}
