package com.sms.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sms.model.Student;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {

    private static final String FILE_PATH = "src/main/resources/data/students.json";

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void saveStudents(List<Student> students) {

        try (FileWriter writer = new FileWriter(FILE_PATH)) {

            gson.toJson(students, writer);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public List<Student> loadStudents() {

        try (FileReader reader = new FileReader(FILE_PATH)) {

            Type type = new TypeToken<List<Student>>() {}.getType();

            List<Student> students = gson.fromJson(reader, type);

            if (students == null) {
                return new ArrayList<>();
            }

            return students;

        } catch (Exception e) {

            return new ArrayList<>();

        }

    }

}