package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JokesAPI {
  //  private boolean error;
    private String category;
    private String type;
    private String joke;
    private String setup;
    private String delivery;
    private long id;
    private boolean safe;
    private String lang;

/*    public boolean isError() {
        return error;
    }*/

/*    public void setError(boolean error) {
        this.error = error;
    }*/

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
        if (type.equals("twopart")){
            return this.setup +"\n\n" + this.delivery;
        }else return joke;
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
}
