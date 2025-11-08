package com.sergey.evaluator;

public class Token {
    public final TokenType type; // Тип токена
    public final String value;   // Строковое представление, например, "3.14", "+", "x"

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Token(" + type + ", '" + value + "')";
    }
}