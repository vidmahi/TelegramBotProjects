//import org.telegram.telegrambots.ApiContextInitializer;
//import org.telegram.telegrambots.TelegramBotsApi;
//import org.telegram.telegrambots.exceptions.TelegramApiException;


import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class MainClass {
    public static void main(String[] args) {
     //   LeElephantPhotoBot userId = new LeElephantPhotoBot();


        try {
            //botsApi.registerBot(new LeElephantPhotoBot());
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
         //   telegramBotsApi.registerBot(userId);
            telegramBotsApi.registerBot(new LeElephantPhotoBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
       // System.out.println("PhotoBot successfully started!");
    }
}

