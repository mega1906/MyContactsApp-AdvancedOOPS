package com.mycontactapp.model;

import com.mycontactapp.exception.ValidationException;
import com.mycontactapp.util.InputValidator;

public class EmailAddress {

    private final String value;

    public EmailAddress(String value) throws ValidationException {
        InputValidator.validateEmail(value);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public EmailAddress copy() {
        try {
            return new EmailAddress(value);
        } catch (ValidationException exception) {
            throw new IllegalStateException("Invalid stored email address.", exception);
        }
    }
}
