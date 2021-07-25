import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class MainClass {

    public static void main(String[] args) {

        LeElephantBot userId = new LeElephantBot();

        userId.add(new Members("Pandu", "1825052544"));
        userId.add(new Members("Chukka", "1825777884"));
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