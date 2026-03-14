package com.mycontactapp.command;

import com.mycontactapp.exception.ValidationException;
import com.mycontactapp.model.User;

public class TogglePreferenceCommand implements ProfileCommand {

    private final User user;
    private final String preferenceName;
    private final boolean enabled;

    public TogglePreferenceCommand(User user, String preferenceName, boolean enabled) {
        this.user = user;
        this.preferenceName = preferenceName;
        this.enabled = enabled;
    }

    @Override
    public void execute() throws ValidationException {
        if ("EMAIL_NOTIFICATIONS".equalsIgnoreCase(preferenceName)) {
            user.setEmailNotificationsEnabled(enabled);
            return;
        }

        if ("CONTACT_VIEW".equalsIgnoreCase(preferenceName)) {
            user.setContactViewEnabled(enabled);
            return;
        }

        throw new ValidationException("Invalid preference selected.");
    }
}
