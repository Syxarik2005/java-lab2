package com.sergey.evaluator.parser;

import java.util.*;

/**
 * Парсер, который преобразует список токенов из инфиксной нотации
 * в Обратную Польскую Нотацию (ОПН) с использованием алгоритма "сортировочной станции".
 *
 * @author Белявцев Сергей
 * @version 1.0
 * @see ExpressionTokenizer
 * @see Token
 */
public class ExpressionParser {

    // Карта для хранения приоритетов операторов. Чем больше число, тем выше приоритет.
    private static final Map<String, Integer> PRECEDENCE = new HashMap<>();
    static {
        PRECEDENCE.put("+", 1);
        PRECEDENCE.put("-", 1);
        PRECEDENCE.put("*", 2);
        PRECEDENCE.put("/", 2);
        PRECEDENCE.put("^", 3);
    }

    /**
     * Преобразует список токенов из инфиксной нотации в Обратную Польскую Нотацию (ОПН).
     * @param tokens Список токенов, полученный от {@link ExpressionTokenizer}.
     * @return Список токенов в ОПН.
     * @throws IllegalArgumentException если скобки в выражении расставлены неверно.
     */
    public List<Token> parse(List<Token> tokens) {
        // Выходная очередь для токенов в ОПН.
        List<Token> outputQueue = new ArrayList<>();
        // Стек для операторов, функций и скобок.
        Stack<Token> operatorStack = new Stack<>();

        for (Token token : tokens) {
            switch (token.type) {
                case NUMBER:
                case VARIABLE:
                    // Числа и переменные всегда сразу отправляются в выходную очередь
                    outputQueue.add(token);
                    break;

                case FUNCTION:
                case LEFT_PAREN:
                    // Функции и левые скобки всегда отправляются в стек
                    operatorStack.push(token);
                    break;

                case OPERATOR:
                    // Пока на вершине стека есть оператор с большим или равным приоритетом,
                    // выталкиваем его из стека в выходную очередь
                    while (!operatorStack.isEmpty() && isOperator(operatorStack.peek()) &&
                            getPrecedence(operatorStack.peek().value) >= getPrecedence(token.value)) {
                        outputQueue.add(operatorStack.pop());
                    }
                    // После этого кладем текущий оператор в стек
                    operatorStack.push(token);
                    break;

                case RIGHT_PAREN:
                    // При встрече правой скобки выталкиваем все из стека, пока не найдем левую скобку
                    while (!operatorStack.isEmpty() && operatorStack.peek().type != TokenType.LEFT_PAREN) {
                        outputQueue.add(operatorStack.pop());
                    }

                    if (operatorStack.isEmpty()) {
                        throw new IllegalArgumentException("Ошибка в расстановке скобок: отсутствует открывающая скобка");
                    }
                    operatorStack.pop(); // Выкидываем '(' из стека

                    // Если после левой скобки в стеке оказалась функция, это значит,
                    // что скобки относились к ней. Выталкиваем функцию тоже
                    if (!operatorStack.isEmpty() && operatorStack.peek().type == TokenType.FUNCTION) {
                        outputQueue.add(operatorStack.pop());
                    }
                    break;
            }
        }

        // Когда все токены обработаны, выталкиваем все оставшиеся операторы и функции из стека
        while (!operatorStack.isEmpty()) {
            Token top = operatorStack.pop();
            // Если в стеке осталась скобка, значит, баланс скобок нарушен
            if (top.type == TokenType.LEFT_PAREN) {
                throw new IllegalArgumentException("Ошибка в расстановке скобок: отсутствует закрывающая скобка");
            }
            outputQueue.add(top);
        }

        return outputQueue;
    }

    /**
     * Вспомогательный метод для проверки, является ли токен оператором
     * @param token Токен для проверки
     * @return true, если токен является оператором, иначе false
     */
    private boolean isOperator(Token token) {
        return token.type == TokenType.OPERATOR;
    }

    /**
     * Вспомогательный метод для получения приоритета оператора
     * @param operator Строковое представление оператора
     * @return Целочисленный приоритет
     */
    private int getPrecedence(String operator) {
        return PRECEDENCE.getOrDefault(operator, 0);
    }
}