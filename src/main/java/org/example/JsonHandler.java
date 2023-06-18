package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;

public class JsonHandler<T> {

    public T MakeOneObj(String Url, Class<T> type){
        GetRequest getRequest2 = Unirest.get(Url);
        try {
            HttpResponse<String> response2 = getRequest2.asString();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        com.fasterxml.jackson.databind.ObjectMapper objectMapper = new ObjectMapper();

        GetRequest getRequest = Unirest.get(Url);
        HttpResponse<String> response;
        try {
            response = getRequest.asString();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        String json = response.getBody();

        T newItem = null;
        try {
            newItem = objectMapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return newItem;
    }
}
