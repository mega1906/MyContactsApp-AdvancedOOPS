package com.mycontactapp.factory;

import com.mycontactapp.exception.ValidationException;
import com.mycontactapp.model.Tag;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TagFlyweightFactory {

    private static final Map<String, Tag> TAG_POOL = new HashMap<>();

    private TagFlyweightFactory() {
    }

    public static Tag getTag(String tagName) throws ValidationException {
        String key = tagName.trim().toUpperCase(Locale.ROOT);

        if (!TAG_POOL.containsKey(key)) {
            TAG_POOL.put(key, new Tag(tagName));
        }

        return TAG_POOL.get(key);
    }
}
