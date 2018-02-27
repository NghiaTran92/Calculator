package com.nghiatt.polishnotation.rpn;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nghiatt.calculator.R;
import com.nghiatt.polishnotation.AbstractElement;
import com.nghiatt.polishnotation.EnumOperater;
import com.nghiatt.polishnotation.Operand;
import com.nghiatt.polishnotation.Operator;

import org.apache.commons.lang3.StringUtils;

public class ReversePolishNotation {

    private final String END_CHARACTER = "#";

    private String expressionOrigin = "";
    private String expression;

    private Context context;
    private Stack<AbstractElement> stackInfixToPostfix;
    private List<AbstractElement> listResultPostfix;

    public ReversePolishNotation(Context context, String expression) {
        this.expressionOrigin = expression;
        this.expression = expression + END_CHARACTER;
        this.context = context;
        convertToStandard();
        listResultPostfix = new ArrayList<AbstractElement>();
        stackInfixToPostfix = new Stack<AbstractElement>();
    }

    private void convertToStandard() {
        //expression.replace(" ", "");
        expression = expression.replace(context.getString(R.string.sym_mul), EnumOperater.MULTIPLICATION.getValue())
                .replace(context.getString(R.string.sym_division), EnumOperater.DIVISION.getValue())
                .replace(context.getString(R.string.sym_sub), EnumOperater.SUBTRACT.getValue())
                .replace(context.getString(R.string.sym_add), EnumOperater.ADD.getValue());
        String regex = "\\d\\(";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(expression);
        while (matcher.find()) {
            expression = expression.substring(0, matcher.start() + 1) + "*(" + expression.substring(matcher.end(), expression.length());
        }

        regex = "\\)\\d";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(expression);
        while (matcher.find()) {
            expression = expression.substring(0, matcher.start()) + ")*" + expression.substring(matcher.end() - 1, expression.length());
        }
        Log.i("Standard", expression);
    }


    public boolean validation() {
        int openCount = StringUtils.countMatches(expressionOrigin, "(");
        int closeCount = StringUtils.countMatches(expressionOrigin, ")");
        return openCount == closeCount;
    }

    public String getExpressionOrigin() {
        return expressionOrigin;
    }

    public boolean convertInfixToPostfix() {

        if (validation()) {
            stackInfixToPostfix.clear();
            // init stack element to check empty
            Operator operator = new Operator("");
            operator.convert();

            boolean isNegative = true;

            stackInfixToPostfix.push(operator);
            listResultPostfix.clear();
            String temp = "";
            // not include END_CHARACTER
            for (int i = 0; i < expression.length() - 1; i++) {
                char character = expression.charAt(i);
                if ((character >= '0' && character <= '9') || character == '.') {
                    temp += character;

                    char nextCharacter = expression.charAt(i + 1);
                    if (!((nextCharacter >= '0' && nextCharacter <= '9') || nextCharacter == '.')) {
                        Operand operand = new Operand(temp);
                        isNegative = false;
                        if (operand.convert()) {
                            listResultPostfix.add(operand);
                            temp = "";
                        } else return false;
                    }

                } else if (character == '+' || character == '-' || character == '*'
                        || character == '/') {
                    if (isNegative) {
                        operator = null;
                        if (character == '+') {
                            operator = new Operator(EnumOperater.POSITIVE.getValue());
                        } else if (character == '-') {
                            operator = new Operator(EnumOperater.NEGATIVE.getValue());
                        }

                        if (operator != null) {
                            operator.convert();
                            stackInfixToPostfix.push(operator);
                        }

                    } else {
                        Operator currentOperator = new Operator(character + "");
                        currentOperator.convert();
                        boolean isLoop = true;
                        while (isLoop) {
                            // check top stack
                            operator = (Operator) stackInfixToPostfix
                                    .get(stackInfixToPostfix.size() - 1);
                            if (operator.getPriority() >= currentOperator.getPriority()) {
                                listResultPostfix.add(stackInfixToPostfix.pop());
                            } else if (operator.getPriority() < currentOperator
                                    .getPriority()) {
                                operator = new Operator(character + "");
                                operator.convert();
                                stackInfixToPostfix.push(operator);
                                isLoop = false;
                            }
                        }
                    }
                    isNegative=true;

                } else if (character == '(') {
                    operator = new Operator(character + "");
                    operator.convert();
                    stackInfixToPostfix.push(operator);

                } else if (character == ')') {
                    boolean isLoop = true;
                    while (isLoop) {
                        // check top stack
                        operator = (Operator) stackInfixToPostfix
                                .get(stackInfixToPostfix.size() - 1);
                        if (operator.checkOperator()) {
                            listResultPostfix.add(stackInfixToPostfix.pop());
                        } else if ("(".equals(operator.getElement())
                                || "".equals(operator.getElement()) /* stackempty */) {
                            stackInfixToPostfix.pop();
                            isLoop = false;
                        }
                    }
                }
            }
            // if stack not empty so then add into listResultPostfix
            // "" is stack empty
            while (!"".equals((operator = (Operator) stackInfixToPostfix.pop())
                    .getElement())) {
                listResultPostfix.add(operator);
            }
            stackInfixToPostfix.clear();
           // Log.i("postfix",getPostfix());
            return true;
        } else {
            return false;
        }
    }

    public double calculate() throws Exception {
        if (listResultPostfix.size() > 0) {
            stackInfixToPostfix.clear();
            Operand firstNum = null;
            Operand secondNum = null;
            Operator operator;
            for (AbstractElement ele : listResultPostfix) {
                if (ele instanceof Operand) {
                    stackInfixToPostfix.push(ele);
                } else if (ele instanceof Operator) {
                    operator = (Operator) ele;
                    switch (operator.getOperator()) {
                        case POSITIVE:
                            firstNum = (Operand) stackInfixToPostfix.pop();
                            stackInfixToPostfix.push(new Operand(firstNum.getValue()));
                            break;
                        case NEGATIVE:
                            firstNum = (Operand) stackInfixToPostfix.pop();
                            stackInfixToPostfix.push(new Operand(-firstNum.getValue()));
                            break;
                        default:
                            secondNum = (Operand) stackInfixToPostfix.pop();
                            firstNum = (Operand) stackInfixToPostfix.pop();
                            if (firstNum != null && secondNum != null) {

                                stackInfixToPostfix.push(operator.calculate(firstNum,
                                        secondNum));
                            }
                            break;
                    }

                }
            }
            Operand result = (Operand) stackInfixToPostfix.pop();
            return result.getValue();
        } else {
            throw new Exception("Postfix is empty");
        }

    }

    public String getPostfix() {
        String result = "";
        for (AbstractElement ele : listResultPostfix) {
            result += ele.getElement() + " ";
        }
        return result;
    }

}
