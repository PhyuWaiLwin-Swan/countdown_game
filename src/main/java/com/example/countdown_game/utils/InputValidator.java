package com.example.countdown_game.utils;

/**
 * Backend input validator
 */
public class InputValidator {

    /**
     * Validates the input for the following rules:
     * 1. Only letters and spaces are allowed.
     * 2. The input length must not exceed 20 characters.
     *
     * @param input The input string to validate.
     * @return True if the input is valid, false otherwise.
     */
    public boolean validateInput(String input) {
        if (input == null) {
            return false; // Null input is invalid
        }

        // Allow empty string
        if (input.isEmpty()) {
            return true; // Empty string is valid
        }

        // Check for valid characters: letters and spaces only
        String validPattern = "^[a-zA-Z ]+$";
        if (!input.matches(validPattern)) {
            return false;
        }

        // Check for maximum length
        return input.length() <= 20;
    }

}
