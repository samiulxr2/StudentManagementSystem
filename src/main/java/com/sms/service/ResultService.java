package com.sms.service;

import com.sms.model.Result;
import com.sms.repository.ResultRepository;

import java.util.ArrayList;
import java.util.List;

public class ResultService {

    private final ResultRepository repository =
            new ResultRepository();

    // =========================================================
    // ADD RESULT
    // =========================================================

    public boolean addResult(Result result) {

        if (result == null) {
            return false;
        }

        List<Result> results =
                repository.loadResults();

        // One result per student + course + semester
        if (findResult(
                results,
                result.getStudentId(),
                result.getCourseCode(),
                result.getSemester()) != null) {

            return false;
        }

        results.add(result);

        repository.saveResults(results);

        return true;
    }

    // =========================================================
    // GET ALL RESULTS
    // =========================================================

    public List<Result> getAllResults() {

        return repository.loadResults();
    }

    // =========================================================
    // FIND RESULT
    // Student + Course + Semester
    // =========================================================

    public Result findResult(
            String studentId,
            String courseCode,
            String semester) {

        List<Result> results =
                repository.loadResults();

        return findResult(
                results,
                studentId,
                courseCode,
                semester
        );
    }

    private Result findResult(
            List<Result> results,
            String studentId,
            String courseCode,
            String semester) {

        if (studentId == null
                || studentId.isBlank()
                || courseCode == null
                || courseCode.isBlank()
                || semester == null
                || semester.isBlank()) {

            return null;
        }

        for (Result result : results) {

            if (result == null) {
                continue;
            }

            if (result.getStudentId() != null
                    && result.getCourseCode() != null
                    && result.getSemester() != null

                    && result.getStudentId()
                    .equalsIgnoreCase(
                            studentId.trim())

                    && result.getCourseCode()
                    .equalsIgnoreCase(
                            courseCode.trim())

                    && result.getSemester()
                    .equalsIgnoreCase(
                            semester.trim())) {

                return result;
            }
        }

        return null;
    }

    // =========================================================
    // UPDATE RESULT
    // =========================================================

    public boolean updateResult(Result updatedResult) {

        if (updatedResult == null) {
            return false;
        }

        List<Result> results =
                repository.loadResults();

        for (int i = 0; i < results.size(); i++) {

            Result existing = results.get(i);

            if (existing == null) {
                continue;
            }

            if (existing.getStudentId() != null
                    && existing.getCourseCode() != null
                    && existing.getSemester() != null

                    && existing.getStudentId()
                    .equalsIgnoreCase(
                            updatedResult.getStudentId())

                    && existing.getCourseCode()
                    .equalsIgnoreCase(
                            updatedResult.getCourseCode())

                    && existing.getSemester()
                    .equalsIgnoreCase(
                            updatedResult.getSemester())) {

                results.set(i, updatedResult);

                repository.saveResults(results);

                return true;
            }
        }

        return false;
    }

    // =========================================================
    // DELETE RESULT
    // =========================================================

    public boolean deleteResult(
            String studentId,
            String courseCode,
            String semester) {

        if (studentId == null
                || studentId.isBlank()
                || courseCode == null
                || courseCode.isBlank()
                || semester == null
                || semester.isBlank()) {

            return false;
        }

        List<Result> results =
                repository.loadResults();

        boolean removed =
                results.removeIf(result ->

                        result != null

                                && result.getStudentId() != null
                                && result.getCourseCode() != null
                                && result.getSemester() != null

                                && result.getStudentId()
                                .equalsIgnoreCase(
                                        studentId.trim())

                                && result.getCourseCode()
                                .equalsIgnoreCase(
                                        courseCode.trim())

                                && result.getSemester()
                                .equalsIgnoreCase(
                                        semester.trim())
                );

        if (removed) {

            repository.saveResults(results);
        }

        return removed;
    }

    // =========================================================
    // GET ALL RESULTS OF A STUDENT
    // =========================================================

    public List<Result> getStudentResults(
            String studentId) {

        List<Result> studentResults =
                new ArrayList<>();

        if (studentId == null
                || studentId.isBlank()) {

            return studentResults;
        }

        for (Result result :
                repository.loadResults()) {

            if (result == null) {
                continue;
            }

            if (result.getStudentId() != null
                    && result.getStudentId()
                    .equalsIgnoreCase(
                            studentId.trim())) {

                studentResults.add(result);
            }
        }

        return studentResults;
    }

    // =========================================================
    // GET RESULTS OF ONE SEMESTER
    // =========================================================

    public List<Result> getSemesterResults(
            String studentId,
            String semester) {

        List<Result> semesterResults =
                new ArrayList<>();

        if (studentId == null
                || studentId.isBlank()
                || semester == null
                || semester.isBlank()) {

            return semesterResults;
        }

        for (Result result :
                repository.loadResults()) {

            if (result == null) {
                continue;
            }

            if (result.getStudentId() != null
                    && result.getCourseCode() != null
                    && result.getSemester() != null

                    && result.getStudentId()
                    .equalsIgnoreCase(
                            studentId.trim())

                    && result.getSemester()
                    .equalsIgnoreCase(
                            semester.trim())) {

                semesterResults.add(result);
            }
        }

        return semesterResults;
    }

