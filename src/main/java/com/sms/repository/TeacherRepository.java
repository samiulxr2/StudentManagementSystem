package com.sms.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sms.model.Teacher;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TeacherRepository {

    private static final String FILE_PATH = "src/main/resources/data/teachers.json";

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void saveTeachers(List<Teacher> teachers) {

        try (FileWriter writer = new FileWriter(FILE_PATH)) {

            gson.toJson(teachers, writer);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public List<Teacher> loadTeachers() {

        try (FileReader reader = new FileReader(FILE_PATH)) {

            Type type = new TypeToken<List<Teacher>>() {}.getType();

            List<Teacher> teachers = gson.fromJson(reader, type);

            if (teachers == null) {
                return new ArrayList<>();
            }

            return teachers;

        } catch (Exception e) {

            return new ArrayList<>();

        }

    }

}