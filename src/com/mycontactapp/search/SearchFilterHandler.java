package com.mycontactapp.search;

import com.mycontactapp.model.Contact;

import java.util.List;

public abstract class SearchFilterHandler {

    private SearchFilterHandler next;

    public SearchFilterHandler setNext(SearchFilterHandler next) {
        this.next = next;
        return next;
    }

    public List<Contact> handle(List<Contact> contacts, SearchCriteria searchCriteria) {
        List<Contact> filteredContacts = applyFilter(contacts, searchCriteria);

        if (next == null) {
            return filteredContacts;
        }

        return next.handle(filteredContacts, searchCriteria);
    }

    protected abstract List<Contact> applyFilter(List<Contact> contacts, SearchCriteria searchCriteria);
}
