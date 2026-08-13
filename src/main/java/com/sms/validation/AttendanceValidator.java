package com.sms.validation;

import com.sms.model.Attendance;
import com.sms.utils.ValidationUtil;

public class AttendanceValidator {

    public boolean isValid(Attendance attendance) {

        if (attendance == null) {
            return false;
        }

        if (ValidationUtil.isEmpty(attendance.getAttendanceId())) {
            return false;
        }

        if (ValidationUtil.isEmpty(attendance.getStudentId())) {
            return false;
        }

        if (ValidationUtil.isEmpty(attendance.getCourseCode())) {
            return false;
        }

        if (ValidationUtil.isEmpty(attendance.getDate())) {
            return false;
        }

        if (ValidationUtil.isEmpty(attendance.getStatus())) {
            return false;
        }

        return true;
    }
}