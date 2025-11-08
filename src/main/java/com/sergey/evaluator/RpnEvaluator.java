package com.sergey.evaluator;

import java.util.List;
import java.util.Map;
import java.util.Stack;

public class RpnEvaluator {

    /**
     * Вычисляет значение выражения, представленного в виде ОПН
     * @param rpnTokens Список токенов в ОПН
     * @param variables Словарь с именами и значениями переменных
     * @return Результат вычисления (double)
     * @throws IllegalArgumentException если выражение некорректно (например, не хватает операндов)
     */
    public double evaluate(List<Token> rpnTokens, Map<String, Double> variables) {
        Stack<Double> stack = new Stack<>();

        for (Token token : rpnTokens) {
            switch (token.type) {
                case NUMBER:
                    // Правило 3: Число просто кладем в стек
                    stack.push(Double.parseDouble(token.value));
                    break;

                case OPERATOR:
                    // Правило 4: Оператор выполняет действие над числами из стека
                    if (stack.size() < 2) {
                        throw new IllegalArgumentException("Ошибка в выражении: не хватает операндов для оператора " + token.value);
                    }
                    double b = stack.pop(); // Второй операнд
                    double a = stack.pop(); // Первый операнд

                    double result = performOperation(token.value, a, b);
                    stack.push(result);
                    break;

                // Добавляем обработку переменных
                case VARIABLE:
                    String varName = token.value;
                    if (!variables.containsKey(varName)) {
                        throw new IllegalArgumentException("Не найдено значение для переменной: " + varName);
                    }
                    // Берем значение из карты и кладем в стек
                    stack.push(variables.get(varName));
                    break;

                default:
                    throw new IllegalArgumentException("Неподдерживаемый тип токена в ОПН: " + token.type);
            }
        }

        // Правило 5: В конце в стеке должен остаться один результат
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Ошибка в выражении: неверное количество операторов и операндов.");
        }

        return stack.pop();
    }

    private double performOperation(String operator, double a, double b) {
        switch (operator) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                if (b == 0) {
                    throw new ArithmeticException("Ошибка: деление на ноль.");
                }
                return a / b;
            case "^":
                return Math.pow(a, b);
            default:
                throw new IllegalArgumentException("Неизвестный оператор: " + operator);
        }
    }
}