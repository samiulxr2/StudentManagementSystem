package com.sms.utils;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {

        if (isEmpty(email)) {
            return false;
        }

        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public static boolean isValidPhone(String phone) {

        if (isEmpty(phone)) {
            return false;
        }

        return phone.matches("\\d{11}");
    }

    public static boolean isValidStudentId(String studentId) {

        if (isEmpty(studentId)) {
            return false;
        }

        return studentId.matches("[A-Za-z0-9-]+");
    }

}