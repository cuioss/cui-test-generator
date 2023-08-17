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
package de.cuioss.test.generator.internal.net.java.quickcheck.characteristic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.cuioss.test.generator.internal.net.java.quickcheck.Characteristic;
import de.cuioss.test.generator.internal.net.java.quickcheck.collection.Pair;

/**
 * Gather frequency information about test values.
 * <p>
 * {@link Classification}s are used to categories test cases. While
 * {@link Characteristic#specify(Object)} is executed characteristic instances
 * can add classifications with
 * {@link Classification#doClassify(boolean, Object)}. For each execution of
 * {@link Characteristic#specify(Object)} {@link Classification#call()} has to
 * be executed once.
 * </p>
 */
public class Classification {

    private final Map<Object, Integer> classifications = new HashMap<>();
    private int total;
    private boolean reportState;

    // derived value
    private HashMap<Object, Double> classifiedWithPercents;
    // derived value
    private ArrayList<Object> sortedCategories;

    /**
     * Get the frequency of the given category.
     *
     * @return per cent of test cases with this classification.
     */
    public double getFrequency(Object classification) {
        reportState();
        Double frequency = getClassified().get(classification);
        return frequency == null ? 0 : frequency;
    }

    /**
     * Get a list of known categories. Categories are sorted in descending
     * frequency.
     */
    public List<Object> getCategories() {
        reportState();
        if (sortedCategories == null) {
            ArrayList<Pair<Object, Double>> toSort = buildCategoryFrequencyList();
            sortByFrequency(toSort);
            sortedCategories = transFormPairListToCategoryList(toSort);
        }
        return sortedCategories;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Map<Object, Double> classified = getClassified();
        builder.append("Classifications :");
        List<Object> categories = getCategories();
        for (Object key : categories) {
            builder.append("%n%s = %1.2f%%".formatted(key, classified.get(key)));
        }
        if (classified.isEmpty()) {
            builder.append("none");
        }
        return builder.toString();
    }

    public void doClassify(Object classification) {
        doClassify(true, classification);
    }

    /**
     * Increment the classification counter for the given classification.
     *
     * @param predicate      increment only if the predicate is true.
     * @param classification classification key
     */
    public void doClassify(boolean predicate, Object classification) {
        checkReportState();
        if (!predicate)
            return;
        boolean categoryFound = classifications.containsKey(classification);
        int current = categoryFound ? classifications.get(classification) : 0;
        classifications.put(classification, ++current);
    }

    /**
     * Count the number of calls.
     */
    public void call() {
        checkReportState();
        total++;
    }

    private Map<Object, Double> getClassified() {
        if (classifiedWithPercents == null) {
            classifiedWithPercents = new HashMap<>();
            for (Entry<Object, Integer> entry : classifications.entrySet()) {
                Object key = entry.getKey();
                Integer count = entry.getValue();
                double percent = (double) count * 100 / total;
                classifiedWithPercents.put(key, percent);
            }
        }
        return classifiedWithPercents;
    }

    private void reportState() {
        reportState = true;
    }

    private ArrayList<Pair<Object, Double>> buildCategoryFrequencyList() {
        Map<Object, Double> classified = getClassified();
        ArrayList<Pair<Object, Double>> toSort = new ArrayList<>();
        for (Entry<Object, Double> e : classified.entrySet()) {
            toSort.add(new Pair<>(e.getKey(), e.getValue()));
        }
        return toSort;
    }

    private ArrayList<Object> transFormPairListToCategoryList(ArrayList<Pair<Object, Double>> toSort) {
        ArrayList<Object> sortedCategoryList = new ArrayList<>();
        for (Pair<Object, Double> pair : toSort) {
            sortedCategoryList.add(pair.getFirst());
        }
        return sortedCategoryList;
    }

    private void sortByFrequency(ArrayList<Pair<Object, Double>> toSort) {
        toSort.sort((o1, o2) -> o2.getSecond().compareTo(o1.getSecond()));
    }

    // this is the easiest implementation report calculation can be resource
    // demanding
    private void checkReportState() {
        if (reportState)
            throw new IllegalStateException("do not call after report was started.");
    }

    public void classifyCall(boolean predicate, Object classification) {
        doClassify(predicate, classification);
        call();
    }

    public void classifyCall(Object classification) {
        classifyCall(true, classification);
    }
}
