package com.sms.service;

import com.sms.model.Student;
import com.sms.repository.StudentRepository;

import java.util.List;

public class StudentService {

    private final StudentRepository repository = new StudentRepository();

    public void addStudent(Student student) {

        List<Student> students = repository.loadStudents();

        students.add(student);

        repository.saveStudents(students);

    }

    public List<Student> getAllStudents() {

        return repository.loadStudents();

    }

    public boolean updateStudent(Student updatedStudent) {

        List<Student> students = repository.loadStudents();

        for (int i = 0; i < students.size(); i++) {

            if (students.get(i).getStudentId().equals(updatedStudent.getStudentId())) {

                students.set(i, updatedStudent);

                repository.saveStudents(students);

                return true;

            }

        }

        return false;

    }

    public boolean deleteStudent(String studentId) {

        List<Student> students = repository.loadStudents();

        boolean removed = students.removeIf(student ->
                student.getStudentId().equals(studentId));

        if (removed) {

            repository.saveStudents(students);

        }

        return removed;

    }

    public Student searchStudent(String studentId) {

        for (Student student : repository.loadStudents()) {

            if (student.getStudentId().equals(studentId)) {

                return student;

            }

        }

        return null;

    }

}