package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QuotesAPI {
    private String quoteAuthor;
    private String quoteText;

    public String getQuoteAuthor() {
        return quoteAuthor;
    }

    public void setQuoteAuthor(String quoteAuthor) {
        this.quoteAuthor = quoteAuthor;
    }

    public String getQuoteText() {
        return quoteText;
    }

    public void setQuoteText(String quoteText) {
        this.quoteText = quoteText;
    }

    @Override
    public String toString() {
        return
                "author: " + quoteAuthor + '\n' +
                "quote: " + quoteText ;
    }

    public static String getQuotes() {
        GetRequest getRequest = Unirest.get("https://rest-quotes-api.onrender.com/api/quotes/random");
        HttpResponse<String> response;
        try {
            response = getRequest.asString();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        String json = response.getBody();
        QuotesAPI quotes;
        quotes = new Gson().fromJson(json, QuotesAPI.class);
        return quotes.toString();

    }
}

