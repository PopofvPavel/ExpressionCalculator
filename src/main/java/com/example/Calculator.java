package com.example;

import java.util.Arrays;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class Calculator {
    static Supplier<Calculator> getCalculator = Calculator::new;

    public BinaryOperator<Double> plus = (a, b) -> a + b;
    public BinaryOperator<Double> minus = (a, b) -> a - b;
    public BinaryOperator<Double> multiply = (a, b) -> a * b;
    public BinaryOperator<Double> divide = (a, b) -> a / b;
    public UnaryOperator<Double> unaryMinus = a -> -a;


    public double calculate(String expression) {
        //"-8 * 10 + ( 6 / 2 ) - 5";

        int openBracketIndex = expression.lastIndexOf('(');
        int closeBracketIndex = expression.indexOf(')',openBracketIndex);
        while (openBracketIndex != -1) {
            String inBracketExpression = expression.substring(openBracketIndex, closeBracketIndex + 1);
            double res = evaluateExpression(inBracketExpression);
            expression = expression.replace(inBracketExpression, String.valueOf(res));
            openBracketIndex = expression.lastIndexOf('(');
            closeBracketIndex = expression.indexOf(')',openBracketIndex);
        }

       // stem.out.println("( = " + openBracketIndex + ") " + closeBracketIndex);
        //System.out.println("new exr = " + expression);

        double result = evaluateExpression(expression);
        return result;
    }

    private double evaluateExpression(String inBracketExpression) {
        //"-8 * (10 + ( 6 + 4 / 2 * 6 )) - 5"

        int pos = 0;
        String[] tokens = inBracketExpression.split(" ");
        tokens = clearFromBrackets(tokens);
        int operatorIndex = findOperatorIndex(tokens);
        double result = 0.0;

        while (operatorIndex != -1) {

            double firstOperand = Double.parseDouble(tokens[operatorIndex - 1]);
            double secondOperand = Double.parseDouble(tokens[operatorIndex + 1]);
            BinaryOperator<Double> operator = getBinaryOperator(tokens[operatorIndex]);
            if (operator.getClass() == divide.getClass() && secondOperand == 0) {
                throw new ArithmeticException("Division by zero");
            }
            result = (double) operator.apply(firstOperand, secondOperand);
            String[] newTokens = new String[tokens.length - 2];
            System.arraycopy(tokens, 0, newTokens, 0, operatorIndex - 1);

            //Копируем массив, за исключением оператора и операнда
            newTokens[operatorIndex - 1] = Double.toString(result);
            System.arraycopy(tokens, operatorIndex + 2, newTokens, operatorIndex, tokens.length - operatorIndex - 2);
            tokens = newTokens;

            operatorIndex = findOperatorIndex(tokens);
        }



        return result;
    }

    private String[] clearFromBrackets(String[] tokens) {
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].length() == 1 || tokens[i].length() == 0) {
                continue;
            }
            if (tokens[i].charAt(0) == '(') {

                tokens[i] = tokens[i].substring(1);
            }

            if (tokens[i].charAt(tokens[i].length() - 1) == ')') {

                tokens[i] = tokens[i].substring(0, tokens[i].length() - 1);
            }
        }


        return Arrays.stream(tokens).

                filter(t -> !t.equals("(") && !t.equals(")")).

                toArray(String[]::new);

    }

    private int findOperatorIndex(String[] tokens) {
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].equals("*") || tokens[i].equals("/")) {
                return i;
            }
        }
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].equals("+") || tokens[i].equals("-")) {
                return i;
            }
        }
        return -1;
    }

    private BinaryOperator<Double> getBinaryOperator(String operator) {
        switch (operator) {
            case "+":
                return this.plus;

            case "-":
                return this.minus;

            case "*":
                return this.multiply;

            case "/":
                return this.divide;


        }
        return null;
    }
}
