package com.mycontactapp.composite;

import com.mycontactapp.model.Contact;

import java.util.List;

public class SingleContactComponent implements ContactComponent {

    private final Contact contact;

    public SingleContactComponent(Contact contact) {
        this.contact = contact;
    }

    @Override
    public List<Contact> getContacts() {
        return List.of(contact);
    }
}
