package com.example.derivative_calculator;

public class Derivative {

    /**
     * Computes the derivative of the given binary tree
     * @param tree Binary tree to differentiate
     * @param diffVar Variable to differentiate with respect to
     * @return The differentiated binary tree
     * @throws InvalidInputException
     */
    public static BinaryTreeNode derive(BinaryTreeNode tree, String diffVar) throws InvalidInputException {

        String element = tree.getElement();
        BinaryTreeNode leftChild = tree.getLeftChild();
        BinaryTreeNode rightChild = tree.getRightChild();

        if (!Utilities.isLetter(diffVar)) {
            throw new InvalidInputException("Differentiable variable must be a single letter");
        }

        BinaryTreeNode tempTree;
        BinaryTreeNode tempRightTree;
        BinaryTreeNode tempLeftTree;
        switch (element) {
            case "+":
            case "-":
                //Sum and Difference Rule: f ± g --> f’ ± g’
                return new BinaryTreeNode(element, derive(leftChild, diffVar), derive(rightChild, diffVar));
            case "*":
                // Product Rule: fg	--> fg’ + f’g
                tempTree = new BinaryTreeNode("+");
                tempTree.setLeftChild(new BinaryTreeNode("*", derive(leftChild, diffVar), rightChild));
                tempTree.setRightChild(new BinaryTreeNode("*", leftChild, derive(rightChild, diffVar)));
                return tempTree;
            case "/":
                //Quotient Rule: f/g --> (f’g − fg’)/g^2
                tempTree = new BinaryTreeNode("/");
                tempLeftTree = new BinaryTreeNode("-");
                tempRightTree = new BinaryTreeNode("^");

                tempLeftTree.setLeftChild(new BinaryTreeNode("*", derive(leftChild, diffVar), rightChild));
                tempLeftTree.setRightChild(new BinaryTreeNode("*", leftChild, derive(rightChild, diffVar)));

                tempRightTree.setLeftChild(rightChild);
                tempRightTree.setRightChild(new BinaryTreeNode("2"));

                tempTree.setLeftChild(tempLeftTree);
                tempTree.setRightChild(tempRightTree);

                return tempTree;
            case "^":
                if (!Utilities.isSymbol(leftChild.getElement())) {
                    //Power Rule: x^n --> nx^(n-1)
                    return deriveExponent(tree, diffVar);
                } else {
                    // Chain Rule: f(g(x)) --> f’(g(x)) * g’(x)

                    Double exponent = Double.parseDouble(rightChild.getElement());
                    Double newExponent = exponent - 1;

                    tempTree = new BinaryTreeNode("*");
                    tempLeftTree = new BinaryTreeNode("*", new BinaryTreeNode(exponent + ""),
                            new BinaryTreeNode("^", leftChild, new BinaryTreeNode(newExponent + "")));
                    tempRightTree = derive(leftChild, diffVar);

                    tempTree.setLeftChild(tempLeftTree);
                    tempTree.setRightChild(tempRightTree);

                    return tempTree;
                }
        }

        if (element.equals(diffVar)) {
            //Power Rule with power of 1 (x by itself): x --> 1
            return new BinaryTreeNode("1");
        } else if (Utilities.isNumeric(element)) {
            //Constant rule: c --> 0
            return new BinaryTreeNode("0");
        } else if (Utilities.isLetter(element)) {
            //Constant rule for variable: y --> 0
            return new BinaryTreeNode("0");
        }

        throw new InvalidInputException("Invalid Input - \"" + element + "\"");
    }

    /**
     * Differentiates a tree where the top node is '^' (Exponent rule)
     * @param tree Binary tree to differentiate
     * @param diffVar Variable to differentiate with respect to
     * @return The differentiated binary tree
     * @throws InvalidInputException
     */
    private static BinaryTreeNode deriveExponent(BinaryTreeNode tree, String diffVar) throws InvalidInputException {
        if (Utilities.containsTree(diffVar, tree.getLeftChild()) && Utilities.isNumeric(tree.getRightChild().getElement())) {

            Double exponent = Double.parseDouble(tree.getRightChild().getElement());
            double newExponent = exponent - 1;

            BinaryTreeNode newTree = new BinaryTreeNode("*");
            BinaryTreeNode newTreeLeftChild = new BinaryTreeNode(exponent + "");
            BinaryTreeNode newTreeRightChild = new BinaryTreeNode("^", new BinaryTreeNode(diffVar), new BinaryTreeNode(newExponent + ""));
            newTree.setLeftChild(newTreeLeftChild);
            newTree.setRightChild(newTreeRightChild);

            return newTree;


        } else if (Utilities.containsTree(diffVar, tree.getRightChild())) {
            //TODO Allow the differential variable to be in exponents
            throw new InvalidInputException("Invalid Input - The differential variable is currently not supported for exponents\"");
        } else if (!Utilities.containsTree(diffVar, tree.getLeftChild())) {
            return new BinaryTreeNode("0");
        } else {
            //TODO Allow variables in exponents
            throw new InvalidInputException("Invalid Input - Variables are currently not supported for exponents\"");
        }
    }
}

