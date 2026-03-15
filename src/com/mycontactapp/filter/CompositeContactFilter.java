package com.mycontactapp.filter;

import com.mycontactapp.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class CompositeContactFilter implements ContactFilter {

    private final List<ContactFilter> filters = new ArrayList<>();

    public void addFilter(ContactFilter contactFilter) {
        filters.add(contactFilter);
    }

    @Override
    public List<Contact> apply(List<Contact> contacts) {
        List<Contact> filteredContacts = contacts;

        for (ContactFilter filter : filters) {
            filteredContacts = filter.apply(filteredContacts);
        }

        return filteredContacts;
    }
}
