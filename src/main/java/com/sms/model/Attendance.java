package com.sms.model;

public class Attendance {

    private String attendanceId;

    private String studentId;
    private String courseCode;

    private String teacherId;

    private String semester;
    private String batch;
    private String section;

    private String labSection;

    private String date;
    private String status;

    public Attendance() {
    }

    public Attendance(String attendanceId,
                      String studentId,
                      String courseCode,
                      String teacherId,
                      String semester,
                      String batch,
                      String section,
                      String labSection,
                      String date,
                      String status) {

        this.attendanceId = attendanceId;
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.teacherId = teacherId;
        this.semester = semester;
        this.batch = batch;
        this.section = section;
        this.labSection = labSection;
        this.date = date;
        this.status = status;
    }

    public String getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(String attendanceId) {
        this.attendanceId = attendanceId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
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

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getLabSection() {
        return labSection;
    }

    public void setLabSection(String labSection) {
        this.labSection = labSection;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}