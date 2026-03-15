package com.mycontactapp.model;

public class ContactMemento {

    private final Contact snapshot;

    public ContactMemento(Contact snapshot) {
        this.snapshot = snapshot;
    }

    public Contact getSnapshot() {
        return snapshot;
    }
}
