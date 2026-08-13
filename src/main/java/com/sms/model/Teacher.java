package com.sms.model;

public class Teacher {

    private String teacherId;
    private String fullName;
    private String department;
    private String designation;
    private String email;
    private String phone;
    private String gender;
    private String address;

    public Teacher() {
    }

    public Teacher(String teacherId, String fullName, String department,
                   String designation, String email, String phone,
                   String gender, String address) {

        this.teacherId = teacherId;
        this.fullName = fullName;
        this.department = department;
        this.designation = designation;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.address = address;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
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