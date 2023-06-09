package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Flags {
    final String JOKES_API_URL = "https://v2.jokeapi.dev/joke/Programming,Miscellaneous,Dark?blacklistFlags=nsfw,religious,political,racist,sexist,explicit&amount=2";
    String type;
    private boolean nsfw;
    private boolean religious;
    private boolean political;
    private boolean racist;
    private boolean sexist;
    private boolean explicit;
    private List<JokesAPI> jokesAPIList = new ArrayList<>();

    public List<String> getJoke() {
        GetRequest getRequest = Unirest.get(JOKES_API_URL);
        HttpResponse<String> response;
        try {
            response = getRequest.asString();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        String json = response.getBody();

        ObjectMapper objectMapper1 = new ObjectMapper();
        JokesAPI joke;


        try {
            for (JokesAPI countryModel1 : jokesAPIList) {
            }
        JokesAPI countryModel =objectMapper1.readValue(json, JokesAPI.class);
        }catch(JsonProcessingException e){
                throw new RuntimeException(e);
    }
     //   System.out.println(.size());
        return jokesAPIList.stream().map(jokesAPI -> jokesAPI.getFullJoke()).collect(Collectors.toList());
    }
}
