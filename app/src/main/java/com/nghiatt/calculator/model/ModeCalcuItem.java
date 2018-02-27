package com.nghiatt.calculator.model;

import com.nghiatt.polishnotation.EnumTypeDecimal;

/**
 * Created by FRAMGIA\tran.thanh.nghia on 09/09/2015.
 */
public class ModeCalcuItem {
    private String name;
    private EnumTypeDecimal typeDecimal;

    public ModeCalcuItem(String name, EnumTypeDecimal typeDecimal) {
        this.name = name;
        this.typeDecimal = typeDecimal;
    }

    public EnumTypeDecimal getTypeDecimal() {
        return typeDecimal;
    }

    public void setTypeDecimal(EnumTypeDecimal typeDecimal) {
        this.typeDecimal = typeDecimal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
