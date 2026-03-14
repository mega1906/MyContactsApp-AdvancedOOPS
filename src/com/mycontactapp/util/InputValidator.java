package com.mycontactapp.util;

import com.mycontactapp.exception.ValidationException;

import java.util.regex.Pattern;

public class InputValidator {

    // Basic validation rules for console inputs.
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^\\d{10}$");

    private InputValidator() {
    }

    public static void validateName(String fullName) throws ValidationException {
        if (fullName == null || fullName.isBlank()) {
            throw new ValidationException("Full name cannot be empty.");
        }
    }

    public static void validateEmail(String email) throws ValidationException {
        if (email == null || email.isBlank()) {
            throw new ValidationException("Email cannot be empty.");
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new ValidationException("Enter a valid email address.");
        }
    }

    public static void validatePhoneNumber(String phoneNumber) throws ValidationException {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new ValidationException("Phone number cannot be empty.");
        }

        if (!PHONE_PATTERN.matcher(phoneNumber).matches()) {
            throw new ValidationException("Phone number must contain exactly 10 digits.");
        }
    }

    public static void validatePassword(String password) throws ValidationException {
        if (password == null || password.isBlank()) {
            throw new ValidationException("Password cannot be empty.");
        }

        if (password.length() < 6) {
            throw new ValidationException("Password must be at least 6 characters long.");
        }
    }

    public static void validateCity(String city) throws ValidationException {
        if (city == null || city.isBlank()) {
            throw new ValidationException("City cannot be empty.");
        }
    }
}
