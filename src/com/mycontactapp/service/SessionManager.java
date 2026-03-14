package com.mycontactapp.service;

import com.mycontactapp.model.User;

import java.util.Optional;

public class SessionManager {

    private static SessionManager instance;

    private User loggedInUser;

    private SessionManager() {
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }

        return instance;
    }

    public void startSession(User user) {
        this.loggedInUser = user;
    }

    public Optional<User> getLoggedInUser() {
        return Optional.ofNullable(loggedInUser);
    }

    public void endSession() {
        this.loggedInUser = null;
    }
}
