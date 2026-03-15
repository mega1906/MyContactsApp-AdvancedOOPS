package com.mycontactapp.model;

import com.mycontactapp.exception.ValidationException;
import com.mycontactapp.util.InputValidator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Contact {

    private final String contactId;
    private final String referenceId;
    private final String ownerUserId;
    private String name;
    private List<PhoneNumber> phoneNumbers;
    private List<EmailAddress> emailAddresses;
    private String address;
    private String notes;
    private List<String> tags;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean deleted;
    private LocalDateTime deletedAt;

    protected Contact(String contactId, String referenceId, String ownerUserId, String name,
                      List<PhoneNumber> phoneNumbers, List<EmailAddress> emailAddresses,
                      String address, String notes, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this(contactId, referenceId, ownerUserId, name, phoneNumbers, emailAddresses,
                address, notes, createdAt, updatedAt, false, null);
    }

    protected Contact(String contactId, String referenceId, String ownerUserId, String name,
                      List<PhoneNumber> phoneNumbers, List<EmailAddress> emailAddresses,
                      String address, String notes, LocalDateTime createdAt, LocalDateTime updatedAt,
                      boolean deleted, LocalDateTime deletedAt) {
        this.contactId = contactId;
        this.referenceId = referenceId;
        this.ownerUserId = ownerUserId;
        this.name = name;
        this.phoneNumbers = copyPhoneNumbers(phoneNumbers);
        this.emailAddresses = copyEmailAddresses(emailAddresses);
        this.address = address;
        this.notes = notes;
        this.tags = new ArrayList<>();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deleted = deleted;
        this.deletedAt = deletedAt;
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
        return copyPhoneNumbers(phoneNumbers);
    }

    public List<EmailAddress> getEmailAddresses() {
        return copyEmailAddresses(emailAddresses);
    }

    public String getAddress() {
        return address;
    }

    public String getNotes() {
        return notes;
    }

    public List<String> getTags() {
        return new ArrayList<>(tags);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setName(String name) throws ValidationException {
        InputValidator.validateName(name);
        this.name = name;
    }

    public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) throws ValidationException {
        if (phoneNumbers == null || phoneNumbers.isEmpty()) {
            throw new ValidationException("At least one phone number is required.");
        }

        this.phoneNumbers = copyPhoneNumbers(phoneNumbers);
    }

    public void setEmailAddresses(List<EmailAddress> emailAddresses) throws ValidationException {
        if (emailAddresses == null || emailAddresses.isEmpty()) {
            throw new ValidationException("At least one email address is required.");
        }

        this.emailAddresses = copyEmailAddresses(emailAddresses);
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setTags(List<String> tags) {
        this.tags = new ArrayList<>(tags);
    }

    public void addTag(String tag) {
        if (tag != null && !tag.isBlank() && tags.stream().noneMatch(existing -> existing.equalsIgnoreCase(tag))) {
            tags.add(tag.trim());
        }
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    private List<PhoneNumber> copyPhoneNumbers(List<PhoneNumber> source) {
        List<PhoneNumber> copiedPhoneNumbers = new ArrayList<>();

        if (source == null) {
            return copiedPhoneNumbers;
        }

        for (PhoneNumber phoneNumber : source) {
            copiedPhoneNumbers.add(phoneNumber.copy());
        }

        return copiedPhoneNumbers;
    }

    private List<EmailAddress> copyEmailAddresses(List<EmailAddress> source) {
        List<EmailAddress> copiedEmailAddresses = new ArrayList<>();

        if (source == null) {
            return copiedEmailAddresses;
        }

        for (EmailAddress emailAddress : source) {
            copiedEmailAddresses.add(emailAddress.copy());
        }

        return copiedEmailAddresses;
    }

    public abstract String getContactType();
}
