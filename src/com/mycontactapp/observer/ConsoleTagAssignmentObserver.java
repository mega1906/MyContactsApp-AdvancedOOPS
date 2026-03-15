package com.mycontactapp.observer;

import com.mycontactapp.model.Contact;

public class ConsoleTagAssignmentObserver implements TagAssignmentObserver {

    @Override
    public void onTagsChanged(Contact contact, String action) {
        System.out.println("UI updated: tags " + action.toLowerCase() + " for " + contact.getReferenceId());
    }
}
