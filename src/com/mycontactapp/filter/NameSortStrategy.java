package com.mycontactapp.filter;

import com.mycontactapp.model.Contact;

import java.util.Comparator;
import java.util.List;

public class NameSortStrategy implements ContactSortStrategy {

    @Override
    public List<Contact> sort(List<Contact> contacts) {
        return contacts.stream()
                .sorted(Comparator.comparing(Contact::getName, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }
}
