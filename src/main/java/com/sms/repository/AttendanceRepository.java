package com.sms.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sms.model.Attendance;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AttendanceRepository {

    private static final String FILE_PATH = "src/main/resources/data/attendance.json";

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void saveAttendance(List<Attendance> attendanceList) {

        try (FileWriter writer = new FileWriter(FILE_PATH)) {

            gson.toJson(attendanceList, writer);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public List<Attendance> loadAttendance() {

        try (FileReader reader = new FileReader(FILE_PATH)) {

            Type type = new TypeToken<List<Attendance>>() {}.getType();

            List<Attendance> attendanceList = gson.fromJson(reader, type);

            if (attendanceList == null) {
                return new ArrayList<>();
            }

            return attendanceList;

        } catch (Exception e) {

            return new ArrayList<>();

        }

    }

}