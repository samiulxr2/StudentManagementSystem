package com.sms.service;

import com.sms.model.Course;
import com.sms.repository.CourseRepository;

import java.util.List;

public class CourseService {

    private final CourseRepository repository = new CourseRepository();

    public void addCourse(Course course) {

        List<Course> courses = repository.loadCourses();

        courses.add(course);

        repository.saveCourses(courses);

    }

    public List<Course> getAllCourses() {

        return repository.loadCourses();

    }

    public boolean updateCourse(Course updatedCourse) {

        List<Course> courses = repository.loadCourses();

        for (int i = 0; i < courses.size(); i++) {

            if (courses.get(i).getCourseCode().equals(updatedCourse.getCourseCode())) {

                courses.set(i, updatedCourse);

                repository.saveCourses(courses);

                return true;

            }

        }

        return false;

    }

    public boolean deleteCourse(String courseCode) {

        List<Course> courses = repository.loadCourses();

        boolean removed = courses.removeIf(course ->
                course.getCourseCode().equals(courseCode));

        if (removed) {

            repository.saveCourses(courses);

        }

        return removed;

    }

    public Course searchCourse(String courseCode) {

        for (Course course : repository.loadCourses()) {

            if (course.getCourseCode().equals(courseCode)) {

                return course;

            }

        }

        return null;

    }

}