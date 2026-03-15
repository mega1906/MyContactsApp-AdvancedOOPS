package com.mycontactapp.command;

import com.mycontactapp.model.Contact;
import com.mycontactapp.model.ContactMemento;
import com.mycontactapp.service.ContactStore;

public class EditContactCommand implements ContactEditCommand {

    private final ContactMemento beforeState;
    private final ContactMemento afterState;

    public EditContactCommand(Contact beforeContact, Contact afterContact) {
        this.beforeState = new ContactMemento(beforeContact);
        this.afterState = new ContactMemento(afterContact);
    }

    @Override
    public void execute() {
        ContactStore.replaceContact(afterState.getSnapshot());
    }

    @Override
    public void undo() {
        ContactStore.replaceContact(beforeState.getSnapshot());
    }

    @Override
    public void redo() {
        ContactStore.replaceContact(afterState.getSnapshot());
    }

    @Override
    public ContactMemento getAfterState() {
        return afterState;
    }
}
