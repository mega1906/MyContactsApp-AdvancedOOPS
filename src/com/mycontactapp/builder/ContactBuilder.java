package com.mycontactapp.builder;

import com.mycontactapp.model.EmailAddress;
import com.mycontactapp.model.PhoneNumber;

import java.time.LocalDateTime;
import java.util.List;

public class ContactBuilder {

    private String contactId;
    private String referenceId;
    private String ownerUserId;
    private String name;
    private List<PhoneNumber> phoneNumbers;
    private List<EmailAddress> emailAddresses;
    private String address;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ContactBuilder setContactId(String contactId) {
        this.contactId = contactId;
        return this;
    }

    public ContactBuilder setReferenceId(String referenceId) {
        this.referenceId = referenceId;
        return this;
    }

    public ContactBuilder setOwnerUserId(String ownerUserId) {
        this.ownerUserId = ownerUserId;
        return this;
    }

    public ContactBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ContactBuilder setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
        return this;
    }

    public ContactBuilder setEmailAddresses(List<EmailAddress> emailAddresses) {
        this.emailAddresses = emailAddresses;
        return this;
    }

    public ContactBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public ContactBuilder setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public ContactBuilder setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public ContactBuilder setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
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
}
