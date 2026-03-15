package com.mycontactapp.service;

import com.mycontactapp.composite.ContactComponent;
import com.mycontactapp.composite.ContactGroupComponent;
import com.mycontactapp.composite.SingleContactComponent;
import com.mycontactapp.exception.ValidationException;
import com.mycontactapp.model.Contact;
import com.mycontactapp.model.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class BulkContactService {

    private final ContactService contactService;

    public BulkContactService() {
        this.contactService = new ContactService();
    }

    public ContactComponent buildSelection(User owner, List<String> referenceIds) throws ValidationException {
        List<Contact> selectedContacts = ContactStore.getContactsByOwner(owner.getUserId()).stream()
                .filter(contact -> referenceIds.stream()
                        .anyMatch(referenceId -> contact.getReferenceId().equalsIgnoreCase(referenceId)))
                .collect(Collectors.toList());

        if (selectedContacts.isEmpty()) {
            throw new ValidationException("No matching contacts found.");
        }

        if (selectedContacts.size() == 1) {
            return new SingleContactComponent(selectedContacts.get(0));
        }

        ContactGroupComponent contactGroupComponent = new ContactGroupComponent();
        selectedContacts.forEach(contact -> contactGroupComponent.add(new SingleContactComponent(contact)));
        return contactGroupComponent;
    }

    public int bulkDelete(User owner, ContactComponent contactComponent, String deleteMode) throws ValidationException {
        List<Contact> contacts = contactComponent.getContacts();

        for (Contact contact : contacts) {
            contactService.deleteContact(owner, contact.getReferenceId(), deleteMode);
        }

        return contacts.size();
    }

    public int bulkTag(User owner, ContactComponent contactComponent, String tag) throws ValidationException {
        if (tag == null || tag.isBlank()) {
            throw new ValidationException("Tag cannot be empty.");
        }

        List<Contact> contacts = contactComponent.getContacts();

        for (Contact contact : contacts) {
            contactService.addTagToContact(owner, contact.getReferenceId(), tag);
        }

        return contacts.size();
    }

    public Path bulkExport(User owner, ContactComponent contactComponent) throws IOException {
        List<String> lines = contactComponent.getContacts().stream()
                .map(contact -> String.format(
                        "%s | %s | %s | %s | %s",
                        contact.getReferenceId(),
                        contact.getName(),
                        contact.getPhoneNumbers().stream().map(phone -> phone.getValue()).collect(Collectors.joining(", ")),
                        contact.getEmailAddresses().stream().map(email -> email.getValue()).collect(Collectors.joining(", ")),
                        contact.getTags().isEmpty() ? "-" : String.join(", ", contact.getTags())
                ))
                .collect(Collectors.toList());

        Path exportDirectory = Path.of("exports");
        Files.createDirectories(exportDirectory);

        String fileName = owner.getUserId() + "-contacts-" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".txt";
        Path exportPath = exportDirectory.resolve(fileName);
        Files.write(exportPath, lines);
        return exportPath;
    }
}
