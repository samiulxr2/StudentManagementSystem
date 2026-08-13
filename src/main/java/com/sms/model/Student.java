package com.sms.model;

public class Student {

    private String studentId;
    private String fullName;
    private String department;
    private String email;
    private String phone;
    private String gender;
    private String address;

    public Student() {
    }

    public Student(String studentId,
                   String fullName,
                   String department,
                   String email,
                   String phone,
                   String gender,
                   String address) {

        this.studentId = studentId;
        this.fullName = fullName;
        this.department = department;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.address = address;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}