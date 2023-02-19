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
package io.cui.test.generator.internal.net.java.quickcheck.generator.distribution;

import static java.lang.Math.abs;

/**
 * <a
 * href="http://en.wikipedia.org/wiki/Image:Normal_distribution_pdf.png">Normal
 * distribution</a> and <a
 * href="http://en.wikipedia.org/wiki/Image:Uniform_distribution_PDF.png">uniform
 * distribution</a> distribution functions.
 *
 * @author $Id$
 */
public interface Distribution {

    /**
     * Right side of the bell curve. Values range from 0.0 to 1.0. Values near
     * 0.0 are the most probable.
     */
    Distribution POSITIV_NORMAL = new AbstractDistribution() {

        @Override
        public double nextRandomNumber() {
            return abs(nextGausian());
        }
    };

    /**
     * Left side of the bell curve. Values range from 0.0 to 1.0. Values near
     * 1.0 are the most probable.
     */
    Distribution NEGATIV_NORMAL = new AbstractDistribution() {

        @Override
        public double nextRandomNumber() {
            return abs(-1 + abs(nextGausian()));
        }
    };

    /**
     * An inverted bell curve. Values range from 0.0 to 1.0. Values near 0.0 and
     * 1.0 are the most probable.
     */
    Distribution INVERTED_NORMAL = new AbstractDistribution() {

        @Override
        public double nextRandomNumber() {
            double next = nextGausian(N_SIGMA * 2);
            return (next < 0) ? 1 + next : next;
        }
    };

    /**
     * A uniform distribution function. Values range from 0.0 to 1.0.
     */
    Distribution UNIFORM = new AbstractDistribution() {

        @Override
        public double nextRandomNumber() {
            return RandomConfiguration.random.nextDouble();
        }
    };

    /**
     * Generate the next random number for this distribution function.
     *
     * @return double 0 &lt;= x &lt;= 1.0
     */
    double nextRandomNumber();

    abstract class AbstractDistribution implements Distribution {

        static final int N_SIGMA = 3;

        double nextGausian() {
            return nextGausian(N_SIGMA);
        }

        double nextGausian(int sigma) {
            // n * sigma range normalized to 1.0
            return (RandomConfiguration.random.nextGaussian() % sigma) / sigma;
        }
    }
}
