package com.mycontactapp.service;

import com.mycontactapp.auth.AuthenticationStrategy;
import com.mycontactapp.auth.BasicAuth;
import com.mycontactapp.auth.OAuthAuth;
import com.mycontactapp.exception.ValidationException;
import com.mycontactapp.model.User;

import java.util.Optional;

public class AuthenticationService {

    public Optional<User> login(String authType, String email, String secret) throws ValidationException {
        AuthenticationStrategy authenticationStrategy = getStrategy(authType);
        Optional<User> userOptional = authenticationStrategy.authenticate(email, secret);

        userOptional.ifPresent(user -> SessionManager.getInstance().startSession(user));
        return userOptional;
    }

    private AuthenticationStrategy getStrategy(String authType) throws ValidationException {
        // Strategy picks the correct login mechanism at runtime.
        if ("BASIC".equalsIgnoreCase(authType)) {
            return new BasicAuth();
        }

        if ("OAUTH".equalsIgnoreCase(authType)) {
            return new OAuthAuth();
        }

        throw new ValidationException("Unsupported authentication method.");
    }
}
