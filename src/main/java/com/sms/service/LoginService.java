package com.sms.service;

import com.sms.model.User;
import com.sms.repository.UserRepository;
import com.sms.session.Session;

import java.util.List;

public class LoginService {

    private final UserRepository userRepository = new UserRepository();

    public boolean authenticate(String username, String password) {

        try {

            List<User> users = userRepository.loadUsers();

            if (users != null) {

                for (User user : users) {

                    if (user.getUsername() != null
                            && user.getPassword() != null
                            && user.getUsername().trim().equals(username.trim())
                            && user.getPassword().trim().equals(password.trim())) {

                        Session.login(user);
                        return true;
                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Default Admin Login
        if (username.equals("admin") && password.equals("1234")) {

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("1234");
            admin.setRole("ADMIN");

            Session.login(admin);

            return true;
        }

        return false;
    }

}