package com.sergey.evaluator;

import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Вычислитель, который принимает на вход выражение в Обратной Польской Нотации (ОПН)
 * и вычисляет его конечное значение
 *
 * @author Белявцев Сергей
 * @version 1.0
 * @see ExpressionParser
 * @see Token
 */
public class RpnEvaluator {

    /**
     * Вычисляет значение выражения, представленного в виде ОПН
     *
     * @param rpnTokens Список токенов в ОПН, полученный от {@link ExpressionParser}
     * @param variables Карта (словарь) с именами и значениями переменных
     * @return Результат вычисления (double)
     * @throws IllegalArgumentException если выражение некорректно (например, не хватает операндов)
     * @throws ArithmeticException при математических ошибках (например, деление на ноль)
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

                // Добавляем обработку функций
                case FUNCTION:
                    if (stack.isEmpty()) {
                        throw new IllegalArgumentException("Ошибка в выражении: не хватает операндов для функции " + token.value);
                    }
                    double arg = stack.pop(); // Берем один аргумент из стека
                    double functionResult = performFunction(token.value, arg);
                    stack.push(functionResult); // Кладем результат обратно
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

    /**
     * Выполняет бинарную математическую операцию
     * @param operator оператор (+, -, *, /, ^)
     * @param a первый операнд
     * @param b второй операнд
     * @return результат операции
     */
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

    /**
     * Выполняет унарную математическую функцию
     * @param functionName имя функции (sin, cos, sqrt и т.д)
     * @param arg аргумент функции
     * @return результат функции
     */
    private double performFunction(String functionName, double arg) {
        switch (functionName) {
            case "sin":
                return Math.sin(Math.toRadians(arg)); // Math.sin работает с радианами, а люди вводят градусы
            case "cos":
                return Math.cos(Math.toRadians(arg));
            case "tan":
                return Math.tan(Math.toRadians(arg));
            case "sqrt":
                if (arg < 0) {
                    throw new ArithmeticException("Нельзя извлечь корень из отрицательного числа.");
                }
                return Math.sqrt(arg);
            case "abs":
                return Math.abs(arg);
            default:
                throw new IllegalArgumentException("Неизвестная функция: " + functionName);
        }
    }
}