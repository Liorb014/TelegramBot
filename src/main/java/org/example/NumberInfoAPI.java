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
}
