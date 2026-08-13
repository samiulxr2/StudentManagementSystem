package com.sms.model;

public class Registration {

    private String registrationId;
    private String studentId;
    private String semester;

    private String batch;
    private String section;

    private String labSection;

    private String courseCode;
    private boolean retake;

    public Registration() {
    }

    public Registration(String registrationId,
                        String studentId,
                        String semester,
                        String batch,
                        String section,
                        String labSection,
                        String courseCode,
                        boolean retake) {

        this.registrationId = registrationId;
        this.studentId = studentId;
        this.semester = semester;
        this.batch = batch;
        this.section = section;
        this.labSection = labSection;
        this.courseCode = courseCode;
        this.retake = retake;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public boolean isRetake() {
        return retake;
    }

    public boolean getRetake() {
        return retake;
    }

    public void setRetake(boolean retake) {
        this.retake = retake;
    }
}