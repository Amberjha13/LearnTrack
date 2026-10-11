package com.airtribe.learntrack.entity;

/**
 * Base class for every person in the system (students, trainers).
 * Holds the common fields and demonstrates encapsulation.
 */
public class Person {

    private int id;
    private String firstName;
    private String lastName;
    private String email;

    public Person() {
        // default constructor
    }

    public Person(int id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    /**
     * Returns a friendly name. Subclasses override this to add their own details
     * (runtime polymorphism).
     */
    public String getDisplayName() {
        return firstName + " " + lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
