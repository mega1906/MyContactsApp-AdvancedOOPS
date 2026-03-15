package com.mycontactapp.search;

import com.mycontactapp.model.Contact;

import java.util.regex.Pattern;

public class TagCriteria implements SearchCriteria {

    private final Pattern pattern;

    public TagCriteria(String query) {
        this.pattern = Pattern.compile(Pattern.quote(query), Pattern.CASE_INSENSITIVE);
    }

    @Override
    public boolean test(Contact contact) {
        return contact.getTags().stream()
                .map(tag -> tag.getName())
                .anyMatch(tag -> pattern.matcher(tag).find());
    }
}
