package com.mycontactapp.factory;

import com.mycontactapp.builder.UserBuilder;
import com.mycontactapp.exception.ValidationException;
import com.mycontactapp.model.FreeUser;
import com.mycontactapp.model.PremiumUser;
import com.mycontactapp.model.User;

public class UserFactory {

    public User createUser(String userType, UserBuilder userBuilder) throws ValidationException {
        // Factory decides which concrete user object should be created.
        if ("FREE".equalsIgnoreCase(userType)) {
            return new FreeUser(
                    userBuilder.getUserId(),
                    userBuilder.getFullName(),
                    userBuilder.getEmail(),
                    userBuilder.getPhoneNumber(),
                    userBuilder.getPasswordHash(),
                    userBuilder.getCity()
            );
        }

        if ("PREMIUM".equalsIgnoreCase(userType)) {
            return new PremiumUser(
                    userBuilder.getUserId(),
                    userBuilder.getFullName(),
                    userBuilder.getEmail(),
                    userBuilder.getPhoneNumber(),
                    userBuilder.getPasswordHash(),
                    userBuilder.getCity()
            );
        }

        throw new ValidationException("Invalid user type selected.");
    }
}
