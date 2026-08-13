package com.sms.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sms.model.Result;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ResultRepository {

    private static final String FILE_PATH =
            "src/main/resources/data/results.json";

    private final Gson gson =
            new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

    public void saveResults(List<Result> results) {

        try (FileWriter writer =
                     new FileWriter(FILE_PATH)) {

            gson.toJson(results, writer);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public List<Result> loadResults() {

        try (FileReader reader =
                     new FileReader(FILE_PATH)) {

            Type type =
                    new TypeToken<List<Result>>() {
                    }.getType();

            List<Result> results =
                    gson.fromJson(reader, type);

            if (results == null) {
                return new ArrayList<>();
            }

            return results;

        } catch (Exception e) {

            return new ArrayList<>();
        }
    }
}