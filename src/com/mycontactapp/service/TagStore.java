package com.mycontactapp.service;

import com.mycontactapp.model.Tag;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TagStore {

    private static final Map<String, Set<Tag>> USER_TAGS = new HashMap<>();

    private TagStore() {
    }

    public static Set<Tag> getTagsByOwner(String ownerUserId) {
        return new HashSet<>(USER_TAGS.getOrDefault(ownerUserId, new HashSet<>()));
    }

    public static void addTag(String ownerUserId, Tag tag) {
        USER_TAGS.computeIfAbsent(ownerUserId, key -> new HashSet<>()).add(tag);
    }
}
