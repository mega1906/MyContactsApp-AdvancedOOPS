package com.mycontactapp.view;

public abstract class ContactViewDecorator implements ContactViewComponent {

    private final ContactViewComponent contactViewComponent;

    protected ContactViewDecorator(ContactViewComponent contactViewComponent) {
        this.contactViewComponent = contactViewComponent;
    }

    protected ContactView getBaseView() {
        return contactViewComponent.getView();
    }
}
