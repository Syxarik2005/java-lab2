package com.sergey.evaluator;

import java.util.*; // Импортируем всё необходимое

public class Main {
    public static void main(String[] args) {
        ExpressionTokenizer tokenizer = new ExpressionTokenizer();
        ExpressionParser parser = new ExpressionParser();
        RpnEvaluator evaluator = new RpnEvaluator();

        // Новое выражение для теста
        String expression = "sqrt(x) + abs(y) * sin(90)";

        try {
            System.out.println("Исходное выражение: " + expression);

            // Шаг 1: Токенизация
            List<Token> tokens = tokenizer.tokenize(expression);
            System.out.println("Токены: " + tokens);

            // Ищем переменные и запрашиваем их значения
            // Используем Set, чтобы хранить только уникальные имена переменных
            Set<String> variableNames = new HashSet<>();
            for (Token token : tokens) {
                if (token.type == TokenType.VARIABLE) {
                    variableNames.add(token.value);
                }
            }

            // Создаем карту для хранения значений: Имя -> Значение
            Map<String, Double> variables = new HashMap<>();
            if (!variableNames.isEmpty()) {
                System.out.println("=================================================");
                System.out.println("Найдены переменные. Пожалуйста, введите их значения:");
                Scanner scanner = new Scanner(System.in);
                for (String varName : variableNames) {
                    System.out.print(varName + " = ");
                    try {
                        double value = scanner.nextDouble();
                        variables.put(varName, value);
                    } catch (InputMismatchException e) {
                        System.err.println("ОШИБКА: Введено не число. Завершение работы.");
                        return;
                    }
                }
                System.out.println("=================================================");
            }

            // Шаг 2: Парсинг в ОПН
            List<Token> rpn = parser.parse(tokens);
            System.out.println("ОПН: " + rpn);

            // Шаг 3: Вычисление (теперь передаем карту с переменными)
            double result = evaluator.evaluate(rpn, variables);
            System.out.println("Результат: " + result);

        } catch (Exception e) {
            System.err.println("ОШИБКА: " + e.getMessage());
        }
    }
}