package com.mycontactapp.service;

import com.mycontactapp.model.Contact;

import java.util.ArrayList;
import java.util.List;

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
}
