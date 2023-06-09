package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QuotesAPI {
    private  final String QUOTES_API_URL = "https://quotes.rest/quote/random";
    public QuotesAPI () {

    }



}
