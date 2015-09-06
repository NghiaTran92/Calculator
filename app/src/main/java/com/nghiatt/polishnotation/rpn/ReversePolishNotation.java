package com.nghiatt.polishnotation.rpn;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.nghiatt.polishnotation.AbstractElement;
import com.nghiatt.polishnotation.Operand;
import com.nghiatt.polishnotation.Operator;

public class ReversePolishNotation {
    private final String END_CHARACTER = "#";
    private String expressionOrigin="";
    private String expression;
    private Stack<AbstractElement> stackInfixToPostfix;
    private List<AbstractElement> listResultPostfix;

    public ReversePolishNotation(String expression) {
        this.expressionOrigin=expression;
        this.expression = expression + END_CHARACTER;
        this.expression.replace(" ", "");
        listResultPostfix = new ArrayList<AbstractElement>();
        stackInfixToPostfix = new Stack<AbstractElement>();
    }

    public String getExpressionOrigin(){
        return expressionOrigin;
    }

    public boolean convertInfixToPostfix() {
        stackInfixToPostfix.clear();
        // init stack element to check empty
        Operator operator = new Operator("");
        operator.convert();
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
                    operand.convert();
                    listResultPostfix.add(operand);
                    temp = "";
                }

            } else if (character == '+' || character == '-' || character == '*'
                    || character == '/') {
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
        return true;
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
                    secondNum = (Operand) stackInfixToPostfix.pop();
                    firstNum = (Operand) stackInfixToPostfix.pop();
                    if (firstNum != null && secondNum != null) {
                        operator = (Operator) ele;
                        stackInfixToPostfix.push(operator.calculate(firstNum,
                                secondNum));
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
