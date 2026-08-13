package com.sms.utils;

public class GradeCalculator {

    private GradeCalculator() {
    }

    public static String calculateGrade(double marks) {

        if (marks >= 80) {
            return "A+";
        } else if (marks >= 75) {
            return "A";
        } else if (marks >= 70) {
            return "A-";
        } else if (marks >= 65) {
            return "B+";
        } else if (marks >= 60) {
            return "B";
        } else if (marks >= 55) {
            return "B-";
        } else if (marks >= 50) {
            return "C+";
        } else if (marks >= 45) {
            return "C";
        } else if (marks >= 40) {
            return "D";
        } else {
            return "F";
        }

    }

    public static double calculateGPA(double marks) {

        if (marks >= 80) {
            return 4.00;
        } else if (marks >= 75) {
            return 3.75;
        } else if (marks >= 70) {
            return 3.50;
        } else if (marks >= 65) {
            return 3.25;
        } else if (marks >= 60) {
            return 3.00;
        } else if (marks >= 55) {
            return 2.75;
        } else if (marks >= 50) {
            return 2.50;
        } else if (marks >= 45) {
            return 2.25;
        } else if (marks >= 40) {
            return 2.00;
        } else {
            return 0.00;
        }

    }

}