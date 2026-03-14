package com.mycontactapp.controller;

import com.mycontactapp.model.User;
import com.mycontactapp.service.SessionManager;

import java.util.Optional;

public class ProfileController {

    public void viewProfile() {
        Optional<User> userOptional = SessionManager.getInstance().getLoggedInUser();

        if (userOptional.isEmpty()) {
            System.out.println("No user is currently logged in.");
            return;
        }

        User user = userOptional.get();

        System.out.println();
        System.out.println("Profile Details");
        System.out.println("User Id    : " + user.getUserId());
        System.out.println("User Type  : " + user.getUserType());
        System.out.println("Full Name  : " + user.getFullName());
        System.out.println("Email      : " + user.getEmail());
        System.out.println("Phone      : " + user.getPhoneNumber());
        System.out.println("City       : " + user.getCity());
    }
}
