# Design Notes

## Overall Design

The code is split by responsibility (separation of concerns):

| Package     | Responsibility                                                |
|-------------|---------------------------------------------------------------|
| `entity`    | Plain data classes with private fields and getters/setters    |
| `service`   | Business logic and in-memory storage (`ArrayList`)            |
| `ui`        | `Main` – menus, reading input, printing results               |
| `exception` | Custom checked exceptions for clear error messages            |
| `util`      | Stateless static helpers (`IdGenerator`, `InputValidator`)    |

`Main` never touches the lists directly; it only calls service methods. This keeps the UI simple
and means the logic could be reused later (for example, behind a REST API or with a database).

## Why ArrayList instead of an array?

- **Dynamic size:** an array has a fixed length chosen when it is created (`new Student[10]`). We do
  not know in advance how many students, courses or enrollments the admin will add. An `ArrayList`
  grows automatically as items are added.
- **Convenient methods:** `add()`, `remove()`, `size()`, `isEmpty()` and `contains()` are built in.
  With an array we would have to track the "used" count ourselves and shift elements manually on removal.
- **Works with generics:** `List<Student>` is type-safe, so the compiler stops us from putting a
  `Course` into the student list.
- **Easy to return copies:** services return `new ArrayList<>(students)` so callers cannot modify the
  internal list by accident.

An array would only be preferable for fixed-size, performance-critical data, which is not the case here.

## Where static members are used and why

| Location | Static member | Reason |
|----------|---------------|--------|
| `IdGenerator` | `private static int studentIdCounter`, `courseIdCounter`, `enrollmentIdCounter`, `trainerIdCounter` | IDs must be unique across the whole application. A static counter belongs to the class, so there is exactly one copy no matter how many objects exist. |
| `IdGenerator` | `getNextStudentId()`, `getNextCourseId()`, etc. | These methods don't depend on any object state, so callers use them directly: `IdGenerator.getNextStudentId()`. |
| `InputValidator` | `parseInt()`, `parsePositiveInt()`, `requireNonBlank()`, `isValidEmail()` | Pure helper functions with no state – no reason to create an object. |
| `IdGenerator`, `InputValidator` | `private` constructor | Prevents anyone from creating instances of utility classes. |
| `Main` | `static final` `Scanner` and service fields | `main` is static, so the objects it uses are created once and shared by all the static menu methods. |

Instance (non-static) members are used everywhere else – e.g., every `Student` object has its own
`firstName` and `active`, and every service holds its own list.

## Where inheritance is used and what we gained

```
        Person  (id, firstName, lastName, email, getDisplayName())
        /    \
   Student   Trainer
 (batch,     (expertise)
  active)
```

- **Code reuse:** `id`, `firstName`, `lastName`, `email` and their getters/setters are written once in
  `Person` instead of being duplicated in `Student` and `Trainer`.
- **`super` in constructors:** `Student` and `Trainer` call `super(id, firstName, lastName, email)` so
  the parent initialises its own fields.
- **Method overriding:** `Person.getDisplayName()` returns `"First Last"`.
  `Student` overrides it to add the batch, and `Trainer` overrides it to add the expertise. `Student`
  also calls `super.getDisplayName()` to build on the parent version.
- **Polymorphism:** menu option 4 stores a `Person`, `Student` and `Trainer` in a `Person[]` array and
  calls `getDisplayName()` on each. The JVM picks the correct overridden version at runtime.
- **Easy extension:** a new type of person (e.g., `Admin`) can be added by extending `Person` without
  touching existing code.

## Other OOP points demonstrated

- **Encapsulation:** all entity fields are `private` and accessed through getters/setters.
- **Constructor overloading:** `Student(id, first, last, batch)` and `Student(id, first, last, email, batch)`;
  `Enrollment(id, studentId, courseId)` and `Enrollment(id, studentId, courseId, date)`. The shorter
  ones delegate with `this(...)`.
- **Method overloading:** `StudentService.addStudent(...)` with 3 or 4 parameters;
  `CourseService.findCourseById(int)` and `findCourseByName(String)`;
  `EntityNotFoundException(String)` and `EntityNotFoundException(String, int)`.
- **Access modifiers:** `public` for the API classes and methods, `private` for fields and internal
  helpers (`EnrollmentService.updateStatus`), and **default (package-private)** for helpers that only
  the service package needs (`StudentService.isProvided`, `EnrollmentService.isAlreadyEnrolled`).
- **Enum:** `EnrollmentStatus` (ACTIVE, COMPLETED, CANCELLED) prevents typos that a plain String would allow.

## Exception handling

- `EntityNotFoundException` is thrown by `find...ById` methods when an ID does not exist.
- `InvalidInputException` is thrown for bad numbers, empty required fields, invalid emails, and rule
  violations (inactive student, duplicate enrollment, changing a finished enrollment).
- `InputValidator.parseInt` wraps `Integer.parseInt` in `try-catch` and converts
  `NumberFormatException` into a friendly `InvalidInputException`.
- Each sub-menu in `Main` wraps its operations in a single `try-catch`, prints `Error: <message>` and
  returns to the menu, so the program never crashes on bad input.
- Soft delete (`active = false`) is used for students and courses so that existing enrollment history
  stays valid.
