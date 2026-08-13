package com.sms.utils;

import java.io.File;

public class FileUtil {

    private FileUtil() {
    }

    public static boolean fileExists(String filePath) {
        return new File(filePath).exists();
    }

    public static boolean createFile(String filePath) {

        try {

            File file = new File(filePath);

            if (!file.exists()) {

                file.getParentFile().mkdirs();

                return file.createNewFile();

            }

            return true;

        } catch (Exception e) {

            e.printStackTrace();
            return false;

        }

    }

}