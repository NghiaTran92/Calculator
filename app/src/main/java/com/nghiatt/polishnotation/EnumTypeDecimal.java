package com.nghiatt.polishnotation;

/**
 * Created by FRAMGIA\tran.thanh.nghia on 09/09/2015.
 */
public enum EnumTypeDecimal {
    BINARY(2),
    DECIMAL(10),
    OCTAL(8),
    HEXA(16);
    private int value;
    EnumTypeDecimal(int value){
        this.value=value;
    }
    public int getValue(){
        return value;
    }
}
