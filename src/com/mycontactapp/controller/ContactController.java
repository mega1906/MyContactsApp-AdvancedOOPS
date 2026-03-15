package com.mycontactapp.controller;

import com.mycontactapp.exception.ValidationException;
import com.mycontactapp.model.Contact;
import com.mycontactapp.model.EmailAddress;
import com.mycontactapp.model.PhoneNumber;
import com.mycontactapp.model.User;
import com.mycontactapp.service.ContactService;
import com.mycontactapp.service.SessionManager;
import com.mycontactapp.util.InputValidator;
import com.mycontactapp.view.ContactView;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ContactController {

    private final ContactService contactService;

    public ContactController() {
        this.contactService = new ContactService();
    }

    public void createContact(Scanner scanner) {
        Optional<User> userOptional = SessionManager.getInstance().getLoggedInUser();

        if (userOptional.isEmpty()) {
            System.out.println("No user is currently logged in.");
            return;
        }

        User owner = userOptional.get();

        try {
            String contactType = readContactType(scanner);
            String contactName = readValidContactName(scanner);
            List<PhoneNumber> phoneNumbers = readPhoneNumbers(scanner);
            List<EmailAddress> emailAddresses = readEmailAddresses(scanner);
            String address = readOptionalValue(scanner, "Enter address (optional): ");
            String notes = readOptionalValue(scanner, "Enter notes (optional): ");

            Contact contact = contactService.createContact(
                    owner,
                    contactType,
                    contactName,
                    phoneNumbers,
                    emailAddresses,
                    address,
                    notes
            );

            printSuccessMessage(contact);
        } catch (ValidationException exception) {
            System.out.println("Contact creation failed: " + exception.getMessage());
        }
    }

    public void viewContactDetails(Scanner scanner) {
        Optional<User> userOptional = SessionManager.getInstance().getLoggedInUser();

        if (userOptional.isEmpty()) {
            System.out.println("No user is currently logged in.");
            return;
        }

        User owner = userOptional.get();
        List<String> referenceList = contactService.getContactReferenceList(owner);

        if (referenceList.isEmpty()) {
            System.out.println("No contacts found.");
            return;
        }

        System.out.println();
        System.out.println("Available Contact Ids");

        for (String referenceItem : referenceList) {
            System.out.println(referenceItem);
        }

        System.out.print("Enter reference id: ");
        String referenceId = scanner.nextLine().trim();

        Optional<ContactView> contactViewOptional = contactService.getContactView(owner, referenceId);

        if (contactViewOptional.isEmpty()) {
            System.out.println("Contact not found.");
            return;
        }

        System.out.println();
        System.out.println("Contact Details");
        System.out.println(contactViewOptional.get());
    }

    private String readContactType(Scanner scanner) {
        while (true) {
            System.out.println();
            System.out.println("Select contact type:");
            System.out.println("1. Person Contact");
            System.out.println("2. Organization Contact");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine().trim();

            if ("1".equals(choice)) {
                return "PERSON";
            }

            if ("2".equals(choice)) {
                return "ORGANIZATION";
            }

            System.out.println("Invalid choice. Please enter 1 or 2.");
        }
    }

    private String readValidContactName(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Enter contact name: ");
                String contactName = scanner.nextLine().trim();
                InputValidator.validateName(contactName);
                return contactName;
            } catch (ValidationException exception) {
                System.out.println("Invalid contact name: " + exception.getMessage());
            }
        }
    }

    private List<PhoneNumber> readPhoneNumbers(Scanner scanner) throws ValidationException {
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        boolean addMore = true;

        while (addMore) {
            phoneNumbers.add(new PhoneNumber(readValidPhoneNumber(scanner)));
            addMore = askToAddMore(scanner, "Do you want to add another phone number? (yes/no): ");
        }

        return phoneNumbers;
    }

    private List<EmailAddress> readEmailAddresses(Scanner scanner) throws ValidationException {
        List<EmailAddress> emailAddresses = new ArrayList<>();
        boolean addMore = true;

        while (addMore) {
            emailAddresses.add(new EmailAddress(readValidEmail(scanner)));
            addMore = askToAddMore(scanner, "Do you want to add another email address? (yes/no): ");
        }

        return emailAddresses;
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

    private String readValidEmail(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Enter email address: ");
                String email = scanner.nextLine().trim();
                InputValidator.validateEmail(email);
                return email;
            } catch (ValidationException exception) {
                System.out.println("Invalid email address: " + exception.getMessage());
            }
        }
    }

    private boolean askToAddMore(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String choice = scanner.nextLine().trim();

            if (choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("y")) {
                return true;
            }

            if (choice.equalsIgnoreCase("no") || choice.equalsIgnoreCase("n")) {
                return false;
            }

            System.out.println("Invalid choice. Please enter yes or no.");
        }
    }

    private String readOptionalValue(Scanner scanner, String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    private void printSuccessMessage(Contact contact) {
        System.out.println();
        System.out.println("Contact created successfully.");
        System.out.println("Contact Type : " + contact.getContactType());
        System.out.println("Reference Id : " + contact.getReferenceId());
        System.out.println("Contact Id   : " + contact.getContactId());
        System.out.println("Name         : " + contact.getName());
        System.out.println("Phones       : " + formatPhoneNumbers(contact.getPhoneNumbers()));
        System.out.println("Emails       : " + formatEmailAddresses(contact.getEmailAddresses()));
        System.out.println("Address      : " + getDisplayValue(contact.getAddress()));
        System.out.println("Notes        : " + getDisplayValue(contact.getNotes()));
        System.out.println("Created At   : " + contact.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println();
    }

    private String formatPhoneNumbers(List<PhoneNumber> phoneNumbers) {
        List<String> values = new ArrayList<>();

        for (PhoneNumber phoneNumber : phoneNumbers) {
            values.add(phoneNumber.getValue());
        }

        return String.join(", ", values);
    }

    private String formatEmailAddresses(List<EmailAddress> emailAddresses) {
        List<String> values = new ArrayList<>();

        for (EmailAddress emailAddress : emailAddresses) {
            values.add(emailAddress.getValue());
        }

        return String.join(", ", values);
    }

    private String getDisplayValue(String value) {
        return value == null || value.isBlank() ? "-" : value;
    }
}
