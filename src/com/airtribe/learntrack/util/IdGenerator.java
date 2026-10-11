package com.airtribe.learntrack.util;

/**
 * Generates unique IDs for each entity type.
 * The counters are static, so they are shared across the whole application
 * and no object of this class is ever needed.
 */
public final class IdGenerator {

    private static int studentIdCounter = 1;
    private static int courseIdCounter = 101;
    private static int enrollmentIdCounter = 1001;
    private static int trainerIdCounter = 501;

    private IdGenerator() {
        // utility class: prevent instantiation
    }

    public static int getNextStudentId() {
        return studentIdCounter++;
    }

    public static int getNextCourseId() {
        return courseIdCounter++;
    }

    public static int getNextEnrollmentId() {
        return enrollmentIdCounter++;
    }

    public static int getNextTrainerId() {
        return trainerIdCounter++;
    }
}
