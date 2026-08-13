package com.sms.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class TableUtil {

    private TableUtil() {
    }

    public static <T> ObservableList<T> toObservableList(List<T> list) {

        ObservableList<T> observableList = FXCollections.observableArrayList();

        if (list != null) {
            observableList.addAll(list);
        }

        return observableList;
    }

}