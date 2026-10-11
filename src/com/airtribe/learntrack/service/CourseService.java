package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.util.IdGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Business logic for managing courses. Data is stored in memory.
 */
public class CourseService {

    private final List<Course> courses = new ArrayList<>();

    public Course addCourse(String courseName, String description, int durationInWeeks) {
        Course course = new Course(IdGenerator.getNextCourseId(), courseName, description, durationInWeeks);
        courses.add(course);
        return course;
    }

    public List<Course> listCourses() {
        return new ArrayList<>(courses);
    }

    public Course findCourseById(int id) throws EntityNotFoundException {
        for (Course course : courses) {
            if (course.getId() == id) {
                return course;
            }
        }
        throw new EntityNotFoundException("Course", id);
    }

    /** Overloaded search: find a course by its name (case-insensitive). */
    public Course findCourseByName(String name) throws EntityNotFoundException {
        for (Course course : courses) {
            if (course.getCourseName().equalsIgnoreCase(name.trim())) {
                return course;
            }
        }
        throw new EntityNotFoundException("Course named '" + name + "' was not found.");
    }

    public void activateCourse(int id) throws EntityNotFoundException {
        findCourseById(id).setActive(true);
    }

    public void deactivateCourse(int id) throws EntityNotFoundException {
        findCourseById(id).setActive(false);
    }
}
