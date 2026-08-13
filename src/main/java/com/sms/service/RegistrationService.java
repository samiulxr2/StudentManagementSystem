package com.sms.service;

import java.util.ArrayList;
import java.util.List;

import com.sms.model.Course;
import com.sms.model.Registration;
import com.sms.model.Student;
import com.sms.repository.CourseRepository;
import com.sms.repository.RegistrationRepository;
import com.sms.repository.StudentRepository;

public class RegistrationService {

    private static final int MAX_SECTION_STUDENTS = 50;
    private static final int MAX_LAB_STUDENTS = 25;

    private final RegistrationRepository registrationRepository =
            new RegistrationRepository();

    private final StudentRepository studentRepository =
            new StudentRepository();

    private final CourseRepository courseRepository =
            new CourseRepository();

    public List<Registration> getAllRegistrations() {

        return registrationRepository.loadRegistrations();
    }

    public List<Student> getAllStudents() {

        return studentRepository.loadStudents();
    }

    public List<Course> getAllCourses() {

        return courseRepository.loadCourses();
    }

    public boolean registerStudent(
            String studentId,
            String semester,
            String batch,
            String section,
            String courseCode,
            boolean retake) {

        if (studentId == null
                || semester == null
                || batch == null
                || section == null
                || courseCode == null) {

            return false;
        }

        if (studentId.isBlank()
                || semester.isBlank()
                || batch.isBlank()
                || section.isBlank()
                || courseCode.isBlank()) {

            return false;
        }

        Student student = findStudent(studentId);
        Course course = findCourse(courseCode);

        if (student == null || course == null) {
            return false;
        }

        List<Registration> registrations =
                registrationRepository.loadRegistrations();

        /*
         * Prevent duplicate registration.
         */
        if (isAlreadyRegistered(
                registrations,
                studentId,
                semester,
                courseCode,
                retake)) {

            return false;
        }

        /*
         * Old course records may not have courseType.
         * Treat them as THEORY for compatibility.
         */
        String courseType = course.getCourseType();

        if (courseType == null || courseType.isBlank()) {
            courseType = "THEORY";
        }

        String labSection = "";

        /*
         * =========================
         * LAB COURSE
         * =========================
         */

        if ("LAB".equalsIgnoreCase(courseType)) {

            labSection = findAvailableLabSection(
                    semester,
                    batch,
                    section
            );

            if (labSection.isEmpty()) {
                return false;
            }
        }

        /*
         * =========================
         * THEORY COURSE
         * =========================
         */

        else {

            int sectionCount =
                    countTheoryStudents(
                            semester,
                            batch,
                            section
                    );

            if (sectionCount >= MAX_SECTION_STUDENTS) {
                return false;
            }
        }

        /*
         * =========================
         * CREATE REGISTRATION
         * =========================
         */

        Registration registration =
                new Registration();

        registration.setRegistrationId(
                generateRegistrationId(registrations)
        );

        registration.setStudentId(
                studentId.trim()
        );

        registration.setSemester(
                semester.trim()
        );

        registration.setBatch(
                batch.trim()
        );

        registration.setSection(
                section.trim().toUpperCase()
        );

        registration.setLabSection(
                labSection
        );

        registration.setCourseCode(
                courseCode.trim()
        );

        registration.setRetake(
                retake
        );

        registrations.add(registration);

        registrationRepository.saveRegistrations(
                registrations
        );

        return true;
    }

    /*
     * =========================
     * REMOVE REGISTRATION
     * =========================
     */

    public boolean removeRegistration(
            String registrationId) {

        if (registrationId == null
                || registrationId.isBlank()) {

            return false;
        }

        List<Registration> registrations =
                registrationRepository.loadRegistrations();

        boolean removed =
                registrations.removeIf(
                        registration ->
                                registration.getRegistrationId() != null
                                        && registration.getRegistrationId()
                                        .equalsIgnoreCase(
                                                registrationId.trim()
                                        )
                );

        if (removed) {

            registrationRepository.saveRegistrations(
                    registrations
            );
        }

        return removed;
    }

    /*
     * =========================
     * GET STUDENTS FOR SECTION
     * =========================
     */

    public List<Registration> getStudentsForSection(
            String semester,
            String batch,
            String section) {

        List<Registration> result =
                new ArrayList<>();

        for (Registration registration :
                registrationRepository.loadRegistrations()) {

            if (matches(
                    registration.getSemester(),
                    semester)
                    && matches(
                    registration.getBatch(),
                    batch)
                    && matches(
                    registration.getSection(),
                    section)) {

                result.add(registration);
            }
        }

        return result;
    }

    /*
     * =========================
     * GET STUDENTS FOR LAB
     * =========================
     */

