package com.airtribe.learntrack.entity;

/**
 * A trainer who teaches courses. Added mainly to demonstrate inheritance
 * and method overriding alongside Student.
 */
public class Trainer extends Person {

    private String expertise;

    public Trainer(int id, String firstName, String lastName, String email, String expertise) {
        super(id, firstName, lastName, email);
        this.expertise = expertise;
    }

    /** Overrides Person#getDisplayName with trainer-specific formatting. */
    @Override
    public String getDisplayName() {
        return "Trainer " + super.getDisplayName() + " [" + expertise + "]";
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }
}
