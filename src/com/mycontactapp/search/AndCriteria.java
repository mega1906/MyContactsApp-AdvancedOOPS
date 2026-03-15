package com.mycontactapp.search;

import com.mycontactapp.model.Contact;

public class AndCriteria implements SearchCriteria {

    private final SearchCriteria left;
    private final SearchCriteria right;

    public AndCriteria(SearchCriteria left, SearchCriteria right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean test(Contact contact) {
        return left.test(contact) && right.test(contact);
    }
}
