package org.example;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Element<TYPE> {
    private TYPE value;
    private Element<TYPE> left;
    private Element<TYPE> right;

    public Element(TYPE newValue) {
        this.value = newValue;
        this.left = null;
        this.right = null;
    }
}