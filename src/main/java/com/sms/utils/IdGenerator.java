package com.sms.utils;

import java.util.UUID;

public class IdGenerator {

    private IdGenerator() {
    }

    public static String generateStudentId() {
        return "STU-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public static String generateTeacherId() {
        return "TEA-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public static String generateCourseId() {
        return "CRS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public static String generateAttendanceId() {
        return "ATT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public static String generateResultId() {
        return "RES-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

}