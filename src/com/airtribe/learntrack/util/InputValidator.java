package com.airtribe.learntrack.util;

import com.airtribe.learntrack.exception.InvalidInputException;

/**
 * Small static helpers for validating and parsing user input.
 */
public final class InputValidator {

    private InputValidator() {
        // utility class: prevent instantiation
    }

    /** Parses a String into an int, throwing a friendly exception on failure. */
    public static int parseInt(String input) throws InvalidInputException {
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new InvalidInputException("'" + input + "' is not a valid number.");
        }
    }

    /** Parses a positive int (greater than zero). */
    public static int parsePositiveInt(String input) throws InvalidInputException {
        int value = parseInt(input);
        if (value <= 0) {
            throw new InvalidInputException("Value must be greater than zero.");
        }
        return value;
    }

    /** Ensures the text is not null or blank. */
    public static String requireNonBlank(String input, String fieldName) throws InvalidInputException {
        if (input == null || input.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " cannot be empty.");
        }
        return input.trim();
    }

    /** Very simple email check: optional field, but if given it must contain '@' and '.'. */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return true;
        }
        int atIndex = email.indexOf('@');
        return atIndex > 0 && email.indexOf('.', atIndex) > atIndex + 1;
    }
}
