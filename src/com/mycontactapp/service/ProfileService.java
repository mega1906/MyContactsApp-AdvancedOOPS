package com.mycontactapp.service;

import com.mycontactapp.command.ChangePasswordCommand;
import com.mycontactapp.command.ProfileCommand;
import com.mycontactapp.command.TogglePreferenceCommand;
import com.mycontactapp.command.UpdateProfileCommand;
import com.mycontactapp.exception.ValidationException;
import com.mycontactapp.model.User;

public class ProfileService {

    // Commands keep profile actions separate and easy to extend later.
    public void updateProfile(User user, String fullName, String phoneNumber, String city) throws ValidationException {
        ProfileCommand profileCommand = new UpdateProfileCommand(user, fullName, phoneNumber, city);
        profileCommand.execute();
    }

    public void changePassword(User user, String currentPassword, String newPassword) throws ValidationException {
        ProfileCommand profileCommand = new ChangePasswordCommand(user, currentPassword, newPassword);
        profileCommand.execute();
    }

    public void updatePreference(User user, String preferenceName, boolean enabled) throws ValidationException {
        ProfileCommand profileCommand = new TogglePreferenceCommand(user, preferenceName, enabled);
        profileCommand.execute();
    }
}
