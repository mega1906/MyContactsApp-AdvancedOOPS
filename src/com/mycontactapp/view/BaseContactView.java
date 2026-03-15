package com.mycontactapp.view;

public class BaseContactView implements ContactViewComponent {

    private final ContactView contactView;

    public BaseContactView(ContactView contactView) {
        this.contactView = contactView;
    }

    @Override
    public ContactView getView() {
        return contactView;
    }
}
