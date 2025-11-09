package com.sergey.evaluator;

/**
 * Представляет одну лексему (токен) в математическом выражении
 * Является неизменяемым объектом
 */
public class Token {
    /** Тип токена (число, оператор и др) */
    public final TokenType type;
    /** Строковое значение токена, например, "3.14", "+", "x" */
    public final String value;

    /**
     * Создает новый токен
     * @param type тип токена
     * @param value строковое значение токена
     */
    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Token(" + type + ", '" + value + "')";
    }
}