package com.mycontactapp.search;

import com.mycontactapp.model.Contact;

import java.util.List;
import java.util.stream.Collectors;

public class CriteriaFilterHandler extends SearchFilterHandler {

    @Override
    protected List<Contact> applyFilter(List<Contact> contacts, SearchCriteria searchCriteria) {
        return contacts.stream()
                .filter(searchCriteria)
                .collect(Collectors.toList());
    }
}
