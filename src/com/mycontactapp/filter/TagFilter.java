package com.mycontactapp.filter;

import com.mycontactapp.model.Contact;

import java.util.List;
import java.util.stream.Collectors;

public class TagFilter implements ContactFilter {

    private final String tag;

    public TagFilter(String tag) {
        this.tag = tag;
    }

    @Override
    public List<Contact> apply(List<Contact> contacts) {
        return contacts.stream()
                .filter(contact -> contact.getTags().stream()
                        .anyMatch(existingTag -> existingTag.equalsIgnoreCase(tag)))
                .collect(Collectors.toList());
    }
}
