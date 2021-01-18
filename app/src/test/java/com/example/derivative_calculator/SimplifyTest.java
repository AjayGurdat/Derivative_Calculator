package com.example.derivative_calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SimplifyTest {

    @Test
    void testAddition() throws InvalidInputException {
        BinaryTreeNode actualTree = Parser.parse("5 + 4");
        actualTree = Simplify.simplify(actualTree);

        BinaryTreeNode expectedTree = new BinaryTreeNode("9");

        assertEquals(expectedTree, actualTree, "Trees are supposed to be equal");
    }

    @Test
    void testSubtraction() throws InvalidInputException {
        BinaryTreeNode actualTree = Parser.parse("10 - 2");
        actualTree = Simplify.simplify(actualTree);

        BinaryTreeNode expectedTree = new BinaryTreeNode("8");

        assertEquals(expectedTree, actualTree, "Trees are supposed to be equal");
    }

    @Test
    void testMultiplication() throws InvalidInputException {
        BinaryTreeNode actualTree = Parser.parse("6 * 3");
        actualTree = Simplify.simplify(actualTree);

        BinaryTreeNode expectedTree = new BinaryTreeNode("18");

        assertEquals(expectedTree, actualTree, "Trees are supposed to be equal");
    }

    @Test
    void testDivision() throws InvalidInputException {
        BinaryTreeNode actualTree = Parser.parse("12 / 4");
        actualTree = Simplify.simplify(actualTree);

        BinaryTreeNode expectedTree = new BinaryTreeNode("3");

        assertEquals(expectedTree, actualTree, "Trees are supposed to be equal");
    }

    @Test
    void testDivisionByZero() throws InvalidInputException {
        BinaryTreeNode actualTree = Parser.parse("5 / 0");
        assertThrows(ArithmeticException.class,
                () -> Simplify.simplify(actualTree));
    }

    @Test
    void testDivisionSingleNumber() throws InvalidInputException {
        BinaryTreeNode actualTree = Parser.parse("4");
        actualTree = Simplify.simplify(actualTree);

        BinaryTreeNode expectedTree = new BinaryTreeNode("4");

        assertEquals(expectedTree, actualTree, "Trees are supposed to be equal");
    }

    @Test
    void testDivisionSingleVariable() throws InvalidInputException {
        BinaryTreeNode actualTree = Parser.parse("x");
        actualTree = Simplify.simplify(actualTree);

        BinaryTreeNode expectedTree = new BinaryTreeNode("x");

        assertEquals(expectedTree, actualTree, "Trees are supposed to be equal");
    }

    @Test
    void testAdvancedArithmetic() throws InvalidInputException {
        BinaryTreeNode actualTree = Parser.parse("10 - 4 / 2 * 12");

        actualTree = Simplify.simplify(actualTree);

        BinaryTreeNode expectedTree = new BinaryTreeNode("-14");

        assertEquals(expectedTree, actualTree, "Trees are supposed to be equal");
    }

    @Test
    void testAdvancedArithmeticBrackets() throws InvalidInputException {
        BinaryTreeNode actualTree = Parser.parse("(10 - 4) / 2 * 12");

        actualTree = Simplify.simplify(actualTree);

        BinaryTreeNode expectedTree = new BinaryTreeNode("36");

        assertEquals(expectedTree, actualTree, "Trees are supposed to be equal");
    }

    @Test
    void testAdvancedArithmeticDoubleBrackets() throws InvalidInputException {
        BinaryTreeNode actualTree = Parser.parse("((2 + 3) * (2 - 5)) / 1");

        actualTree = Simplify.simplify(actualTree);

        BinaryTreeNode expectedTree = new BinaryTreeNode("-15");

        assertEquals(expectedTree, actualTree, "Trees are supposed to be equal");
    }

    @Test
    void testAdvancedArithmeticDoubleBracketsWithVariables() throws InvalidInputException {
        BinaryTreeNode actualTree = Parser.parse("((x + 3) * (2 - x)) / 1");

        actualTree = Simplify.simplify(actualTree);

        BinaryTreeNode expectedTree = new BinaryTreeNode("*");
        BinaryTreeNode leftTree = new BinaryTreeNode("+", new BinaryTreeNode("x"), new BinaryTreeNode("3"));
        BinaryTreeNode rightTree = new BinaryTreeNode("-", new BinaryTreeNode("2"), new BinaryTreeNode("x"));
        expectedTree.setLeftChild(leftTree);
        expectedTree.setRightChild(rightTree);

        assertEquals(expectedTree, actualTree, "Trees are supposed to be equal");
    }

    @Test
    void testAdvancedArithmeticManyBrackets() throws InvalidInputException {
        BinaryTreeNode actualTree = Parser.parse("((10 - (4) / 2)) * 12");

        actualTree = Simplify.simplify(actualTree);

        BinaryTreeNode expectedTree = new BinaryTreeNode("96");

        assertEquals(expectedTree, actualTree, "Trees are supposed to be equal");
    }

    @Test
    void testNegative() throws InvalidInputException {
        BinaryTreeNode actualTree = Parser.parse("0 - 10 + 5");

        actualTree = Simplify.simplify(actualTree);

        BinaryTreeNode expectedTree = new BinaryTreeNode("-5");

        assertEquals(expectedTree, actualTree, "Trees are supposed to be equal");
    }

}