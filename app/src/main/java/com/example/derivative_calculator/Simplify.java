package com.example.derivative_calculator;

public class Simplify {

    /**
     * Attempts to simplify the given binary tree as much as possible
     * @param tree Input tree to simplify
     * @return Simplified version of the input binary tree
     */
    public static BinaryTreeNode simplify(BinaryTreeNode tree) {
        return collectLikeTerms(simplifyHelper(tree));
    }

    /**
     * Does the bulk of the work for simplifying.
     * This method computes the answer of a tree if their children are numbers, else recursively call of its children.
     * Made as a separate method to not call "collectLikeTerms" method more than necessary.
     * @param tree Input tree to simplify
     * @return Simplified version of the input binary tree
     * @throws ArithmeticException
     */
    private static BinaryTreeNode simplifyHelper (BinaryTreeNode tree) throws ArithmeticException {

        String element = tree.getElement();
        BinaryTreeNode leftChild = tree.getLeftChild();
        BinaryTreeNode rightChild = tree.getRightChild();

        if (leftChild != null && rightChild != null) {
            //If tree is not a single node

            String leftChildElement = leftChild.getElement();
            String rightChildElement = rightChild.getElement();

            if (Utilities.isNumeric(leftChildElement) && Utilities.isNumeric(rightChildElement)) {
                // If both children are numbers, compute the answer

                double leftChildNum = Double.parseDouble(leftChildElement);
                double rightChildNum = Double.parseDouble(rightChildElement);

                switch (element) {
                    case "+":
                        return new BinaryTreeNode(leftChildNum + rightChildNum + "");
                    case "-":
                        return new BinaryTreeNode(leftChildNum - rightChildNum + "");
                    case "*":
                        return new BinaryTreeNode(leftChildNum * rightChildNum + "");
                    case "/":
                        if (rightChildNum == 0) {
                            throw new ArithmeticException("Error - Can't divide by 0");
                        } else {
                            return new BinaryTreeNode(leftChildNum / rightChildNum + "");
                        }
                    case "^":
                        return new BinaryTreeNode(Math.pow(leftChildNum, rightChildNum) + "");
                }

            }

            if (Utilities.isSymbol(leftChildElement)) {
                // Simplify left child further if possible

                BinaryTreeNode newLeftChild = simplify(leftChild);
                if (!leftChild.equals(newLeftChild)) {
                    // Only continue to recurse if the tree changed
                    tree.setLeftChild(newLeftChild);
                    return simplify(tree);
                }

            }
            if (Utilities.isSymbol(rightChildElement)) {
                // Simplify right child further if possible

                BinaryTreeNode newRightChild = simplify(rightChild);
                if (!rightChild.equals(newRightChild)) {
                    // Only continue to recurse if the tree changed
                    tree.setRightChild(newRightChild);
                    return simplify(tree);
                }
            }

            if (Utilities.isLetter(leftChildElement) || Utilities.isLetter(rightChildElement) || Utilities.isSymbol(leftChildElement) || Utilities.isSymbol(rightChildElement)) {
                // If left child or right child is a variable OR a symbol
                if (Utilities.isLetter(leftChildElement) && leftChildElement.equals(rightChildElement)) {
                    // If left child and right child are the same variable
                    switch (element) {
                        case "*":
                            return new BinaryTreeNode("^", new BinaryTreeNode("2"), new BinaryTreeNode("x"));
                        case "/":
                            return new BinaryTreeNode("1");
                        case "+":
                            return new BinaryTreeNode("*", new BinaryTreeNode("2"), new BinaryTreeNode("x"));
                        case "-":
                            return new BinaryTreeNode("0");
                    }
                } else {
                    // Base cases if left or right child are 0 or 1

                    switch (element) {
                        case "+":
                            if (leftChildElement.equals("0")) {
                                return rightChild;
                            } else if (rightChildElement.equals("0")) {
                                return leftChild;
                            }
                            break;
                        case "-":
                            if (rightChildElement.equals("0")) {
                                return leftChild;
                            }
                            // Else if leftChild is 0
                            // Results in a negative number
                            break;
                        case "*":
                            if (leftChildElement.equals("0") || rightChildElement.equals("0")) {
                                return new BinaryTreeNode("0");
                            } else if (leftChildElement.equals("1")) {
                                return rightChild;
                            } else if (rightChildElement.equals("1")) {
                                return leftChild;
                            }
                            break;
                        case "/":
                            if (leftChildElement.equals("0")) {
                                return new BinaryTreeNode("0");
                            } else if (rightChildElement.equals("0")) {
                                throw new ArithmeticException("Error - Can't divide by 0");
                            } else if (rightChildElement.equals("1")) {
                                return leftChild;
                            }
                            break;
                        case "^":
                            if (leftChildElement.equals("0")) {
                                return new BinaryTreeNode("0");
                            } else if (rightChildElement.equals("0")) {
                                return new BinaryTreeNode("1");
                            } else if (leftChildElement.equals("1")) {
                                return new BinaryTreeNode("1");
                            } else if (rightChildElement.equals("1")) {
                                return leftChild;
                            }
                            break;
                    }
                }
            }
        }

        // Return back tree if unable to further simplify
        return tree;
    }

