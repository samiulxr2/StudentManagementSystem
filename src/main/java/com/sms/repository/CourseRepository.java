package com.sms.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sms.model.Course;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CourseRepository {

    private static final String FILE_PATH = "src/main/resources/data/courses.json";

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void saveCourses(List<Course> courses) {

        try (FileWriter writer = new FileWriter(FILE_PATH)) {

            gson.toJson(courses, writer);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public List<Course> loadCourses() {

        try (FileReader reader = new FileReader(FILE_PATH)) {

            Type type = new TypeToken<List<Course>>() {}.getType();

            List<Course> courses = gson.fromJson(reader, type);

            if (courses == null) {
                return new ArrayList<>();
            }

            return courses;

        } catch (Exception e) {

            return new ArrayList<>();

        }

    }

}