package com.mycontactapp.auth;

import com.mycontactapp.model.User;
import com.mycontactapp.service.UserStore;
import com.mycontactapp.util.PasswordUtil;

import java.util.Optional;

public class BasicAuth implements AuthenticationStrategy {

    @Override
    public Optional<User> authenticate(String email, String secret) {
        Optional<User> userOptional = UserStore.findUserByEmail(email);

        if (userOptional.isPresent()) {
            String hashedPassword = PasswordUtil.hashPassword(secret);

            if (userOptional.get().getPasswordHash().equals(hashedPassword)) {
                return userOptional;
            }
        }

        return Optional.empty();
    }
}
