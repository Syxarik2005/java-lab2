package com.sergey.evaluator;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Создаем экземпляры всех наших компонентов
        ExpressionTokenizer tokenizer = new ExpressionTokenizer();
        ExpressionParser parser = new ExpressionParser();
        RpnEvaluator evaluator = new RpnEvaluator();

        String expression = "3 + 4 * 2 / ( 1 - 5 ) ^ 2";

        try {
            System.out.println("Исходное выражение: " + expression);

            // Шаг 1: Токенизация (Строка -> Список токенов)
            List<Token> tokens = tokenizer.tokenize(expression);
            System.out.println("Токены: " + tokens);

            // Шаг 2: Парсинг (Токены -> ОПН)
            List<Token> rpn = parser.parse(tokens);
            System.out.println("ОПН: " + rpn);

            // Шаг 3: Вычисление (ОПН -> Результат)
            double result = evaluator.evaluate(rpn);
            System.out.println("======================");
            System.out.println("Результат: " + result);
            System.out.println("======================");

        } catch (Exception e) {
            // Ловим все возможные ошибки: некорректный символ, скобки, деление на ноль и т.д.
            System.err.println("ОШИБКА: " + e.getMessage());
        }
    }
}