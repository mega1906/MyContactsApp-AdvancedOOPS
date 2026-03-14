package com.mycontactapp.service;

import com.mycontactapp.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserStore {

    // Shared in-memory store for registration and login in the same app run.
    private static final List<User> REGISTERED_USERS = new ArrayList<>();

    private UserStore() {
    }

    public static void addUser(User user) {
        REGISTERED_USERS.add(user);
    }

    public static Optional<User> findUserByEmail(String email) {
        for (User user : REGISTERED_USERS) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }
}
