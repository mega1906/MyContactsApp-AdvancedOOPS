package com.mycontactapp.search;

import com.mycontactapp.model.Contact;

import java.util.regex.Pattern;

public class PhoneCriteria implements SearchCriteria {

    private final Pattern pattern;

    public PhoneCriteria(String query) {
        this.pattern = Pattern.compile(Pattern.quote(query), Pattern.CASE_INSENSITIVE);
    }

    @Override
    public boolean test(Contact contact) {
        return contact.getPhoneNumbers().stream()
                .map(phoneNumber -> phoneNumber.getValue())
                .anyMatch(phoneNumber -> pattern.matcher(phoneNumber).find());
    }
}
