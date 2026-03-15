package com.mycontactapp.controller;

import com.mycontactapp.service.SessionManager;

import java.util.Scanner;

public class AppController {

    private final RegistrationController registrationController;
    private final AuthenticationController authenticationController;
    private final ProfileController profileController;
    private final ContactController contactController;

    public AppController() {
        this.registrationController = new RegistrationController();
        this.authenticationController = new AuthenticationController();
        this.profileController = new ProfileController();
        this.contactController = new ContactController();
    }

    public void startApp() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            if (SessionManager.getInstance().getLoggedInUser().isPresent()) {
                running = handleLoggedInMenu(scanner);
                continue;
            }

            printMainMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    registrationController.startRegistration(scanner);
                    break;
                case "2":
                    authenticationController.startAuthentication(scanner);
                    break;
                case "3":
                    running = false;
                    System.out.println("MyContacts App closed.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2 or 3.");
            }
        }

        scanner.close();
    }

    private boolean handleLoggedInMenu(Scanner scanner) {
        printLoggedInMenu();
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                profileController.viewProfile();
                return true;
            case "2":
                profileController.updateProfile(scanner);
                return true;
            case "3":
                profileController.changePassword(scanner);
                return true;
            case "4":
                profileController.managePreferences(scanner);
                return true;
            case "5":
                contactController.createContact(scanner);
                return true;
            case "6":
                contactController.viewContactDetails(scanner);
                return true;
            case "7":
                contactController.editContact(scanner);
                return true;
            case "8":
                contactController.undoLastEdit();
                return true;
            case "9":
                contactController.redoLastEdit();
                return true;
            case "10":
                contactController.deleteContact(scanner);
                return true;
            case "11":
                SessionManager.getInstance().endSession();
                System.out.println("Logged out successfully.");
                return true;
            case "12":
                SessionManager.getInstance().endSession();
                System.out.println("MyContacts App closed.");
                return false;
            default:
                System.out.println("Invalid choice. Please enter 1 to 12.");
                return true;
        }
    }

    private void printMainMenu() {
        System.out.println();
        System.out.println("====================================");
        System.out.println(" Welcome to MyContacts App");
        System.out.println("====================================");
        System.out.println("1. User Registration");
        System.out.println("2. User Login");
        System.out.println("3. Exit");
        System.out.print("Enter choice: ");
    }

    private void printLoggedInMenu() {
        System.out.println();
        System.out.println("1. View Profile");
        System.out.println("2. Update Profile");
        System.out.println("3. Change Password");
        System.out.println("4. Manage Preferences");
        System.out.println("5. Create Contact");
        System.out.println("6. View Contact Details");
        System.out.println("7. Edit Contact");
        System.out.println("8. Undo Last Contact Edit");
        System.out.println("9. Redo Last Contact Edit");
        System.out.println("10. Delete Contact");
        System.out.println("11. Logout");
        System.out.println("12. Exit");
        System.out.print("Enter choice: ");
    }
}
