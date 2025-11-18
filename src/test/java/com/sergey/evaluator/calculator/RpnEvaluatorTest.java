package com.sergey.evaluator.calculator;

import com.sergey.evaluator.parser.ExpressionParser;
import com.sergey.evaluator.parser.ExpressionTokenizer;
import com.sergey.evaluator.parser.Token;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class RpnEvaluatorTest {

    private final ExpressionTokenizer tokenizer = new ExpressionTokenizer();
    private final ExpressionParser parser = new ExpressionParser();
    private final RpnEvaluator evaluator = new RpnEvaluator();

    @Test
    void testEvaluationWithVariablesAndFunctions() {
        // Arrange
        String expression = "sqrt(x) + abs(y) * sin(90)";
        List<Token> tokens = tokenizer.tokenize(expression);
        List<Token> rpn = parser.parse(tokens);
        Map<String, Double> variables = Map.of("x", 16.0, "y", -5.0);

        // Act
        double result = evaluator.evaluate(rpn, variables);

        // Assert
        // Используем дельту для сравнения чисел с плавающей точкой
        assertEquals(9.0, result, 0.00001);
    }

    @Test
    void testDivisionByZero() {
        // Arrange
        String expression = "10 / (x - 5)";
        List<Token> tokens = tokenizer.tokenize(expression);
        List<Token> rpn = parser.parse(tokens);
        Map<String, Double> variables = Map.of("x", 5.0); // x-5 будет 0

        // Act & Assert
        assertThrows(ArithmeticException.class, () -> {
            evaluator.evaluate(rpn, variables);
        });
    }
}
