package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;

public class JsonHandler<T> {
    private final Class<T> TYPE;

    public JsonHandler(Class<T> type) {
        this.TYPE = type;
    }

    public T readJson(String path) {
        try {
            URL url = new URL(path);
            HttpURLConnection connection;
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                ObjectMapper objectMapper = new ObjectMapper();
                T item = objectMapper.readValue((response.toString()), this.TYPE);
                return item;
            } else return null;
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }
}
