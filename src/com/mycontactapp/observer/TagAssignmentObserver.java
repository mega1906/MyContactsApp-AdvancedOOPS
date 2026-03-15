package com.mycontactapp.observer;

import com.mycontactapp.model.Contact;

public interface TagAssignmentObserver {

    void onTagsChanged(Contact contact, String action);
}
