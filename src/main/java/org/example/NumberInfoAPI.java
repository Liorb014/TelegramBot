package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NumberInfoAPI {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "NumberInfoAPI{" +
                "number='" + text + '\'' +
                '}';
    }

    public static String getNumber(String type) {
        GetRequest getRequest = Unirest.get("http://numbersapi.com/random/"+type+"?json");
        HttpResponse<String> response;
        try {
            response = getRequest.asString();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        String json = response.getBody();
        NumberInfoAPI number;

        number = new Gson().fromJson(json, NumberInfoAPI.class);
        return number.getText();
    }
}
