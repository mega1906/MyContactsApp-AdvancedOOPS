package com.mycontactapp.model;

import java.time.LocalDateTime;
import java.util.List;

public class OrganizationContact extends Contact {

    public OrganizationContact(String contactId, String referenceId, String ownerUserId, String name,
                               List<PhoneNumber> phoneNumbers, List<EmailAddress> emailAddresses,
                               String address, String notes, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(contactId, referenceId, ownerUserId, name, phoneNumbers, emailAddresses, address, notes, createdAt, updatedAt);
    }

    public OrganizationContact(OrganizationContact other) {
        super(
                other.getContactId(),
                other.getReferenceId(),
                other.getOwnerUserId(),
                other.getName(),
                other.getPhoneNumbers(),
                other.getEmailAddresses(),
                other.getAddress(),
                other.getNotes(),
                other.getCreatedAt(),
                other.getUpdatedAt(),
                other.isDeleted(),
                other.getDeletedAt()
        );
        setTags(other.getTags());
        setContactFrequency(other.getContactFrequency());
    }

    @Override
    public String getContactType() {
        return "Organization Contact";
    }
}
