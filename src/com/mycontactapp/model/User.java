package com.mycontactapp.model;

import com.mycontactapp.exception.ValidationException;
import com.mycontactapp.util.InputValidator;
import com.mycontactapp.util.PasswordUtil;

public abstract class User {

    // User data is kept private to maintain encapsulation.
    private final String userId;
    private String fullName;
    private final String email;
    private String phoneNumber;
    private String passwordHash;
    private String city;
    private boolean emailNotificationsEnabled;
    private boolean contactViewEnabled;

    protected User(String userId, String fullName, String email, String phoneNumber, String passwordHash, String city) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.passwordHash = passwordHash;
        this.city = city;
        this.emailNotificationsEnabled = true;
        this.contactViewEnabled = true;
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

    public boolean isEmailNotificationsEnabled() {
        return emailNotificationsEnabled;
    }

    public boolean isContactViewEnabled() {
        return contactViewEnabled;
    }

    public void setFullName(String fullName) throws ValidationException {
        InputValidator.validateName(fullName);
        this.fullName = fullName;
    }

    public void setPhoneNumber(String phoneNumber) throws ValidationException {
        InputValidator.validatePhoneNumber(phoneNumber);
        this.phoneNumber = phoneNumber;
    }

    public void setCity(String city) throws ValidationException {
        InputValidator.validateCity(city);
        this.city = city;
    }

    public void setPassword(String password) throws ValidationException {
        InputValidator.validatePassword(password);
        this.passwordHash = PasswordUtil.hashPassword(password);
    }

    public void setEmailNotificationsEnabled(boolean emailNotificationsEnabled) {
        this.emailNotificationsEnabled = emailNotificationsEnabled;
    }

    public void setContactViewEnabled(boolean contactViewEnabled) {
        this.contactViewEnabled = contactViewEnabled;
    }

    public abstract String getUserType();
}
