package com.mycontactapp.service;

import com.mycontactapp.model.Contact;
import com.mycontactapp.model.User;
import com.mycontactapp.search.AndCriteria;
import com.mycontactapp.search.CriteriaFilterHandler;
import com.mycontactapp.search.EmailCriteria;
import com.mycontactapp.search.NameCriteria;
import com.mycontactapp.search.OwnerFilterHandler;
import com.mycontactapp.search.PhoneCriteria;
import com.mycontactapp.search.SearchCriteria;
import com.mycontactapp.search.SearchFilterHandler;
import com.mycontactapp.search.TagCriteria;
import com.mycontactapp.view.ContactView;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SearchService {

    public List<ContactView> searchContacts(User owner, String name, String phone, String email, String tag) {
        SearchCriteria searchCriteria = buildCriteria(name, phone, email, tag);
        SearchFilterHandler ownerFilterHandler = new OwnerFilterHandler();
        ownerFilterHandler.setNext(new CriteriaFilterHandler());

        List<Contact> ownerContacts = ContactStore.getContactsByOwner(owner.getUserId());
        List<Contact> results = ownerFilterHandler.handle(ownerContacts, searchCriteria);

        return results.stream()
                .map(this::toContactView)
                .toList();
    }

    private SearchCriteria buildCriteria(String name, String phone, String email, String tag) {
        List<SearchCriteria> criteriaList = new ArrayList<>();

        if (name != null && !name.isBlank()) {
            criteriaList.add(new NameCriteria(name));
        }

        if (phone != null && !phone.isBlank()) {
            criteriaList.add(new PhoneCriteria(phone));
        }

        if (email != null && !email.isBlank()) {
            criteriaList.add(new EmailCriteria(email));
        }

        if (tag != null && !tag.isBlank()) {
            criteriaList.add(new TagCriteria(tag));
        }

        if (criteriaList.isEmpty()) {
            return contact -> true;
        }

        SearchCriteria combined = criteriaList.get(0);

        for (int index = 1; index < criteriaList.size(); index++) {
            combined = new AndCriteria(combined, criteriaList.get(index));
        }

        return combined;
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
                contact.getUpdatedAt().format(formatter)
        );
    }
}
