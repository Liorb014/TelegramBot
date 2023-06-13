package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NumberInfoAPI {
    private String numberInfo;


    public void setNumberInfo(String numberInfo) {
        this.numberInfo = numberInfo;
    }

    public String getNumberInfo() {
        return numberInfo;
    }

    @Override
    public String toString() {
        return "NumberInfoAPI{" +
                "numberInfo='" + numberInfo + '\'' +
                '}';
    }

    public void printNewInfo(){
        final String NUMBER_API_URL = "http://numbersapi.com/random";
        GetRequest getRequest2 = Unirest.get(NUMBER_API_URL);
        HttpResponse<String> response2 = null;
        try {
            response2 = getRequest2.asString();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String json = response2.getBody();
        NumberInfoAPI number= new NumberInfoAPI();
        number.setNumberInfo(response2.getBody());
        System.out.println(number.getNumberInfo());

    }
}
