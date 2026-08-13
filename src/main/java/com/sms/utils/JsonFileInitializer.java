package com.sms.utils;

import com.sms.config.AppConfig;

public class JsonFileInitializer {

    private JsonFileInitializer() {
    }

    public static void initialize() {

        FileUtil.createFile(AppConfig.STUDENT_FILE);
        FileUtil.createFile(AppConfig.TEACHER_FILE);
        FileUtil.createFile(AppConfig.COURSE_FILE);
        FileUtil.createFile(AppConfig.ATTENDANCE_FILE);
        FileUtil.createFile(AppConfig.RESULT_FILE);
        FileUtil.createFile(AppConfig.USER_FILE);

    }

}