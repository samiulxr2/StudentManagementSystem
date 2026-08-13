package com.sms.model;

public class Course {

    private String courseCode;
    private String courseName;
    private double credit;
    private String teacherId;
    private String semester;
    private String courseType; // THEORY or LAB

    public Course() {
    }

    public Course(String courseCode,
                  String courseName,
                  double credit,
                  String teacherId,
                  String semester,
                  String courseType) {

        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credit = credit;
        this.teacherId = teacherId;
        this.semester = semester;
        this.courseType = courseType;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }
}