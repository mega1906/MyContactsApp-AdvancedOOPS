package com.mycontactapp.controller;

import com.mycontactapp.exception.ValidationException;
import com.mycontactapp.model.User;
import com.mycontactapp.service.ProfileService;
import com.mycontactapp.service.SessionManager;
import com.mycontactapp.util.InputValidator;

import java.util.Optional;
import java.util.Scanner;

public class ProfileController {

    private final ProfileService profileService;

    public ProfileController() {
        this.profileService = new ProfileService();
    }

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
        System.out.println("Email Notifications : " + getStatusLabel(user.isEmailNotificationsEnabled()));
        System.out.println("Contact View        : " + getStatusLabel(user.isContactViewEnabled()));
    }

    public void updateProfile(Scanner scanner) {
        Optional<User> userOptional = SessionManager.getInstance().getLoggedInUser();

        if (userOptional.isEmpty()) {
            System.out.println("No user is currently logged in.");
            return;
        }

        User user = userOptional.get();
        boolean editing = true;

        while (editing) {
            printEditProfileMenu();
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        profileService.updateProfile(
                                user,
                                readValidFullName(scanner),
                                user.getPhoneNumber(),
                                user.getCity()
                        );
                        System.out.println("Full name updated successfully.");
                        break;
                    case "2":
                        profileService.updateProfile(
                                user,
                                user.getFullName(),
                                readValidPhoneNumber(scanner),
                                user.getCity()
                        );
                        System.out.println("Phone number updated successfully.");
                        break;
                    case "3":
                        profileService.updateProfile(
                                user,
                                user.getFullName(),
                                user.getPhoneNumber(),
                                readValidCity(scanner)
                        );
                        System.out.println("City updated successfully.");
                        break;
                    case "4":
                        editing = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter 1, 2, 3 or 4.");
                }
            } catch (ValidationException exception) {
                System.out.println("Profile update failed: " + exception.getMessage());
            }
        }
    }

    public void changePassword(Scanner scanner) {
        Optional<User> userOptional = SessionManager.getInstance().getLoggedInUser();

        if (userOptional.isEmpty()) {
            System.out.println("No user is currently logged in.");
            return;
        }

        User user = userOptional.get();

        try {
            String currentPassword = readValidPassword(scanner, "Enter current password: ");
            String newPassword = readValidPassword(scanner, "Enter new password: ");

            profileService.changePassword(user, currentPassword, newPassword);
            System.out.println("Password changed successfully.");
        } catch (ValidationException exception) {
            System.out.println("Password change failed: " + exception.getMessage());
        }
    }

    public void managePreferences(Scanner scanner) {
        Optional<User> userOptional = SessionManager.getInstance().getLoggedInUser();

        if (userOptional.isEmpty()) {
            System.out.println("No user is currently logged in.");
            return;
        }

        User user = userOptional.get();
        boolean managing = true;

        while (managing) {
            printPreferenceMenu(user);
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        profileService.updatePreference(user, "EMAIL_NOTIFICATIONS", !user.isEmailNotificationsEnabled());
                        System.out.println("Email notifications updated.");
                        break;
                    case "2":
                        profileService.updatePreference(user, "CONTACT_VIEW", !user.isContactViewEnabled());
                        System.out.println("Contact view preference updated.");
                        break;
                    case "3":
                        managing = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter 1, 2 or 3.");
                }
            } catch (ValidationException exception) {
                System.out.println("Preference update failed: " + exception.getMessage());
            }
        }
    }

    private String readValidFullName(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Enter full name: ");
                String fullName = scanner.nextLine().trim();
                InputValidator.validateName(fullName);
                return fullName;
            } catch (ValidationException exception) {
                System.out.println("Invalid full name: " + exception.getMessage());
            }
        }
    }

    private String readValidPhoneNumber(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Enter phone number: ");
                String phoneNumber = scanner.nextLine().trim();
                InputValidator.validatePhoneNumber(phoneNumber);
                return phoneNumber;
            } catch (ValidationException exception) {
                System.out.println("Invalid phone number: " + exception.getMessage());
            }
        }
    }

    private String readValidCity(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Enter city: ");
                String city = scanner.nextLine().trim();
                InputValidator.validateCity(city);
                return city;
            } catch (ValidationException exception) {
                System.out.println("Invalid city: " + exception.getMessage());
            }
        }
    }

    private String readValidPassword(Scanner scanner, String message) {
        while (true) {
            try {
                System.out.print(message);
                String password = scanner.nextLine().trim();
                InputValidator.validatePassword(password);
                return password;
            } catch (ValidationException exception) {
                System.out.println("Invalid password: " + exception.getMessage());
            }
        }
    }

    private void printPreferenceMenu(User user) {
        System.out.println();
        System.out.println("Manage Preferences");
        System.out.println("1. Email Notifications : " + getStatusLabel(user.isEmailNotificationsEnabled()));
        System.out.println("2. Contact View        : " + getStatusLabel(user.isContactViewEnabled()));
        System.out.println("3. Back");
        System.out.print("Enter choice: ");
    }

    private void printEditProfileMenu() {
        System.out.println();
        System.out.println("What do you want to edit?");
        System.out.println("1. Full Name");
        System.out.println("2. Phone Number");
        System.out.println("3. City");
        System.out.println("4. Back");
        System.out.print("Enter choice: ");
    }

    private String getStatusLabel(boolean enabled) {
        return enabled ? "ON" : "OFF";
    }
}
