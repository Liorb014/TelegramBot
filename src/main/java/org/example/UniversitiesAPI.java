package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UniversitiesAPI {
    private String name;
    private String country;
    private List<String> domains;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<String> getDomains() {
        return domains;
    }

    public void setDomains(List<String> domains) {
        this.domains = domains;
    }

    @Override
    public String toString() {
        return "UniversitiesAPI{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", domains=" + domains +
                '}';
    }

    public static String getUniversities(int limit , String country) {
        try {
        String apiUrl = "http://universities.hipolabs.com/search?country="+country;
        URL url = new URL(apiUrl);
        HttpURLConnection connection = null;
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
                UniversitiesAPI[] universities = objectMapper.readValue((response.toString()), UniversitiesAPI[].class);
                String uni = "";
                for (UniversitiesAPI university : Arrays.stream(universities).limit(limit).collect(Collectors.toList())) {
                    uni += "University Name: " + university.getName() + "\n";
                    uni += "Country: " + university.getCountry() + "\n";
                    uni += "website: " + university.getDomains().get(0);
                    uni += "\n\n";
                }
                return uni;
            }else return "error";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}