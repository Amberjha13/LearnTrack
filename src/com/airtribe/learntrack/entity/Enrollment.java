package com.airtribe.learntrack.entity;

import java.time.LocalDate;

/**
 * Links a student to a course.
 */
public class Enrollment {

    private int id;
    private int studentId;
    private int courseId;
    private LocalDate enrollmentDate;
    private EnrollmentStatus status;

    public Enrollment() {
        this.status = EnrollmentStatus.ACTIVE;
    }

    /** Creates an enrollment dated today. */
    public Enrollment(int id, int studentId, int courseId) {
        this(id, studentId, courseId, LocalDate.now());
    }

    /** Creates an enrollment with an explicit date (constructor overloading). */
    public Enrollment(int id, int studentId, int courseId, LocalDate enrollmentDate) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrollmentDate = enrollmentDate;
        this.status = EnrollmentStatus.ACTIVE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public void setStatus(EnrollmentStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("Enrollment ID: %-4d | Student ID: %-4d | Course ID: %-4d | Date: %s | %s",
                id, studentId, courseId, enrollmentDate, status);
    }
}
