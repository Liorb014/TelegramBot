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
//        try {
//            // Create an instance of CloseableHttpClient
//            CloseableHttpClient httpClient = HttpClients.createDefault();
//
//            // Create an instance of HttpGet with the API endpoint URL
//            HttpGet httpGet = new HttpGet("https://quotes.rest/quote/random?language=en&limit=1");
//
//            // Set the API key in the request headers
//            httpGet.setHeader(HttpHeaders.AUTHORIZATION, "API_KEY " + "YCirakH9vWh2bLfxqSUZ62O4kNd8ROE3uKZAUZJEm");
//
//            // Execute the request and get the response
//            CloseableHttpResponse response = httpClient.execute(httpGet);
//
//            // Get the response code
//            int responseCode = response.getStatusLine().getStatusCode();
//
//            // Get the response body
//            String responseBody = EntityUtils.toString(response.getEntity());
//
//            // Process the response
//            System.out.println("Response Code: " + responseCode);
//            System.out.println("Response Body: " + responseBody);
//            return responseBody;
//            // Close the HttpClient
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "no";

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
        System.out.println(quotes);
        return quotes.toString();

    }
}

