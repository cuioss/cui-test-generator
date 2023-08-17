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

import java.net.MalformedURLException;
import java.net.URL;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;

/**
 * {@link TypedGenerator} for generating basic {@link URL}s
 *
 * @author Oliver Wolff
 */
public class URLGenerator implements TypedGenerator<URL> {

    private static final TypedGenerator<String> generator = Generators.fixedValues(String.class, "https://www.heise.de",
            "https://stackoverflow.com", "https://github.com/cuioss", "https://www.gitlab.com", "https://sonarcloud.io",
            "https://www.eclipse.org", "https://www.sesamestreet.org/", "https://de.wikipedia.org",
            "https://www.xing.com", "https://www.mozilla.org", "https://avm.de/", "https://www.primefaces.org/",
            "http://getbootstrap.com", "https://x-tention.com", "https://www.microsoft.com", "https://www.apple.com",
            "https://www.android.com");

    @Override
    public URL next() {
        try {
            return new URL(generator.next());
        } catch (final MalformedURLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Class<URL> getType() {
        return URL.class;
    }

}
