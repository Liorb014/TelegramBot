package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonHandler<T> {
    private final Class<T> TYPE;

    public JsonHandler(Class<T> type) {
        this.TYPE = type;
    }
//    public T MakeOneObj(String Url, Class<T> type){
//        GetRequest getRequest2 = Unirest.get(Url);
//        try {
//            HttpResponse<String> response2 = getRequest2.asString();
//        } catch (UnirestException e) {
//            throw new RuntimeException(e);
//        }
//        com.fasterxml.jackson.databind.ObjectMapper objectMapper = new ObjectMapper();
//
//        GetRequest getRequest = Unirest.get(Url);
//        HttpResponse<String> response;
//        try {
//            response = getRequest.asString();
//        } catch (UnirestException e) {
//            throw new RuntimeException(e);
//        }
//        String json = response.getBody();
//
//        T newItem = null;
//        try {
//            newItem = objectMapper.readValue(json, type);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        return newItem;
//    }
//

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
                System.out.println("aa");
             T item =objectMapper.readValue((response.toString()), this.TYPE);
                System.out.println("asa");
                return item;
            } else return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
