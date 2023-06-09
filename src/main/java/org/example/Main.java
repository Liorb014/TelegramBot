package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

public class Main extends Exception {
    public static void main(String[] args) throws UnirestException, IOException {
        new Window();
//        try {
//            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
//            botsApi.registerBot(new MessagesBot());
//        } catch (TelegramApiException e) {
//            throw new RuntimeException(e);
//        }

//        final String NUMBER_API_URL = "http://numbersapi.com/random";
//        GetRequest getRequest2 = Unirest.get(NUMBER_API_URL);
//        HttpResponse<String> response2 = getRequest2.asString();
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = response2.getBody();
//        NumberInfoAPI number= new NumberInfoAPI();
//        number.setNumberInfo(response2.getBody());
//        System.out.println(number.getNumberInfo());


    /*    Flags flags = new Flags();
       System.out.println(flags.getJoke());*/

    }
}
