package com.mycontactapp.filter;

import com.mycontactapp.model.Contact;

import java.util.List;

public interface ContactFilter {

    List<Contact> apply(List<Contact> contacts);
}
