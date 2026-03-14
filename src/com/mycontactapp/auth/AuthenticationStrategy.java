package com.mycontactapp.auth;

import com.mycontactapp.model.User;

import java.util.Optional;

public interface AuthenticationStrategy {

    Optional<User> authenticate(String email, String secret);
}
