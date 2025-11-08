package com.sergey.evaluator;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ExpressionTokenizer tokenizer = new ExpressionTokenizer();
        ExpressionParser parser = new ExpressionParser();

        String expression = "3 + 4 * 2 / ( 1 - 5 ) ^ 2";

        try {
            System.out.println("Исходное выражение: " + expression);

            // Шаг 1: Токенизация
            List<Token> tokens = tokenizer.tokenize(expression);
            System.out.println("Токены: " + tokens);

            // Шаг 2: Парсинг в ОПН
            List<Token> rpn = parser.parse(tokens);
            System.out.println("Обратная Польская Нотация (ОПН): " + rpn);

        } catch (IllegalArgumentException e) {
            System.err.println("ОШИБКА: " + e.getMessage());
        }
    }
}