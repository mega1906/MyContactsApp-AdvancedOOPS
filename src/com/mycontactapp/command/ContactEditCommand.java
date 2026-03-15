package com.mycontactapp.command;

import com.mycontactapp.model.ContactMemento;

public interface ContactEditCommand {

    void execute();

    void undo();

    void redo();

    ContactMemento getAfterState();
}
