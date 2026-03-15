package com.mycontactapp.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class ContactTagAssignment {

    private final String contactId;
    private final Tag tag;
    private final LocalDateTime assignedAt;

    public ContactTagAssignment(String contactId, Tag tag, LocalDateTime assignedAt) {
        this.contactId = contactId;
        this.tag = tag;
        this.assignedAt = assignedAt;
    }

    public String getContactId() {
        return contactId;
    }

    public Tag getTag() {
        return tag;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof ContactTagAssignment other)) {
            return false;
        }

        return Objects.equals(contactId, other.contactId) && Objects.equals(tag, other.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactId, tag);
    }
}
