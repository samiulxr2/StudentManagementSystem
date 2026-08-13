package com.sms.validation;

import com.sms.model.Teacher;
import com.sms.utils.ValidationUtil;

public class TeacherValidator {

    public boolean isValid(Teacher teacher) {

        if (teacher == null) {
            return false;
        }

        if (ValidationUtil.isEmpty(teacher.getTeacherId())) {
            return false;
        }

        if (ValidationUtil.isEmpty(teacher.getFullName())) {
            return false;
        }

        if (!ValidationUtil.isValidEmail(teacher.getEmail())) {
            return false;
        }

        if (!ValidationUtil.isValidPhone(teacher.getPhone())) {
            return false;
        }

        return true;
    }

}