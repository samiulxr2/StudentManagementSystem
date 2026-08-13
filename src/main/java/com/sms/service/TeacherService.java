package com.sms.service;

import com.sms.model.Teacher;
import com.sms.repository.TeacherRepository;

import java.util.List;

public class TeacherService {

    private final TeacherRepository repository = new TeacherRepository();

    public void addTeacher(Teacher teacher) {

        List<Teacher> teachers = repository.loadTeachers();

        teachers.add(teacher);

        repository.saveTeachers(teachers);

    }

    public List<Teacher> getAllTeachers() {

        return repository.loadTeachers();

    }

    public boolean updateTeacher(Teacher updatedTeacher) {

        List<Teacher> teachers = repository.loadTeachers();

        for (int i = 0; i < teachers.size(); i++) {

            if (teachers.get(i).getTeacherId().equals(updatedTeacher.getTeacherId())) {

                teachers.set(i, updatedTeacher);

                repository.saveTeachers(teachers);

                return true;

            }

        }

        return false;

    }

    public boolean deleteTeacher(String teacherId) {

        List<Teacher> teachers = repository.loadTeachers();

        boolean removed = teachers.removeIf(teacher ->
                teacher.getTeacherId().equals(teacherId));

        if (removed) {

            repository.saveTeachers(teachers);

        }

        return removed;

    }

    public Teacher searchTeacher(String teacherId) {

        for (Teacher teacher : repository.loadTeachers()) {

            if (teacher.getTeacherId().equals(teacherId)) {

                return teacher;

            }

        }

        return null;

    }

}