    // =========================================================
    // CALCULATE SEMESTER GPA
    // =========================================================

    /*
     * Example:
     *
     * OOP     = 3.75
     * OOP Lab = 3.50
     * AOL     = 3.25
     *
     * (3.75 + 3.50 + 3.25) / 3
     *
     * = 3.50
     */

    public double calculateSemesterGPA(
            String studentId,
            String semester) {

        List<Result> semesterResults =
                getSemesterResults(
                        studentId,
                        semester
                );

        if (semesterResults.isEmpty()) {
            return 0.0;
        }

        double totalGPA = 0.0;

        for (Result result :
                semesterResults) {

            totalGPA += result.getGpa();
        }

        double average =
                totalGPA /
                        semesterResults.size();

        return roundUp(average);
    }

    // =========================================================
    // RAW SEMESTER GPA
    // =========================================================

    /*
     * This method does NOT round.
     *
     * It is important for Overall CGPA.
     *
     * Example:
     *
     * Semester 1 = 3.504
     * Semester 2 = 3.601
     *
     * Overall calculation must use
     * the actual values, not already-rounded
     * values.
     */

    public double calculateRawSemesterGPA(
            String studentId,
            String semester) {

        List<Result> semesterResults =
                getSemesterResults(
                        studentId,
                        semester
                );

        if (semesterResults.isEmpty()) {
            return 0.0;
        }

        double totalGPA = 0.0;

        for (Result result :
                semesterResults) {

            totalGPA += result.getGpa();
        }

        return totalGPA /
                semesterResults.size();
    }

    // =========================================================
    // GET ALL SEMESTERS OF A STUDENT
    // =========================================================

    public List<String> getStudentSemesters(
            String studentId) {

        List<String> semesters =
                new ArrayList<>();

        if (studentId == null
                || studentId.isBlank()) {

            return semesters;
        }

        for (Result result :
                repository.loadResults()) {

            if (result == null) {
                continue;
            }

            String semester =
                    result.getSemester();

            if (result.getStudentId() != null
                    && semester != null
                    && !semester.isBlank()

                    && result.getStudentId()
                    .equalsIgnoreCase(
                            studentId.trim())

                    && !containsIgnoreCase(
                            semesters,
                            semester)) {

                semesters.add(semester);
            }
        }

        return semesters;
    }

    // =========================================================
    // CALCULATE OVERALL CGPA
    // =========================================================

    /*
     * Example:
     *
     * Semester 1 = 3.50
     * Semester 2 = 3.67
     * Semester 3 = 3.33
     * Semester 4 = 3.75
     *
     * (3.50 + 3.67 + 3.33 + 3.75) / 4
     *
     * = 3.5625
     *
     * Required:
     *
     * 3.5625 -> 3.57
     */

    public double calculateOverallCGPA(
            String studentId) {

        List<String> semesters =
                getStudentSemesters(
                        studentId
                );

        if (semesters.isEmpty()) {
            return 0.0;
        }

        double totalSemesterGPA = 0.0;

        int semesterCount = 0;

        for (String semester :
                semesters) {

            double semesterGPA =
                    calculateRawSemesterGPA(
                            studentId,
                            semester
                    );

            if (semesterGPA > 0) {

                totalSemesterGPA +=
                        semesterGPA;

                semesterCount++;
            }
        }

        if (semesterCount == 0) {
            return 0.0;
        }

        double average =
                totalSemesterGPA /
                        semesterCount;

        return roundUp(average);
    }

    // =========================================================
    // SEMESTER GPA HISTORY
    // =========================================================

    public List<SemesterGPA> getSemesterGPAHistory(
            String studentId) {

        List<SemesterGPA> history =
                new ArrayList<>();

        for (String semester :
                getStudentSemesters(
                        studentId)) {

            double gpa =
                    calculateSemesterGPA(
                            studentId,
                            semester
                    );

            history.add(
                    new SemesterGPA(
                            semester,
                            gpa
                    )
            );
        }

        return history;
    }

    // =========================================================
    // ROUND UP TO TWO DECIMAL PLACES
    // =========================================================

    /*
     * Your requested rule:
     *
     * 3.5625 -> 3.57
     *
     * We intentionally use CEILING
     * instead of normal Math.round().
     */

    private double roundUp(double value) {

        return Math.ceil(
                value * 100.0
        ) / 100.0;
    }

    // =========================================================
    // CHECK DUPLICATE SEMESTER
    // =========================================================

    private boolean containsIgnoreCase(
            List<String> list,
            String value) {

        for (String item : list) {

            if (item.equalsIgnoreCase(value)) {

                return true;
            }
        }

        return false;
    }

    // =========================================================
    // SEMESTER GPA DATA
    // =========================================================

    public static class SemesterGPA {

        private final String semester;

        private final double gpa;

        public SemesterGPA(
                String semester,
                double gpa) {

            this.semester = semester;

            this.gpa = gpa;
        }

        public String getSemester() {

            return semester;
        }

        public double getGpa() {

            return gpa;
        }
    }
}