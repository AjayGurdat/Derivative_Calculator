package com.example.derivative_calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void testBasicInput() throws InvalidInputException {
        BinaryTreeNode actualTree = Parser.parse("5");

        BinaryTreeNode expectedTree = new BinaryTreeNode("5");

        assertEquals(expectedTree, actualTree);
    }

    @Test
    void testSymbol() throws InvalidInputException {
        BinaryTreeNode actualTree = Parser.parse("5x + 42");

        BinaryTreeNode expectedTree = new BinaryTreeNode("+");
        BinaryTreeNode leftChild = new BinaryTreeNode("*", new BinaryTreeNode("5"), new BinaryTreeNode("x"));
        expectedTree.setLeftChild(leftChild);
        expectedTree.setRightChild(new BinaryTreeNode("42"));

        assertEquals(expectedTree, actualTree);
    }

    @Test
    void testBracketsFront() throws InvalidInputException {
        BinaryTreeNode actualTree = Parser.parse("(2 * x) + 3 / y");

        BinaryTreeNode expectedTree = new BinaryTreeNode("+");
        BinaryTreeNode leftChild = new BinaryTreeNode("*", new BinaryTreeNode("2"), new BinaryTreeNode("x"));
        BinaryTreeNode rightChild = new BinaryTreeNode("/", new BinaryTreeNode("3"), new BinaryTreeNode("y"));
        expectedTree.setLeftChild(leftChild);
        expectedTree.setRightChild(rightChild);

        assertEquals(expectedTree, actualTree);
    }

    @Test
    void testBracketsEnd() throws InvalidInputException {
        BinaryTreeNode actualTree = Parser.parse("2 * x + (3 / y)");

        BinaryTreeNode expectedTree = new BinaryTreeNode("+");
        BinaryTreeNode leftChild = new BinaryTreeNode("*", new BinaryTreeNode("2"), new BinaryTreeNode("x"));
        BinaryTreeNode rightChild = new BinaryTreeNode("/", new BinaryTreeNode("3"), new BinaryTreeNode("y"));
        expectedTree.setLeftChild(leftChild);
        expectedTree.setRightChild(rightChild);

        assertEquals(expectedTree, actualTree);
    }

    @Test
    void testBracketsMiddleNoBedmas() throws InvalidInputException {
        BinaryTreeNode actualTree = Parser.parse("2 * (x + 3) / y");

        BinaryTreeNode expectedTree = new BinaryTreeNode("/");
        BinaryTreeNode leftChild = new BinaryTreeNode("*");
        BinaryTreeNode rightChild = new BinaryTreeNode("y");
        expectedTree.setLeftChild(leftChild);
        expectedTree.setRightChild(rightChild);

        leftChild.setLeftChild(new BinaryTreeNode("2"));
        leftChild.setRightChild(new BinaryTreeNode("+", new BinaryTreeNode("x"), new BinaryTreeNode("3")));

        assertEquals(expectedTree, actualTree);
    }

    @Test
    void testBracketsMiddleWithBedmasRight() throws InvalidInputException {
        BinaryTreeNode actualTree = Parser.parse("2 * (x + 3) - y");

        BinaryTreeNode expectedTree = new BinaryTreeNode("-");
        BinaryTreeNode leftChild = new BinaryTreeNode("*");
        BinaryTreeNode rightChild = new BinaryTreeNode("y");
        expectedTree.setLeftChild(leftChild);
        expectedTree.setRightChild(rightChild);

        leftChild.setLeftChild(new BinaryTreeNode("2"));
        leftChild.setRightChild(new BinaryTreeNode("+", new BinaryTreeNode("x"), new BinaryTreeNode("3")));

        assertEquals(expectedTree, actualTree);
    }

    @Test
    void testBracketsMiddleWithBedmasLeft() throws InvalidInputException {
        BinaryTreeNode actualTree = Parser.parse("2 + (x + 3) / y");

        BinaryTreeNode expectedTree = new BinaryTreeNode("+");
        BinaryTreeNode leftChild = new BinaryTreeNode("2");
        BinaryTreeNode rightChild = new BinaryTreeNode("/");
        expectedTree.setLeftChild(leftChild);
        expectedTree.setRightChild(rightChild);

        rightChild.setLeftChild(new BinaryTreeNode("+", new BinaryTreeNode("x"), new BinaryTreeNode("3")));
        rightChild.setRightChild(new BinaryTreeNode("y"));

        assertEquals(expectedTree, actualTree);
    }
}