    public List<Registration> getStudentsForLabSection(
            String semester,
            String batch,
            String labSection) {

        List<Registration> result =
                new ArrayList<>();

        for (Registration registration :
                registrationRepository.loadRegistrations()) {

            if (matches(
                    registration.getSemester(),
                    semester)
                    && matches(
                    registration.getBatch(),
                    batch)
                    && matches(
                    registration.getLabSection(),
                    labSection)) {

                result.add(registration);
            }
        }

        return result;
    }

    /*
     * =========================
     * COUNT THEORY STUDENTS
     * =========================
     *
     * Maximum = 50 students
     *
     * A student is counted only once
     * even if registered for multiple
     * theory courses.
     */

    public int countTheoryStudents(
            String semester,
            String batch,
            String section) {

        List<String> studentIds =
                new ArrayList<>();

        for (Registration registration :
                registrationRepository.loadRegistrations()) {

            if (matches(
                    registration.getSemester(),
                    semester)
                    && matches(
                    registration.getBatch(),
                    batch)
                    && matches(
                    registration.getSection(),
                    section)
                    && !registration.getRetake()
                    && !studentIds.contains(
                    registration.getStudentId())) {

                studentIds.add(
                        registration.getStudentId()
                );
            }
        }

        return studentIds.size();
    }

    /*
     * =========================
     * COUNT LAB STUDENTS
     * =========================
     *
     * Maximum per lab group = 25
     */

    public int countLabStudents(
            String semester,
            String batch,
            String labSection) {

        List<String> studentIds =
                new ArrayList<>();

        for (Registration registration :
                registrationRepository.loadRegistrations()) {

            if (matches(
                    registration.getSemester(),
                    semester)
                    && matches(
                    registration.getBatch(),
                    batch)
                    && matches(
                    registration.getLabSection(),
                    labSection)
                    && !registration.getRetake()
                    && !studentIds.contains(
                    registration.getStudentId())) {

                studentIds.add(
                        registration.getStudentId()
                );
            }
        }

        return studentIds.size();
    }

    /*
     * =========================
     * FIND AVAILABLE LAB GROUP
     * =========================
     *
     * Example:
     *
     * A1 = 25
     * A2 = 10
     *
     * Next student -> A2
     */

    public String findAvailableLabSection(
            String semester,
            String batch,
            String section) {

        if (semester == null
                || batch == null
                || section == null
                || section.isBlank()) {

            return "";
        }

        String lab1 =
                section.trim().toUpperCase() + "1";

        String lab2 =
                section.trim().toUpperCase() + "2";

        int lab1Count =
                countLabStudents(
                        semester,
                        batch,
                        lab1
                );

        int lab2Count =
                countLabStudents(
                        semester,
                        batch,
                        lab2
                );

        /*
         * Fill first lab group first.
         */

        if (lab1Count < MAX_LAB_STUDENTS) {
            return lab1;
        }

        /*
         * Then fill second lab group.
         */

        if (lab2Count < MAX_LAB_STUDENTS) {
            return lab2;
        }

        /*
         * Both groups are full.
         */

        return "";
    }

    /*
     * =========================
     * DUPLICATE CHECK
     * =========================
     */

    private boolean isAlreadyRegistered(
            List<Registration> registrations,
            String studentId,
            String semester,
            String courseCode,
            boolean retake) {

        for (Registration registration :
                registrations) {

            if (matches(
                    registration.getStudentId(),
                    studentId)
                    && matches(
                    registration.getSemester(),
                    semester)
                    && matches(
                    registration.getCourseCode(),
                    courseCode)
                    && registration.getRetake() == retake) {

                return true;
            }
        }

        return false;
    }

    /*
     * =========================
     * FIND STUDENT
     * =========================
     */

    private Student findStudent(
            String studentId) {

        for (Student student :
                studentRepository.loadStudents()) {

            if (matches(
                    student.getStudentId(),
                    studentId)) {

                return student;
            }
        }

        return null;
    }

    /*
     * =========================
     * FIND COURSE
     * =========================
     */

    private Course findCourse(
            String courseCode) {

        for (Course course :
                courseRepository.loadCourses()) {

            if (matches(
                    course.getCourseCode(),
                    courseCode)) {

                return course;
            }
        }

        return null;
    }

    /*
     * =========================
     * GENERATE REGISTRATION ID
     * =========================
     */

    private String generateRegistrationId(
            List<Registration> registrations) {

        return "REG-"
                + String.format(
                "%04d",
                registrations.size() + 1
        );
    }

    /*
     * =========================
     * STRING COMPARISON
     * =========================
     */

    private boolean matches(
            String actual,
            String expected) {

        if (actual == null
                || expected == null) {

            return false;
        }

        return actual.trim()
                .equalsIgnoreCase(
                        expected.trim()
                );
    }
}