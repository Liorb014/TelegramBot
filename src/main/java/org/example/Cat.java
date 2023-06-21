package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Cat {
    public static final String PATH = "https://catfact.ninja/fact";
    private String fact;
    private int length;

    public String getFact() {
        return fact;
    }

    public int getLength() {
        return length;
    }

    public void setFact(String fact) {
        this.fact = fact;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return fact;
    }

    public String getS() {
        return fact;
    }

    public static String getCatFact() {
        GetRequest getRequest = Unirest.get(PATH);
        HttpResponse<String> response;
        try {
            response = getRequest.asString();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        String json = response.getBody();
        Cat cat;

        cat = new Gson().fromJson(json, Cat.class);
        return cat.fact;
    }
}
