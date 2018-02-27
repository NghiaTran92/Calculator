package com.nghiatt.polishnotation;

public enum EnumOperater {
    NONE(""),
    /**
     * (
     */
    OPEN("("),
    /**
     * )
     */
    CLOSE(")"),
    /**
     * +
     */
    ADD("+"),
    /**
     * -
     */
    SUBTRACT("-"),
    /** * */
    MULTIPLICATION("*"),
    /**
     * /
     */
    DIVISION("/"),
    POSITIVE("t+"),
    NEGATIVE("t-")
    ;
    private String value;
    EnumOperater(String value){
        this.value=value;
    }
    public int getPriority() {
        switch (this) {

            case OPEN:
            case CLOSE:
                return 1;

            case ADD:
            case SUBTRACT:
                return 2;

            case MULTIPLICATION:
            case DIVISION:
                return 3;

            case POSITIVE:
            case NEGATIVE:
                return 4;

            default:
                // None
                return 0;
        }
    }

    public String getValue(){
        return value;
    }

    @Override
    public String toString() {
       return this.value;
    }
}
