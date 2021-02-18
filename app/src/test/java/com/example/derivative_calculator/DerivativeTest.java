package com.example.derivative_calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DerivativeTest {

    @Test
    void testSumRule() throws InvalidInputException {
        BinaryTreeNode actualTree = Parser.parse("x + 4");
        actualTree = Derivative.derive(actualTree, "x");

        BinaryTreeNode expectedTree = new BinaryTreeNode("+");
        BinaryTreeNode leftChild = new BinaryTreeNode("1");
        BinaryTreeNode rightChild = new BinaryTreeNode("0");
        expectedTree.setLeftChild(leftChild);
        expectedTree.setRightChild(rightChild);

        assertEquals(expectedTree, actualTree, "Trees are supposed to be equal");
    }

    @Test
    void testDifferenceRule() throws InvalidInputException {
        BinaryTreeNode actualTree = Parser.parse("x - 6");
        actualTree = Derivative.derive(actualTree, "x");

        BinaryTreeNode expectedTree = new BinaryTreeNode("-");
        BinaryTreeNode leftChild = new BinaryTreeNode("1");
        BinaryTreeNode rightChild = new BinaryTreeNode("0");
        expectedTree.setLeftChild(leftChild);
        expectedTree.setRightChild(rightChild);

        assertEquals(expectedTree, actualTree, "Trees are supposed to be equal");
    }

    @Test
    void testPowerRuleNoCoefficient() throws InvalidInputException {
        BinaryTreeNode actualTree = Parser.parse("x^3");
        actualTree = Derivative.derive(actualTree, "x");

        BinaryTreeNode expectedTree = new BinaryTreeNode("*");
        BinaryTreeNode leftChild = new BinaryTreeNode("3");
        BinaryTreeNode rightChild = new BinaryTreeNode("^", new BinaryTreeNode("x"), new BinaryTreeNode("2"));
        expectedTree.setLeftChild(leftChild);
        expectedTree.setRightChild(rightChild);


        assertEquals(expectedTree, actualTree, "Trees are supposed to be equal");
    }

    @Test
    void testPowerRuleWithCoefficient() throws InvalidInputException {
        BinaryTreeNode actualTree = Parser.parse("2x^4");
        actualTree = Derivative.derive(actualTree, "x");

        BinaryTreeNode expectedTree = new BinaryTreeNode("*");
        BinaryTreeNode leftChild = new BinaryTreeNode("*", new BinaryTreeNode("4"), new BinaryTreeNode("2"));
        BinaryTreeNode rightChild = new BinaryTreeNode("^", new BinaryTreeNode("x"), new BinaryTreeNode("3"));
        expectedTree.setLeftChild(leftChild);
        expectedTree.setRightChild(rightChild);


        assertEquals(expectedTree, actualTree, "Trees are supposed to be equal");
    }

    @Test
    void testProductRule() throws InvalidInputException {
        BinaryTreeNode actualTree = Parser.parse("5x");
        actualTree = Derivative.derive(actualTree, "x");

        BinaryTreeNode expectedTree = new BinaryTreeNode("+");
        BinaryTreeNode leftChild = new BinaryTreeNode("*", new BinaryTreeNode("0"), new BinaryTreeNode("x"));
        BinaryTreeNode rightChild = new BinaryTreeNode("*", new BinaryTreeNode("5"), new BinaryTreeNode("1"));

        expectedTree.setLeftChild(leftChild);
        expectedTree.setRightChild(rightChild);

        assertEquals(expectedTree, actualTree, "Trees are supposed to be equal");
    }

    @Test
    void testQuotientRule() throws InvalidInputException {
        BinaryTreeNode actualTree = Parser.parse("5 / x");
        actualTree = Derivative.derive(actualTree, "x");

        BinaryTreeNode expectedTree = new BinaryTreeNode("/");
        BinaryTreeNode rightChild = new BinaryTreeNode("^", new BinaryTreeNode("x"), new BinaryTreeNode("2"));
        BinaryTreeNode leftChild = new BinaryTreeNode("-", new BinaryTreeNode("*"), new BinaryTreeNode("*"));

        leftChild.getLeftChild().setLeftChild(new BinaryTreeNode("0"));
        leftChild.getLeftChild().setRightChild(new BinaryTreeNode("x"));

        leftChild.getRightChild().setLeftChild(new BinaryTreeNode("5"));
        leftChild.getRightChild().setRightChild(new BinaryTreeNode("1"));

        expectedTree.setLeftChild(leftChild);
        expectedTree.setRightChild(rightChild);

        assertEquals(expectedTree, actualTree, "Trees are supposed to be equal");
    }

    @Test
    void testPowerOfOneRule() throws InvalidInputException {
        BinaryTreeNode actualTree = Parser.parse("x");
        actualTree = Derivative.derive(actualTree, "x");

        BinaryTreeNode expectedTree = new BinaryTreeNode("1");

        assertEquals(expectedTree, actualTree, "Trees are supposed to be equal");
    }

    @Test
    void testConstantRuleWithNumber() throws InvalidInputException {
        BinaryTreeNode actualTree = Parser.parse("6");
        actualTree = Derivative.derive(actualTree, "x");

        BinaryTreeNode expectedTree = new BinaryTreeNode("0");

        assertEquals(expectedTree, actualTree, "Trees are supposed to be equal");
    }

    @Test
    void testConstantRuleWithVariable() throws InvalidInputException {
        BinaryTreeNode actualTree = Parser.parse("y");
        actualTree = Derivative.derive(actualTree, "x");

        BinaryTreeNode expectedTree = new BinaryTreeNode("0");

        assertEquals(expectedTree, actualTree, "Trees are supposed to be equal");
    }
}