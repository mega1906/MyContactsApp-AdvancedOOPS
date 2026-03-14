package com.mycontactapp.model;

public class PremiumUser extends User {

    public PremiumUser(String userId, String fullName, String email, String phoneNumber, String passwordHash, String city) {
        super(userId, fullName, email, phoneNumber, passwordHash, city);
    }

    @Override
    public String getUserType() {
        return "Premium User";
    }
}
