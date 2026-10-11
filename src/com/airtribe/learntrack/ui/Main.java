package com.airtribe.learntrack.ui;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Person;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.entity.Trainer;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.service.CourseService;
import com.airtribe.learntrack.service.EnrollmentService;
import com.airtribe.learntrack.service.StudentService;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;

import java.util.List;
import java.util.Scanner;

/**
 * Console UI for LearnTrack.
 * Responsibilities: show menus, read input, call services, print results.
 * No business logic lives here.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentService studentService = new StudentService();
    private static final CourseService courseService = new CourseService();
    private static final EnrollmentService enrollmentService =
            new EnrollmentService(studentService, courseService);

    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("   Welcome to LearnTrack (Admin Console)  ");
        System.out.println("=========================================");
        loadSampleData();

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readMenuChoice();
            switch (choice) {
                case 1:
                    studentMenu();
                    break;
                case 2:
                    courseMenu();
                    break;
                case 3:
                    enrollmentMenu();
                    break;
                case 4:
                    showPolymorphismDemo();
                    break;
                case 0:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose from the menu.");
            }
        }
    }

    // ---------------------------------------------------------------- menus

    private static void printMainMenu() {
        System.out.println();
        System.out.println("===== MAIN MENU =====");
        System.out.println("1. Student Management");
        System.out.println("2. Course Management");
        System.out.println("3. Enrollment Management");
        System.out.println("4. Inheritance / Polymorphism Demo");
        System.out.println("0. Exit");
    }

    private static void studentMenu() {
        int choice;
        do {
            System.out.println();
            System.out.println("--- Student Management ---");
            System.out.println("1. Add new student");
            System.out.println("2. View all students");
            System.out.println("3. Search student by ID");
            System.out.println("4. Update student");
            System.out.println("5. Deactivate student");
            System.out.println("0. Back");
            choice = readMenuChoice();
            try {
                switch (choice) {
                    case 1 -> addStudent();
                    case 2 -> viewAllStudents();
                    case 3 -> searchStudent();
                    case 4 -> updateStudent();
                    case 5 -> deactivateStudent();
                    case 0 -> { }
                    default -> System.out.println("Invalid option. Please choose from the menu.");
                }
            } catch (EntityNotFoundException | InvalidInputException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (choice != 0);
    }

    private static void courseMenu() {
        int choice;
        do {
            System.out.println();
            System.out.println("--- Course Management ---");
            System.out.println("1. Add new course");
            System.out.println("2. View all courses");
            System.out.println("3. Activate course");
            System.out.println("4. Deactivate course");
            System.out.println("0. Back");
            choice = readMenuChoice();
            try {
                switch (choice) {
                    case 1 -> addCourse();
                    case 2 -> viewAllCourses();
                    case 3 -> {
                        int id = readInt("Enter course ID to activate: ");
                        courseService.activateCourse(id);
                        System.out.println("Course " + id + " activated.");
                    }
                    case 4 -> {
                        int id = readInt("Enter course ID to deactivate: ");
                        courseService.deactivateCourse(id);
                        System.out.println("Course " + id + " deactivated.");
                    }
                    case 0 -> { }
                    default -> System.out.println("Invalid option. Please choose from the menu.");
                }
            } catch (EntityNotFoundException | InvalidInputException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (choice != 0);
    }

    private static void enrollmentMenu() {
        int choice;
        do {
            System.out.println();
            System.out.println("--- Enrollment Management ---");
            System.out.println("1. Enroll a student in a course");
            System.out.println("2. View enrollments for a student");
            System.out.println("3. View all enrollments");
            System.out.println("4. Mark enrollment as COMPLETED");
            System.out.println("5. Mark enrollment as CANCELLED");
            System.out.println("0. Back");
            choice = readMenuChoice();
            try {
                switch (choice) {
                    case 1 -> enrollStudent();
                    case 2 -> viewEnrollmentsForStudent();
                    case 3 -> printList(enrollmentService.listEnrollments(), "No enrollments yet.");
                    case 4 -> {
                        int id = readInt("Enter enrollment ID: ");
                        enrollmentService.markCompleted(id);
                        System.out.println("Enrollment " + id + " marked as COMPLETED.");
                    }
                    case 5 -> {
                        int id = readInt("Enter enrollment ID: ");
                        enrollmentService.markCancelled(id);
                        System.out.println("Enrollment " + id + " marked as CANCELLED.");
                    }
                    case 0 -> { }
                    default -> System.out.println("Invalid option. Please choose from the menu.");
                }
            } catch (EntityNotFoundException | InvalidInputException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (choice != 0);
    }

    // ------------------------------------------------------ student actions

    private static void addStudent() throws InvalidInputException {
        String firstName = InputValidator.requireNonBlank(readLine("First name: "), "First name");
        String lastName = InputValidator.requireNonBlank(readLine("Last name: "), "Last name");
        String email = readLine("Email (press Enter to skip): ");
        String batch = InputValidator.requireNonBlank(readLine("Batch: "), "Batch");

        Student student;
        if (email.isEmpty()) {
            student = studentService.addStudent(firstName, lastName, batch);
        } else {
            if (!InputValidator.isValidEmail(email)) {
                throw new InvalidInputException("'" + email + "' is not a valid email address.");
            }
            student = studentService.addStudent(firstName, lastName, email, batch);
        }
        System.out.println("Student added: " + student);
    }

    private static void viewAllStudents() {
        printList(studentService.listStudents(), "No students found.");
    }

    private static void searchStudent() throws InvalidInputException, EntityNotFoundException {
        int id = readInt("Enter student ID: ");
        System.out.println(studentService.findStudentById(id));
    }

    private static void updateStudent() throws InvalidInputException, EntityNotFoundException {
        int id = readInt("Enter student ID to update: ");
        Student current = studentService.findStudentById(id);
        System.out.println("Current: " + current);
        System.out.println("Press Enter to keep the existing value.");
        String firstName = readLine("New first name: ");
        String lastName = readLine("New last name: ");
        String email = readLine("New email: ");
        String batch = readLine("New batch: ");
        if (!email.isEmpty() && !InputValidator.isValidEmail(email)) {
            throw new InvalidInputException("'" + email + "' is not a valid email address.");
        }
        studentService.updateStudent(id, firstName, lastName, email, batch);
        System.out.println("Updated: " + studentService.findStudentById(id));
    }

    private static void deactivateStudent() throws InvalidInputException, EntityNotFoundException {
        int id = readInt("Enter student ID to deactivate: ");
        studentService.deactivateStudent(id);
        System.out.println("Student " + id + " deactivated.");
    }

    // ------------------------------------------------------- course actions

    private static void addCourse() throws InvalidInputException {
        String name = InputValidator.requireNonBlank(readLine("Course name: "), "Course name");
        String description = readLine("Description: ");
        int weeks = InputValidator.parsePositiveInt(readLine("Duration (weeks): "));
        Course course = courseService.addCourse(name, description, weeks);
        System.out.println("Course added: " + course);
    }

    private static void viewAllCourses() {
        printList(courseService.listCourses(), "No courses found.");
    }

    // --------------------------------------------------- enrollment actions

    private static void enrollStudent() throws InvalidInputException, EntityNotFoundException {
        int studentId = readInt("Enter student ID: ");
        int courseId = readInt("Enter course ID: ");
        Enrollment enrollment = enrollmentService.enrollStudent(studentId, courseId);
        System.out.println("Enrolled successfully: " + enrollment);
    }

    private static void viewEnrollmentsForStudent() throws InvalidInputException, EntityNotFoundException {
        int studentId = readInt("Enter student ID: ");
        Student student = studentService.findStudentById(studentId);
        System.out.println("Enrollments for " + student.getDisplayName() + ":");
        List<Enrollment> list = enrollmentService.getEnrollmentsForStudent(studentId);
        if (list.isEmpty()) {
            System.out.println("  This student has no enrollments.");
            return;
        }
        for (Enrollment e : list) {
            String courseName = describeCourse(e.getCourseId());
            System.out.println("  " + e + " | " + courseName);
        }
    }

    private static String describeCourse(int courseId) {
        try {
            return courseService.findCourseById(courseId).getCourseName();
        } catch (EntityNotFoundException e) {
            return "(unknown course)";
        }
    }

    // ------------------------------------------------------------ demo

    /** Shows runtime polymorphism: same method call, different behaviour. */
    private static void showPolymorphismDemo() {
        Person[] people = {
            new Person(0, "Generic", "Person", null),
            new Student(0, "Riya", "Sen", "riya@example.com", "Batch-B"),
            new Trainer(IdGenerator.getNextTrainerId(), "Arjun", "Mehta", "arjun@example.com", "Core Java")
        };
        // Demo objects are not saved, so the Student uses id 0 to avoid consuming a real student ID.
        System.out.println();
        System.out.println("Calling getDisplayName() on Person references:");
        for (Person person : people) {
            System.out.println("  " + person.getClass().getSimpleName() + " -> " + person.getDisplayName());
        }
    }

    // ------------------------------------------------------- input helpers

    private static String readLine(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) {
            System.out.println();
            System.out.println("Input closed. Exiting LearnTrack.");
            System.exit(0);
        }
        return scanner.nextLine().trim();
    }

    private static int readInt(String prompt) throws InvalidInputException {
        return InputValidator.parseInt(readLine(prompt));
    }

    /** Reads a menu choice; returns -1 for non-numeric input so the menu reports "invalid option". */
    private static int readMenuChoice() {
        try {
            return readInt("Choose an option: ");
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
            return -1;
        }
    }

    private static <T> void printList(List<T> items, String emptyMessage) {
        if (items.isEmpty()) {
            System.out.println(emptyMessage);
            return;
        }
        for (T item : items) {
            System.out.println("  " + item);
        }
    }

    private static void loadSampleData() {
        studentService.addStudent("Amit", "Sharma", "amit@example.com", "Batch-A");
        studentService.addStudent("Neha", "Gupta", "Batch-A");
        courseService.addCourse("Core Java", "Java fundamentals and OOP", 8);
        courseService.addCourse("Spring Boot", "Building REST APIs", 6);
        System.out.println("Sample data loaded: 2 students (IDs 1-2), 2 courses (IDs 101-102).");
    }
}
