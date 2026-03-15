package com.mycontactapp.search;

import com.mycontactapp.model.Contact;

import java.util.regex.Pattern;

public class EmailCriteria implements SearchCriteria {

    private final Pattern pattern;

    public EmailCriteria(String query) {
        this.pattern = Pattern.compile(Pattern.quote(query), Pattern.CASE_INSENSITIVE);
    }

    @Override
    public boolean test(Contact contact) {
        return contact.getEmailAddresses().stream()
                .map(emailAddress -> emailAddress.getValue())
                .anyMatch(email -> pattern.matcher(email).find());
    }
}
