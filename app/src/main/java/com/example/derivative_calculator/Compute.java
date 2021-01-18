package com.example.derivative_calculator;

public class Compute {

    /**
     * Complies all java classes into one class that can be called by the main method at once.
     * Takes in a string and a a variable and outputs the derivative of the string with respect to the variable as another string.
     * @param input Input string to differentiate
     * @param diffVar Variable to differentiate with respect to
     * @return Derivative of input string
     * @throws InvalidInputException
     */
    public static String getAnswer(String input, String diffVar) throws InvalidInputException {
            BinaryTreeNode tree = Parser.parse(input);
            tree = Simplify.simplify(tree);
            tree = Derivative.derive(tree, diffVar);
            tree = Simplify.simplify(tree);
            System.out.println(tree);
            return TreeToString.convert(tree);
    }

}
