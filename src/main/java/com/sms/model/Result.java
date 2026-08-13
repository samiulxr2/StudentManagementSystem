package com.sms.model;

public class Result {

    private String studentId;
    private String courseCode;
    private String semester;

    // =========================================================
    // THEORY ASSESSMENTS
    // =========================================================

    private double attendance;

    private double quiz1;
    private double quiz2;
    private double quiz3;

    private double quizAverage;

    private double midterm;

    private double presentation;

    private double assignment;

    // =========================================================
    // LAB ASSESSMENTS
    // =========================================================

    // Class Performance + Mini Project Presentation = 25
    private double classPerformance;

    private double labReport;

    // =========================================================
    // FINAL EXAM
    // =========================================================

    private double finalExam;

    // =========================================================
    // CALCULATED RESULT
    // =========================================================

    private double totalMarks;

    private String grade;

    private double gpa;

    // =========================================================
    // DEFAULT CONSTRUCTOR
    // =========================================================

    public Result() {
    }

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public Result(String studentId,
                  String courseCode,
                  String semester) {

        this.studentId = studentId;
        this.courseCode = courseCode;
        this.semester = semester;
    }

    // =========================================================
    // STUDENT ID
    // =========================================================

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    // =========================================================
    // COURSE CODE
    // =========================================================

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    // =========================================================
    // SEMESTER
    // =========================================================

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    // =========================================================
    // ATTENDANCE
    // =========================================================

    public double getAttendance() {
        return attendance;
    }

    public void setAttendance(double attendance) {
        this.attendance = attendance;
    }

    // =========================================================
    // QUIZ 1
    // =========================================================

    public double getQuiz1() {
        return quiz1;
    }

    public void setQuiz1(double quiz1) {
        this.quiz1 = quiz1;
    }

    // =========================================================
    // QUIZ 2
    // =========================================================

    public double getQuiz2() {
        return quiz2;
    }

    public void setQuiz2(double quiz2) {
        this.quiz2 = quiz2;
    }

    // =========================================================
    // QUIZ 3
    // =========================================================

    public double getQuiz3() {
        return quiz3;
    }

    public void setQuiz3(double quiz3) {
        this.quiz3 = quiz3;
    }

    // =========================================================
    // QUIZ AVERAGE
    // =========================================================

    public double getQuizAverage() {
        return quizAverage;
    }

    public void setQuizAverage(double quizAverage) {
        this.quizAverage = quizAverage;
    }

    // =========================================================
    // MIDTERM
    // =========================================================

    public double getMidterm() {
        return midterm;
    }

    public void setMidterm(double midterm) {
        this.midterm = midterm;
    }

    // =========================================================
    // PRESENTATION
    // =========================================================

    public double getPresentation() {
        return presentation;
    }

    public void setPresentation(double presentation) {
        this.presentation = presentation;
    }

    // =========================================================
    // ASSIGNMENT
    // =========================================================

    public double getAssignment() {
        return assignment;
    }

    public void setAssignment(double assignment) {
        this.assignment = assignment;
    }

    // =========================================================
    // LAB:
    // CLASS PERFORMANCE + MINI PROJECT PRESENTATION
    // =========================================================

    public double getClassPerformance() {
        return classPerformance;
    }

    public void setClassPerformance(double classPerformance) {
        this.classPerformance = classPerformance;
    }

    // =========================================================
    // LAB REPORT
    // =========================================================

    public double getLabReport() {
        return labReport;
    }

    public void setLabReport(double labReport) {
        this.labReport = labReport;
    }

    // =========================================================
    // FINAL EXAM
    // =========================================================

    public double getFinalExam() {
        return finalExam;
    }

    public void setFinalExam(double finalExam) {
        this.finalExam = finalExam;
    }

    // =========================================================
    // TOTAL MARKS
    // =========================================================

    public double getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(double totalMarks) {
        this.totalMarks = totalMarks;
    }

    // =========================================================
    // GRADE
    // =========================================================

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    // =========================================================
    // GPA
    // =========================================================

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
}