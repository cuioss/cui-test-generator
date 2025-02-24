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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.internal.net.java.quickcheck.GeneratorException;

class AbstractTreeGeneratorTest {

    private int id;

    @Test
    void flatGenerationBaseOnLevel() {
        testFlatGenerationBaseOnLevel(0);
        testFlatGenerationBaseOnLevel(10);
    }

    @Test
    void flatGenerationBaseOnSiblings() {
        var root = new TreeGenerator() {

            @Override
            protected int getChildCount(int level, int numberOfSiblings) {
                assertEquals(1, numberOfSiblings);
                return numberOfSiblings / 2;
            }
        }.next();
        assertFlatGeneration(0, root);
    }

    @Test
    void treeGenerationBaseOnLevel() {

        var next = new TreeGenerator() {

            @Override
            protected int getChildCount(int level, int numberOfSiblings) {
                return switch (level) {
                    case 0 -> 2;
                    case 1 -> 1;
                    default -> 0;
                };
            }
        }.next();

        assert1210Node(next);
    }

    @Test
    void generationAbortsAfterMaxDepthReached() {

        try {
            new TreeGenerator() {

                @Override
                protected int getChildCount(int level, int numberOfSiblings) {
                    return 1;
                }
            }.next();
            fail();
        } catch (GeneratorException e) {
            assertTrue(true);
        }
    }

    @Test
    void treeGenerationBaseSiblings() {

        var next = new TreeGenerator() {

            @Override
            protected int getChildCount(int level, int numberOfSiblings) {
                if (level == 0) {
                    return 2;
                }
                return numberOfSiblings / 2;
            }
        }.next();

        assert1210Node(next);
    }

    private void assert1210Node(Node next) {
        List<Node> childrenOfRoot = new ArrayList<>();
        id = 1;
        var expected = create1210Node(childrenOfRoot);
        assertEquals(expected, next);
    }

    private Node create1210Node(List<Node> childrenOfRoot) {
        var expected = new Node(id++);
        create10Node(childrenOfRoot);
        create10Node(childrenOfRoot);
        expected.setChildren(childrenOfRoot);
        return expected;
    }

    private void create10Node(List<Node> childrenOfRoot) {
        childrenOfRoot.add(new Node(id++, List.of(new Node(id++, Collections.emptyList()))));
    }

    private void testFlatGenerationBaseOnLevel(final int numberOfChildren) {
        var root = new TreeGenerator() {

            @Override
            protected int getChildCount(int level, int numberOfSiblings) {
                return level == 0 ? numberOfChildren : 0;
            }
        }.next();
        assertFlatGeneration(numberOfChildren, root);
    }

    private void assertFlatGeneration(final int numberOfChildren, Node root) {
        assertNotNull(root);
        assertEquals(numberOfChildren, root.getChildren().size());
    }

    public abstract static class TreeGenerator extends AbstractTreeGenerator<Node> {

        private int content = 0;

        @Override
        protected Node createNode() {
            return new Node(++content);
        }

        @Override
        protected void addChildren(Node node, List<Node> children) {
            node.setChildren(children);
        }

    }

    public static class Node {

        private List<Node> children;
        private int content;

        public Node(int content, List<Node> children) {
            setChildren(children);
        }

        public Node(int content) {
        }

        void setChildren(List<Node> children) {
            this.children = children;
        }

        public List<Node> getChildren() {
            return children;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Node)) {
                return false;
            }
            var node = (Node) obj;
            return node.children.equals(children) && node.content == content;
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(new Object[]{children.hashCode(), content});
        }

        @Override
        public String toString() {
            return "Node[size=%d,children=%s]".formatted(children.size(), children);
        }
    }
}
