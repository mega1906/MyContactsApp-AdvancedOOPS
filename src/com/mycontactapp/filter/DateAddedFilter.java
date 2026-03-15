package com.mycontactapp.filter;

import com.mycontactapp.model.Contact;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class DateAddedFilter implements ContactFilter {

    private final LocalDate dateAdded;

    public DateAddedFilter(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public List<Contact> apply(List<Contact> contacts) {
        return contacts.stream()
                .filter(contact -> contact.getCreatedAt().toLocalDate().isEqual(dateAdded))
                .collect(Collectors.toList());
    }
}
