package com.mycontactapp.service;

import com.mycontactapp.builder.UserBuilder;
import com.mycontactapp.exception.ValidationException;
import com.mycontactapp.factory.UserFactory;
import com.mycontactapp.model.User;
import com.mycontactapp.util.InputValidator;
import com.mycontactapp.util.PasswordUtil;

import java.util.UUID;

public class RegistrationService {

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
        UserStore.addUser(user);
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
        if (UserStore.findUserByEmail(email).isPresent()) {
            throw new ValidationException("This email is already registered.");
        }
    }

    private String generateUserId() {
        return "USR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
