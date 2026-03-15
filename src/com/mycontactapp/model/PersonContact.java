package com.mycontactapp.model;

import java.time.LocalDateTime;
import java.util.List;

public class PersonContact extends Contact {

    public PersonContact(String contactId, String referenceId, String ownerUserId, String name,
                         List<PhoneNumber> phoneNumbers, List<EmailAddress> emailAddresses,
                         String address, String notes, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(contactId, referenceId, ownerUserId, name, phoneNumbers, emailAddresses, address, notes, createdAt, updatedAt);
    }

    public PersonContact(PersonContact other) {
        this(
                other.getContactId(),
                other.getReferenceId(),
                other.getOwnerUserId(),
                other.getName(),
                other.getPhoneNumbers(),
                other.getEmailAddresses(),
                other.getAddress(),
                other.getNotes(),
                other.getCreatedAt(),
                other.getUpdatedAt()
        );
    }

    @Override
    public String getContactType() {
        return "Person Contact";
    }
}
