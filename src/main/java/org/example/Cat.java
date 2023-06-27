package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Cat {
    public static final String PATH = "https://catfact.ninja/fact";
    private String fact;
    private int length;

    public String getFact() {
        return fact;
    }

    public int getLength() {
        return length;
    }

    public void setFact(String fact) {
        this.fact = fact;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return fact;
    }
}
