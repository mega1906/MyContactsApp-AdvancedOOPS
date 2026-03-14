package com.mycontactapp.controller;

import com.mycontactapp.exception.ValidationException;
import com.mycontactapp.model.User;
import com.mycontactapp.service.AuthenticationService;
import com.mycontactapp.service.SessionManager;
import com.mycontactapp.util.InputValidator;

import java.util.Optional;
import java.util.Scanner;

public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController() {
        this.authenticationService = new AuthenticationService();
    }

    public void startAuthentication(Scanner scanner) {
        if (SessionManager.getInstance().getLoggedInUser().isPresent()) {
            System.out.println();
            System.out.println("One user is already logged in.");
            System.out.println("Please continue with the active session.");
            return;
        }

        boolean continueLogin = true;

        System.out.println();
        System.out.println("====================================");
        System.out.println(" User Profile Management - UC3");
        System.out.println("====================================");

        while (continueLogin) {
            try {
                String authType = readAuthType(scanner);
                String email = readValidEmail(scanner);
                String secret = readPassword(scanner);

                Optional<User> loginResult = authenticationService.login(authType, email, secret);

                if (loginResult.isPresent()) {
                    printLoginSuccess(loginResult.get());
                    break;
                } else {
                    System.out.println("Login failed: Invalid credentials.");
                    continueLogin = shouldContinue(scanner);
                }
            } catch (ValidationException exception) {
                System.out.println("Login failed: " + exception.getMessage());
                continueLogin = shouldContinue(scanner);
            } catch (Exception exception) {
                System.out.println("Something went wrong: " + exception.getMessage());
                continueLogin = shouldContinue(scanner);
            }
        }
    }

    private String readAuthType(Scanner scanner) {
        while (true) {
            System.out.println("Select authentication method:");
            System.out.println("1. Basic Authentication");
            System.out.println("2. OAuth Authentication");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine().trim();

            if ("1".equals(choice)) {
                return "BASIC";
            }

            if ("2".equals(choice)) {
                return "OAUTH";
            }

            System.out.println("Invalid choice. Please enter 1 or 2.");
        }
    }

    private String readValidEmail(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Enter email: ");
                String email = scanner.nextLine().trim();
                InputValidator.validateEmail(email);
                return email;
            } catch (ValidationException exception) {
                System.out.println("Invalid email: " + exception.getMessage());
            }
        }
    }

    private String readPassword(Scanner scanner) throws ValidationException {
        while (true) {
            try {
                System.out.print("Enter password: ");
                String password = scanner.nextLine().trim();
                InputValidator.validatePassword(password);
                return password;
            } catch (ValidationException exception) {
                System.out.println("Invalid password: " + exception.getMessage());
            }
        }
    }

    private boolean shouldContinue(Scanner scanner) {
        System.out.print("Do you want to try login again? (yes/no): ");
        String choice = scanner.nextLine().trim();
        return choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("y");
    }

    private void printLoginSuccess(User user) {
        System.out.println();
        System.out.println("Welcome, " + user.getFullName());
        System.out.println();
    }
}
