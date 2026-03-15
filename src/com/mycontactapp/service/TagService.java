package com.mycontactapp.service;

import com.mycontactapp.exception.ValidationException;
import com.mycontactapp.factory.TagFlyweightFactory;
import com.mycontactapp.model.Contact;
import com.mycontactapp.model.PredefinedTag;
import com.mycontactapp.model.Tag;
import com.mycontactapp.model.User;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class TagService {

    private final ContactService contactService;

    public TagService() {
        this.contactService = new ContactService();
    }

    public List<Tag> getAvailableTags(User owner) {
        Set<Tag> availableTags = TagStore.getTagsByOwner(owner.getUserId());

        for (PredefinedTag predefinedTag : EnumSet.allOf(PredefinedTag.class)) {
            try {
                availableTags.add(TagFlyweightFactory.getTag(formatPredefinedTag(predefinedTag)));
            } catch (ValidationException exception) {
                throw new IllegalStateException("Invalid predefined tag.", exception);
            }
        }

        return new ArrayList<>(availableTags);
    }

    public Tag createCustomTag(User owner, String tagName) throws ValidationException {
        Tag tag = TagFlyweightFactory.getTag(tagName);
        TagStore.addTag(owner.getUserId(), tag);
        return tag;
    }

    public Contact assignTagToContact(User owner, String referenceId, String tagName) throws ValidationException {
        Tag tag = createCustomTag(owner, tagName);
        return contactService.addTagToContact(owner, referenceId, tag);
    }

    public Contact removeTagFromContact(User owner, String referenceId, String tagName) throws ValidationException {
        Optional<Contact> contactOptional = contactService.findContact(owner, referenceId);

        if (contactOptional.isEmpty()) {
            throw new ValidationException("Contact not found.");
        }

        Contact modifiedContact = contactService.copyForUpdate(contactOptional.get());
        modifiedContact.removeTag(TagFlyweightFactory.getTag(tagName));
        modifiedContact.setUpdatedAt(java.time.LocalDateTime.now());
        ContactStore.replaceContact(modifiedContact);
        return modifiedContact;
    }

    private String formatPredefinedTag(PredefinedTag predefinedTag) {
        String lowercase = predefinedTag.name().toLowerCase();
        return Character.toUpperCase(lowercase.charAt(0)) + lowercase.substring(1);
    }
}
