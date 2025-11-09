package com.sergey.evaluator;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.*;

class ExpressionParserTest {

    private final ExpressionTokenizer tokenizer = new ExpressionTokenizer();
    private final ExpressionParser parser = new ExpressionParser();

    @Test
    void testShuntingYardWithComplexExpression() {
        // Arrange
        String expression = "3 + 4 * 2 / ( 1 - 5 ) ^ 2";
        List<Token> tokens = tokenizer.tokenize(expression);

        // Act
        List<Token> rpn = parser.parse(tokens);

        // Assert
        // Преобразуем список токенов в строку для удобства сравнения
        String rpnString = rpn.stream().map(t -> t.value).collect(Collectors.joining(" "));
        assertEquals("3 4 2 * 1 5 - 2 ^ / +", rpnString);
    }

    @Test
    void testFunctionsAndVariables() {
        // Arrange
        String expression = "sin(x) * (1 + y)";
        List<Token> tokens = tokenizer.tokenize(expression);

        // Act
        List<Token> rpn = parser.parse(tokens);

        // Assert
        String rpnString = rpn.stream().map(t -> t.value).collect(Collectors.joining(" "));
        assertEquals("x sin 1 y + *", rpnString);
    }

    @Test
    void testMismatchedParentheses() {
        // Arrange
        String expression = "(3 + 4 * 2";
        List<Token> tokens = tokenizer.tokenize(expression);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            parser.parse(tokens);
        });
    }
}
