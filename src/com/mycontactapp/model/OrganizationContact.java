package com.mycontactapp.model;

import java.time.LocalDateTime;
import java.util.List;

public class OrganizationContact extends Contact {

    public OrganizationContact(String contactId, String referenceId, String ownerUserId, String name,
                               List<PhoneNumber> phoneNumbers, List<EmailAddress> emailAddresses,
                               String address, String notes, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(contactId, referenceId, ownerUserId, name, phoneNumbers, emailAddresses, address, notes, createdAt, updatedAt);
    }

    @Override
    public String getContactType() {
        return "Organization Contact";
    }
}
