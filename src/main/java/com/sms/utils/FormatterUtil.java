package com.sms.utils;

import java.text.DecimalFormat;

public class FormatterUtil {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.00");

    private FormatterUtil() {
    }

    public static String formatGPA(double gpa) {
        return DECIMAL_FORMAT.format(gpa);
    }

    public static String formatMarks(double marks) {
        return DECIMAL_FORMAT.format(marks);
    }

    public static String capitalize(String text) {

        if (text == null || text.trim().isEmpty()) {
            return "";
        }

        return text.substring(0, 1).toUpperCase() +
                text.substring(1).toLowerCase();
    }

}