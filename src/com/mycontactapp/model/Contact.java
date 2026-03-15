package com.mycontactapp.model;

import java.time.LocalDateTime;
import java.util.List;

public abstract class Contact {

    private final String contactId;
    private final String referenceId;
    private final String ownerUserId;
    private final String name;
    private final List<PhoneNumber> phoneNumbers;
    private final List<EmailAddress> emailAddresses;
    private final String address;
    private final String notes;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    protected Contact(String contactId, String referenceId, String ownerUserId, String name,
                      List<PhoneNumber> phoneNumbers, List<EmailAddress> emailAddresses,
                      String address, String notes, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.contactId = contactId;
        this.referenceId = referenceId;
        this.ownerUserId = ownerUserId;
        this.name = name;
        this.phoneNumbers = phoneNumbers;
        this.emailAddresses = emailAddresses;
        this.address = address;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getContactId() {
        return contactId;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public String getOwnerUserId() {
        return ownerUserId;
    }

    public String getName() {
        return name;
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public List<EmailAddress> getEmailAddresses() {
        return emailAddresses;
    }

    public String getAddress() {
        return address;
    }

    public String getNotes() {
        return notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public abstract String getContactType();
}
