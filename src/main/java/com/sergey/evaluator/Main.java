package com.sergey.evaluator;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ExpressionTokenizer tokenizer = new ExpressionTokenizer();
        String expression = "3.14 * (x - 2) + sin(y)";

        try {
            List<Token> tokens = tokenizer.tokenize(expression);
            System.out.println("Выражение: " + expression);
            System.out.println("Найденные токены:");
            for (Token token : tokens) {
                System.out.println(token);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка в выражении: " + e.getMessage());
        }
    }
}