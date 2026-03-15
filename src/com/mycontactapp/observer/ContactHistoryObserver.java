package com.mycontactapp.observer;

import com.mycontactapp.model.Contact;
import com.mycontactapp.service.ContactHistoryManager;

public class ContactHistoryObserver implements ContactDeletionObserver {

    @Override
    public void onContactDeleted(Contact contact, String deleteMode) {
        ContactHistoryManager.clearHistoryForContact(contact.getOwnerUserId(), contact.getContactId());
    }
}
