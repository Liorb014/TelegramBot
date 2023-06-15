package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Joke {
    //    public static final String Path= "https://v2.jokeapi.dev/joke/Any";
    private boolean error;
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

    public boolean isError() {
        return error;
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

    public void setError(boolean error) {
        this.error = error;
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
        final StringBuilder sb = new StringBuilder("JokesApi{");
        //   sb.append("error=").append(error);
        sb.append(", category='").append(category).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", joke='").append(joke).append('\'');
        sb.append(", setup='").append(setup).append('\'');
        sb.append(", delivery='").append(delivery).append('\'');
        sb.append(", id=").append(id);
        sb.append(", safe=").append(safe);
        sb.append(", lang='").append(lang).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public static String getJokeText() {
        GetRequest getRequest = Unirest.get("https://v2.jokeapi.dev/joke/Any");
        HttpResponse<String> response;
        try {
            response = getRequest.asString();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        String json = response.getBody();
        Joke joke;

        joke = new Gson().fromJson(json, Joke.class);
        System.out.println(joke.getFullJoke());
        return joke.getFullJoke();
    }
}

