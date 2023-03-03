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

    private static final TypedGenerator<String> generator =
        Generators.fixedValues(String.class, "https://www.heise.de", "https://stackoverflow.com",
                "https://github.com/cuioss", "https://www.gitlab.com", "https://sonarcloud.io",
                "https://www.eclipse.org", "https://www.sesamestreet.org/", "https://de.wikipedia.org",
                "https://www.xing.com", "https://www.mozilla.org", "https://avm.de/", "https://www.primefaces.org/",
                "http://getbootstrap.com", "https://x-tention.com", "https://www.microsoft.com",
                "https://www.apple.com", "https://www.android.com");

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
