package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class QuotesAPI {
    public static final String PATH="https://rest-quotes-api.onrender.com/api/quotes/random";
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
}

