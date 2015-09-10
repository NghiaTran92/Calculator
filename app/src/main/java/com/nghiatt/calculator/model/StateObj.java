package com.nghiatt.calculator.model;

import com.nghiatt.polishnotation.EnumTypeDecimal;

import java.io.Serializable;

/**
 * Created by FRAMGIA\tran.thanh.nghia on 10/09/2015.
 */
public class StateObj implements Serializable {
    private String expression;
    private String result;
    private EnumTypeDecimal typeDecimal;

    public StateObj(String expression, String result, EnumTypeDecimal typeDecimal) {
        this.expression = expression;
        this.result = result;
        this.typeDecimal = typeDecimal;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public EnumTypeDecimal getTypeDecimal() {
        return typeDecimal;
    }

    public void setTypeDecimal(EnumTypeDecimal typeDecimal) {
        this.typeDecimal = typeDecimal;
    }
}
