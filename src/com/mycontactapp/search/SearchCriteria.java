package com.mycontactapp.search;

import com.mycontactapp.model.Contact;

import java.util.function.Predicate;

public interface SearchCriteria extends Predicate<Contact> {
}
