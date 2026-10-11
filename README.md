# LearnTrack – Student & Course Management System

LearnTrack is a console-based admin application built with **Core Java**. It lets an admin manage
**Students**, **Courses** and **Enrollments** entirely in memory, and was built to practise Java
fundamentals: OOP, encapsulation, inheritance, polymorphism, static members, `ArrayList`, and
exception handling.

## Features

**Student Management** – add (with or without email), view all, search by ID, update, deactivate (soft delete)
**Course Management** – add, view all, activate / deactivate
**Enrollment Management** – enroll a student in a course, view a student's enrollments, view all, mark as COMPLETED / CANCELLED
**Polymorphism Demo** – calls `getDisplayName()` on `Person`, `Student` and `Trainer` objects through a `Person` reference

Business rules enforced by the services:
- Inactive students cannot be enrolled; inactive courses cannot accept enrollments.
- A student cannot be actively enrolled in the same course twice.
- Only ACTIVE enrollments can be marked COMPLETED or CANCELLED.

Sample data (2 students, 2 courses) is loaded at start-up so you can try things immediately.

## Project Structure

```
LearnTrack/
├── README.md
├── docs/
│   ├── Setup_Instructions.md
│   ├── JVM_Basics.md
│   ├── Design_Notes.md
│   └── HelloWorld.java
└── src/com/airtribe/learntrack/
    ├── entity/      Person, Student, Trainer, Course, Enrollment, EnrollmentStatus
    ├── service/     StudentService, CourseService, EnrollmentService
    ├── ui/          Main (menu-driven console)
    ├── exception/   EntityNotFoundException, InvalidInputException
    └── util/        IdGenerator, InputValidator
```

## How to Compile and Run

Requires JDK 17 or later (developed and tested on JDK 21).

### From the terminal

From the `LearnTrack` folder:

**macOS / Linux**
```bash
javac -d out $(find src -name "*.java")
java -cp out com.airtribe.learntrack.ui.Main
```

**Windows (PowerShell)**
```powershell
javac -d out (Get-ChildItem -Recurse -Filter *.java src).FullName
java -cp out com.airtribe.learntrack.ui.Main
```

**Windows (Command Prompt)**
```cmd
dir /s /b src\*.java > sources.txt
javac -d out @sources.txt
java -cp out com.airtribe.learntrack.ui.Main
```

### From an IDE (IntelliJ IDEA / Eclipse / VS Code)

1. Open the `LearnTrack` folder as a project.
2. Mark `src` as the *Sources Root* (IntelliJ: right-click `src` → Mark Directory as → Sources Root).
3. Open `com.airtribe.learntrack.ui.Main` and click **Run**.

## Sample Session

```
===== MAIN MENU =====
1. Student Management
2. Course Management
3. Enrollment Management
4. Inheritance / Polymorphism Demo
0. Exit
Choose an option: 3
--- Enrollment Management ---
1. Enroll a student in a course
...
Choose an option: 1
Enter student ID: 2
Enter course ID: 101
Enrolled successfully: Enrollment ID: 1001 | Student ID: 2 | Course ID: 101 | Date: 2026-10-11 | ACTIVE
```

Invalid input is handled without crashing:
```
Choose an option: abc
Error: 'abc' is not a valid number.
Invalid option. Please choose from the menu.

Enter student ID: 99
Error: Student with ID 99 was not found.
```
