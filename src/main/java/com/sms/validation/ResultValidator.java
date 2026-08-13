package com.sms.validation;

import com.sms.model.Result;
import com.sms.utils.ValidationUtil;

public class ResultValidator {

    public boolean isValid(Result result) {

        if (result == null) {
            return false;
        }

        // Student ID
        if (ValidationUtil.isEmpty(result.getStudentId())) {
            return false;
        }

        // Course Code
        if (ValidationUtil.isEmpty(result.getCourseCode())) {
            return false;
        }

        // Semester
        if (ValidationUtil.isEmpty(result.getSemester())) {
            return false;
        }

        // Attendance
        if (result.getAttendance() < 0) {
            return false;
        }

        // Quiz marks
        if (result.getQuiz1() < 0
                || result.getQuiz2() < 0
                || result.getQuiz3() < 0) {
            return false;
        }

        // Quiz average
        if (result.getQuizAverage() < 0
                || result.getQuizAverage() > 15) {
            return false;
        }

        // Midterm
        if (result.getMidterm() < 0
                || result.getMidterm() > 25) {
            return false;
        }

        // Presentation
        if (result.getPresentation() < 0
                || result.getPresentation() > 8) {
            return false;
        }

        // Assignment
        if (result.getAssignment() < 0
                || result.getAssignment() > 5) {
            return false;
        }

        // Lab class performance
        if (result.getClassPerformance() < 0
                || result.getClassPerformance() > 25) {
            return false;
        }

        // Lab report
        if (result.getLabReport() < 0
                || result.getLabReport() > 25) {
            return false;
        }

        // Final exam
        if (result.getFinalExam() < 0
                || result.getFinalExam() > 40) {
            return false;
        }

        // Total marks
        if (result.getTotalMarks() < 0
                || result.getTotalMarks() > 100) {
            return false;
        }

        // Grade
        if (ValidationUtil.isEmpty(result.getGrade())) {
            return false;
        }

        // GPA
        if (result.getGpa() < 0.0
                || result.getGpa() > 4.0) {
            return false;
        }

        return true;
    }
}