    /**
     * Further simplifies the binary tree where inputs such as 2 * (4x) to 8x.
     * It does this by looking at the outer edge of the tree where BEDMAS is maintained computing the answer for numbers.
     * @param tree Input tree to simplify
     * @return Simplified version of the input binary tree
     */
    private static BinaryTreeNode collectLikeTerms (BinaryTreeNode tree) {
        String element = tree.getElement();

        if (Utilities.isSymbol(element)) {
            String leftChildElement = tree.getLeftChild().getElement();
            String rightChildElement = tree.getRightChild().getElement();

            if (!element.equals(leftChildElement) && Utilities.isSymbol(leftChildElement)) {
                BinaryTreeNode newChild = collectLikeTerms(tree.getLeftChild());
                tree.setLeftChild(newChild);
            }

            if (!element.equals(rightChildElement) && Utilities.isSymbol(rightChildElement)) {
                BinaryTreeNode newChild = collectLikeTerms(tree.getRightChild());
                tree.setRightChild(newChild);
            }

            Double leftValue = 0.0, rightValue = 0.0, newValue;
            BinaryTreeNode nodeToChange = tree;
            if (element.equals(leftChildElement) && Utilities.isNumeric(rightChildElement)) {
                rightValue = Double.parseDouble(rightChildElement);
                tree = tree.getLeftChild(); // Shrinks the tree to simplify it
                leftChildElement = tree.getLeftChild().getElement();
                rightChildElement = tree.getRightChild().getElement();
                if (Utilities.isNumeric(leftChildElement)) {
                    leftValue = Double.parseDouble(leftChildElement);
                    tree.setRightChild(collectLikeTerms(tree.getRightChild()));
                    nodeToChange = tree.getLeftChild();
                } else if (Utilities.isNumeric(rightChildElement)) {
                    leftValue = Double.parseDouble(rightChildElement);
                    tree.setLeftChild(collectLikeTerms(tree.getLeftChild()));
                    nodeToChange = tree.getRightChild();
                }
                newValue = collectLikeTermsHelper(element, leftValue, rightValue);
                nodeToChange.setElement(newValue + "");

            } else if (element.equals(rightChildElement) && Utilities.isNumeric(leftChildElement)) {
                leftValue = Double.parseDouble(leftChildElement);
                tree = tree.getRightChild(); // Shrinks the tree to simplify it
                leftChildElement = tree.getLeftChild().getElement();
                rightChildElement = tree.getRightChild().getElement();
                if (Utilities.isNumeric(leftChildElement)) {
                    rightValue = Double.parseDouble(leftChildElement);
                    tree.setRightChild(collectLikeTerms(tree.getRightChild()));
                    nodeToChange = tree.getLeftChild();
                } else if (Utilities.isNumeric(rightChildElement)) {
                    rightValue = Double.parseDouble(rightChildElement);
                    tree.setLeftChild(collectLikeTerms(tree.getLeftChild()));
                    nodeToChange = tree.getRightChild();
                }
                newValue = collectLikeTermsHelper(element, leftValue, rightValue);
                nodeToChange.setElement(newValue + "");
            } else if (element.equals(leftChildElement) && element.equals(rightChildElement)) {

            }
        }
        return tree;
    }

    /**
     * Does the arithmetic on the input numbers to be used to replace the value of a select node
     * @param element Symbol of the arithmetic
     * @param leftValue Left variable
     * @param rightValue Right variable
     * @return The number after the calculations
     */
    private static double collectLikeTermsHelper (String element, Double leftValue, Double rightValue) {
        Double newValue = 0.0;
        switch (element) {
            case "+":
                newValue = leftValue + rightValue;
                break;
            case "-":
                newValue = leftValue - rightValue;
                break;
            case "*":
                newValue = leftValue * rightValue;
                break;
            case "/":
                newValue = leftValue / rightValue;
                break;
        }
        return newValue;
    }
}
