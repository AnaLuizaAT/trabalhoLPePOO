package org.example;

import lombok.Getter;

@Getter
public class Tree<TYPE extends Comparable> {

    private Element<TYPE> source;

    public Tree() {
        this.source = null;
    }

    public void add(TYPE valor) {
        Element<TYPE> newElement = new Element<>(valor);
        if (source == null) {
            this.source = newElement;
        } else {
            Element<TYPE> actual = this.source;
            while (true) {
                if (newElement.getValue().compareTo(actual.getValue()) < 0) {
                    if (actual.getLeft() != null) {
                        actual = actual.getLeft();
                    } else {
                        actual.setLeft(newElement);
                        break;
                    }
                } else {
                    if (actual.getRight() != null) {
                        actual = actual.getRight();
                    } else {
                        actual.setRight(newElement);
                        break;
                    }
                }
            }
        }
    }

    public void inOrder(Element<TYPE> current) {
        if (current != null) {
            inOrder(current.getLeft());
            System.out.print(current.getValue() + " ");
            inOrder(current.getRight());
        }
    }

    public void preOrder(Element<TYPE> current) {
        if (current != null) {
            System.out.print(current.getValue() + " ");
            preOrder(current.getLeft());
            preOrder(current.getRight());
        }
    }

    public void postOrder(Element<TYPE> current) {
        if (current != null) {
            postOrder(current.getLeft());
            postOrder(current.getRight());
            System.out.print(current.getValue() + " ");
        }
    }

    public boolean remove(TYPE valor) {
        Element<TYPE> current = this.source;
        Element<TYPE> fatherCurrent = null;
        while (current != null) {
            if (current.getValue().equals(valor)) {
                break;
            } else if (valor.compareTo(current.getValue()) < 0) {
                fatherCurrent = current;
                current = current.getLeft();
            } else {
                fatherCurrent = current;
                current = current.getRight();
            }
        }
        if (current != null) {
            if (current.getRight() != null) {
                Element<TYPE> substitute = current.getRight();
                Element<TYPE> fatherSubstitute = current;
                while (substitute.getLeft() != null) {
                    fatherSubstitute = substitute;
                    substitute = substitute.getLeft();
                }
                substitute.setLeft(current.getLeft());
                if (fatherCurrent != null) {
                    if (current.getValue().compareTo(fatherCurrent.getValue()) < 0) {
                        fatherCurrent.setLeft(substitute);
                    } else {
                        fatherCurrent.setRight(substitute);
                    }
                } else {
                    this.source = substitute;
                    fatherSubstitute.setLeft(null);
                    this.source.setRight(fatherSubstitute);
                    this.source.setLeft(current.getLeft());
                }
                if (substitute.getValue().compareTo(fatherSubstitute.getValue()) < 0) {
                    fatherSubstitute.setLeft(null);
                } else {
                    fatherSubstitute.setRight(null);
                }
            } else if (current.getLeft() != null) {
                Element<TYPE> substitute = current.getLeft();
                Element<TYPE> fatherSubstitute = current;
                while (substitute.getRight() != null) {
                    fatherSubstitute = substitute;
                    substitute = substitute.getRight();
                }
                if (fatherCurrent != null) {
                    if (current.getValue().compareTo(fatherCurrent.getValue()) < 0) {
                        fatherCurrent.setLeft(substitute);
                    } else {
                        fatherCurrent.setRight(substitute);
                    }
                } else {
                    this.source = substitute;
                }
                if (substitute.getValue().compareTo(fatherSubstitute.getValue()) < 0) {
                    fatherSubstitute.setLeft(null);
                } else {
                    fatherSubstitute.setRight(null);
                }
            } else {
                if (fatherCurrent != null) {
                    if (current.getValue().compareTo(fatherCurrent.getValue()) < 0) {
                        fatherCurrent.setLeft(null);
                    } else {
                        fatherCurrent.setRight(null);
                    }
                } else {
                    this.source = null;
                }
            }
            return true;
        } else {
            return false;
        }
    }
}