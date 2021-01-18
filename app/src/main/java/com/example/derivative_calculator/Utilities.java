package com.example.derivative_calculator;

public class Utilities {
    public static boolean isNumeric(String string) {
        for (char c : string.toCharArray()) {
            if (!(Character.isDigit(c) || c == '.' || (c == '-' && string.length() != 1))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNumeric(char character) {
        return Character.isDigit(character);
    }

    public static boolean isSymbol (String string) {
        if (string != null) {
            String[] symbols = {"^", "/", "*", "+", "-"};
            for (int i = 0; i < symbols.length; i++) {
                if (string.equals(symbols[i])) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isSymbol (char character) {
        return isSymbol(character + "");
    }

    public static boolean isBracket(char character) {
        return character == '(' || character == ')';
    }

    public static boolean isLetter (String str) {
        if (str.length() == 1) {
            return Character.isLetter(str.charAt(0));
        }
        return false;
    }

    public static boolean isLetter (char character) {
        return isLetter(character + "");
    }

    public static BinaryTreeNode replaceTree (String key, BinaryTreeNode tree, BinaryTreeNode newTree) {
        if (key.equals(tree.getElement())) {
            return newTree;
        } else if (tree.getLeftChild() != null && tree.getRightChild() != null) {
            tree.setLeftChild(replaceTree(key, tree.getLeftChild(), newTree));
            tree.setRightChild(replaceTree(key, tree.getRightChild(), newTree));
        }
        return tree;
    }

    public static int getBedmasOrder(String symbol) {
        if (isNumeric(symbol)) {
            return -1;
        } else {
            int value = 0;
            switch (symbol) {
                case "*":
                case "/":
                    value = 1;
                    break;
                case "^":
                    value = 2;
                    break;
            }
            return value;
        }
    }

    public static boolean containsTree(String element, BinaryTreeNode tree) {
        if (tree == null) {
            return false;
        } else {
            if (element.equals(tree.getElement())) {
                return true;
            } else {
                return containsTree(element, tree.getLeftChild()) || containsTree(element, tree.getRightChild());
            }
        }
    }
}
