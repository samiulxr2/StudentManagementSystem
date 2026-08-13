package com.sms.validation;

import com.sms.model.Student;
import com.sms.utils.ValidationUtil;

public class StudentValidator {

    public boolean isValid(Student student) {

        if (student == null) {
            return false;
        }

        if (ValidationUtil.isEmpty(student.getStudentId())) {
            return false;
        }

        if (ValidationUtil.isEmpty(student.getFullName())) {
            return false;
        }

        if (!ValidationUtil.isValidEmail(student.getEmail())) {
            return false;
        }

        if (!ValidationUtil.isValidPhone(student.getPhone())) {
            return false;
        }

        return true;
    }

}