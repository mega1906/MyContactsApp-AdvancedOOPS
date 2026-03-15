package com.mycontactapp.view;

import java.util.ArrayList;
import java.util.List;

public class MaskedEmailContactViewDecorator extends ContactViewDecorator {

    public MaskedEmailContactViewDecorator(ContactViewComponent contactViewComponent) {
        super(contactViewComponent);
    }

    @Override
    public ContactView getView() {
        ContactView baseView = getBaseView();
        List<String> maskedEmails = new ArrayList<>();

        for (String email : baseView.getEmailAddresses()) {
            maskedEmails.add(maskEmail(email));
        }

        return new ContactView(
                baseView.getReferenceId(),
                baseView.getContactType(),
                baseView.getName(),
                baseView.getPhoneNumbers(),
                maskedEmails,
                baseView.getTags(),
                baseView.getAddress(),
                baseView.getNotes(),
                baseView.getCreatedAt(),
                baseView.getUpdatedAt()
        );
    }

    private String maskEmail(String email) {
        int atIndex = email.indexOf('@');

        if (atIndex <= 1) {
            return email;
        }

        String firstPart = email.substring(0, 1);
        String domainPart = email.substring(atIndex);
        return firstPart + "***" + domainPart;
    }
}
