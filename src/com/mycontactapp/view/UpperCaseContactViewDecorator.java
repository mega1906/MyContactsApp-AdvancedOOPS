package com.mycontactapp.view;

public class UpperCaseContactViewDecorator extends ContactViewDecorator {

    public UpperCaseContactViewDecorator(ContactViewComponent contactViewComponent) {
        super(contactViewComponent);
    }

    @Override
    public ContactView getView() {
        ContactView baseView = getBaseView();

        return new ContactView(
                baseView.getReferenceId(),
                baseView.getContactType(),
                baseView.getName().toUpperCase(),
                baseView.getPhoneNumbers(),
                baseView.getEmailAddresses(),
                baseView.getAddress(),
                baseView.getNotes().map(String::toUpperCase),
                baseView.getCreatedAt(),
                baseView.getUpdatedAt()
        );
    }
}
