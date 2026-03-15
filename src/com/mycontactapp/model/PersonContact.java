package com.mycontactapp.model;

import java.time.LocalDateTime;
import java.util.List;

public class PersonContact extends Contact {

    public PersonContact(String contactId, String referenceId, String ownerUserId, String name,
                         List<PhoneNumber> phoneNumbers, List<EmailAddress> emailAddresses,
                         String address, String notes, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(contactId, referenceId, ownerUserId, name, phoneNumbers, emailAddresses, address, notes, createdAt, updatedAt);
    }

    @Override
    public String getContactType() {
        return "Person Contact";
    }
}
