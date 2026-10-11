package com.airtribe.learntrack.exception;

/**
 * Thrown when a Student, Course or Enrollment cannot be found by its ID.
 */
public class EntityNotFoundException extends Exception {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String entityName, int id) {
        super(entityName + " with ID " + id + " was not found.");
    }
}
