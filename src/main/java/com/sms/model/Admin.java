package com.sms.model;

public class Admin extends User {

    private String adminId;
    private String fullName;
    private String email;
    private String phone;

    public Admin() {
    }

    public Admin(String adminId, String fullName, String email,
                 String phone, String username,
                 String password, String role) {

        super(username, password, role);

        this.adminId = adminId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
}