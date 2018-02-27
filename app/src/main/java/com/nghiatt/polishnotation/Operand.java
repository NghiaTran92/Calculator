package com.nghiatt.polishnotation;

public class Operand extends AbstractElement {

    private double value;

    public Operand(String element) {
        super(element);
        // TODO Auto-generated constructor stub
    }

    public Operand(double value) {
        super(value + "");
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public boolean convert() {
        // TODO Auto-generated method stub
        try {
            // should validation

            value = Double.parseDouble(element);
            return true;
        } catch (NumberFormatException formatException) {
            formatException.printStackTrace();
            return false;
        }
    }
}
