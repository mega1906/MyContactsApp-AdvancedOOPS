package com.mycontactapp.filter;

import com.mycontactapp.model.Contact;

import java.util.List;

public interface ContactSortStrategy {

    List<Contact> sort(List<Contact> contacts);
}
