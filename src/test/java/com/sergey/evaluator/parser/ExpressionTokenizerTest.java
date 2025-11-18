package com.sergey.evaluator.parser;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ExpressionTokenizerTest {

    private final ExpressionTokenizer tokenizer = new ExpressionTokenizer();

    @Test
    void testComplexExpression() {
        // Подготовка (Arrange)
        String expression = "sqrt(x1) + 5.5";

        // Действие (Act)
        List<Token> tokens = tokenizer.tokenize(expression);

        // Проверка (Assert)
        assertEquals(6, tokens.size());
        // убеждаемся, что токенизатор правильно распознал функцию в самом начале
        assertEquals(TokenType.FUNCTION, tokens.get(0).type);
        assertEquals("sqrt", tokens.get(0).value);
        // убеждаемся, что токенизатор не разбил x1 на два токена (x и 1), а правильно обработал переменную, содержащую цифру
        assertEquals(TokenType.VARIABLE, tokens.get(2).type);
        assertEquals("x1", tokens.get(2).value);
        // убеждаемся, что токенизатор правильно обработал число с плавающей точкой
        assertEquals(TokenType.NUMBER, tokens.get(5).type);
        assertEquals("5.5", tokens.get(5).value);
    }

    @Test
    void testInvalidCharacter() {
        // Arrange
        String expression = "3 + @";

        // Act & Assert
        // Проверяем, что при вызове tokenize будет выброшено исключение IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            tokenizer.tokenize(expression);
        });
    }
}
