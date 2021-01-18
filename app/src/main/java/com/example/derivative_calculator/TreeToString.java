package com.example.derivative_calculator;

public class TreeToString {

    /**
     * Converts a binary tree into a readable string, uses a helper method do to the bulk of the work
     * This method concat parts of the string when helpful such as 4 * x ^ 2 --> 4x^2 for better readability
     * @param tree Input tree to convert
     * @return String representation of the tree
     */
    public static String convert(BinaryTreeNode tree) {

        String result = convertHelper(tree).trim();
        String newResult = "";

        for (int i = 0; i < result.length(); i++) {
            switch (result.charAt(i)) {
                case '*':
                    if (Character.isDigit(result.charAt(i-2)) && Character.isLetter(result.charAt(i+2))) {
                        newResult = newResult.substring(0, newResult.length() - 1);
                        newResult += result.charAt(i+2);
                        i += 2;
                    } else {
                        newResult += result.charAt(i);
                    }
                    break;
                case '^':
                    if (Character.isLetter(result.charAt(i-2)) && Character.isDigit(result.charAt(i+2))) {
                        newResult = newResult.substring(0, newResult.length() - 1);
                        newResult += "^" + result.charAt(i+2);
                        i += 2;
                    } else {
                        newResult += result.charAt(i);
                    }
                    break;
                default:
                    newResult += result.charAt(i);
            }
        }
        return newResult;
    }

    /**
     * Recursively goes to each node by putting the current node value in the middle of the string, the left child of the left of the string, and the right child of the right of the string
     * Only adds brackets when order of operations are not followed in the tree structure
     * @param tree
     * @return
     */
    private static String convertHelper(BinaryTreeNode tree) {

        BinaryTreeNode leftChild = tree.getLeftChild();
        BinaryTreeNode rightChild = tree.getRightChild();
        String element = tree.getElement();
        String middleString = "", leftString = "", rightString = "";

        if (leftChild != null) {
            String leftChildElement = leftChild.getElement();
            if (Utilities.isSymbol(leftChildElement) && Utilities.getBedmasOrder(leftChildElement) < Utilities.getBedmasOrder(element)) {
                leftString += "(";
                leftString += convertHelper(leftChild);
                leftString = leftString.trim();
                leftString += ") ";
                leftString.trim();
            } else {
                leftString += convertHelper(leftChild);
            }
        }

        middleString = element + " ";

        if (rightChild != null) {
            String rightChildElement = rightChild.getElement();
            if (Utilities.isSymbol(rightChildElement) && Utilities.getBedmasOrder(rightChildElement) < Utilities.getBedmasOrder(element)) {
                rightString += "(";
                rightString += convertHelper(rightChild);
                rightString = rightString.trim();
                rightString += ") ";
                rightString.trim();
            } else {
                rightString += convertHelper(rightChild);
            }
        }

        return leftString + middleString + rightString;
    }
}
