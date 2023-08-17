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
package de.cuioss.test.generator.internal.net.java.quickcheck.generator.support;

import java.util.ArrayList;
import java.util.List;

import de.cuioss.test.generator.internal.net.java.quickcheck.Generator;
import de.cuioss.test.generator.internal.net.java.quickcheck.GeneratorException;

/**
 * Base class for tree generators.
 *
 * <p>
 * The callback order is:
 * </p>
 * <ul>
 * <li>{@link AbstractTreeGenerator#createNode()}: create current node</li>
 * <li>{@link AbstractTreeGenerator#getChildCount(int, int)}: calculate number
 * of child nodes</li>
 * <li>(create child nodes)</li>
 * <li>{@link AbstractTreeGenerator#addChildren(Object, List)}: add child nodes
 * to node</li>
 * </ul>
 *
 * @param <T> type of tree node
 */
public abstract class AbstractTreeGenerator<T> implements Generator<T> {

    public static final int MAX_TREE_DEPTH = 50;

    /**
     * Create a node of type T.
     */
    protected abstract T createNode();

    /**
     * Add the created children to the parent node.
     */
    protected abstract void addChildren(T node, List<T> children);

    /**
     * Get the number of children for the current level.
     *
     * @param level            current level starting with 0 for the root level
     * @param numberOfSiblings number of siblings (number of siblings is 1 for the
     *                         root node.)
     */
    protected abstract int getChildCount(int level, int numberOfSiblings);

    /**
     * @return root node of the generated tree
     */
    @Override
    public T next() {
        return createNodeAndAddChildren(-1, 1);
    }

    private List<T> down(int level, int numberOfSiblings) {
        checkDepth(level);
        List<T> result = new ArrayList<>();
        int childCount = getChildCount(level, numberOfSiblings);
        for (int i = 0; i < childCount; i++) {
            T node = createNodeAndAddChildren(level, childCount);
            result.add(node);
        }
        return result;
    }

    private void checkDepth(int level) {
        if (level > MAX_TREE_DEPTH) {
            throw new GeneratorException("Max tree depth (%s) exceeded.".formatted(MAX_TREE_DEPTH), this);
        }
    }

    private T createNodeAndAddChildren(int level, int numberOfSiblings) {
        T node = createNode();
        addChildren(node, down(level + 1, numberOfSiblings));
        return node;
    }

}
