package com.mycontactapp.service;

import com.mycontactapp.builder.ContactBuilder;
import com.mycontactapp.exception.ValidationException;
import com.mycontactapp.factory.ContactFactory;
import com.mycontactapp.model.Contact;
import com.mycontactapp.model.EmailAddress;
import com.mycontactapp.model.PhoneNumber;
import com.mycontactapp.model.User;
import com.mycontactapp.util.InputValidator;

import java.time.LocalDateTime;
import java.util.List;
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
}
