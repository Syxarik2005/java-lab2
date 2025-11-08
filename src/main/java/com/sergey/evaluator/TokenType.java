package com.sergey.evaluator;

public enum TokenType {
    NUMBER, // число (3.14)
    OPERATOR, // +,-,*,/
    VARIABLE, // x
    FUNCTION, // sin
    LEFT_PAREN, // (
    RIGHT_PAREN // )
}
