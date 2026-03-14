package com.mycontactapp.command;

import com.mycontactapp.exception.ValidationException;
import com.mycontactapp.model.User;

public class UpdateProfileCommand implements ProfileCommand {

    private final User user;
    private final String fullName;
    private final String phoneNumber;
    private final String city;

    public UpdateProfileCommand(User user, String fullName, String phoneNumber, String city) {
        this.user = user;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.city = city;
    }

    @Override
    public void execute() throws ValidationException {
        user.setFullName(fullName);
        user.setPhoneNumber(phoneNumber);
        user.setCity(city);
    }
}
