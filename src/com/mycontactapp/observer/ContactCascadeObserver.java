package com.mycontactapp.observer;

import com.mycontactapp.model.Contact;

public class ContactCascadeObserver implements ContactDeletionObserver {

    @Override
    public void onContactDeleted(Contact contact, String deleteMode) {
        // Phones and emails are composed inside the contact, so they disappear with it.
    }
}
