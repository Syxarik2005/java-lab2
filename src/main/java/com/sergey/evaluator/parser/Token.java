package com.sergey.evaluator.parser;

/**
 * Представляет одну лексему (токен) в математическом выражении
 * Является неизменяемым (immutable) объектом
 *
 * @author Белявцев Сергей
 * @version 1.0
 * @see TokenType
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