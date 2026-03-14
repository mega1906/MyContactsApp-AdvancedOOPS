package com.mycontactapp.command;

import com.mycontactapp.exception.ValidationException;
import com.mycontactapp.model.User;
import com.mycontactapp.util.PasswordUtil;

public class ChangePasswordCommand implements ProfileCommand {

    private final User user;
    private final String currentPassword;
    private final String newPassword;

    public ChangePasswordCommand(User user, String currentPassword, String newPassword) {
        this.user = user;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    @Override
    public void execute() throws ValidationException {
        String currentPasswordHash = PasswordUtil.hashPassword(currentPassword);

        if (!user.getPasswordHash().equals(currentPasswordHash)) {
            throw new ValidationException("Current password is incorrect.");
        }

        user.setPassword(newPassword);
    }
}
