package com.sms.validation;

import com.sms.model.Course;
import com.sms.utils.ValidationUtil;

public class CourseValidator {

    public boolean isValid(Course course) {

        if (course == null) {
            return false;
        }

        if (ValidationUtil.isEmpty(course.getCourseCode())) {
            return false;
        }

        if (ValidationUtil.isEmpty(course.getCourseName())) {
            return false;
        }

        if (course.getCredit() <= 0) {
            return false;
        }

        if (ValidationUtil.isEmpty(course.getTeacherId())) {
            return false;
        }

        if (ValidationUtil.isEmpty(course.getSemester())) {
            return false;
        }

        return true;
    }

}