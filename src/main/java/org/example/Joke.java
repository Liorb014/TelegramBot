package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Joke {
  //  private boolean error;
    private String category;
    private String type;
    private String joke;
    private String setup;
    private String delivery;
    private long id;
    private boolean safe;
    private String lang;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }



    public void setType(String type) {
        this.type = type;
    }

    public String getSetup() {
        return setup;
    }

    public void setSetup(String setup) {
        this.setup = setup;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    public boolean isSafe() {
        return safe;
    }

    public void setSafe(boolean safe) {
        this.safe = safe;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

    public String getJoke() {
        return joke;
    }

    public String getFullJoke() {
        if (type.equals("twopart")) {
            return this.setup + "\n\n" + this.delivery;
        } else return joke;
    }

    @Override
    public String toString() {
        String sb = "JokesApi{" + ", category='" + category + '\'' +
                ", type='" + type + '\'' +
                ", joke='" + joke + '\'' +
                ", setup='" + setup + '\'' +
                ", delivery='" + delivery + '\'' +
                ", id=" + id +
                ", safe=" + safe +
                ", lang='" + lang + '\'' +
                '}';
        return sb;
    }

    public static String getJokeText(String type, String lang, String amount) {
        String path = "https://v2.jokeapi.dev/joke/" + type + "?lang=" + lang + "&amount=" + amount;
        System.out.println(path);
        GetRequest getRequest = Unirest.get("https://v2.jokeapi.dev/joke/" + type + "?lang=" + lang + "&amount=" + amount);
        HttpResponse<String> response;
        try {
            response = getRequest.asString();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        String json = response.getBody();

        System.out.println("a");
        ObjectMapper objectMapper = new ObjectMapper();
        Joke[] jokes;
        try {
            jokes= objectMapper.readValue(json, Joke[].class);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println("b");
        String s = "";
        for (Joke p : jokes
        ) {
            s += p.getFullJoke() + "\n";
        }
        System.out.println(s);
        return s;
    }

    public static  String nnn(String type, String lang, String amount) {
        try {
            String apiUrl =  "https://v2.jokeapi.dev/joke/" + type + "?lang=" + lang + "&amount=" + amount;
            URL url = new URL(apiUrl);
            HttpURLConnection connection;
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
                Jokes jokes = objectMapper.readValue((response.toString()), Jokes.class);

                StringBuilder s = new StringBuilder();
                for (Joke p : jokes.getJokes()
                ) {
                    s.append(p.getFullJoke()).append("\n\n--------------------------------------\n\n");
                }

                return s.toString();
            }else return "error";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

