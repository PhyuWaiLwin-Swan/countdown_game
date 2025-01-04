package com.example.countdown_game.unitTest.utils;


import com.example.countdown_game.utils.InputValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputValidatorTest {

    private final InputValidator inputValidator = new InputValidator();

    @Test
    void testValidInput() {
        assertTrue(inputValidator.validateInput("John Doe"), "Valid input with letters and spaces should pass.");
        assertTrue(inputValidator.validateInput("ValidName"), "Valid input with only letters should pass.");
        assertTrue(inputValidator.validateInput("Simple Test"), "Valid input with letters and spaces should pass.");
    }

    @Test
    void testInvalidInputWithSpecialCharacters() {
        assertFalse(inputValidator.validateInput("John@Doe"), "Input with special characters should fail.");
        assertFalse(inputValidator.validateInput("Hello#World!"), "Input with special characters should fail.");
        assertFalse(inputValidator.validateInput("12345"), "Input with numbers only should fail.");
    }

    @Test
    void testInvalidInputWithNumbers() {
        assertFalse(inputValidator.validateInput("Player123"), "Input with letters and numbers should fail.");
        assertFalse(inputValidator.validateInput("12345"), "Input with only numbers should fail.");
    }

    @Test
    void testInvalidInputExceedingMaxLength() {
        assertFalse(inputValidator.validateInput("ThisInputIsWayTooLongToBeValid"),
                "Input exceeding 20 characters should fail.");
    }

    @Test
    void testNullInput() {
        assertFalse(inputValidator.validateInput(null), "Null input should fail.");
    }

    @Test
    void testEmptyInput() {
        assertTrue(inputValidator.validateInput(""), "Empty input should fail.");
    }

    @Test
    void testInputAtBoundaryLength() {
        assertFalse(inputValidator.validateInput("ExactLengthTwenty!!"),
                "Input of exact length with invalid characters should fail.");
    }
}
