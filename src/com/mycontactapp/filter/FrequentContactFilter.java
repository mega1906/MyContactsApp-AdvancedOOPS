package com.mycontactapp.filter;

import com.mycontactapp.model.Contact;

import java.util.List;
import java.util.stream.Collectors;

public class FrequentContactFilter implements ContactFilter {

    private final int minimumFrequency;

    public FrequentContactFilter(int minimumFrequency) {
        this.minimumFrequency = minimumFrequency;
    }

    @Override
    public List<Contact> apply(List<Contact> contacts) {
        return contacts.stream()
                .filter(contact -> contact.getContactFrequency() >= minimumFrequency)
                .collect(Collectors.toList());
    }
}
