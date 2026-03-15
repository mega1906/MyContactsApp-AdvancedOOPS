package com.mycontactapp.service;

import com.mycontactapp.filter.CompositeContactFilter;
import com.mycontactapp.filter.ContactFilter;
import com.mycontactapp.filter.ContactSortStrategy;
import com.mycontactapp.filter.DateAddedFilter;
import com.mycontactapp.filter.DateAddedSortStrategy;
import com.mycontactapp.filter.FrequencySortStrategy;
import com.mycontactapp.filter.FrequentContactFilter;
import com.mycontactapp.filter.NameSortStrategy;
import com.mycontactapp.filter.TagFilter;
import com.mycontactapp.model.Contact;
import com.mycontactapp.model.User;
import com.mycontactapp.view.ContactView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AdvancedFilterService {

    public List<ContactView> filterContacts(User owner, String tag, LocalDate dateAdded,
                                            Integer minimumFrequency, String sortOption) {
        List<Contact> ownerContacts = new ArrayList<>(ContactStore.getContactsByOwner(owner.getUserId()));
        CompositeContactFilter compositeContactFilter = new CompositeContactFilter();

        if (tag != null && !tag.isBlank()) {
            compositeContactFilter.addFilter(new TagFilter(tag));
        }

        if (dateAdded != null) {
            compositeContactFilter.addFilter(new DateAddedFilter(dateAdded));
        }

        if (minimumFrequency != null) {
            compositeContactFilter.addFilter(new FrequentContactFilter(minimumFrequency));
        }

        List<Contact> filteredContacts = compositeContactFilter.apply(ownerContacts);
        List<Contact> sortedContacts = selectSortStrategy(sortOption).sort(filteredContacts);

        return sortedContacts.stream()
                .map(this::toContactView)
                .toList();
    }

    private ContactSortStrategy selectSortStrategy(String sortOption) {
        if ("DATE".equalsIgnoreCase(sortOption)) {
            return new DateAddedSortStrategy();
        }

        if ("FREQUENCY".equalsIgnoreCase(sortOption)) {
            return new FrequencySortStrategy();
        }

        return new NameSortStrategy();
    }

    private ContactView toContactView(Contact contact) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return new ContactView(
                contact.getReferenceId(),
                contact.getContactType(),
                contact.getName(),
                contact.getPhoneNumbers().stream().map(phone -> phone.getValue()).toList(),
                contact.getEmailAddresses().stream().map(mail -> mail.getValue()).toList(),
                contact.getTags(),
                java.util.Optional.ofNullable(contact.getAddress()).filter(value -> !value.isBlank()),
                java.util.Optional.ofNullable(contact.getNotes()).filter(value -> !value.isBlank()),
                contact.getCreatedAt().format(formatter),
                contact.getUpdatedAt().format(formatter),
                contact.getContactFrequency()
        );
    }
}
