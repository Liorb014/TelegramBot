package org.example;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;

import java.net.HttpURLConnection;
import java.net.URL;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QuotesAPI {
    private Contents contents;

    public Contents getContents() {
        return contents;
    }

    public void setContents(Contents contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "QuotesAPI{" +
                "contents=" + contents +
                '}';
    }

    public static String getQuotes() {
        GetRequest getRequest = Unirest.get("https://quotes.rest/quote/random?language=en&limit=1");
        HttpResponse<String> response;
        try {
            response =  getRequest.asString();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        String json = response.getStatusText();
        QuotesAPI quote;

        quote = new Gson().fromJson(json, QuotesAPI.class);
        System.out.println(quote);
        return quote.getContents().getQuote();
//        String apiUrl = "https://quotes.rest/quote/random?language=en&limit=1";
//        String apiToken = "YCirakH9vWh2bLfxqSUZ62O4kNd8ROE3uKZAUZJEm";
//
//        try {
//            // Create the URL object with the API endpoint
//            URL url = new URL(apiUrl);
//
//            // Open a connection to the URL
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//            // Set the request method (e.g., GET)
//            connection.setRequestMethod("GET");
//
//            // Set the API token in the request headers
//            connection.setRequestProperty("Authorization", "Bearer " + apiToken);
//
//            // Get the response code
//            int responseCode = connection.getResponseCode();
//
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                // Create an instance of ObjectMapper
//                ObjectMapper objectMapper = new ObjectMapper();
//
//                // Read JSON data from the API response
//                QuotesAPI quote = objectMapper.readValue(connection.getInputStream(), QuotesAPI.class);
//
//                // Access the quote data
//                Contents contents = quote.getContents();
//                System.out.println("Quote: " + contents.getQuote());
//                System.out.println("Author: " + contents.getAuthor());
//            } else {
//                System.out.println("Error occurred. Response Code: " + responseCode);
//            }
//
//
//            // Close the connection
//            connection.disconnect();
//            return connection.getContent().toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return getQuotes().toString();
    }
}

