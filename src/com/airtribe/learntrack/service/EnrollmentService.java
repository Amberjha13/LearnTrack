package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.EnrollmentStatus;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.util.IdGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Business logic for enrolling students into courses.
 * Depends on StudentService and CourseService to validate IDs.
 */
public class EnrollmentService {

    private final List<Enrollment> enrollments = new ArrayList<>();
    private final StudentService studentService;
    private final CourseService courseService;

    public EnrollmentService(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    public Enrollment enrollStudent(int studentId, int courseId)
            throws EntityNotFoundException, InvalidInputException {
        Student student = studentService.findStudentById(studentId);
        Course course = courseService.findCourseById(courseId);

        if (!student.isActive()) {
            throw new InvalidInputException("Student " + studentId + " is inactive and cannot be enrolled.");
        }
        if (!course.isActive()) {
            throw new InvalidInputException("Course " + courseId + " is inactive and cannot accept enrollments.");
        }
        if (isAlreadyEnrolled(studentId, courseId)) {
            throw new InvalidInputException("Student " + studentId + " is already actively enrolled in course " + courseId + ".");
        }

        Enrollment enrollment = new Enrollment(IdGenerator.getNextEnrollmentId(), studentId, courseId);
        enrollments.add(enrollment);
        return enrollment;
    }

    public List<Enrollment> listEnrollments() {
        return new ArrayList<>(enrollments);
    }

    public List<Enrollment> getEnrollmentsForStudent(int studentId) throws EntityNotFoundException {
        studentService.findStudentById(studentId); // validates the student exists
        List<Enrollment> result = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudentId() == studentId) {
                result.add(enrollment);
            }
        }
        return result;
    }

    public Enrollment findEnrollmentById(int id) throws EntityNotFoundException {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getId() == id) {
                return enrollment;
            }
        }
        throw new EntityNotFoundException("Enrollment", id);
    }

    public void markCompleted(int enrollmentId) throws EntityNotFoundException, InvalidInputException {
        updateStatus(enrollmentId, EnrollmentStatus.COMPLETED);
    }

    public void markCancelled(int enrollmentId) throws EntityNotFoundException, InvalidInputException {
        updateStatus(enrollmentId, EnrollmentStatus.CANCELLED);
    }

    private void updateStatus(int enrollmentId, EnrollmentStatus newStatus)
            throws EntityNotFoundException, InvalidInputException {
        Enrollment enrollment = findEnrollmentById(enrollmentId);
        if (enrollment.getStatus() != EnrollmentStatus.ACTIVE) {
            throw new InvalidInputException("Enrollment " + enrollmentId + " is already "
                    + enrollment.getStatus() + " and cannot be changed.");
        }
        enrollment.setStatus(newStatus);
    }

    /** Package-private (default access) helper. */
    boolean isAlreadyEnrolled(int studentId, int courseId) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudentId() == studentId
                    && enrollment.getCourseId() == courseId
                    && enrollment.getStatus() == EnrollmentStatus.ACTIVE) {
                return true;
            }
        }
        return false;
    }
}
