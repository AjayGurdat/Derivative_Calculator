package com.example.derivative_calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComputeTest {

    @Test
    void testBasicInput() throws InvalidInputException{

        String actualOutput = Compute.getAnswer("5", "x");

        assertEquals("0", actualOutput);
    }

    @Test
    void testVariableInput() throws InvalidInputException{

        String actualOutput = Compute.getAnswer("x", "x");

        assertEquals("1", actualOutput);
    }

    @Test
    void testVariableInputWithNumbers() throws InvalidInputException {

        String actualOutput = Compute.getAnswer("5x", "x");

        assertEquals("5", actualOutput);
    }

    @Test
    void testExponent() throws InvalidInputException {

        String actualOutput = Compute.getAnswer("2^3", "x");

        assertEquals("0", actualOutput);
    }

    @Test
    void testVariableInputWithExponent2() throws InvalidInputException{

        String actualOutput = Compute.getAnswer("5x^2", "x");

        assertEquals("10x", actualOutput);
    }

    @Test
    void testVariableInputWithExponentGreaterThan2() throws InvalidInputException {

        String actualOutput = Compute.getAnswer("5x^4", "x");

        assertEquals("20x^3", actualOutput);
    }

    @Test
    void testDivision() throws InvalidInputException {

        String actualOutput = Compute.getAnswer("x / 2", "x");

        assertEquals("0.5", actualOutput);
    }

    @Test
    void testVariableInputAdvanced() throws InvalidInputException {

        String actualOutput = Compute.getAnswer("2x^3 + 3x^2 - 6x - 9", "x");

        assertEquals("6x^2 + 6x - 6", actualOutput);
    }

    @Test
    void testNonDiffVariable() throws InvalidInputException {

        String actualOutput = Compute.getAnswer("y^3", "x");

        assertEquals("0", actualOutput);
    }

    @Test
    void testNonDiffVariableAdvanced() throws InvalidInputException {

        String actualOutput = Compute.getAnswer("3x^2 + y^3 - y", "x");

        assertEquals("6x", actualOutput);
    }

    @Test
    void testBrackets() throws InvalidInputException {

        String actualOutput = Compute.getAnswer("(x+3)/4", "x");

        assertEquals("0.25", actualOutput);
    }

    @Test
    void testLayeredBrackets() throws InvalidInputException {

        String actualOutput = Compute.getAnswer("(((x+3)))", "x");

        assertEquals("1", actualOutput);
    }

    @Test
    void testLayeredBracketsAdvancedPositive() throws InvalidInputException {

        String actualOutput = Compute.getAnswer("((x + 3) * (2 + x)) / 1", "x");

        assertEquals("2x + 5", actualOutput);
    }

    @Test
    void testLayeredBracketsNegative() throws InvalidInputException {

        String actualOutput = Compute.getAnswer("((x + 3) * (2 - x)) / 1", "x");

        assertEquals("-2x - 1", actualOutput);
    }

    @Test
    void testExponentOutside() throws InvalidInputException {

        String actualOutput = Compute.getAnswer("(3x)^4", "x");  // 4/x + (2x)^2

        assertEquals("324x^3", actualOutput);
    }
}