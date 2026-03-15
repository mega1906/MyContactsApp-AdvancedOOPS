package com.mycontactapp.model;

import com.mycontactapp.exception.ValidationException;

import java.util.Locale;
import java.util.Objects;

public class Tag {

    private final String name;

    public Tag(String name) throws ValidationException {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Tag name cannot be empty.");
        }

        String trimmedName = name.trim();

        if (!trimmedName.matches("[A-Za-z ]{2,20}")) {
            throw new ValidationException("Tag name must contain only letters and spaces, 2 to 20 characters.");
        }

        this.name = trimmedName;
    }

    public String getName() {
        return name;
    }

    private String normalizedName() {
        return name.toUpperCase(Locale.ROOT);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Tag other)) {
            return false;
        }

        return normalizedName().equals(other.normalizedName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(normalizedName());
    }

    @Override
    public String toString() {
        return name;
    }
}
