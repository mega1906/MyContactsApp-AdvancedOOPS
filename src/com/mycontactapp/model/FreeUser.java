package com.mycontactapp.model;

public class FreeUser extends User {

    public FreeUser(String userId, String fullName, String email, String phoneNumber, String passwordHash, String city) {
        super(userId, fullName, email, phoneNumber, passwordHash, city);
    }

    @Override
    public String getUserType() {
        return "Free User";
    }
}
