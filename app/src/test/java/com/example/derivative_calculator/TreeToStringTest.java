package com.example.derivative_calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TreeToStringTest {

    @Test
    void testBasicAddition() throws InvalidInputException {

        BinaryTreeNode actualTree = Parser.parse("5 + 2");
        String actualTreeString = TreeToString.convert(actualTree);

        assertEquals("5 + 2", actualTreeString);
    }

    @Test
    void testBasicSubtraction() throws InvalidInputException {

        BinaryTreeNode actualTree = Parser.parse("0 - 3");
        String actualTreeString = TreeToString.convert(actualTree);

        assertEquals("0 - 3", actualTreeString);
    }

    @Test
    void testBasicMultiplication() throws InvalidInputException {

        BinaryTreeNode actualTree = Parser.parse("12 * 4");
        String actualTreeString = TreeToString.convert(actualTree);

        assertEquals("12 * 4", actualTreeString);
    }

    @Test
    void testBasicDivision() throws InvalidInputException {

        BinaryTreeNode actualTree = Parser.parse("40 / 10");
        String actualTreeString = TreeToString.convert(actualTree);

        assertEquals("40 / 10", actualTreeString);
    }

    @Test
    void testAdvancedArithmetic() throws InvalidInputException {

        BinaryTreeNode actualTree = Parser.parse("3 + 4 + 2 - 1 * 42 + 234 - 0 / 2");
        String actualTreeString = TreeToString.convert(actualTree);

        assertEquals("3 + 4 + 2 - 1 * 42 + 234 - 0 / 2", actualTreeString);
    }

    @Test
    void testVariablesMultiplication() throws InvalidInputException {

        BinaryTreeNode actualTree = Parser.parse("37y");
        String actualTreeString = TreeToString.convert(actualTree);

        assertEquals("37y", actualTreeString);
    }

    @Test
    void testVariablesExponentWithoutMulti() throws InvalidInputException {

        BinaryTreeNode actualTree = Parser.parse("x^2");
        String actualTreeString = TreeToString.convert(actualTree);

        assertEquals("x^2", actualTreeString);
    }

    @Test
    void testVariablesExponentWithMulti() throws InvalidInputException {

        BinaryTreeNode actualTree = Parser.parse("2x^2");
        String actualTreeString = TreeToString.convert(actualTree);

        assertEquals("2x^2", actualTreeString);
    }

    @Test
    void testVariablesAdvanced() throws InvalidInputException {

        BinaryTreeNode actualTree = Parser.parse("2x^2 + 37y + z");
        String actualTreeString = TreeToString.convert(actualTree);

        assertEquals("2x^2 + 37y + z", actualTreeString);
    }
}