package com.sms.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class SearchUtil {

    private SearchUtil() {
    }

    public static <T> List<T> filter(List<T> items, Predicate<T> predicate) {

        List<T> filteredList = new ArrayList<>();

        if (items == null) {
            return filteredList;
        }

        for (T item : items) {

            if (predicate.test(item)) {
                filteredList.add(item);
            }

        }

        return filteredList;
    }

}