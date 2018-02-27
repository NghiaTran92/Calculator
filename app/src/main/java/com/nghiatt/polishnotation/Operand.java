package com.nghiatt.polishnotation;

public class Operand extends AbstractElement {

    private double value;
    private EnumTypeDecimal typeDecimal;

    /**
     * default is DECIMAL
     * */
    public Operand(String element) {
        this(element, EnumTypeDecimal.DECIMAL);
    }

    public Operand(String element,EnumTypeDecimal typeDecimal){
        super(element);
        this.typeDecimal=typeDecimal;
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
            switch (typeDecimal){
                case BINARY:
                    value=Integer.parseInt(element,2);
                    break;
                case OCTAL:
                    value=Integer.parseInt(element,8);
                    break;
                case HEXA:
                    value=Integer.parseInt(element,16);
                    break;

                // decimal
                default:
                    value = Double.parseDouble(element);
                    break;

            }

            return true;
        } catch (NumberFormatException formatException) {
            formatException.printStackTrace();
            return false;
        }
    }
}
