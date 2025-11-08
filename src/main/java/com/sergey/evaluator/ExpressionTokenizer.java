package com.sergey.evaluator;

import java.util.ArrayList;
import java.util.List;

public class ExpressionTokenizer {

    /**
     * Преобразует строку с математическим выражением в список токенов.
     * @param expression Входная строка Пример: 3 + 4 * (2 - 1)
     * @return Список токенов.
     * @throws IllegalArgumentException если в выражении есть недопустимые символы.
     */
    public List<Token> tokenize(String expression) {
        List<Token> tokens = new ArrayList<>();
        int position = 0; // Наша текущая позиция в строке

        while (position < expression.length()) {
            char currentChar = expression.charAt(position);

            if (Character.isDigit(currentChar)) {
                // Если это цифра, мы читаем все число целиком (может быть и 123, и 3.14)
                StringBuilder number = new StringBuilder();
                while (position < expression.length() && (Character.isDigit(expression.charAt(position)) || expression.charAt(position) == '.')) {
                    number.append(expression.charAt(position));
                    position++;
                }
                tokens.add(new Token(TokenType.NUMBER, number.toString()));
                continue; // Возвращаемся в начало цикла, т.к position уже обновлена
            }

            if (Character.isLetter(currentChar)) {
                // Если это буква, читаем все имя переменной или функции
                StringBuilder name = new StringBuilder();
                while (position < expression.length() && Character.isLetterOrDigit(expression.charAt(position))) {
                    name.append(expression.charAt(position));
                    position++;
                }
                // TODO: В будущем здесь нужно будет отличать функции от переменных
                tokens.add(new Token(TokenType.VARIABLE, name.toString()));
                continue;
            }

            if (Character.isWhitespace(currentChar)) {
                // Пробелы просто пропускаем
                position++;
                continue;
            }

            switch (currentChar) {
                case '+':
                case '-':
                case '*':
                case '/':
                case '^': // Добавим оператор возведения в степень
                    tokens.add(new Token(TokenType.OPERATOR, String.valueOf(currentChar)));
                    break;
                case '(':
                    tokens.add(new Token(TokenType.LEFT_PAREN, String.valueOf(currentChar)));
                    break;
                case ')':
                    tokens.add(new Token(TokenType.RIGHT_PAREN, String.valueOf(currentChar)));
                    break;
                default:
                    // Если мы встретили символ, который не знаем, это ошибка
                    throw new IllegalArgumentException("Недопустимый символ в выражении: " + currentChar);
            }

            position++; // Переходим к следующему символу
        }

        return tokens;
    }
}