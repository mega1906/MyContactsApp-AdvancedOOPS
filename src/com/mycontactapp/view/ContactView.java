package com.mycontactapp.view;

import java.util.List;
import java.util.Optional;

public final class ContactView {

    private final String referenceId;
    private final String contactType;
    private final String name;
    private final List<String> phoneNumbers;
    private final List<String> emailAddresses;
    private final List<String> tags;
    private final Optional<String> address;
    private final Optional<String> notes;
    private final String createdAt;
    private final String updatedAt;
    private final int contactFrequency;

    public ContactView(String referenceId, String contactType, String name, List<String> phoneNumbers,
                       List<String> emailAddresses, List<String> tags, Optional<String> address, Optional<String> notes,
                       String createdAt, String updatedAt, int contactFrequency) {
        this.referenceId = referenceId;
        this.contactType = contactType;
        this.name = name;
        this.phoneNumbers = List.copyOf(phoneNumbers);
        this.emailAddresses = List.copyOf(emailAddresses);
        this.tags = List.copyOf(tags);
        this.address = address;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.contactFrequency = contactFrequency;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public String getContactType() {
        return contactType;
    }

    public String getName() {
        return name;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public List<String> getEmailAddresses() {
        return emailAddresses;
    }

    public List<String> getTags() {
        return tags;
    }

    public Optional<String> getAddress() {
        return address;
    }

    public Optional<String> getNotes() {
        return notes;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public int getContactFrequency() {
        return contactFrequency;
    }

    @Override
    public String toString() {
        return String.format(
                "Reference Id : %s%n" +
                        "Contact Type : %s%n" +
                        "Name         : %s%n" +
                        "Phones       : %s%n" +
                        "Emails       : %s%n" +
                        "Tags         : %s%n" +
                        "Address      : %s%n" +
                        "Notes        : %s%n" +
                        "Created At   : %s%n" +
                        "Updated At   : %s%n" +
                        "Frequency    : %s",
                referenceId,
                contactType,
                name,
                String.join(", ", phoneNumbers),
                String.join(", ", emailAddresses),
                tags.isEmpty() ? "-" : String.join(", ", tags),
                address.orElse("-"),
                notes.orElse("-"),
                createdAt,
                updatedAt,
                contactFrequency
        );
    }
}
