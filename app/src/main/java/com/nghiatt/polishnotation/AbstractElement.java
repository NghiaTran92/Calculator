package com.nghiatt.polishnotation;

public abstract class AbstractElement {
    /**
     * number or operator
     */
    protected String element;

    public AbstractElement(String element) {
        this.element = element;
    }

    public abstract boolean convert();

    public String getElement() {
        return element;
    }


}
