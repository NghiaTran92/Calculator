package com.nghiatt.polishnotation;

public class Operator extends AbstractElement {

    private EnumOperater operater;

    public Operator(String element) {
        super(element);
        // TODO Auto-generated constructor stub
    }

    public EnumOperater getOperator() {
        return operater;
    }

    public int getPriority() {
        return operater.getPriority();
    }

    public boolean checkOperator() {
        switch (operater) {
            case ADD:
            case SUBTRACT:
            case DIVISION:
            case MULTIPLICATION:
                return true;

            default:
                return false;
        }
    }

    @Override
    public boolean convert() {
        // TODO Auto-generated method stub
        boolean flag = true;
        switch (element) {
            // for empty stack
            case "":
                operater = EnumOperater.NONE;
                break;
            // for create RPN
            case "(":
                operater = EnumOperater.OPEN;
                break;
            // for create RPN
            case ")":
                operater = EnumOperater.CLOSE;
                break;
            case "+":
                operater = EnumOperater.ADD;
                break;
            case "-":
                operater = EnumOperater.SUBTRACT;
                break;
            case "*":
                operater = EnumOperater.MULTIPLICATION;
                break;

            case "/":
            //case "÷":
                operater = EnumOperater.DIVISION;
                break;

            // Unknow
            default:
                flag = false;
                break;
        }
        return flag;
    }

    public double calculate(double firstNum, double secondNum) throws Exception {
        double result = 0;
        switch (operater) {
            case ADD:
                result = firstNum + secondNum;
                break;
            case SUBTRACT:
                result = firstNum - secondNum;
                break;
            case MULTIPLICATION:
                result = firstNum * secondNum;
                break;
            case DIVISION:
                if (secondNum == 0) {
                    throw new Exception("Error: Division by zero");
                } else {
                    result = firstNum / secondNum;
                }
                break;
            default:
                break;
        }
        return result;
    }

    public Operand calculate(Operand firstNum, Operand secondNum) throws Exception {
        return new Operand(this.calculate(firstNum.getValue(), secondNum.getValue()));
    }

}
