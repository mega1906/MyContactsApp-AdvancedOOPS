package com.mycontactapp.model;

import com.mycontactapp.exception.ValidationException;
import com.mycontactapp.util.InputValidator;

public class PhoneNumber {

    private final String value;

    public PhoneNumber(String value) throws ValidationException {
        InputValidator.validatePhoneNumber(value);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public PhoneNumber copy() {
        try {
            return new PhoneNumber(value);
        } catch (ValidationException exception) {
            throw new IllegalStateException("Invalid stored phone number.", exception);
        }
    }
}
