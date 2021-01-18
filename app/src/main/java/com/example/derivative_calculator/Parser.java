package com.example.derivative_calculator;

public class Parser {

    /**
     * Parses an input string into a Binary Tree structure for easier differentiating
     * @param input Input string to parse
     * @return A Binary Tree structure made using the given String
     */
    public static BinaryTreeNode parse(String input) throws InvalidInputException {
        return parseHelper(parseStringFixer(input));
    }

    /**
     * Recursively parses the input into each branch while following BEDMAS
     * @param input Input string to parse
     * @return A Binary Tree structure made using the given String
     */
    private static BinaryTreeNode parseHelper (String input) throws InvalidInputException {
        BinaryTreeNode tree = new BinaryTreeNode("");

        int firstBracket = input.indexOf('(');
        int lastBracket = -1;
        int bracketCounter = 0;

        for (int i = 0; i < input.length(); i++) {
            char token = input.charAt(i);
            if (token == '(') {
                bracketCounter ++;
            } else if (token == ')') {
                bracketCounter--;
                if (bracketCounter == 0 && lastBracket == -1) {
                    lastBracket = i;
                }
                if (bracketCounter < 0) {
                    throw new InvalidInputException("Unexpected \")\"");
                }
            } else if (Utilities.isSymbol(token)) {
                if (token == 0 || token == input.length() - 1) {
                    throw new InvalidInputException("Syntax error 1");
                } else if (!(Utilities.isNumeric(input.charAt(i - 2)) || Utilities.isNumeric(input.charAt(i + 2))
                        || Utilities.isLetter(input.charAt(i - 2)) || Utilities.isLetter(input.charAt(i + 2))
                        || Utilities.isBracket(input.charAt(i - 2)) || Utilities.isBracket(input.charAt(i + 2)))) {
                    throw new InvalidInputException("Unexpected token: " + input.charAt(i + 2) + " and/or " + input.charAt(i - 2));
                }
            }
        }
        if (bracketCounter != 0) {
            throw new InvalidInputException("The number of left and right brackets are not equal");
        }

        if (firstBracket == -1) {
            // If there are no brackets

            // Case when token is + or -
            for (int i = input.length() - 1; i >= 0; i--) {
                char token = input.charAt(i);
                if (token == '+' || token == '-') {
                    tree.setElement(token + "");
                    tree.setLeftChild(parseHelper(input.substring(0, i - 1)));
                    tree.setRightChild(parseHelper(input.substring(i + 2)));
                    return tree;
                }
            }

            // Case where token is * or /
            for (int i = input.length() - 1; i >= 0; i--) {
                char token = input.charAt(i);
                if (token == '*' || token == '/') {
                    tree.setElement(token + "");
                    tree.setLeftChild(parseHelper(input.substring(0, i - 1)));
                    tree.setRightChild(parseHelper(input.substring(i + 2)));
                    return tree;
                }
            }

            // Case where token is ^
            for (int i = input.length() - 1; i >= 0; i--) {
                char token = input.charAt(i);
                if (token == '^') {
                    tree.setElement(token + "");
                    tree.setLeftChild(parseHelper(input.substring(0, i - 1)));
                    tree.setRightChild(parseHelper(input.substring(i + 2)));
                    return tree;
                }
            }

        } else {
            BinaryTreeNode bracketTree = parseHelper(input.substring(firstBracket + 2, lastBracket - 1)); // Remove extra space on each side
            String treeHashCode = "$" + bracketTree.hashCode();

            if (firstBracket == 0 && lastBracket == input.length() - 1) {
                // ( ( x + y ) )  --->  ( x + y )  --->  x + y
                return bracketTree;

            } else if (firstBracket == 0) {
                // ( a + b ) + c + d
                tree = parseHelper(treeHashCode + " " + input.substring(lastBracket + 2));
                return Utilities.replaceTree(treeHashCode, tree, bracketTree);

            } else if (lastBracket == input.length() - 1) {
                // a + b + ( c + d )
                tree = parseHelper(input.substring(0, firstBracket - 1) + " " + treeHashCode);
                return Utilities.replaceTree(treeHashCode, tree, bracketTree);

            } else if (Utilities.isSymbol(input.charAt(firstBracket - 2)) && Utilities.isSymbol(input.charAt(lastBracket + 2))) {
                // a + ( b + c ) + d
                tree = parseHelper(input.substring(0, firstBracket - 1) + " "  + treeHashCode + " " + input.substring(lastBracket + 2));
                return Utilities.replaceTree(treeHashCode, tree, bracketTree);

            } else {
                throw new InvalidInputException("Syntax error 3");
            }
        }

        // Tree is a single element
        tree.setElement(input);
        return tree;
    }

    /**
     * This method changes the input into a more readable string to parse into a binary tree object
     * ie: 5x^2 + (4x) + 3  --->  5 * x ^ 2 + ( 4 * x ) + 3
     * @param input String to fix
     * @return An easier to parse string
     */
    private static String parseStringFixer(String input) throws InvalidInputException {
        String fixedInput = "";
        input = input.replace(" ", "");
        for (int i = 0; i < input.length(); i++) {
            char token = input.charAt(i);
            if (i != 0 && Utilities.isSymbol(token)) {
                // (n+x)  --->  ( n + x )
                fixedInput += " " + token + " ";
            } else if (token == '(') {
                fixedInput += token + " ";
            } else if (token == ')') {
                fixedInput += " " + token;
            } else if (i != 0 && Utilities.isLetter(token) && Utilities.isNumeric(input.charAt(i-1))) {
                // xn  --->  x * n
                fixedInput += " * " + token;
            } else if (token == '$') {
                throw new InvalidInputException("Invalid Input - \"" + "$" + "\"");
            } else {
                fixedInput += token;
            }
        }
        return fixedInput;
    }
}
