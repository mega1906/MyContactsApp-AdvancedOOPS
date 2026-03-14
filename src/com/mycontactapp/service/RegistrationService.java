package com.mycontactapp.service;

import com.mycontactapp.builder.UserBuilder;
import com.mycontactapp.exception.ValidationException;
import com.mycontactapp.factory.UserFactory;
import com.mycontactapp.model.User;
import com.mycontactapp.util.InputValidator;
import com.mycontactapp.util.PasswordUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RegistrationService {

    // List to store users data
    private static final List<User> REGISTERED_USERS = new ArrayList<>();

    private final UserFactory userFactory;

    public RegistrationService() {
        this.userFactory = new UserFactory();
    }

    public User registerUser(String userType, String fullName, String email, String phoneNumber,
                             String password, String city) throws ValidationException {
        validateRegistrationData(fullName, email, phoneNumber, password, city);
        ensureEmailIsUnique(email);

        // Builder collects all user data before factory creates the final object.
        UserBuilder userBuilder = new UserBuilder()
                .setUserId(generateUserId())
                .setFullName(fullName)
                .setEmail(email)
                .setPhoneNumber(phoneNumber)
                .setPasswordHash(PasswordUtil.hashPassword(password))
                .setCity(city);

        User user = userFactory.createUser(userType, userBuilder);
        REGISTERED_USERS.add(user);
        return user;
    }

    private void validateRegistrationData(String fullName, String email, String phoneNumber,
                                          String password, String city) throws ValidationException {
        InputValidator.validateName(fullName);
        InputValidator.validateEmail(email);
        InputValidator.validatePhoneNumber(phoneNumber);
        InputValidator.validatePassword(password);
        InputValidator.validateCity(city);
    }

    private void ensureEmailIsUnique(String email) throws ValidationException {
        for (User user : REGISTERED_USERS) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                throw new ValidationException("This email is already registered.");
            }
        }
    }

    private String generateUserId() {
        return "USR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
