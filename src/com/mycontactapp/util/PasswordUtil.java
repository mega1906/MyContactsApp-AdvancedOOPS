package com.mycontactapp.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {

    private PasswordUtil() {
    }

    public static String hashPassword(String password) {
        try {
            // SHA-256 demonstrating password hashing.
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
            return convertToHex(hashedBytes);
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("Password hashing is not available.", exception);
        }
    }

    private static String convertToHex(byte[] hashedBytes) {
        StringBuilder stringBuilder = new StringBuilder();

        for (byte hashedByte : hashedBytes) {
            stringBuilder.append(String.format("%02x", hashedByte));
        }

        return stringBuilder.toString();
    }
}
