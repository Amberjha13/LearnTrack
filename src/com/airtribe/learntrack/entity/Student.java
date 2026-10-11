package com.airtribe.learntrack.entity;

/**
 * A student enrolled in LearnTrack. Inherits common fields from Person.
 */
public class Student extends Person {

    private String batch;
    private boolean active;

    public Student() {
        super();
        this.active = true;
    }

    /** Constructor without email (constructor overloading). */
    public Student(int id, String firstName, String lastName, String batch) {
        this(id, firstName, lastName, null, batch);
    }

    /** Constructor with email (constructor overloading). */
    public Student(int id, String firstName, String lastName, String email, String batch) {
        super(id, firstName, lastName, email);
        this.batch = batch;
        this.active = true;
    }

    /** Overrides Person#getDisplayName to include the batch. */
    @Override
    public String getDisplayName() {
        return super.getDisplayName() + " (Batch: " + batch + ")";
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        String emailText = (getEmail() == null || getEmail().isEmpty()) ? "-" : getEmail();
        return String.format("ID: %-4d | %-30s | Email: %-25s | %s",
                getId(), getDisplayName(), emailText, active ? "ACTIVE" : "INACTIVE");
    }
}
