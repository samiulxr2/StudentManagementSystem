package com.sms.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sms.model.User;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private static final String FILE_PATH = "src/main/resources/data/users.json";

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void saveUsers(List<User> users) {

        try (FileWriter writer = new FileWriter(FILE_PATH)) {

            gson.toJson(users, writer);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

   public List<User> loadUsers() {

    try (FileReader reader = new FileReader(FILE_PATH)) {

        Type type = new TypeToken<List<User>>() {}.getType();

        List<User> users = gson.fromJson(reader, type);

        if (users == null) {
            users = new ArrayList<>();
        }

        System.out.println("Users Loaded: " + users.size());

        return users;

    } catch (Exception e) {

        System.out.println("Cannot load users.json");
        e.printStackTrace();

        return new ArrayList<>();
    }

}

}