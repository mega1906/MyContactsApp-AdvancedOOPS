package com.mycontactapp.factory;

import com.mycontactapp.builder.ContactBuilder;
import com.mycontactapp.exception.ValidationException;
import com.mycontactapp.model.Contact;
import com.mycontactapp.model.OrganizationContact;
import com.mycontactapp.model.PersonContact;

public class ContactFactory {

    public Contact createContact(String contactType, ContactBuilder contactBuilder) throws ValidationException {
        if ("PERSON".equalsIgnoreCase(contactType)) {
            return new PersonContact(
                    contactBuilder.getContactId(),
                    contactBuilder.getReferenceId(),
                    contactBuilder.getOwnerUserId(),
                    contactBuilder.getName(),
                    contactBuilder.getPhoneNumbers(),
                    contactBuilder.getEmailAddresses(),
                    contactBuilder.getAddress(),
                    contactBuilder.getNotes(),
                    contactBuilder.getCreatedAt(),
                    contactBuilder.getUpdatedAt()
            );
        }

        if ("ORGANIZATION".equalsIgnoreCase(contactType)) {
            return new OrganizationContact(
                    contactBuilder.getContactId(),
                    contactBuilder.getReferenceId(),
                    contactBuilder.getOwnerUserId(),
                    contactBuilder.getName(),
                    contactBuilder.getPhoneNumbers(),
                    contactBuilder.getEmailAddresses(),
                    contactBuilder.getAddress(),
                    contactBuilder.getNotes(),
                    contactBuilder.getCreatedAt(),
                    contactBuilder.getUpdatedAt()
            );
        }

        throw new ValidationException("Invalid contact type selected.");
    }
}
