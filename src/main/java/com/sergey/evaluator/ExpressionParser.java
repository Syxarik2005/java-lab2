package com.sergey.evaluator;

import java.util.*;

public class ExpressionParser {

    // Карта для хранения "приоритетов" операторов. Чем больше число, тем выше приоритет.
    private static final Map<String, Integer> PRECEDENCE = new HashMap<>();
    static {
        PRECEDENCE.put("+", 1);
        PRECEDENCE.put("-", 1);
        PRECEDENCE.put("*", 2);
        PRECEDENCE.put("/", 2);
        PRECEDENCE.put("^", 3);
    }

    private int getPrecedence(String operator) {
        return PRECEDENCE.getOrDefault(operator, 0);
    }

    /**
     * Преобразует список токенов из инфиксной нотации в Обратную Польскую Нотацию (ОПН)
     * @param tokens Список токенов, полученный от токенизатора
     * @return Список токенов в ОПН
     * @throws IllegalArgumentException если скобки расставлены неверно
     */
    public List<Token> parse(List<Token> tokens) {
        // Наша "конечная станция"
        List<Token> outputQueue = new ArrayList<>();
        // Наш "сортировочный путь"
        Stack<Token> operatorStack = new Stack<>();

        for (Token token : tokens) {
            switch (token.type) {
                case NUMBER:
                case VARIABLE:
                    // Правило 1: Числа и переменные сразу идут в выходную очередь
                    outputQueue.add(token);
                    break;

                case OPERATOR:
                    // Правило 2: Обрабатываем операторы
                    while (!operatorStack.isEmpty() &&
                            operatorStack.peek().type == TokenType.OPERATOR &&
                            getPrecedence(operatorStack.peek().value) >= getPrecedence(token.value)) {
                        outputQueue.add(operatorStack.pop());
                    }
                    operatorStack.push(token);
                    break;

                case LEFT_PAREN:
                    // Правило 3: Левая скобка всегда идет в стек операторов
                    operatorStack.push(token);
                    break;

                case RIGHT_PAREN:
                    // Правило 4: Правая скобка "выталкивает" операторы из стека
                    while (!operatorStack.isEmpty() && operatorStack.peek().type != TokenType.LEFT_PAREN) {
                        outputQueue.add(operatorStack.pop());
                    }

                    if (operatorStack.isEmpty()) {
                        // Если мы не нашли левую скобку, значит, они расставлены неверно
                        throw new IllegalArgumentException("Ошибка в расстановке скобок: отсутствует открывающая скобка.");
                    }
                    // Выбрасываем левую скобку из стека
                    operatorStack.pop();
                    break;

                // TODO: Добавить обработку функций
            }
        }

        // Правило 5: Выталкиваем все оставшиеся операторы из стека
        while (!operatorStack.isEmpty()) {
            Token topOperator = operatorStack.pop();
            if (topOperator.type == TokenType.LEFT_PAREN) {
                // Если в стеке осталась левая скобка, значит, нет закрывающей
                throw new IllegalArgumentException("Ошибка в расстановке скобок: отсутствует закрывающая скобка.");
            }
            outputQueue.add(topOperator);
        }

        return outputQueue;
    }
}

