package com.mycontactapp.controller;

import com.mycontactapp.exception.ValidationException;
import com.mycontactapp.model.User;
import com.mycontactapp.service.RegistrationService;
import com.mycontactapp.util.InputValidator;

import java.util.Scanner;

public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController() {
        this.registrationService = new RegistrationService();
    }

    public void startRegistration() {
        Scanner scanner = new Scanner(System.in);
        boolean continueRegistration = true;

        System.out.println("====================================");
        System.out.println(" Welcome to MyContacts App - UC1");
        System.out.println(" User Registration");
        System.out.println("====================================");

        while (continueRegistration) {
            try {
                String userType = readUserType(scanner);
                String fullName = readValidFullName(scanner);
                String email = readValidEmail(scanner);
                String phoneNumber = readValidPhoneNumber(scanner);
                String password = readValidPassword(scanner);
                String city = readValidCity(scanner);

                User registeredUser = registrationService.registerUser(
                        userType,
                        fullName,
                        email,
                        phoneNumber,
                        password,
                        city
                );

                printSuccessMessage(registeredUser);
            } catch (ValidationException exception) {
                System.out.println("Registration failed: " + exception.getMessage());
            } catch (Exception exception) {
                System.out.println("Something went wrong: " + exception.getMessage());
            }

            continueRegistration = shouldContinue(scanner);
        }

        System.out.println("Registration module closed.");
        scanner.close();
    }

    private String readUserType(Scanner scanner) {
        while (true) {
            System.out.println();
            System.out.println("Select user type:");
            System.out.println("1. Free User");
            System.out.println("2. Premium User");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine().trim();

            if ("1".equals(choice)) {
                return "FREE";
            }

            if ("2".equals(choice)) {
                return "PREMIUM";
            }

            System.out.println("Invalid choice. Please enter 1 or 2.");
        }
    }

    private String readValue(Scanner scanner, String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    private String readValidFullName(Scanner scanner) {
        while (true) {
            try {
                String fullName = readValue(scanner, "Enter full name: ");
                InputValidator.validateName(fullName);
                return fullName;
            } catch (ValidationException exception) {
                System.out.println("Invalid full name: " + exception.getMessage());
            }
        }
    }

    private String readValidEmail(Scanner scanner) {
        while (true) {
            try {
                String email = readValue(scanner, "Enter email: ");
                InputValidator.validateEmail(email);
                return email;
            } catch (ValidationException exception) {
                System.out.println("Invalid email: " + exception.getMessage());
            }
        }
    }

    private String readValidPhoneNumber(Scanner scanner) {
        while (true) {
            try {
                String phoneNumber = readValue(scanner, "Enter phone number: ");
                InputValidator.validatePhoneNumber(phoneNumber);
                return phoneNumber;
            } catch (ValidationException exception) {
                System.out.println("Invalid phone number: " + exception.getMessage());
            }
        }
    }

    private String readValidPassword(Scanner scanner) {
        while (true) {
            try {
                String password = readValue(scanner, "Enter password: ");
                InputValidator.validatePassword(password);
                return password;
            } catch (ValidationException exception) {
                System.out.println("Invalid password: " + exception.getMessage());
            }
        }
    }

    private String readValidCity(Scanner scanner) {
        while (true) {
            try {
                String city = readValue(scanner, "Enter city: ");
                InputValidator.validateCity(city);
                return city;
            } catch (ValidationException exception) {
                System.out.println("Invalid city: " + exception.getMessage());
            }
        }
    }

    private boolean shouldContinue(Scanner scanner) {
        System.out.print("Do you want to register another user? (yes/no): ");
        String choice = scanner.nextLine().trim();
        return choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("y");
    }

    private void printSuccessMessage(User user) {
        System.out.println();
        System.out.println("User registered successfully.");
        System.out.println("User Type  : " + user.getUserType());
        System.out.println("Full Name  : " + user.getFullName());
        System.out.println("Email      : " + user.getEmail());
        System.out.println("Phone      : " + user.getPhoneNumber());
        System.out.println("City       : " + user.getCity());
        System.out.println("User Id    : " + user.getUserId());
        System.out.println();
    }
}
