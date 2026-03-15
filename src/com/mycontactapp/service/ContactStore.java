package com.mycontactapp.service;

import com.mycontactapp.model.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContactStore {

    private static final List<Contact> CONTACTS = new ArrayList<>();

    private ContactStore() {
    }

    public static void addContact(Contact contact) {
        CONTACTS.add(contact);
    }

    public static int getContactCountByOwner(String ownerUserId) {
        int count = 0;

        for (Contact contact : CONTACTS) {
            if (contact.getOwnerUserId().equals(ownerUserId)) {
                count++;
            }
        }

        return count;
    }

    public static Optional<Contact> findContactByReferenceId(String ownerUserId, String referenceId) {
        for (Contact contact : CONTACTS) {
            if (contact.getOwnerUserId().equals(ownerUserId)
                    && contact.getReferenceId().equalsIgnoreCase(referenceId)
                    && !contact.isDeleted()) {
                return Optional.of(contact);
            }
        }

        return Optional.empty();
    }

    public static List<Contact> getContactsByOwner(String ownerUserId) {
        List<Contact> ownerContacts = new ArrayList<>();

        for (Contact contact : CONTACTS) {
            if (contact.getOwnerUserId().equals(ownerUserId) && !contact.isDeleted()) {
                ownerContacts.add(contact);
            }
        }

        return ownerContacts;
    }

    public static void replaceContact(Contact updatedContact) {
        for (int index = 0; index < CONTACTS.size(); index++) {
            Contact existingContact = CONTACTS.get(index);

            if (existingContact.getContactId().equals(updatedContact.getContactId())) {
                CONTACTS.set(index, updatedContact);
                return;
            }
        }
    }

    public static void deleteContact(String contactId) {
        CONTACTS.removeIf(contact -> contact.getContactId().equals(contactId));
    }
}
