package com.hyfata.najoan.koreanpatch.util;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TreeSearchTest {

    private static final class Node {
        private final List<Node> children = new ArrayList<>();
        private final boolean textWidget;

        private Node(boolean textWidget) {
            this.textWidget = textWidget;
        }

        static Node leaf() {
            return new Node(false);
        }

        static Node text() {
            return new Node(true);
        }

        static Node container(Node... kids) {
            Node node = new Node(false);
            for (Node kid : kids) {
                node.children.add(kid);
            }
            return node;
        }

        List<Node> children() {
            return children;
        }
    }

    private static boolean hasText(Node root) {
        return TreeSearch.containsBfs(root, node -> node.textWidget, Node::children);
    }

    @Test
    void directChildDetected() {
        Node root = Node.container(Node.leaf(), Node.text());

        assertTrue(hasText(root));
    }

    @Test
    void deeplyNestedWidgetDetected() {
        Node root = Node.container(
                Node.leaf(),
                Node.container(Node.container(Node.text())));

        assertTrue(hasText(root));
    }

    @Test
    void nonTextTreeReturnsFalse() {
        Node root = Node.container(Node.leaf(), Node.container(Node.leaf()));

        assertFalse(hasText(root));
    }

    @Test
    void cyclicTreeTerminates() {
        Node root = Node.container(Node.leaf());
        root.children().add(root);

        assertFalse(hasText(root));
    }

    @Test
    void nullInputsReturnFalse() {
        assertFalse(TreeSearch.containsBfs(null, node -> true, Node::children));
        assertFalse(TreeSearch.containsBfs(Node.leaf(), null, Node::children));
        assertFalse(TreeSearch.containsBfs(Node.leaf(), node -> false, null));
    }
}
