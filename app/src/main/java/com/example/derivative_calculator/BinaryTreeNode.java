package com.example.derivative_calculator;


import java.util.Objects;


public class BinaryTreeNode{

    private String element;
    private BinaryTreeNode leftChild;
    private BinaryTreeNode rightChild;

    public BinaryTreeNode(String element) {
        this.element = element.replaceFirst("\\.0*$|(\\.\\d*?)0+$", "$1");// Removes trailing .0  (6.0 --> 6) resulting from the double type
        this.leftChild = null;
        this.rightChild = null;
    }

    public BinaryTreeNode(String element, BinaryTreeNode leftChild, BinaryTreeNode rightChild) {
        this.element = element.replaceFirst("\\.0*$|(\\.\\d*?)0+$", "$1");
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public BinaryTreeNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(BinaryTreeNode leftChild) {
        this.leftChild = leftChild;
    }

    public BinaryTreeNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(BinaryTreeNode rightChild) {
        this.rightChild = rightChild;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element.replaceFirst("\\.0*$|(\\.\\d*?)0+$", "$1");
    }

    @Override
    public String toString() {
        String s = "";
        s += "Element: " + this.element + "\n";
        if (this.leftChild != null || this.rightChild != null) {
            s += "Left Child: " + this.leftChild.toString();
            s += "Right Child: " + this.rightChild.toString();
        }
        return s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BinaryTreeNode that = (BinaryTreeNode) o;
        return Objects.equals(getElement(), that.getElement()) &&
                Objects.equals(getLeftChild(), that.getLeftChild()) &&
                Objects.equals(getRightChild(), that.getRightChild());
    }
}
