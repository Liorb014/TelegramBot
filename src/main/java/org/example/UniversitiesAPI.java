package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

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
        String uni = "";
            uni += "University Name: " + this.getName() + "\n";
            uni += "Country: " + this.getCountry() + "\n";
            uni += "website: " + this.getDomains().get(0);
            uni += "\n\n";
            return uni;
    }
}

