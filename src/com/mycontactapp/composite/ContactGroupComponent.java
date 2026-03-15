package com.mycontactapp.composite;

import com.mycontactapp.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactGroupComponent implements ContactComponent {

    private final List<ContactComponent> children = new ArrayList<>();

    public void add(ContactComponent contactComponent) {
        children.add(contactComponent);
    }

    @Override
    public List<Contact> getContacts() {
        List<Contact> contacts = new ArrayList<>();

        for (ContactComponent child : children) {
            contacts.addAll(child.getContacts());
        }

        return contacts;
    }
}
