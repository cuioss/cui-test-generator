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

import java.time.LocalDateTime;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;

/**
 * Provide any valid value for {@linkplain LocalDateTime}
 *
 * @author Eugen Fischer
 */
public class LocalDateTimeGenerator implements TypedGenerator<LocalDateTime> {

    @Override
    public LocalDateTime next() {
        return LocalDateTime.of(Generators.localDates().next(), Generators.localTimes().next());
    }

    @Override
    public Class<LocalDateTime> getType() {
        return LocalDateTime.class;
    }

}
