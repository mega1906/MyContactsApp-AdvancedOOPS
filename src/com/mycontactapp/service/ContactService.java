package com.mycontactapp.service;

import com.mycontactapp.builder.ContactBuilder;
import com.mycontactapp.command.ContactEditCommand;
import com.mycontactapp.command.EditContactCommand;
import com.mycontactapp.exception.ValidationException;
import com.mycontactapp.factory.ContactFactory;
import com.mycontactapp.model.Contact;
import com.mycontactapp.model.EmailAddress;
import com.mycontactapp.model.OrganizationContact;
import com.mycontactapp.model.PersonContact;
import com.mycontactapp.model.PhoneNumber;
import com.mycontactapp.model.User;
import com.mycontactapp.util.InputValidator;
import com.mycontactapp.view.ContactView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ContactService {

    private final ContactFactory contactFactory;

    public ContactService() {
        this.contactFactory = new ContactFactory();
    }

    public Contact createContact(User owner, String contactType, String name, List<PhoneNumber> phoneNumbers,
                                 List<EmailAddress> emailAddresses, String address, String notes)
            throws ValidationException {
        validateContactData(name, phoneNumbers, emailAddresses);

        LocalDateTime now = LocalDateTime.now();

        ContactBuilder contactBuilder = new ContactBuilder()
                .setContactId(UUID.randomUUID().toString())
                .setReferenceId(generateReferenceId(owner))
                .setOwnerUserId(owner.getUserId())
                .setName(name)
                .setPhoneNumbers(phoneNumbers)
                .setEmailAddresses(emailAddresses)
                .setAddress(address)
                .setNotes(notes)
                .setCreatedAt(now)
                .setUpdatedAt(now);

        Contact contact = contactFactory.createContact(contactType, contactBuilder);
        ContactStore.addContact(contact);
        return contact;
    }

    public Optional<ContactView> getContactView(User owner, String referenceId) {
        Optional<Contact> contactOptional = ContactStore.findContactByReferenceId(owner.getUserId(), referenceId);

        if (contactOptional.isEmpty()) {
            return Optional.empty();
        }

        Contact contact = contactOptional.get();
        List<String> phoneNumbers = new ArrayList<>();
        List<String> emailAddresses = new ArrayList<>();

        for (PhoneNumber phoneNumber : contact.getPhoneNumbers()) {
            phoneNumbers.add(phoneNumber.getValue());
        }

        for (EmailAddress emailAddress : contact.getEmailAddresses()) {
            emailAddresses.add(emailAddress.getValue());
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return Optional.of(new ContactView(
                contact.getReferenceId(),
                contact.getContactType(),
                contact.getName(),
                phoneNumbers,
                emailAddresses,
                Optional.ofNullable(contact.getAddress()).filter(value -> !value.isBlank()),
                Optional.ofNullable(contact.getNotes()).filter(value -> !value.isBlank()),
                contact.getCreatedAt().format(formatter),
                contact.getUpdatedAt().format(formatter)
        ));
    }

    public List<String> getContactReferenceList(User owner) {
        List<Contact> contacts = ContactStore.getContactsByOwner(owner.getUserId());
        List<String> referenceList = new ArrayList<>();

        for (Contact contact : contacts) {
            referenceList.add(contact.getReferenceId() + " - " + contact.getName());
        }

        return referenceList;
    }

    public Optional<Contact> findContact(User owner, String referenceId) {
        return ContactStore.findContactByReferenceId(owner.getUserId(), referenceId);
    }

    public Contact editContact(User owner, String referenceId, String fieldName, String value,
                               List<PhoneNumber> phoneNumbers, List<EmailAddress> emailAddresses)
            throws ValidationException {
        Optional<Contact> contactOptional = findContact(owner, referenceId);

        if (contactOptional.isEmpty()) {
            throw new ValidationException("Contact not found.");
        }

        Contact originalContact = contactOptional.get();
        Contact modifiedContact = copyContact(originalContact);

        applyEdit(modifiedContact, fieldName, value, phoneNumbers, emailAddresses);
        modifiedContact.setUpdatedAt(LocalDateTime.now());

        ContactEditCommand command = new EditContactCommand(copyContact(originalContact), modifiedContact);
        command.execute();
        ContactHistoryManager.record(owner.getUserId(), command);
        return modifiedContact;
    }

    public boolean undoLastEdit(User owner) {
        return ContactHistoryManager.undo(owner.getUserId()).isPresent();
    }

    public boolean redoLastEdit(User owner) {
        return ContactHistoryManager.redo(owner.getUserId()).isPresent();
    }

    private void validateContactData(String name, List<PhoneNumber> phoneNumbers,
                                     List<EmailAddress> emailAddresses) throws ValidationException {
        InputValidator.validateName(name);

        if (phoneNumbers == null || phoneNumbers.isEmpty()) {
            throw new ValidationException("At least one phone number is required.");
        }

        if (emailAddresses == null || emailAddresses.isEmpty()) {
            throw new ValidationException("At least one email address is required.");
        }
    }

    private String generateReferenceId(User owner) {
        String cleanedName = owner.getFullName().replaceAll("[^A-Za-z]", "").toUpperCase();

        if (cleanedName.length() < 3) {
            cleanedName = (cleanedName + "USR").substring(0, 3);
        } else {
            cleanedName = cleanedName.substring(0, 3);
        }

        int nextNumber = ContactStore.getContactCountByOwner(owner.getUserId()) + 1;
        return cleanedName + nextNumber;
    }

    private void applyEdit(Contact contact, String fieldName, String value, List<PhoneNumber> phoneNumbers,
                           List<EmailAddress> emailAddresses) throws ValidationException {
        if ("NAME".equalsIgnoreCase(fieldName)) {
            contact.setName(value);
            return;
        }

        if ("PHONES".equalsIgnoreCase(fieldName)) {
            contact.setPhoneNumbers(phoneNumbers);
            return;
        }

        if ("EMAILS".equalsIgnoreCase(fieldName)) {
            contact.setEmailAddresses(emailAddresses);
            return;
        }

        if ("ADDRESS".equalsIgnoreCase(fieldName)) {
            contact.setAddress(value);
            return;
        }

        if ("NOTES".equalsIgnoreCase(fieldName)) {
            contact.setNotes(value);
            return;
        }

        throw new ValidationException("Invalid field selected.");
    }

    private Contact copyContact(Contact contact) {
        // Copy constructor keeps edit operations isolated from stored state.
        if (contact instanceof PersonContact) {
            return new PersonContact((PersonContact) contact);
        }

        return new OrganizationContact((OrganizationContact) contact);
    }
}
