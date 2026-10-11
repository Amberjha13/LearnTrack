package com.airtribe.learntrack.exception;

/**
 * Thrown when user input or a requested operation is not valid.
 */
public class InvalidInputException extends Exception {

    public InvalidInputException(String message) {
        super(message);
    }
}
