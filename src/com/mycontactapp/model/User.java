package com.mycontactapp.model;

public abstract class User {

    // User data is kept private to maintain encapsulation.
    private final String userId;
    private final String fullName;
    private final String email;
    private final String phoneNumber;
    private final String passwordHash;
    private final String city;

    protected User(String userId, String fullName, String email, String phoneNumber, String passwordHash, String city) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.passwordHash = passwordHash;
        this.city = city;
    }

    public String getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getCity() {
        return city;
    }

    public abstract String getUserType();
}
