package com.mycontactapp.controller;

import com.mycontactapp.composite.ContactComponent;
import com.mycontactapp.exception.ValidationException;
import com.mycontactapp.model.Contact;
import com.mycontactapp.model.EmailAddress;
import com.mycontactapp.model.PhoneNumber;
import com.mycontactapp.model.Tag;
import com.mycontactapp.model.User;
import com.mycontactapp.service.BulkContactService;
import com.mycontactapp.service.ContactService;
import com.mycontactapp.service.AdvancedFilterService;
import com.mycontactapp.service.SearchService;
import com.mycontactapp.service.SessionManager;
import com.mycontactapp.service.TagService;
import com.mycontactapp.util.InputValidator;
import com.mycontactapp.view.ContactView;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ContactController {

    private final ContactService contactService;
    private final BulkContactService bulkContactService;
    private final SearchService searchService;
    private final AdvancedFilterService advancedFilterService;
    private final TagService tagService;

    public ContactController() {
        this.contactService = new ContactService();
        this.bulkContactService = new BulkContactService();
        this.searchService = new SearchService();
        this.advancedFilterService = new AdvancedFilterService();
        this.tagService = new TagService();
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

    public void editContact(Scanner scanner) {
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
        boolean editing = true;

        while (editing) {
            printEditContactMenu();
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        contactService.editContact(owner, referenceId, "NAME", readValidContactName(scanner), null, null);
                        System.out.println("Contact name updated successfully.");
                        break;
                    case "2":
                        contactService.editContact(owner, referenceId, "PHONES", null, readPhoneNumbers(scanner), null);
                        System.out.println("Contact phone numbers updated successfully.");
                        break;
                    case "3":
                        contactService.editContact(owner, referenceId, "EMAILS", null, null, readEmailAddresses(scanner));
                        System.out.println("Contact email addresses updated successfully.");
                        break;
                    case "4":
                        contactService.editContact(owner, referenceId, "ADDRESS",
                                readOptionalValue(scanner, "Enter address (optional): "), null, null);
                        System.out.println("Contact address updated successfully.");
                        break;
                    case "5":
                        contactService.editContact(owner, referenceId, "NOTES",
                                readOptionalValue(scanner, "Enter notes (optional): "), null, null);
                        System.out.println("Contact notes updated successfully.");
                        break;
                    case "6":
                        editing = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter 1 to 6.");
                }
            } catch (ValidationException exception) {
                System.out.println("Contact edit failed: " + exception.getMessage());
                if ("Contact not found.".equals(exception.getMessage())) {
                    editing = false;
                }
            }
        }
    }

    public void undoLastEdit() {
        Optional<User> userOptional = SessionManager.getInstance().getLoggedInUser();

        if (userOptional.isEmpty()) {
            System.out.println("No user is currently logged in.");
            return;
        }

        if (contactService.undoLastEdit(userOptional.get())) {
            System.out.println("Last contact edit undone successfully.");
        } else {
            System.out.println("No contact edit available to undo.");
        }
    }

    public void redoLastEdit() {
        Optional<User> userOptional = SessionManager.getInstance().getLoggedInUser();

        if (userOptional.isEmpty()) {
            System.out.println("No user is currently logged in.");
            return;
        }

        if (contactService.redoLastEdit(userOptional.get())) {
            System.out.println("Last contact edit redone successfully.");
        } else {
            System.out.println("No contact edit available to redo.");
        }
    }

    public void deleteContact(Scanner scanner) {
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
        String deleteMode = readDeleteMode(scanner);

        if (!confirmDelete(scanner)) {
            System.out.println("Delete operation cancelled.");
            return;
        }

        try {
            String message = contactService.deleteContact(owner, referenceId, deleteMode);
            System.out.println(message);
        } catch (ValidationException exception) {
            System.out.println("Contact delete failed: " + exception.getMessage());
        }
    }

    public void bulkOperations(Scanner scanner) {
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
        referenceList.forEach(System.out::println);
        System.out.print("Enter reference ids separated by comma or ALL: ");

        try {
            ContactComponent selectedContacts = bulkContactService.buildSelection(
                    owner,
                    readReferenceIds(scanner, referenceList)
            );

            switch (readBulkOperation(scanner)) {
                case "DELETE":
                    String deleteMode = readDeleteMode(scanner);
                    if (!confirmDelete(scanner)) {
                        System.out.println("Bulk delete cancelled.");
                        return;
                    }
                    int deletedCount = bulkContactService.bulkDelete(owner, selectedContacts, deleteMode);
                    System.out.println("Bulk delete completed for " + deletedCount + " contact(s).");
                    break;
                case "TAG":
                    System.out.print("Enter tag: ");
                    String tag = scanner.nextLine().trim();
                    int taggedCount = bulkContactService.bulkTag(owner, selectedContacts, tag);
                    System.out.println("Tag added to " + taggedCount + " contact(s).");
                    break;
                default:
                    Path exportPath = bulkContactService.bulkExport(owner, selectedContacts);
                    System.out.println("Contacts exported to: " + exportPath);
                    break;
            }
        } catch (ValidationException exception) {
            System.out.println("Bulk operation failed: " + exception.getMessage());
        } catch (IOException exception) {
            System.out.println("Export failed: " + exception.getMessage());
        }
    }

    public void searchContacts(Scanner scanner) {
        Optional<User> userOptional = SessionManager.getInstance().getLoggedInUser();

        if (userOptional.isEmpty()) {
            System.out.println("No user is currently logged in.");
            return;
        }

        User owner = userOptional.get();

        System.out.println();
        System.out.println("Search Contacts");
        System.out.print("Enter name to search (leave blank to skip): ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter phone to search (leave blank to skip): ");
        String phone = scanner.nextLine().trim();
        System.out.print("Enter email to search (leave blank to skip): ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter tag to search (leave blank to skip): ");
        String tag = scanner.nextLine().trim();

        List<ContactView> results = searchService.searchContacts(owner, name, phone, email, tag);

        if (results.isEmpty()) {
            System.out.println("No matching contacts found.");
            return;
        }

        System.out.println();
        System.out.println("Search Results");
        results.forEach(contactView -> {
            System.out.println(contactView);
            System.out.println();
        });
    }

    public void advancedFiltering(Scanner scanner) {
        Optional<User> userOptional = SessionManager.getInstance().getLoggedInUser();

        if (userOptional.isEmpty()) {
            System.out.println("No user is currently logged in.");
            return;
        }

        User owner = userOptional.get();

        System.out.println();
        System.out.println("Advanced Filtering");
        System.out.print("Enter tag to filter (leave blank to skip): ");
        String tag = scanner.nextLine().trim();
        LocalDate dateAdded = readOptionalDate(scanner);
        Integer minimumFrequency = readOptionalFrequency(scanner);
        String sortOption = readSortOption(scanner);

        List<ContactView> results = advancedFilterService.filterContacts(
                owner,
                tag,
                dateAdded,
                minimumFrequency,
                sortOption
        );

        if (results.isEmpty()) {
            System.out.println("No contacts matched the selected filters.");
            return;
        }

        System.out.println();
        System.out.println("Filtered Contacts");
        results.forEach(contactView -> {
            System.out.println(contactView);
            System.out.println();
        });
    }

    public void manageTags(Scanner scanner) {
        Optional<User> userOptional = SessionManager.getInstance().getLoggedInUser();

        if (userOptional.isEmpty()) {
            System.out.println("No user is currently logged in.");
            return;
        }

        User owner = userOptional.get();
        boolean managing = true;

        while (managing) {
            printTagMenu();
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        printAvailableTags(owner);
                        break;
                    case "2":
                        System.out.print("Enter custom tag name: ");
                        Tag createdTag = tagService.createCustomTag(owner, scanner.nextLine().trim());
                        System.out.println("Tag created: " + createdTag.getName());
                        break;
                    case "3":
                        assignTagToContact(scanner, owner);
                        break;
                    case "4":
                        removeTagFromContact(scanner, owner);
                        break;
                    case "5":
                        managing = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter 1 to 5.");
                }
            } catch (ValidationException exception) {
                System.out.println("Tag operation failed: " + exception.getMessage());
            }
        }
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

    private String readDeleteMode(Scanner scanner) {
        while (true) {
            System.out.println();
            System.out.println("Select delete type:");
            System.out.println("1. Soft Delete");
            System.out.println("2. Hard Delete");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine().trim();

            if ("1".equals(choice)) {
                return "SOFT";
            }

            if ("2".equals(choice)) {
                return "HARD";
            }

            System.out.println("Invalid choice. Please enter 1 or 2.");
        }
    }

    private LocalDate readOptionalDate(Scanner scanner) {
        while (true) {
            System.out.print("Enter date added in yyyy-MM-dd (leave blank to skip): ");
            String input = scanner.nextLine().trim();

            if (input.isBlank()) {
                return null;
            }

            try {
                return LocalDate.parse(input);
            } catch (Exception exception) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            }
        }
    }

    private Integer readOptionalFrequency(Scanner scanner) {
        while (true) {
            System.out.print("Enter minimum frequency count (leave blank to skip): ");
            String input = scanner.nextLine().trim();

            if (input.isBlank()) {
                return null;
            }

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException exception) {
                System.out.println("Invalid number. Please enter a whole number.");
            }
        }
    }

    private String readSortOption(Scanner scanner) {
        while (true) {
            System.out.println();
            System.out.println("Select sort option:");
            System.out.println("1. Name");
            System.out.println("2. Date Added");
            System.out.println("3. Frequently Contacted");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine().trim();

            if ("1".equals(choice)) {
                return "NAME";
            }

            if ("2".equals(choice)) {
                return "DATE";
            }

            if ("3".equals(choice)) {
                return "FREQUENCY";
            }

            System.out.println("Invalid choice. Please enter 1, 2 or 3.");
        }
    }

    private void printAvailableTags(User owner) {
        List<Tag> availableTags = tagService.getAvailableTags(owner);

        System.out.println();
        System.out.println("Available Tags");
        availableTags.stream()
                .map(Tag::getName)
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .forEach(System.out::println);
    }

    private void assignTagToContact(Scanner scanner, User owner) throws ValidationException {
        showAvailableContactIds(owner);
        System.out.print("Enter reference id: ");
        String referenceId = scanner.nextLine().trim();
        System.out.print("Enter tag name: ");
        String tagName = scanner.nextLine().trim();
        tagService.assignTagToContact(owner, referenceId, tagName);
        System.out.println("Tag assigned successfully.");
    }

    private void removeTagFromContact(Scanner scanner, User owner) throws ValidationException {
        showAvailableContactIds(owner);
        System.out.print("Enter reference id: ");
        String referenceId = scanner.nextLine().trim();
        System.out.print("Enter tag name to remove: ");
        String tagName = scanner.nextLine().trim();
        tagService.removeTagFromContact(owner, referenceId, tagName);
        System.out.println("Tag removed successfully.");
    }

    private void showAvailableContactIds(User owner) {
        List<String> referenceList = contactService.getContactReferenceList(owner);

        if (referenceList.isEmpty()) {
            System.out.println("No contacts found.");
            return;
        }

        System.out.println();
        System.out.println("Available Contact Ids");
        referenceList.forEach(System.out::println);
    }

    private void printTagMenu() {
        System.out.println();
        System.out.println("Manage Tags");
        System.out.println("1. View Available Tags");
        System.out.println("2. Create Custom Tag");
        System.out.println("3. Assign Tag To Contact");
        System.out.println("4. Remove Tag From Contact");
        System.out.println("5. Back");
        System.out.print("Enter choice: ");
    }

    private String readBulkOperation(Scanner scanner) {
        while (true) {
            System.out.println();
            System.out.println("Select bulk operation:");
            System.out.println("1. Bulk Delete");
            System.out.println("2. Bulk Tag");
            System.out.println("3. Bulk Export");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine().trim();

            if ("1".equals(choice)) {
                return "DELETE";
            }

            if ("2".equals(choice)) {
                return "TAG";
            }

            if ("3".equals(choice)) {
                return "EXPORT";
            }

            System.out.println("Invalid choice. Please enter 1, 2 or 3.");
        }
    }

    private List<String> readReferenceIds(Scanner scanner, List<String> referenceList) {
        String input = scanner.nextLine().trim();

        if ("ALL".equalsIgnoreCase(input)) {
            return referenceList.stream()
                    .map(value -> value.split(" - ")[0])
                    .collect(Collectors.toList());
        }

        return Arrays.stream(input.split(","))
                .map(String::trim)
                .filter(value -> !value.isBlank())
                .collect(Collectors.toList());
    }

    private boolean confirmDelete(Scanner scanner) {
        while (true) {
            System.out.print("Are you sure you want to delete this contact? (yes/no): ");
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

    private void printEditContactMenu() {
        System.out.println();
        System.out.println("What do you want to edit?");
        System.out.println("1. Name");
        System.out.println("2. Phone Numbers");
        System.out.println("3. Email Addresses");
        System.out.println("4. Address");
        System.out.println("5. Notes");
        System.out.println("6. Back");
        System.out.print("Enter choice: ");
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
