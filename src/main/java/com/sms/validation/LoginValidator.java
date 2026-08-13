package com.sms.validation;

import com.sms.utils.ValidationUtil;

public class LoginValidator {

    public boolean isValid(String username, String password) {

        if (ValidationUtil.isEmpty(username)) {
            return false;
        }

        if (ValidationUtil.isEmpty(password)) {
            return false;
        }

        return true;
    }

}