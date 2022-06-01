import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.io.IOException;


public class MainClass {

    public static void main(String[] args) throws IOException, InterruptedException{

        LeElephantBot userId = new LeElephantBot();

        userId.add(new Members("Pandu", "0000000000"));
        userId.add(new Members("Chukka", "0000000000"));
        System.out.println("In main method");
        System.out.println(userId);
       // userId.responseBot();


        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            //telegramBotsApi.registerBot(new LeElephantBot());
            telegramBotsApi.registerBot(userId);



    } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}