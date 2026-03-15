package com.mycontactapp.search;

import com.mycontactapp.model.Contact;

import java.util.regex.Pattern;

public class NameCriteria implements SearchCriteria {

    private final Pattern pattern;

    public NameCriteria(String query) {
        this.pattern = Pattern.compile(Pattern.quote(query), Pattern.CASE_INSENSITIVE);
    }

    @Override
    public boolean test(Contact contact) {
        return pattern.matcher(contact.getName()).find();
    }
}
