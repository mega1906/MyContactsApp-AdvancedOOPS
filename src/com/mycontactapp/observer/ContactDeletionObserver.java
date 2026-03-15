package com.mycontactapp.observer;

import com.mycontactapp.model.Contact;

public interface ContactDeletionObserver {

    void onContactDeleted(Contact contact, String deleteMode);
}
