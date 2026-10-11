package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.util.IdGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Business logic for managing students. Data is stored in memory.
 */
public class StudentService {

    private final List<Student> students = new ArrayList<>();

    /** Adds a student without an email (method overloading). */
    public Student addStudent(String firstName, String lastName, String batch) {
        Student student = new Student(IdGenerator.getNextStudentId(), firstName, lastName, batch);
        students.add(student);
        return student;
    }

    /** Adds a student with an email (method overloading). */
    public Student addStudent(String firstName, String lastName, String email, String batch) {
        Student student = new Student(IdGenerator.getNextStudentId(), firstName, lastName, email, batch);
        students.add(student);
        return student;
    }

    public List<Student> listStudents() {
        return new ArrayList<>(students);
    }

    /** Returns only active students. */
    public List<Student> listActiveStudents() {
        List<Student> active = new ArrayList<>();
        for (Student student : students) {
            if (student.isActive()) {
                active.add(student);
            }
        }
        return active;
    }

    public Student findStudentById(int id) throws EntityNotFoundException {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        throw new EntityNotFoundException("Student", id);
    }

    public void updateStudent(int id, String firstName, String lastName, String email, String batch)
            throws EntityNotFoundException {
        Student student = findStudentById(id);
        if (isProvided(firstName)) {
            student.setFirstName(firstName);
        }
        if (isProvided(lastName)) {
            student.setLastName(lastName);
        }
        if (isProvided(email)) {
            student.setEmail(email);
        }
        if (isProvided(batch)) {
            student.setBatch(batch);
        }
    }

    /** Soft delete: marks the student inactive instead of removing it. */
    public void deactivateStudent(int id) throws EntityNotFoundException {
        findStudentById(id).setActive(false);
    }

    /** Hard delete: removes the student from the list completely. */
    public void removeStudent(int id) throws EntityNotFoundException {
        students.remove(findStudentById(id));
    }

    /** Package-private (default access) helper: only visible inside the service package. */
    boolean isProvided(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
