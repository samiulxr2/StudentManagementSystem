package com.sms.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sms.model.Registration;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RegistrationRepository {

    private static final String FILE_PATH =
            "src/main/resources/data/registrations.json";

    private final Gson gson =
            new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

    public void saveRegistrations(
            List<Registration> registrations) {

        try (FileWriter writer =
                     new FileWriter(FILE_PATH)) {

            gson.toJson(registrations, writer);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public List<Registration> loadRegistrations() {

        try (FileReader reader =
                     new FileReader(FILE_PATH)) {

            Type type =
                    new TypeToken<List<Registration>>() {
                    }.getType();

            List<Registration> registrations =
                    gson.fromJson(reader, type);

            if (registrations == null) {

                return new ArrayList<>();
            }

            return registrations;

        } catch (Exception e) {

            return new ArrayList<>();
        }
    }
}